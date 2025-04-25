package ru.yandex.practicum.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cats")
public class CatsInteractionController {
    private int happiness = 0;

    @GetMapping("/converse")
    public Map<String, String> converse() {
        happiness++;
        checkHappiness();
        return Map.of("talk", "Мяу");
    }

    @GetMapping("/pet")
    public Map<String, String> pet(@RequestParam(required = false) final Integer count) {
        if (count == null) {
            throw new IncorrectCountException("Параметр count равен null.");
        }
        if (count <= 0) {
            throw new IncorrectCountException("Параметр count имеет отрицательное значение.");
        }

        happiness += count;
        checkHappiness();
        return Map.of("talk", "Муррр. ".repeat(count));
    }

    @GetMapping("/happiness")
    public Map<String, Integer> happiness() {
        return Map.of("happiness", happiness);
    }

    @ExceptionHandler
    public Map<String, String> handle(final IncorrectCountException e) {
        return Map.of(
                "error", "Ошибка с параметром count.",
                "errorMessage", e.getMessage()
        );
    }

    @ExceptionHandler
    public Map<String, String> handleRuntimeException(final RuntimeException e) {
        // возвращаем сообщение об ошибке
        return Map.of("error", "Произошла ошибка!");
    }

    @ExceptionHandler
    public Map<String, String> handleHappinessOverflow(final HappinessOverflowException e) {
        return Map.of(
                "error", "Осторожно, вы так избалуете пёсика!",
                "happinessLevel", String.valueOf(e.getHappinessLevel())
        );
    }

    private void checkHappiness() {
        if (happiness > 10) {
            throw new HappinessOverflowException(happiness);
        }
    }
}