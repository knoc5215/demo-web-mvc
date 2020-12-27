package me.jumen.demowebmvc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(method = RequestMethod.GET)// class-level method filter
//@RequestMapping(consumes = MediaType.APPLICATION_ATOM_XML_VALUE)  // class-level consumes is overrided by method-level
@RequestMapping("/sample")
public class SampleController {

    @GetMapping({"/helloGet", "/helloGet2"})
    public @ResponseBody String helloGet() {
        return "helloGet";
    }

    @PostMapping("/helloPost")
    public @ResponseBody String helloPost() {
        return "helloPost";
    }

    @GetMapping("/hello/?")
    public @ResponseBody String helloChar() {
        return "helloChar";
    }

    @GetMapping("/hello/*")
    public @ResponseBody String helloChars() {
        return "helloChars";
    }

    @GetMapping("/hello/**")
    public @ResponseBody String helloPath() {
        return "helloPath";
    }
    @GetMapping("/hello/jumen")
    public @ResponseBody String helloJumen() {
        return "hello jumen";
    }

    @GetMapping("/regExp/{name:[a-z]+}")
    public @ResponseBody String helloRegExp(@PathVariable String name) {
        return "helloRegExp " + name;
    }

    /* consumes - content-type */
    /* produces - Accept */
    @GetMapping(value = "/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String json() {
        return "hello json";
    }








}
