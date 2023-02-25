package com.datajpa.app.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author LuisMD
 */

@Controller
public class localeController {
 
    @GetMapping("/locale")
    public String locale(HttpServletRequest r) {
        String ultimaUrl = r.getHeader("referer");

        return "redirect:".concat(ultimaUrl);
    }
}
