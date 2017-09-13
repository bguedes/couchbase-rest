package com.rest.couchbase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.couchbase.model.IValue;
import com.rest.couchbase.model.Error;
import com.rest.couchbase.service.BeerService;

@RestController
@RequestMapping("/api/beers")
public class BeerControllerImpl {

    private final BeerService beerService;

    @Autowired
    public BeerControllerImpl(BeerService beerService) {
        this.beerService = beerService;
    }
    
    @RequestMapping(value="/state/{state}/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends IValue> findBeersByState(@PathVariable("state") String state) {
        try {
            return ResponseEntity.ok(beerService.findBeersByState(state));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }    
}
