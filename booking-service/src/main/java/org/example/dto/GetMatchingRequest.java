package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.dao.part.MatchingRequestStatus;
@Data
@Builder
@AllArgsConstructor
public class GetMatchingRequest {
    private String id;

    private String bookingId;

    private String requestorId;

    private String hostUserId;

    private MatchingRequestStatus status;

    private Integer limit;
    private Integer skip;
}
