package me.jumen.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(method = RequestMethod.GET)// class-level method filter
public class SampleController {

    @GetMapping("/helloGet")
    public @ResponseBody String helloGet() {
        return "helloGet";
    }

    @PostMapping("/helloPost")
    public @ResponseBody String helloPost() {
        return "helloPost";
    }
}
