package kea_pal_gruppe_3.mini_projekt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontPageController {

    @GetMapping("/")
    public String welcomeSite() {
        return "index.html";
    }

    @GetMapping("/index.html")
    public String goBack() {
        return "index.html";
    }

}
