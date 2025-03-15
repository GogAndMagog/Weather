package org.fizz_buzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    public TestController() {
    }

    @GetMapping("/dummy")
    public ModelAndView index() {
      return new ModelAndView("dummy");
    }
}
