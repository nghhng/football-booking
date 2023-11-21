package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dao.part.MatchingRequestStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReponseMatchRequest {
    private String matchRequestId;

    private String action;
}
