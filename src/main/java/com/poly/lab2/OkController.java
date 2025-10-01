package com.poly.lab2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OkController {
    @RequestMapping("/ok")
    public String ok() {
        return "ok";
    }

    // m1() - Xử lý POST request cho button OK 1
    @PostMapping("/ok")
    public String m1(Model model) {
        model.addAttribute("method", "m1");
        return "ok";
    }

    // m2() - Xử lý GET request cho button OK 2
    @GetMapping("/ok")
    public String m2(Model model) {
        model.addAttribute("method", "m2");
        return "ok";
    }

    // m3() - Xử lý GET request với parameter x cho button OK 3
    @GetMapping(value = "/ok", params = "x")
    public String m3(Model model) {
        model.addAttribute("method", "m3");
        return "ok";
    }
}
