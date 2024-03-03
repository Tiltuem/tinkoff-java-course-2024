package edu.java.bot.controller;

import edu.java.dto.LinkUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    @PostMapping(value = "/updates", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<Void> processUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        //TODO Дописать логику
        return ResponseEntity.ok().build();
    }
}
