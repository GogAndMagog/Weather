package org.fizz_buzz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
public class MainPageController {

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("greetings");
    }

}
