package me.jumen.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    @GetMapping("/file")
    public String fileUploadForm(Model model) {
        return "files/index";
    }

    @PostMapping("/file")
    public String fileUpload(@RequestParam MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        // save 저장 생략
        String message = multipartFile.getOriginalFilename() + " is uploaded.";
        redirectAttributes.addFlashAttribute("message", message);   // model에 자동으로 담긴다
        return "redirect:/file";

    }
}
