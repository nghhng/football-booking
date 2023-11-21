package org.example.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwitchStatusRequest {
    @JsonSerialize(using = ToStringSerializer.class)

    private String bookingId;

}
