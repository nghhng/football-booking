package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.dao.Booking;
import org.example.dao.part.Time;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class BulkBooking extends Booking {
    private int numOfWeeks;

    private List<String> bookedDays;

    private String oneMatchPrice;
}
