package com.cognizant.ai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cognizant.ai.services.OpenAIService;

@Controller
public class TravelGuidanceController {

    @Autowired
    private OpenAIService openAiService;

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
        
        String response = openAiService.getTravelGuidance(place, month, language, budget);
        
        model.addAttribute("place", place);
        model.addAttribute("response", response);
        
        return "travelGuide";
        
    }
}