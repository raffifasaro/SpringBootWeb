package spring.springdevbackend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.springdevbackend.eventModel.EventObj;
@RestController
public class ApplicationController {

    EventObj event = null;

    @GetMapping("/endpoint")
    public EventObj getData(@RequestBody EventObj eventObj) {
        System.out.println(eventObj);
        event = eventObj;
        return eventObj;
    }

    @GetMapping("testEndpoint")
    public String test(@RequestParam String text) {
        return text;
    }

}