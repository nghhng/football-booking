package nghhng.facilityservice.controllers;


import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dto.CreateFacilityRequest;
import nghhng.facilityservice.dto.GetFacilityByUsernameRequest;
import nghhng.facilityservice.services.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;
    @GetMapping
    public ResponseEntity<List<Facility>> getAllFacilities(){
        return new ResponseEntity<List<Facility>>(facilityService.getAllFacilities(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Facility> createFacility(@RequestBody CreateFacilityRequest createFacilityRequest){
        return new ResponseEntity<Facility>(facilityService.createFacility(createFacilityRequest), HttpStatus.OK);
    }

//    @PostMapping("getByUsername")
//    public ResponseEntity<Facility> getFacilityByUsername(@RequestBody GetFacilityByUsernameRequest getFacilityByUsernameRequest){
//        return new ResponseEntity<Facility>(facilityService.get(getFacilityByUsernameRequest.getUsername()), HttpStatus.OK);
//    }
}
