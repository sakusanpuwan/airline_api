package com.example.Airline.API.controllers;

import antlr.FileLineFormatter;
import com.example.Airline.API.models.Flight;
import com.example.Airline.API.repositories.FlightRepository;
import com.example.Airline.API.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> addNewFlight(@RequestBody Flight flight){
        Flight newFlight = flightService.saveFlight(flight);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights(@RequestParam Optional<String> destination){
        List<Flight> flights = null;
        if (destination.isPresent()){
            flights = flightService.getAllFlightsByDestination(destination.get());
        } else {
            flights = flightService.getAllFlights();
        }

        return new ResponseEntity<>(flights,HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id){
        Optional<Flight> flight = flightService.getFlightById(id);
        if (!flight.isEmpty()){
            return new ResponseEntity<>(flight.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping(value = "/booking")
//    public ResponseEntity<Flight> bookPassengerToFlight(
//            @RequestParam Long passengerId,
//            @RequestParam Long flightId){
//        Flight flight = flightService.bookPassengerToFlight(passengerId,flightId);
//        flightService.saveFlight(flight);
//        return new ResponseEntity<>(flightService.getFlightById(flightId).get(),HttpStatus.CREATED);
//    }

    @PostMapping(value = "/booking")
    public ResponseEntity<Flight> bookPassengerToFlight(
            @RequestParam Long passengerId,
            @RequestParam Long flightId){
        if (flightService.isFlightFull(flightId)){
            return new ResponseEntity<>(null,HttpStatus.I_AM_A_TEAPOT);
        }else {
            Flight flight = flightService.bookPassengerToFlight(passengerId,flightId);
            flightService.saveFlight(flight);
            return new ResponseEntity<>(flightService.getFlightById(flightId).get(),HttpStatus.CREATED);
        }

    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<String> cancelFlight(@RequestParam Long flightId){
        flightService.cancelFlight(flightId);
        String message = String.format("Flight %s has been cancelled",flightId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }


}
