package me.jumen.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HandlerController {

    @GetMapping("/events/{id}")
    public @ResponseBody Event getEvent(@PathVariable("id") Integer idValue) {
       Event event = new Event();
       event.setId(idValue);    // 다르게 표현도 가능하다

       return event;
   }

    @GetMapping("/events/matrix/{id}")
    public @ResponseBody Event getEvent(@PathVariable Integer id, @MatrixVariable String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);

        return event;
    }
}
