package nghhng.facilityservice.controllers;

import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dao.Price;
import nghhng.facilityservice.dto.CreateFacilityRequest;
import nghhng.facilityservice.dto.CreatePriceRequest;
import nghhng.facilityservice.dto.GetFacilityByUsernameRequest;
import nghhng.facilityservice.dto.GetPriceByFacilityRequest;
import nghhng.facilityservice.services.FacilityService;
import nghhng.facilityservice.services.PriceService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/price")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @PostMapping("getByFacility")
    public ResponseEntity<Price[]> getPriceByFacility(@RequestBody GetPriceByFacilityRequest getPriceByFacilityRequest){
        return new ResponseEntity<Price[]>(priceService.getPriceByFacility(getPriceByFacilityRequest), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ObjectId> createFacility(@RequestBody CreatePriceRequest createPriceRequest){
        return new ResponseEntity<ObjectId>(priceService.createPrice(createPriceRequest), HttpStatus.OK);
    }
}
