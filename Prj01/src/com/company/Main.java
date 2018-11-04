package com.company;

import com.company.entities.Building;
import com.company.entities.Lightbulb;
import com.company.entities.furnitures.*;
import com.company.exceptions.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            Building building = new Building("Богомолова", 13);
            building.addRoom(1, 68, 4);
            building.addRoom(2, 90, 4);
            building.addRoom(3, 86, 2);
            building.addRoom(4, 65, 3);

            building.getRoom(1).add(new Lightbulb(100));
            building.getRoom(1).add(new Lightbulb(250));
            building.getRoom(1).add(new Lightbulb(200));
            building.getRoom(1).add(new Lightbulb(200));
            building.getRoom(2).add(new Lightbulb(30));
            building.getRoom(2).add(new Lightbulb(500));
            building.getRoom(3).add(new Lightbulb(200));
            building.getRoom(3).add(new Lightbulb(800));
            building.getRoom(4).add(new Lightbulb(100));
            building.getRoom(4).add(new Lightbulb(100));

            building.getRoom(1).add(new Sofa("Потрясающий мягкий диван", 4, 9));
            building.getRoom(1).add(new Fridge("Современный холодный холодильник", 6));
            building.getRoom(2).add(new Chair("Стул", 3));
            building.getRoom(2).add(new Cupboard("Вместительный комфортабельный шкафчик", 10));
            building.getRoom(3).add(new Sofa("Мягкий и упругий диван", 20, 24));
            building.getRoom(3).add(new Armchair("Массажное кресло", 5, 9));
            building.getRoom(3).add(new Cupboard("Огромный платяной шкаф", 20));
            building.getRoom(4).add(new Cupboard("Шкафчик", 7));
            building.getRoom(4).change(new Cupboard("Шкафчик", 7), new Cupboard("Шкафище", 9));
            building.getRoom(4).change(new Lightbulb(100), new Lightbulb(200));
            building.deleteRoom(4);
            building.validate();
            if (building.isValid()) {
                building.describe();
            }
        } catch (LackOfFurnitureException | LackOfLightbulbException | LackOfRoomException | InvalidSquareOfRoomException | InvalidSizeOfFurnitureException | SpaceUsageTooMuchException | IlluminanceTooMuchException | IlluminanceTooLittleException ex) {
            ex.printStackTrace();
        }
    }
}
