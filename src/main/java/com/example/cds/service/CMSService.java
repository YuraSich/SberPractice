package com.example.cds.service;

import com.example.cds.dto.ContentDTO;

public interface CMSService {
    ContentDTO getContent(String contentGuid);
}
