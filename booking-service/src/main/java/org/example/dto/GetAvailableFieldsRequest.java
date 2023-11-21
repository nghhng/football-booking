package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dao.part.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAvailableFieldsRequest {
    private String facilityId;
    private Time startAt;
    private Time endAt;

    private String date;
}
