package spring.springdevbackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.springdevbackend.eventModel.Event;
import spring.springdevbackend.repository.EventRepository;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class ApplicationController {

    private final EventRepository repository;

    @Autowired
    public ApplicationController(EventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/endpoint")
    public Event getData(@RequestBody Event eventObj) {
        System.out.println(eventObj);
        //save given event to database
        repository.save(eventObj);
        return eventObj;
    }

    //public record Event(@Id @JsonIgnore Integer id, Date date, LocalTime time, String text) {
    @GetMapping("/endpoint2")
    public void getData(@RequestParam String dateValue, @RequestParam String timeValue, @RequestParam String text) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateValue);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(timeValue, formatter);
        Event event = new Event(null, date, time, text);
        System.out.println(event);
        repository.save(event);
    }

    @GetMapping("testEndpoint")
    public String test(@RequestParam String text) {
        return text;
    }

}