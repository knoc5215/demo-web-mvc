package me.jumen.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"event"})   // model에 들어가 있는 값들 중에 동일한 값이 있다면, 넣어준다
public class HandlerController {

    // 여러 error를 한꺼번에 처리할때는 매개변수에서 상위타입으로 잡아야 한
//    @ExceptionHandler({EventException.class, RuntimeException.class})
//    public String eventErrorHandler(RuntimeException runtimeException, Model model) {
//        model.addAttribute("message", "error");
//        return "error";
//    }

    @ExceptionHandler
    public String eventErrorHandler(EventException eventException, Model model) {
        model.addAttribute("message", "event error");
        return "error";
    }


    // custom validator도 지원한다
    @InitBinder("event")    // event 객체가 들어올때 적용한다
    public void initEventBinder(WebDataBinder webDataBinder) {
//        webDataBinder.setDisallowedFields("id");    // 블랙리스트 - id를 form에서 보내더라도 걸러내준다
//        webDataBinder.setAllowedFields();       // 화이트리스트
        webDataBinder.addValidators(new EventValidator());  // custom validator add
    }


    @ModelAttribute("cateogories")
    public void categories(Model model) {
        model.addAttribute("categories", List.of("spring", "jpa", "mysql"));
    }

 /*   @ModelAttribute
    public List<String> categories2(Model model) {
        return List.of("spring", "jpa", "mysql");
    }*/

    @GetMapping("/events/{id}")
    @ResponseBody
    public Event getEvent(@PathVariable("id") Integer idValue) {
        Event event = new Event();
        event.setId(idValue);    // 다르게 표현도 가능하다

        return event;
    }

    @GetMapping("/events/matrix/{id}")
    @ResponseBody
    public Event getEvent(@PathVariable Integer id, @MatrixVariable String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);

        return event;
    }

    @PostMapping("/events")
    @ResponseBody
    public Event getEvent(@RequestParam String name, @RequestParam Integer limit, SessionStatus sessionStatus) {
        Event event = new Event();
        event.setName(name);
        event.setLimit(limit);

        sessionStatus.setComplete();    // session clear

        return event;
    }


    @PostMapping("/events/map")
    @ResponseBody
    public Event getEvent(@RequestParam Map<String, String> params) {
        Event event = new Event();
        event.setName(params.get("name"));
        event.setLimit(Integer.parseInt(params.get("limit")));
        return event;
    }

    @GetMapping("/events/form/name")
    public String eventsFormName(Model model) {
//        throw new EventException();
        throw new RuntimeException();
//        model.addAttribute("event", new Event());
//        return "events/form-name";
    }

    @PostMapping("/events/form/name")
    public String eventsFormName(@Validated @ModelAttribute Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("==================");
            bindingResult.getAllErrors().forEach(c -> {
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
    public String eventsFormLimit(@Validated @ModelAttribute Event event, BindingResult bindingResult, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("==================");
            bindingResult.getAllErrors().forEach(c -> {
                System.out.println(c.toString());
            });
            System.out.println("==================");
            return "/events/form-limit";
        }

        sessionStatus.setComplete();

        // make model attribute primitive type to queryParam set
        // http://localhost:8080/events/list?name=zz&limit=1231 로 만들어서 전달 가능하다 -> 수신측에서 @RequestParam로 활용하자
//        redirectAttributes.addAttribute("name", event.getName());
//        redirectAttributes.addAttribute("limit", event.getLimit());
        // string이 가능해야함

        redirectAttributes.addFlashAttribute("newEvent", event);    // 세션에 객체를 저장하고, 리다이렉트 요청 처리 후 그 즉시 제거한다 (1회성, flash)

        return "redirect:/events/list";
    }

    @GetMapping("/events/list")
    public String getEvents(@ModelAttribute("newEvent") Event event, Model model, @SessionAttribute("visitTime") LocalDateTime localDateTime) {
        /*
        @ModelAttribute Event event로 사용할 시, @SessionAtrributes에 설정된 객체명과 겹치지 않게 -> 같을 경우 세션을 먼저 찾다가 없으면 에러가 난다
        @ModelAttribute("modelEvent") Event event & @SessionAttributes({"event"})  --> 이건 겹치지 않아서 에러 발생 X
         */

        System.out.println(localDateTime);

        Event springEvent = new Event();
        springEvent.setName("spring");
        springEvent.setLimit(5555);

        Event newEvent = (Event) model.asMap().get("newEvent");


        List<Event> eventList = new ArrayList<>();
        eventList.add(springEvent);
        eventList.add(event);
        eventList.add(newEvent);

        model.addAttribute("eventList", eventList);

        return "/events/list";

    }

}
