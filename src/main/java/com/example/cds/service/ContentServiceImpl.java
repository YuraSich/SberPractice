package com.example.cds.service;

import com.example.cds.entitty.Content;
import com.example.cds.exceptions.ContentNotFoundException;
import com.example.cds.repository.ContentRep;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentServiceImpl  implements ContentService{

    private final ContentRep contentRep;

    public ContentServiceImpl(ContentRep contentRep) {
        this.contentRep = contentRep;
    }

    public Content create(String guid, String data) {
        Content content = new Content();
        content.setGuid(guid);
        content.setData(data);
        content = contentRep.save(content);
        return content;
    }

    public Content find(String contentId) throws ContentNotFoundException {
        Optional<Content> content = contentRep.findById(contentId);
        return content.orElseThrow(
                () -> new ContentNotFoundException("content with id = '" + contentId + "' not found")
        );
    }
}
