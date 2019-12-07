package com.javahelps.restservice.controller;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.javahelps.restservice.entity.Reservation;
import com.javahelps.restservice.entity.User;
import com.javahelps.restservice.repository.ReservationRepository;
import com.javahelps.restservice.repository.UserRepository;
import com.javahelps.service.DateImplementation;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javassist.tools.web.BadHttpRequest;
@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

	@Autowired
	private ReservationRepository repository;
	private DateImplementation dateUtils;

	@GetMapping
	public JSONArray findAll() throws ParseException, java.text.ParseException {
		dateUtils = new DateImplementation();
		return (JSONArray) new JSONParser().parse(new Gson().toJson(repository.findCampsiteVacancy()));
	}

	@RequestMapping(value = "/vacancy")
	public JSONArray findVacancy() throws java.text.ParseException {
		dateUtils = new DateImplementation();
		Set<Object> reservations = repository.findCampsiteVacancy();
		return dateUtils.getVacancies(reservations);	
	}
}