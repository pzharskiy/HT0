package com.company.entities;

import com.company.BuildingInterface;
import com.company.exceptions.InvalidSquareOfRoomException;
import com.company.exceptions.LackOfRoomException;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Building implements BuildingInterface {

    private String street;
    private int number;
    private boolean valid;
    /*Переменная для хранения всего множества комнат*/
    private Set<Room> rooms = new LinkedHashSet<Room>();

    public Building(String street, int number) {
        this.street = street;
        this.number = number;
        this.valid = false;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void addRoom(int number, int square, int countOfWindows) throws InvalidSquareOfRoomException {
        if (square>0) {
            rooms.add(new Room(number, square, countOfWindows));
        }
        else throw new InvalidSquareOfRoomException("Площадь вашей комнаты должна быть положительным числом. Пожалуйста, проверьте корректность введённых данных");

    }

    @Override
    public Room getRoom(int number) throws LackOfRoomException {

        for (Room room : rooms
                ) {
            if (room.getNumber() == number) {
                return room;
            }

        }
        throw new LackOfRoomException("Запрашиваемая вами комната не существует");

    }

    @Override
    public void describe() {
        System.out.println("Здание " + street + " " + number);
        for (Room room : rooms
                ) {
            /*Вызываем описание каждой комнаты*/
            room.describe();
        }
    }

    @Override
    public void deleteRoom(int number) throws LackOfRoomException {
        /*Проходим по множеству и удаляем, если номер комнаты совпадает с переданным */
        Iterator<Room> iterator = rooms.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getNumber() == number) {
                iterator.remove();
                return;
            }
        }
        throw new LackOfRoomException("Запрашиваемая вами комната не существует");

    }

    @Override
    public void validate() {
        boolean someRoomIsInvalid = false;
        for (Room room : rooms
                ) {
            if (!room.isValid()) {
                someRoomIsInvalid = true;
            }
        }
        if (!someRoomIsInvalid) {
            valid = true;
        }
    }

    @Override
    public boolean isValid() {
        return valid;
    }
}
