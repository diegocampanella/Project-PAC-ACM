package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.service.pianificazione.ServicePianificazione;

@RestController
public class PianificazioneWebController {
	
	@Autowired
	private ServicePianificazione servicePianificazione;
	
	

}
