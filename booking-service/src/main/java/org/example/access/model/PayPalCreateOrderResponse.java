package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalCreateOrderResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("links")
    private List<Link> links;

    // Constructors, getters, and setters
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Link {
        @JsonProperty("href")
        private String href;
        @JsonProperty("rel")
        private String rel;
        @JsonProperty("method")
        private String method;

        // Constructors, getters, and setters
    }
}