package com.galaticos.randomteam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller
 * 
 * @author <a href="mailto:willian.ribeiro@gmail.com">Willian Ribeiro</a>
 * 
 */

@Controller
public class IndexController {

	@RequestMapping(path="/", method=RequestMethod.GET)
    public String indexMove() {
		System.out.println("index.html");
        return "index.html";
    }

}
