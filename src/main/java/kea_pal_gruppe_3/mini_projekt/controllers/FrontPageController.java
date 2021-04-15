package kea_pal_gruppe_3.mini_projekt.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontPageController {

    @GetMapping(value={"/","index"})
    public String index() {
        return "index.html";
    }

}
