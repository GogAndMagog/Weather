package org.fizz_buzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    public TestController() {
    }

    public String index() {
//        model.addAttribute("name", "FizzBuzz");

        return "registration";
//      return new ModelAndView("dummy");
    }
}
