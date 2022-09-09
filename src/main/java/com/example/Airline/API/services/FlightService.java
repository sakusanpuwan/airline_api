package com.example.Airline.API.services;

import com.example.Airline.API.models.Flight;
import com.example.Airline.API.models.Passenger;
import com.example.Airline.API.repositories.FlightRepository;
import com.example.Airline.API.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    public List<Flight> getAllFlights(){
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long id){
        return flightRepository.findById(id);
    }

    public Flight saveFlight(Flight flight){
        flightRepository.save(flight);
        return flight;
    }

    public Flight bookPassengerToFlight(Long passengerId,Long flightId){
        Flight targetFlight = flightRepository.findById(flightId).get();
        targetFlight.addPassenger(passengerRepository.findById(passengerId).get());
        return targetFlight;
    }

    public void cancelFlight(Long flightId){
        flightRepository.deleteById(flightId);
    }

    public List<Flight> getAllFlightsByDestination(String destination){
        return flightRepository.findByDestination(destination);
    }

    public boolean isFlightFull(Long flightId){
        Flight flight = flightRepository.findById(flightId).get();
        if (flight.getPassengers().size()> 5){
            return true;
        }
        return false;
    }
}
