package com.example.Airline.API.components;

import com.example.Airline.API.models.Flight;
import com.example.Airline.API.models.Passenger;
import com.example.Airline.API.repositories.FlightRepository;
import com.example.Airline.API.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Passenger passenger1 = new Passenger("DB Cooper","DB@gmail.com");
        Passenger passenger2 = new Passenger("Amelia Earhart","am@yahoo.com");
        Passenger passenger3 = new Passenger("Wright bro1","wright1@gmail.com");
        Passenger passenger4 = new Passenger("Wright bro2","wright2@gmail.com");

        passengerRepository.save(passenger1);
        passengerRepository.save(passenger2);
        passengerRepository.save(passenger3);
        passengerRepository.save(passenger4);

        Flight flight1 = new Flight("Madrid",250,"20/10/22","15:00");
        Flight flight2 = new Flight("London",100,"04/09/22","18:35");
        Flight flight3 = new Flight("Rome",450,"03/11/22","15:00");
        Flight flight4 = new Flight("Paris",320,"24/01/23","15:00");



        flight1.addPassenger(passengerRepository.findById(1L).get());
        flight1.addPassenger(passengerRepository.findById(2L).get());
        flight2.addPassenger(passengerRepository.findById(3L).get());
        flight3.addPassenger(passengerRepository.findById(4L).get());

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);
        flightRepository.save(flight4);


    }


}
