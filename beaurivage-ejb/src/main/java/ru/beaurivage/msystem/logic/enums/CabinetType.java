package ru.beaurivage.msystem.logic.enums;

public enum CabinetType {

    LASER("Лазерный кабинет"),
    PROCEDURE("Процедурный кабинет");

    private final String description;

    CabinetType(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return description;
    }

}