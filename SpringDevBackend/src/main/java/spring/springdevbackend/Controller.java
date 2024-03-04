package spring.springdevbackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.springdevbackend.calenderFormats.EventObj;
@RestController
public class Controller {

    EventObj event = null;

    @GetMapping("/endpoint")
    //todo request body
    public EventObj getData(@RequestParam String dateValue, @RequestParam String timeValue, @RequestParam String text) {
        EventObj CalendarFile = new EventObj(dateValue, timeValue, text);
        System.out.println(CalendarFile);
        event = CalendarFile;
        return CalendarFile;
    }

    @GetMapping("testEndpoint")
    public String test(@RequestParam String text) {
        return text;
    }

}