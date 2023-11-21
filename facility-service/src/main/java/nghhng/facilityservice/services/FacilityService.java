package nghhng.facilityservice.services;


import nghhng.facilityservice.access.GetUserByIdRequest;
import nghhng.facilityservice.access.GetUserByUsernameRequest;
import nghhng.facilityservice.access.GetUserResponse;
import nghhng.facilityservice.access.UserFeignClient;
import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dto.CreateFacilityRequest;
import nghhng.facilityservice.dto.GetFacilityByFacilityIdRequest;
import nghhng.facilityservice.dto.GetFacilityByUsernameRequest;
import nghhng.facilityservice.exception.BaseException;
import nghhng.facilityservice.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
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



    public List<Facility> getAllFacilities(){
        return facilityRepository.findAll();
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
