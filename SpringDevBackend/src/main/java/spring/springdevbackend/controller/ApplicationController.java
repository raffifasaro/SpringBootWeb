package spring.springdevbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.springdevbackend.eventModel.Event;
import spring.springdevbackend.repository.EventRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ApplicationController {

    private final EventRepository repository;

    private final static Logger LOG = LoggerFactory.getLogger(ApplicationController.class);
    private final static DateTimeFormatter HOURS_AND_MINUTES = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public ApplicationController(final EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/endpoint")
    public Event getData(final @RequestBody Event eventObj) {

        LOG.trace(eventObj.toString());
        //save given event to database
        return repository.save(eventObj);
    }

    @GetMapping("/endpoint2")
    public void getData(final @RequestParam String dateValue, final @RequestParam String timeValue, final @RequestParam String text) {
        //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
        final LocalDate date = LocalDate.parse(dateValue);
        final LocalTime time = LocalTime.parse(timeValue, HOURS_AND_MINUTES);
        final Event event = new Event(date, time, text);
        LOG.trace(event.toString());
        repository.save(event);
    }

    @GetMapping("testEndpoint")
    public String test(final @RequestParam String text) {
        return text;
    }

}