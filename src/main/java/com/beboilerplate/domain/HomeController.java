package com.beboilerplate.domain;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Home", description = "루트(/) 경로 접속 테스트")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "SUCCESS!";
    }

}
