package com.baithak.assignment.controller;

import com.baithak.assignment.dto.PlaceDetailDto;
import com.baithak.assignment.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlacesUtilController {

    private final PlaceService placeService;

    // all places
    @GetMapping("/v1/all")
    public ResponseEntity<List<PlaceDetailDto>> getAllPlaces() {
        log.info("Received request to fetch all places");
        List<PlaceDetailDto> places = placeService.findAll();
        log.debug("Returning {} places", places.size());
        return ResponseEntity.ok(places);
    }

    // male places
    @GetMapping("/v1/all/male")
    public ResponseEntity<List<PlaceDetailDto>> getAllMalePlaces() {
        log.info("Received request to fetch all male places");
        List<PlaceDetailDto> malePlaces = placeService.findAllMalePlaces();
        log.debug("Returning {} male places", malePlaces.size());
        return ResponseEntity.ok(malePlaces);
    }

    // female places
    @GetMapping("/v1/all/female")
    public ResponseEntity<List<PlaceDetailDto>> getAllFemalePlaces() {
        log.info("Received request to fetch all female places");
        List<PlaceDetailDto> femalePlaces = placeService.findAllFemalePlaces();
        log.debug("Returning {} female places", femalePlaces.size());
        return ResponseEntity.ok(femalePlaces);
    }
}
