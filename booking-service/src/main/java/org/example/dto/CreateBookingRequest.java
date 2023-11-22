package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.example.dao.part.Time;

@Data
@Builder
@AllArgsConstructor
public class CreateBookingRequest {

    @Schema(example = "655c2dd3d8d3a53c8dd7131e")
    @NonNull
    private String facilityId;

    @Schema(example = "1")
    @NonNull
    private String fieldIndex;

    @NonNull
    private Time startAt;

    @NonNull
    private Time endAt;

    @Schema(example = "2023-11-16")
    @NonNull
    private String date;



}
