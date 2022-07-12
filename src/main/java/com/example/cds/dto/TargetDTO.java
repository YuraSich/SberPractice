package com.example.cds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TargetDTO {
    @Data
    public static class Targets {
        private String userGuid;
        private List<Offers> offers = new ArrayList<>();

        @Data
        @AllArgsConstructor
        public static class Offers {
            private String contentGuid;
            private Integer priority;
        }
    }

    private String page;
    private String startDate;
    private String endDate;

    private List<Targets> target = new ArrayList<>();

}
