package com.wordladderspring.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Controller {
    @RequestMapping("/userlogin")
    public String login(String uid, String password, HttpSession httpSession) {
        httpSession.setAttribute("user", uid);
        return "true";
    }
    @RequestMapping("/checkstate")
    public String check(String uid, HttpSession httpSession) {
        if(httpSession.getAttribute("user") == null)
            return "false";
        if (httpSession.getAttribute("user").toString().equals(uid))
            return "true";
        return "false";
    }
    @RequestMapping("/userlogout")
    public String logout(HttpSession httpSession) {
        httpSession.setAttribute("user", null);
        return "true";
    }
}
