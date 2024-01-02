package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayPalCreateOrderRequest {
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchase_units;
    @JsonProperty("application_context")
    private ApplicationContext application_context;

    // Constructors, getters, and setters

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class PurchaseUnit {
        @JsonProperty("items")
        private List<Item> items;
        @JsonProperty("amount")
        private Amount amount;

        // Constructors, getters, and setters
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("quantity")
        private String quantity;
        @JsonProperty("unit_amount")
        private UnitAmount unit_amount;

        // Constructors, getters, and setters
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitAmount {
        @JsonProperty("currency_code")
        private String currency_code;
        @JsonProperty("value")
        private String value;

        // Constructors, getters, and setters
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Amount {
        @JsonProperty("currency_code")
        private String currency_code;
        @JsonProperty("value")
        private String value;
        @JsonProperty("breakdown")
        private Breakdown breakdown;

        // Constructors, getters, and setters
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Breakdown {

        @JsonProperty("item_total")
        private UnitAmount item_total;

        // Constructors, getters, and setters
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApplicationContext {
        @JsonProperty("return_url")
        private String return_url;

        // Constructors, getters, and setters
    }
}
