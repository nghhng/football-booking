package nghhng.facilityservice.services;


import nghhng.facilityservice.access.GetUserByUsernameRequest;
import nghhng.facilityservice.access.GetUserResponse;
import nghhng.facilityservice.access.UserFeignClient;
import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dto.CreateFacilityRequest;
import nghhng.facilityservice.dto.GetFacilityByUsernameRequest;
import nghhng.facilityservice.exception.BaseException;
import nghhng.facilityservice.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private UserFeignClient userFeignClient;

    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();
    }

    public Facility createFacility (CreateFacilityRequest createFacilityRequest) {

        GetUserByUsernameRequest getUserByUsernameRequest = new GetUserByUsernameRequest(createFacilityRequest.getUsername());

        GetUserResponse user = userFeignClient.getUserByUsername(getUserByUsernameRequest);
        if(user==null){
            throw new BaseException("User not exist");
        }
        Facility facility = Facility.builder()
                        .name(createFacilityRequest.getName())
                        .address(createFacilityRequest.getAddress())
                        .numOfFields(createFacilityRequest.getNumOfFields())
                .ownerId(user.get_id())
                .build();

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
        Facility[] facility = facilityRepository.findByOwnerId(user.get_id());
        return facility;
    }


}
