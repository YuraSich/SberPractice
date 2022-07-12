package com.example.cds.service;

import com.example.cds.dto.ViewedListDto;
import com.example.cds.dto.ViwedDto;
import com.example.cds.entitty.*;
import com.example.cds.model.AdvertisingInfo;
import com.example.cds.dto.ContentDTO;
import com.example.cds.dto.TargetDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// главный сервис, будет получать таргет и наполнять его контентом обращаясб к сторонним сервисам
@Service
@Log4j2
public class MainServiceImpl implements MainService {

    @Value("${ams.address}")
    private String amsAddress;

    @Value("${ams.scheme}")
    private String amsScheme;

    @Value("${ams.viewedPath}")
    private String amsViewedPath;

    private final AMSService amsService;

    private final CMSService cmsService;

    private final ContentService contentService;

    private final TargetService targetService;

    private final ViewedService viewedService;

    public MainServiceImpl(AMSService amsService, CMSService cmsService, ContentService contentService, TargetService targetService, ViewedService viewedService) {
        this.amsService = amsService;
        this.cmsService = cmsService;
        this.contentService = contentService;
        this.targetService = targetService;
        this.viewedService = viewedService;
    }

    public Target getTarget(Users user, String page) {
        // Запросить у AMS targetDTO
        TargetDTO targetDTO = amsService.getTarget(user, page);
        Target target = new Target();
        target.setUserGuid(user);

        // обрабатываем ДТО и выбираем одно из предложений
        String startDateStr = targetDTO.getStartDate().substring(0, 2) + "-" +
                targetDTO.getStartDate().substring(2, 4) + "-" +
                targetDTO.getStartDate().substring(4, 8);
        String endDateStr = targetDTO.getEndDate().substring(0, 2) + "-" +
                targetDTO.getEndDate().substring(2, 4) + "-" +
                targetDTO.getEndDate().substring(4, 8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        target.setStartDate(LocalDate.parse(startDateStr, formatter));
        target.setEndDate(LocalDate.parse(endDateStr, formatter));

        // ВЫБИРАЕМ СЛУЧАЙНЫМ ОБРАЗОМ ИЗ ТОГО, ЧТО ВЕРНУЛ АМС
        TargetDTO.Targets targetTmp = targetDTO.getTarget().get(new Random().nextInt(targetDTO.getTarget().size()));
        TargetDTO.Targets.Offers offerTmp = targetTmp.getOffers().get(new Random().nextInt(targetTmp.getOffers().size()));

        String contentGuid = offerTmp.getContentGuid();
        target.setPriority(offerTmp.getPriority());

        // Далее нужно будет обратиться к CmS сервису, для получения контента
        ContentDTO contentDto = cmsService.getContent(contentGuid);
        Content content = contentService.create(contentDto.getGuid(), contentDto.getData());
        target.setContentGuid(content);

        // сохранить таргет в бд
        target = targetService.save(target);
        return target;
    }


    public AdvertisingInfo getAdvertisingInfo(Target target) {
        AdvertisingInfo advertisingInfo = new AdvertisingInfo();

        advertisingInfo.getOffers().add(
                new AdvertisingInfo.Offer(
                        new AdvertisingInfo.Offer.Content(
                                target.getContentGuid().getGuid(),
                                target.getContentGuid().getData(),
                                target.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replace("-", ""),
                                target.getPriority()
                        )
                ));

        return advertisingInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendViewedToAms() throws Exception {
        // Получаем просмотры, которые мы еще не отправили
        List<Viewed> unSended = viewedService.getUnSended();

        if(unSended.size() == 0)
            return;

        // Кастим в объект для отправки
        ViewedListDto viewedListDto = new ViewedListDto(
                unSended.stream()
                        .map(
                                v -> new ViwedDto(v.getUser().getGuid(), v.getContent().getGuid())
                        ).collect(Collectors.toList())
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String JSON = "null";
        try {
            JSON = objectMapper.writeValueAsString(viewedListDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // TODO log
            throw new Exception();
        }


        // Тут имитация отправки в AMS
        String  url = new URIBuilder()
                .setScheme(this.amsScheme)
                .setHost(this.amsAddress)
                .setPath(this.amsViewedPath)
                .build()
                .toString();


        // таймаут ожидания ответа
        int timeout = 5;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();

        HttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .build();

        HttpPost httpPost = new HttpPost(url);

        HttpEntity stringEntity = new StringEntity(JSON, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);

        /// выполнение запроса
        HttpResponse response = client.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode != HttpStatus.SC_OK)
            throw new Exception();


        // Помечаем просмотры в бд отправленными
        unSended.forEach(viewed -> viewedService.sended(viewed));
    }
}
