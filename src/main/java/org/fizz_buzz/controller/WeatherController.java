package org.fizz_buzz.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.fizz_buzz.dto.LocationRequestDTO;
import org.fizz_buzz.dto.WeatherViewDTO;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.service.SessionService;
import org.fizz_buzz.service.UserService;
import org.fizz_buzz.service.WeatherService;
import org.fizz_buzz.util.ApplicationConstant;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@PropertySource("classpath:application.properties")
public class WeatherController {

    private static final String COOKIE_SESSION_ID = "SessionId";

    private final WeatherService weatherService;
    private final UserService userService;
    private final SessionService sessionService;
    private final LocationRepository locationRepository;


    public WeatherController(WeatherService weatherService,
                             UserService userService,
                             SessionService sessionService,
                             LocationRepository locationRepository) {
        this.weatherService = weatherService;
        this.userService = userService;
        this.sessionService = sessionService;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/weather")
    public String getWeather(@CookieValue(name = COOKIE_SESSION_ID, required = false) UUID sessionId,
                             Model model,
                             @PageableDefault(size = 5) Pageable pageable) {

        Session session;
        User user;

        try {
            session = sessionService.getSession(sessionId);
            user = userService.getUser(session.getUser().getLogin());
        } catch (Exception e) {
            return "redirect:authenticate";
        }

        var locations = locationRepository.findByUser(user, pageable);

        List<WeatherViewDTO> weatherDTOs = new ArrayList<>();

        locations.forEach(location -> {
            try {
                weatherDTOs.add(new WeatherViewDTO(location.getId(), location.getName(), weatherService.getWeather(location.getLongitude(), location.getLatitude())));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        model.addAttribute("weatherCards", weatherDTOs);
        model.addAttribute("totalPages", locations.getTotalPages());
        model.addAttribute("currentPage", locations.getNumber());

        return ApplicationConstant.WEATHER_VIEW;
    }

    @GetMapping("/locations")
    public String findLocations(@ModelAttribute("locationName") @NotBlank(message = "Location name must not be blank")
                                String locationName,
                                Model model) throws IOException, InterruptedException {

        var locations = weatherService.getLocations(locationName.trim().replace(' ', '_'));

        model.addAttribute("locationCards", locations);

        return ApplicationConstant.LOCATION_VIEW;
    }

    @PostMapping("/locations")
    public RedirectView addLocation(LocationRequestDTO location,
                                    Model model,
                                    HttpServletRequest request) {

        Session session;
        User user;
        try {
            session = sessionService.getSession(request);
            user = userService.getUser(session.getUser().getLogin());
        } catch (RuntimeException | NoSuchFieldException e) {
            //todo: change to throw exception and handle it with global exception handler
            return new RedirectView("registration");
        }

        userService.addLocation(user, location.name(), location.latitude(), location.longitude());

        return new RedirectView(ApplicationConstant.WEATHER_VIEW);
    }

    @DeleteMapping("/locations")
    public ResponseEntity<String> deleteLocation(@RequestParam("locationId") Long locationId,
                                                 HttpServletRequest request) {

        Session session;
        try {
            session = sessionService.getSession(request);
            var user = userService.getUser(session.getUser().getLogin());
            userService.deleteLocation(user, locationId);
        } catch (RuntimeException | NoSuchFieldException e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
