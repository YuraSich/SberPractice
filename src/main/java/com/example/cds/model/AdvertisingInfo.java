package com.example.cds.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdvertisingInfo {

    @Data
    public static class Offer{
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        public static class Content{
            private String contentId;
            private String data;
            private String loadDate;
            private Integer priority;
        }

        private Content content;

        public Offer(Content content) {
            this.content = content;
        }
    }

    public AdvertisingInfo() {
        this.offers = new ArrayList<>();
    }

    private List<Offer> offers;
}
