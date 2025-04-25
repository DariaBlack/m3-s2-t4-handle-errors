package ru.yandex.practicum.controllers;

public class HappinessOverflowException extends RuntimeException {
    private final int hapinessLevel;

    public HappinessOverflowException(int happinessLevel) {
        super("Осторожно, вы так избалуете питомца!");
        this.hapinessLevel = happinessLevel;
    }

  public int getHappinessLevel() {
    return hapinessLevel;
  }
}
