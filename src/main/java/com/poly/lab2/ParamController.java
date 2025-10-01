package com.poly.lab2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ParamController {
    @RequestMapping("/param/form")
    public String form() {
        return "form";
    }

    @RequestMapping("/param/save/{x}")
    public String save(@PathVariable String x, @RequestParam String y, Model model) {
        model.addAttribute("x", x);
        model.addAttribute("y", y);
        return "form";
    }
}
