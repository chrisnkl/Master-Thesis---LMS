package com.chrisnkl.lmscourseservice.infrastructure.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

    public String getStatus() {
        return "OK";
    }

}
