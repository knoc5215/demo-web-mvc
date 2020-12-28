package me.jumen.demowebmvc;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

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

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/file/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws
            IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        File file = resource.getFile();
        Tika tika = new Tika();
        String type = tika.detect(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" +
                        resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, type)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                .body(resource);
    }
}
