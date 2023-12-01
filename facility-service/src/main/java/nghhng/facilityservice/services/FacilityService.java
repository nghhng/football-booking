package nghhng.facilityservice.services;


import nghhng.facilityservice.access.GetUserByIdRequest;
import nghhng.facilityservice.access.GetUserByUsernameRequest;
import nghhng.facilityservice.access.GetUserResponse;
import nghhng.facilityservice.access.UserFeignClient;
import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dao.part.Address;
import nghhng.facilityservice.dto.CreateFacilityRequest;
import nghhng.facilityservice.dto.GetFacilityByFacilityIdRequest;
import nghhng.facilityservice.dto.GetFacilityByUsernameRequest;
import nghhng.facilityservice.dto.GetFacilityRequest;
import nghhng.facilityservice.exception.BaseException;
import nghhng.facilityservice.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.ErrorCommon;

import java.util.List;


@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    MongoTemplate mongoTemplate;



    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();
    }

    public List<Facility> getFacilitiesByFilter(GetFacilityRequest getFacilityRequest){
        Query query = new Query();
        Criteria criteria = new Criteria();
        java.lang.reflect.Field[] fields = getFacilityRequest.getClass().getDeclaredFields();
        for(java.lang.reflect.Field field : fields){
            field.setAccessible(true);
            try {
                Object value = field.get(getFacilityRequest);
                if(value==null) {
                    continue;
                }
                else {
                    if(field.getName().equals("limit")){
                        query.limit(getFacilityRequest.getLimit());
                    } else if(field.getName().equals("skip")){
                        query.skip(getFacilityRequest.getSkip());
                    } else if(field.getName().equals("name")){
                        criteria.and("name").regex(".*" + value + ".*", "i");
                    } else if(field.getName().equals("ward")){
                        criteria.and("address.ward").is(value);
                    } else if(field.getName().equals("city")){
                        criteria.and("address.city").is(value);
                    } else
                    criteria.and(field.getName()).is(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query.addCriteria(criteria);

        List<Facility> facilities = mongoTemplate.find(query, Facility.class);
        return facilities;
    }

    public Facility createFacility (CreateFacilityRequest createFacilityRequest) {

        GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest(createFacilityRequest.getOwnerId());

        GetUserResponse user = userFeignClient.getUserById(getUserByIdRequest);

        if(Integer.parseInt(createFacilityRequest.getNumOfFields())!=createFacilityRequest.getFields().size()){
            throw new AppException(ErrorCommon.NUMBER_OF_FIELDS_WRONG);
        }
        if(user==null){
            throw new BaseException("User not exist");
        }
        Facility facility = Facility.builder()
                        .name(createFacilityRequest.getName())
                        .address(createFacilityRequest.getAddress())
                        .numOfFields(createFacilityRequest.getNumOfFields())
                .ownerId(user.getId())
//                .fields(createFacilityRequest.getFields())
                .build();
        facility.setFields(createFacilityRequest.getFields());

        Facility facilitySaved = facilityRepository.save(facility);
        if(facilitySaved != null){
            return facilitySaved;
        }
        else throw new BaseException("Create Facility unsuccesfully");

    }

    public Facility[] getFacilityByUsername(GetFacilityByUsernameRequest getFacilityByUsernameRequest){

        GetUserByUsernameRequest getUserByUsernameRequest = new GetUserByUsernameRequest(getFacilityByUsernameRequest.getUsername());
        GetUserResponse user = userFeignClient.getUserByUsername(getUserByUsernameRequest);
        if(user==null){
            throw new BaseException("User not exist");
        }
        Facility[] facility = facilityRepository.findByOwnerId(user.getId());
        return facility;
    }
    public Facility getFacilityByFacilityId(GetFacilityByFacilityIdRequest request){

        Facility facility = facilityRepository.findById(request.getFacilityId());
        return facility;
    }




}
