package org.fizz_buzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dummy")
public class TestController {

    public TestController() {
    }

    @GetMapping
    public ModelAndView index() {
      return new ModelAndView("dummy");
    }
}
