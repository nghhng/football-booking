package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPalCaptureOrderResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
}
