package nghhng.facilityservice.services;

import nghhng.facilityservice.dao.Price;
import nghhng.facilityservice.dto.CreatePriceRequest;
import nghhng.facilityservice.dto.GetPriceRequest;
import nghhng.facilityservice.exception.BaseException;
import nghhng.facilityservice.repositories.PriceRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    MongoTemplate mongoTem;


    public String createPrice(CreatePriceRequest createPriceRequest){
        Price price = Price.builder()
                .facilityId(createPriceRequest.getFacilityId())
                .fieldType(createPriceRequest.getFieldType())
                .startAt(createPriceRequest.getStartAt())
                .endAt(createPriceRequest.getEndAt())
                .amount(createPriceRequest.getAmount())
                .specialAmount(createPriceRequest.getSpecialAmount())
                .build();
        Price priceSaved = priceRepository.save(price);
        if(priceSaved==null){
            throw new BaseException("Create Price failed");
        }
        else return priceSaved.getId();
    }

    public List<Price> getPrice(GetPriceRequest getPriceRequest){
        if(getPriceRequest.getFieldType()!=null && getPriceRequest.getFieldType().equals("0")){
            List<Price> prices = priceRepository.findAll();
            return prices;
        }
//        List<Price> prices = priceRepository.findByFacilityIdAndFieldTypeAndStartAtAndEndAtAndAmountAndIsWeekend(
//                getPriceRequest.getFacilityId(),
//                getPriceRequest.getFieldType(),
//                getPriceRequest.getStartAt(),
//                getPriceRequest.getEndAt(),
//                getPriceRequest.getAmount(),
//                getPriceRequest.getIsWeekend()
//        );

        Query query = new Query();
        Criteria criteria = new Criteria();
        Field[] fields = getPriceRequest.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            try {
                Object value = field.get(getPriceRequest);
                if(value==null) {
                    continue;
                }
                if(field.getName().equals("amount")&&(int)value==0){
                    continue;
                }
                if(field.getName().equals("specialAmount")&&(int)value==0){
                    continue;
                }
                else {
                    criteria.and(field.getName()).is(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query.addCriteria(criteria);

        List<Price> prices = mongoTem.find(query, Price.class);
        return prices;
    }
}
