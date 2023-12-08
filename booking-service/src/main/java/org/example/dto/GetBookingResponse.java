package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.dao.Booking;
import org.example.dao.part.Field;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetBookingResponse {
    private List<Booking> data;

    private Integer total;
}
