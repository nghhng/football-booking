package org.example.access.model;

import java.util.*;

public class PayPalPartnerReferralRequest {
    private String trackingId;
    private List<Map<String, Object>> operations;
    private List<String> products;
    private List<Map<String, Object>> legalConsents;

    public PayPalPartnerReferralRequest() {
        this.trackingId = generateTrackingId();
        this.operations = Collections.singletonList(createOperation());
        this.products = Collections.singletonList("EXPRESS_CHECKOUT");
        this.legalConsents = Collections.singletonList(createLegalConsent());
    }

    private String generateTrackingId() {
        return UUID.randomUUID().toString();
    }

    private Map<String, Object> createOperation() {
        Map<String, Object> operation = new HashMap<>();
        operation.put("operation", "API_INTEGRATION");

        Map<String, Object> apiIntegrationPreference = new HashMap<>();

        Map<String, Object> thirdPartyDetails = new HashMap<>();
        thirdPartyDetails.put("features", Arrays.asList("PAYMENT"));

        Map<String, Object> restApiIntegration = new HashMap<>();
        restApiIntegration.put("integration_method", "PAYPAL");
        restApiIntegration.put("integration_type", "THIRD_PARTY");
        restApiIntegration.put("third_party_details", thirdPartyDetails);

        apiIntegrationPreference.put("rest_api_integration", restApiIntegration);
        operation.put("api_integration_preference", apiIntegrationPreference);

        return operation;
    }

    private Map<String, Object> createLegalConsent() {
        Map<String, Object> legalConsent = new HashMap<>();
        legalConsent.put("type", "SHARE_DATA_CONSENT");
        legalConsent.put("granted", true);
        return legalConsent;
    }

    // Getters and setters

    public String getTrackingId() {
        return trackingId;
    }

    public List<Map<String, Object>> getOperations() {
        return operations;
    }

    public List<String> getProducts() {
        return products;
    }

    public List<Map<String, Object>> getLegalConsents() {
        return legalConsents;
    }
}
