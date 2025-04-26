package ru.yandex.practicum.controllers;

public class UnauthorizedUserException extends RuntimeException {
  // реализуйте исключение: добавьте наследование, поля user и owner, конструктор и геттеры
  private final String user;
  private final String owner;

  public UnauthorizedUserException(String user, String owner) {
    super("Питомец даёт себя гладить только хозяину.");
    this.user = user;
    this.owner = owner;
  }

  public String getUser() {
    return user;
  }

  public String getOwner() {
    return owner;
  }
}
