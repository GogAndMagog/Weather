package org.fizz_buzz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fizz_buzz.test.TestBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class TestController {

    private final TestBean testBean;

    public TestController(TestBean tb) {
        this.testBean = tb;
    }

    @GetMapping
    public ResponseEntity<String> doGet() throws JsonProcessingException {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(mapper.writeValueAsString(testBean));
    }

    @Override
    public String toString() {
        return "TestController";
    }

    public TestBean getTestBean() {
        return testBean;
    }
}
