package nghhng.facilityservice.services;

import nghhng.facilityservice.dao.Price;
import nghhng.facilityservice.dto.CreatePriceRequest;
import nghhng.facilityservice.dto.GetPriceByFacilityRequest;
import nghhng.facilityservice.exception.BaseException;
import nghhng.facilityservice.repositories.PriceRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;


    public ObjectId createPrice(CreatePriceRequest createPriceRequest){
        Price price = Price.builder()
                .facilityId(createPriceRequest.getFacilityId())
                .fieldType(createPriceRequest.getFieldType())
                .startAt(createPriceRequest.getStartAt())
                .endAt(createPriceRequest.getEndAt())
                .amount(createPriceRequest.getAmount())
                .isWeekend(createPriceRequest.getIsWeekend())
                .build();
        Price priceSaved = priceRepository.save(price);
        if(priceSaved==null){
            throw new BaseException("Create Price failed");
        }
        else return priceSaved.get_id();
    }

    public Price[] getPriceByFacility(GetPriceByFacilityRequest request){
        Price[] prices = priceRepository.getPriceByFacilityId(request.getFacilityId());
        return prices;
    }
}
