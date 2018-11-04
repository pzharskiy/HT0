package com.company;

import com.company.entities.Room;
import com.company.exceptions.InvalidSquareOfRoomException;
import com.company.exceptions.LackOfRoomException;


public interface BuildingInterface {

    void addRoom(int number, int square, int countOfWindows) throws InvalidSquareOfRoomException;

    Room getRoom(int number);

    void describe();

    void deleteRoom(int number) throws LackOfRoomException;

    void validate();

    boolean isValid();

}
