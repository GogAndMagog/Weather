package org.fizz_buzz.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.fizz_buzz.dto.LocationOpenWeatherDTO;
import org.fizz_buzz.dto.LocationRequestDTO;
import org.fizz_buzz.dto.WeatherViewDTO;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.service.SessionService;
import org.fizz_buzz.service.UserService;
import org.fizz_buzz.service.WeatherService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@PropertySource("classpath:application.properties")
public class WeatherController {

    private final WeatherService weatherService;
    private final UserService userService;
    private final SessionService sessionService;


    public WeatherController(WeatherService weatherService,
                             UserService userService,
                             SessionService sessionService) {
        this.weatherService = weatherService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/weather")
    public String getWeather(Model model,
                             HttpServletRequest request,
                             @CookieValue(name = "SessionId", required = false) UUID sessionId) {

        Session session;
        User user;

        try {
            session = sessionService.getSession(sessionId);
            user = session.getUser();
        } catch (Exception e) {
            return "redirect:authenticate";
        }

        var locations = user.getLocations();

        List<WeatherViewDTO> weatherDTOs = new ArrayList<>();

        locations.forEach(location -> {
            try {
                weatherDTOs.add(new WeatherViewDTO(location.getId(), location.getName(), weatherService.getWeather(location.getLongitude(), location.getLatitude())));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        model.addAttribute("weatherCards", weatherDTOs);

        return "weather";
    }

    @GetMapping("/locations")
    public String findLocations(@ModelAttribute("locationName") @NotBlank(message = "Location name must not be blank")
                                String locationName,
                                Model model) throws IOException, InterruptedException {

        var locations = weatherService.getLocations(locationName.trim().replace(' ', '_'));

        model.addAttribute("locationCards", locations);

        return "locations";
    }

    @PostMapping("/locations")
    public RedirectView addLocation(LocationRequestDTO location,
                                    Model model,
                                    HttpServletRequest request) {

        Session session;
        try {
            session = sessionService.getSession(request);
        } catch (RuntimeException | NoSuchFieldException e) {
            return new RedirectView("registration");
        }

        var locationName = (String) model.getAttribute("locationName");

        userService.addLocation(session.getUser(), location.name(), location.latitude(), location.longitude());

        return new RedirectView("weather");
    }

    @DeleteMapping("/locations")
    public ResponseEntity<String> deleteLocation(@RequestParam("locationId") Long locationId,
                                                 HttpServletRequest request) {

        Session session;
        try {
            session = sessionService.getSession(request);
            userService.deleteLocation(session.getUser(), locationId);
        } catch (RuntimeException | NoSuchFieldException e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
