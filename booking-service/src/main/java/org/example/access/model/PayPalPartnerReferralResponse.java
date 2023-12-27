package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayPalPartnerReferralResponse {
    public static class Link {
        @JsonProperty("href")
        private String href;
        @JsonProperty("rel")
        private String rel;
        @JsonProperty("method")
        private String method;
        @JsonProperty("description")
        private String description;
    }

    @JsonProperty("links")
    private List<Link> links;
}