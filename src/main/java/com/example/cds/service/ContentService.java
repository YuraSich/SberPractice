package com.example.cds.service;

import com.example.cds.entitty.Content;
import com.example.cds.exceptions.ContentNotFoundException;

public interface ContentService {
    Content create(String guid, String data);
    Content find(String contentId) throws ContentNotFoundException;
}
