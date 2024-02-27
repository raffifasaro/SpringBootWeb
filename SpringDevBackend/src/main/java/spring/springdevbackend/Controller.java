package spring.springdevbackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.springdevbackend.calenderFormats.EventObj;

import java.util.Date;
import java.util.List;

@RestController
public class Controller {
    @GetMapping("/endpoint")
    public EventObj getData(@RequestParam Date dateValue, @RequestParam String timeValue, @RequestParam String text) {
        EventObj CalendarFile = new EventObj(dateValue, timeValue, text);
        System.out.println(CalendarFile);
        return CalendarFile;
    }

    @GetMapping("testEndpoint")
    public String test(@RequestParam String text) {
        return text;
    }
}