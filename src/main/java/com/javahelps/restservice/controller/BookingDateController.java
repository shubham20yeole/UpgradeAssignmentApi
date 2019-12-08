package com.javahelps.restservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.javahelps.restservice.repository.BookingDateRepository;
import com.javahelps.service.DateImplementation;

import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController
@RequestMapping(path = "/booking")
public class BookingDateController {

	@Autowired
	private BookingDateRepository bookingDateRepository;
	private DateImplementation dateUtils;

	@GetMapping
	public JSONArray findAll() throws ParseException, java.text.ParseException {
		dateUtils = new DateImplementation();
		return (JSONArray) new JSONParser().parse(new Gson().toJson(bookingDateRepository.findCampsiteVacancy()));
	}

	@GetMapping(value = "/vacancy")
	public JSONArray findVacancy() throws java.text.ParseException {
		dateUtils = new DateImplementation();
		Set<Object> reservations = bookingDateRepository.findCampsiteVacancy();
		return dateUtils.getVacancies(reservations);	
	}
}