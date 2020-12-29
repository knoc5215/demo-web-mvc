package me.jumen.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice(assignableTypes = {HandlerController.class, EventApi.class})   // global -> 패키지 레벨도 가능
//@RestControllerAdvice
public class BaseController {

    // 모든 controller 적용된다


    @ExceptionHandler
    public String runtimeExceptionHandler(RuntimeException runtimeException, Model model) {
        model.addAttribute("message", "runtime exception ");
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

}
