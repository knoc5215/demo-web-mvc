package me.jumen.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/events")
    public @ResponseBody Event getEvent(@RequestParam String name, @RequestParam Integer limit) {
        Event event = new Event();
        event.setName(name);
        event.setLimit(limit);

        return event;
    }

    @PostMapping("/eventsModel")
    public  String createEvent(@Validated @ModelAttribute Event event, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            System.out.println("==================");
            bindingResult.getAllErrors().forEach(c-> {
                System.out.println(c.toString());
            });
            System.out.println("==================");
            return "/events/form";
        }

        return "redirect:/events/list";
    }

    @GetMapping("/events/list")
    public String getEvents(Model model) {
        Event event = new Event();
        event.setName("spring");
        event.setLimit(5555);

        List<Event>eventList = new ArrayList<>();
        eventList.add(event);

        model.addAttribute("eventList", eventList);

        return "/events/list";

    }

    @PostMapping("/events/map")
    public @ResponseBody Event getEvent(@RequestParam Map<String,String> params) {
        Event event = new Event();
        event.setName(params.get("name"));
        event.setLimit(Integer.parseInt(params.get("limit")));
        return event;
    }

    @GetMapping("/events/form")
    public String eventsForm(Model model) {
        Event event = new Event();
        event.setLimit(50);
        model.addAttribute("event", new Event());
        return "events/form";
    }


}
