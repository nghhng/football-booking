package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.access.Time;
import org.example.dao.part.BookField;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAvailableFieldsRequest {
    private ObjectId facilityId;
    private Time startAt;
    private Time endAt;

    private String date;
}
