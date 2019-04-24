package com.galaticos.randomteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.galaticos.randomteam.service.RandomTeamService;

@RestController
public class RandomTeamController {
	
	@Autowired
	RandomTeamService randomTeamService;
	
	@RequestMapping(path="/sortTeams", method=RequestMethod.POST)
    public @ResponseBody String sortTeams(@RequestParam("input") String input){
        return randomTeamService.sortTeams(input);
    }

}
