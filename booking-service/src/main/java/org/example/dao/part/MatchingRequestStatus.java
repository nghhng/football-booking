package org.example.dao.part;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MatchingRequestStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    DENIED("DENIED");
    public final String value;


}
