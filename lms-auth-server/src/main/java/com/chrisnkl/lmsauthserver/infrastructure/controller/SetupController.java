package com.chrisnkl.lmsauthserver.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/setup")
@RequiredArgsConstructor
public class SetupController {

    @GetMapping
    public String setup() {
        return "setup";
    }

}
