package nghhng.facilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import nghhng.facilityservice.dao.part.Time;
import org.bson.types.ObjectId;
@Data
@Builder
@AllArgsConstructor
public class CreatePriceRequest {

    @NonNull
    private ObjectId facilityId;

    @NonNull
    private String fieldType;

    @NonNull
    private Time startAt;

    @NonNull
    private Time endAt;

    @NonNull
    private int amount;

    @NonNull
    private int specialAmount;
}
