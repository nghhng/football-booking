package nghhng.facilityservice.services;


import com.fasterxml.jackson.databind.ser.Serializers;
import nghhng.facilityservice.access.GetUserResponse;
import nghhng.facilityservice.access.UserFeignClient;
import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dto.CreateFacilityRequest;
import nghhng.facilityservice.dto.GetFacilityByUsernameRequest;
import nghhng.facilityservice.exception.BaseException;
import nghhng.facilityservice.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    private UserFeignClient userFeignClient;
    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();
    }

    public Facility createFacility (CreateFacilityRequest createFacilityRequest) {

        GetUserResponse user = userFeignClient.getUserByUsername(createFacilityRequest.getUsername());
        if(user==null){
            throw new BaseException("User not exist");
        }
        Facility facility = Facility.builder()
                        .name(createFacilityRequest.getName())
                        .address(createFacilityRequest.getAddress())
                        .numOfFields(createFacilityRequest.getNumOfFields())
                .ownerId(createFacilityRequest.getOwnerId())
                .build();

        Facility facilitySaved = facilityRepository.save(facility);
        if(facilitySaved != null){
//            return getfacilityByfacilityname(facilitySaved.getfacilityname());
            return null;
        }
        else throw new BaseException("Create Facility unsuccesfully");

    }

    public Facility getfacilityByUsername(GetFacilityByUsernameRequest getFacilityByUsernameRequest){
        GetUserResponse user = userFeignClient.getUserByUsername(getFacilityByUsernameRequest.getUsername());
        if(user==null){
            throw new BaseException("User not exist");
        }
        Facility facility = facilityRepository.findByOwnerId(user.getId());
        return facility;
    }


}
