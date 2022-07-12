package com.example.cds.controllers;

import com.example.cds.entitty.*;
import com.example.cds.model.AdvertisingInfo;
import com.example.cds.dto.BrowserWantAdDTO;
import com.example.cds.dto.UserInfoDto;
import com.example.cds.exceptions.ContentNotFoundException;
import com.example.cds.exceptions.Message.ValidationError;
import com.example.cds.exceptions.Message.JsonResponse;
import com.example.cds.exceptions.UserNotFoundException;
import com.example.cds.repository.ViewedRep;
import com.example.cds.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    private final BrowserService browserService;

    private final UserService userService;

    private final MainService mainService;

    private final ContentService contentService;

    private final ViewedService viewedService;

    public MainController(BrowserService browserService, UserService userService, MainService mainService,
                          ContentService contentService, ViewedService viewedService) {
        this.browserService = browserService;
        this.userService = userService;
        this.mainService = mainService;
        this.contentService = contentService;
        this.viewedService = viewedService;
    }

    @PostMapping(value = "/getAd")
    public ResponseEntity<AdvertisingInfo> getAd(@Valid @RequestBody BrowserWantAdDTO browserWantAdDTO) {

        Users user = userService.getUserByID(browserWantAdDTO.getUserGuid());

        // Если с таким пользователем еще не велась работа, запрашиваем его данные
        if (user.getEmail() == null) {
            UserInfoDto userInfo = browserService.getUserInfo(user);
            user = userService.setUserInfo(user, userInfo);
        }

        // Получаем от стороннего сервиса AMS таргет, заполняем его от CMS контентом
        Target target = mainService.getTarget(user, browserWantAdDTO.getPage());

        // Возвращаем рекламу обратно запрашиваещему
        AdvertisingInfo advertisingInfo = mainService.getAdvertisingInfo(target);

        return ResponseEntity.ok(advertisingInfo);
    }

    @GetMapping(value = "/viewed")
    public ResponseEntity<Object> viewed(@RequestParam(name = "userid") String userId,
                                         @RequestParam(name = "contentid") String contentId) throws UserNotFoundException, ContentNotFoundException {
        Users user = userService.findUserById(userId);
        Content content = contentService.find(contentId);
        viewedService.setViewed(user, content);

        return ResponseEntity.ok(new JsonResponse("Ok"));
    }



    /**
     * Перехватываем ошибки валидации @valid
     *
     * @param exception
     * @param request
     * @return Вовращаем объект ValidationError с перечислением ошибок валидации
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationError> handleMethodArgNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST, request.getServletPath());
        BindingResult bindingResult = exception.getBindingResult();

        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    /**
     * Перехватываем исключения в методах контроллера
     *
     * @param req
     * @param ex
     * @return ответ в формате json с указанной ошибкой
     */
    @ExceptionHandler({ContentNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<JsonResponse> handleError2(HttpServletRequest req, Exception ex) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
