package org.fizz_buzz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.test.TestBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dummy")
public class TestController {

//    private final TestBean testBean;
//
//    private final UserRepository userRepository;

//    public TestController(TestBean tb, UserRepository userRepository) {
//        this.testBean = tb;
//        this.userRepository = userRepository;
//    }


    public TestController() {
    }

    @GetMapping
    public ModelAndView index() {
      return new ModelAndView("dummy");
    }

//    @GetMapping
//    public ResponseEntity<String> doGet() throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//
//        var users = userRepository.findAll();
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(mapper.writeValueAsString(users));
//    }
//
//    @Override
//    public String toString() {
//        return "TestController";
//    }
//
//    public TestBean getTestBean() {
//        return testBean;
//    }
}
