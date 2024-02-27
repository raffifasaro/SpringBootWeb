package amogustest.springtest;

import org.springframework.web.bind.annotation.*;

@RestController
public class YourController {

    @GetMapping("/endpoint")
    public String sayHello(@RequestParam String amogus) {
        System.out.println("Received value: " + amogus);
        return amogus;
    }
}