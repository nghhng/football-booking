package org.example.dao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dao.part.MatchingRequestStatus;
import org.example.dao.part.Time;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "matchingRequest")
public class MatchingRequest {
    @Id
    private String id;

    private String bookingId;

    private String facilityName;

    private Time startAt;

    private Time endAt;

    private String price;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private String date;

    private String requestorId;

    private String requestorName;

    private String requestorImage;

    private String hostUserId;

    private String hostUserName;

    private String hostUserImage;

    private MatchingRequestStatus status;
}
