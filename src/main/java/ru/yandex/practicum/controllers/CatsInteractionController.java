package ru.yandex.practicum.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/cats")
public class CatsInteractionController {
    private int happiness = 0;
    private String owner;

    @PostMapping("/owner")
    public Map<String, String> setOwner(@RequestParam(required = false) final String newOwner) {
        if (newOwner == null) {
            throw new IncorrectParameterException("Параметр newOwner равен null.");
        }
        this.owner = newOwner;
        return Map.of("owner", newOwner);
    }

    @GetMapping("/converse")
    public Map<String, String> converse() {
        happiness++;
        checkHappiness();
        return Map.of("talk", "Мяу");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(
            @RequestParam(required = false) final Integer count,
            @RequestParam(required = false) final String user
    ) {
        if (owner == null) {
            throw new IncorrectParameterException("Необходимо установить параметр owner.");
        }
        if (user == null) {
            throw new IncorrectParameterException("Параметр user равен null.");
        }
        if (!user.equals(owner)) {
            throw new UnauthorizedUserException(user, owner);
        }
        if (count == null) {
            throw new IncorrectParameterException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectParameterException("Параметр count имеет отрицательное значение.");
        }

        happiness += count;
        checkHappiness();
        return Map.of("talk", "Муррр. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }


    @GetMapping("/feed")
    public Map<String, Integer> feed() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /feed ещё не реализован.");
    }

    private void checkHappiness() {
        if (happiness > 10) {
            throw new HappinessOverflowException(happiness);
        }
    }
}