package me.jumen.demowebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping(method = RequestMethod.GET)// class-level method filter
//@RequestMapping(consumes = MediaType.APPLICATION_ATOM_XML_VALUE)  // class-level consumes is overrided by method-level
@RequestMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {

//    @GetMapping("/hello")
    @GetHelloMapping
    public @ResponseBody String hello() {
        return "hello";
    }

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

    @GetMapping(value = "/header", headers = HttpHeaders.FROM)
    public @ResponseBody String header() {
        return "header";
    }

    @GetMapping(value = "/notHeader", headers = "!" + HttpHeaders.FROM)
    public @ResponseBody String notHeader() {
        return "notHeader";
    }

    @GetMapping(value = "/param", params = "param")
    public @ResponseBody String param() {
        return "param";
    }

    @GetMapping(value = "/notParam", params = "!" + "param")
    public @ResponseBody String notParam() {
        return "notParam";
    }

    @GetMapping(value = "/paramAndValue", params = "param=1")
    public @ResponseBody String paramAndValue() {
        return "paramAndValue";
    }

//    @GetMapping("/events")
//    @ResponseBody
//    public String events() {
//        return "events";
//    }
//
//    @GetMapping("/events/{id}")
//    @ResponseBody
//    public String getAnEvents(@PathVariable int id) {
//        return "event";
//    }
//
//    @DeleteMapping("/events/{id}")
//    @ResponseBody
//    public String removeAnEvents(@PathVariable int id) {
//        return "event";
//    }












}
