package nghhng.facilityservice.controllers;

import nghhng.facilityservice.dao.Facility;
import nghhng.facilityservice.dao.Price;
import nghhng.facilityservice.dto.*;
import nghhng.facilityservice.services.FacilityService;
import nghhng.facilityservice.services.PriceService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("price")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @PostMapping("createPrice")
    public ResponseEntity<String> createFacility(@RequestBody CreatePriceRequest createPriceRequest){
        return new ResponseEntity<>(priceService.createPrice(createPriceRequest), HttpStatus.OK);
    }

    @PostMapping("getPrice")
    public ResponseEntity<List<Price>> getPrice(@RequestBody GetPriceRequest getPriceRequest){
        return new ResponseEntity<List<Price>>(priceService.getPrice(getPriceRequest), HttpStatus.OK);
    }


}
