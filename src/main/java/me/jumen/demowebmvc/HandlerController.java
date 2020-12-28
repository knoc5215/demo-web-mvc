package me.jumen.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"event"})   // model에 들어가 있는 값들 중에 동일한 값이 있다면, 넣어준다
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
    public @ResponseBody Event getEvent(@RequestParam String name, @RequestParam Integer limit, SessionStatus sessionStatus) {
        Event event = new Event();
        event.setName(name);
        event.setLimit(limit);

        sessionStatus.setComplete();    // session clear

        return event;
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

    @GetMapping("/events/form/name")
    public String eventsFormName(Model model) {
        model.addAttribute("event", new Event());
        return "events/form-name";
    }

    @PostMapping("/events/form/name")
    public  String eventsFormName(@Validated @ModelAttribute Event event, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println("==================");
            bindingResult.getAllErrors().forEach(c-> {
                System.out.println(c.toString());
            });
            System.out.println("==================");
            return "/events/form-name";
        }

        return "redirect:/events/form/limit";
    }

    @GetMapping("/events/form/limit")
    public String eventsFormLimit(@ModelAttribute Event event, Model model) {
        model.addAttribute("event", event);
        return "events/form-limit";
    }

    @PostMapping("/events/form/limit")
    public  String eventsFormLimit(@Validated @ModelAttribute Event event, BindingResult bindingResult, SessionStatus sessionStatus) {
        if(bindingResult.hasErrors()) {
            System.out.println("==================");
            bindingResult.getAllErrors().forEach(c-> {
                System.out.println(c.toString());
            });
            System.out.println("==================");
            return "/events/form-limit";
        }

        sessionStatus.setComplete();

        return "redirect:/events/list";
    }

}
