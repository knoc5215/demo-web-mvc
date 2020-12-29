package me.jumen.demowebmvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/events")
public class EventApi {

    // REST API 에서 주로 쓴다 - reference 참고
    @ExceptionHandler
    public ResponseEntity errorHandler() {
        return ResponseEntity.badRequest().body("can't create event as...");
    }

    @PostMapping()
    @ResponseBody
    public Event createEvent(/*HttpEntity<Event> request,*/ @RequestBody Event event, BindingResult bindingResult) {
        // save
//        MediaType contentType = request.getHeaders().getContentType();  //@RequestBody와 비슷하지만, 추가적으로 헤더 정보를 가져올 수 있다
//        System.out.println(contentType);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
        }
//        return request.getBody();
        return event;
    }

    @PostMapping("/responseEntity")
    public ResponseEntity createEventResponseEntity(@RequestBody @Valid Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<Event>(event, HttpStatus.CREATED);
    }

}
