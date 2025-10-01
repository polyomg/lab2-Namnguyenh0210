package com.poly.lab2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResultController {
    @RequestMapping("/a")
    public String m1() {
        return "a";
    }

    @RequestMapping("/b")
    public String m2(Model model) {
        model.addAttribute("message", "I come from b");
        return "a"; // ?1 - Forward về trang a để hiển thị message trong Model
    }

    @RequestMapping("/c")
    public String m3(RedirectAttributes params) {
        params.addAttribute("message", "I come from c");
        return "redirect:/a"; // ?2 - Redirect về /a để hiển thị message trong RedirectAttributes
    }

    // ?3 - @ResponseBody để trả về String trực tiếp thay vì view name
    @ResponseBody
    @RequestMapping("/d")
    public String m4() {
        return "I come from d";
    }
}
