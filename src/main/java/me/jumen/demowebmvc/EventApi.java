package me.jumen.demowebmvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventApi {

    @PostMapping()
    public Event createEvent(HttpEntity<Event> request) {
        // save
        MediaType contentType = request.getHeaders().getContentType();  //@RequestBody와 비슷하지만, 추가적으로 헤더 정보를 가져올 수 있다
        System.out.println(contentType);

//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(System.out::println);
//        }
        return request.getBody();
    }

}
