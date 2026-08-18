package com.capgemini.ai.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capgemini.ai.services.TravelAssistantServices;

@Controller
@RequestMapping(path = "/api")
public class TravelAssistanceRestController {
	private TravelAssistantServices services;

	public TravelAssistanceRestController(TravelAssistantServices services) {
		super();
		this.services = services;
	}

    @GetMapping("/showTravelGuide")
    public String showTravelGuide() {
        return "travelGuide";
    }
    
    @PostMapping("/showTravelGuide")
    public String getTravelGuideResponse(@RequestParam("place") String place, 
                                        @RequestParam("month") String month, 
                                        @RequestParam("language") String language, 
                                        @RequestParam("budget")String budget,
                                        Model model) {
    	
    	String response = services.getTravelAssistance(place, month, language, budget);
    	
        model.addAttribute("place", place);
        model.addAttribute("response", response);
        
        return "travelGuide";
    }

}
