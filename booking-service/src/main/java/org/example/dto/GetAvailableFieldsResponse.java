package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.dao.Facility;
import org.example.dao.part.Field;

@AllArgsConstructor
@Getter
@Setter
public class GetAvailableFieldsResponse{
    private Field field;

    private Integer amount;
}
