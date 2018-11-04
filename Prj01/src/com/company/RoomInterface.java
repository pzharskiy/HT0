package com.company;


import com.company.entities.Lightbulb;
import com.company.entities.furnitures.Furniture;
import com.company.exceptions.*;


public interface RoomInterface {

    void add(Furniture furniture);

    void add(Lightbulb lightbulb);

    void change(Furniture furniture, Furniture updateFurniture) throws LackOfFurnitureException;

    void change(Lightbulb lightbulb, Lightbulb updateLightbulb) throws LackOfLightbulbException;

    void delete(Furniture furniture) throws LackOfFurnitureException;

    void delete(Lightbulb lightbulb) throws LackOfLightbulbException;

    void describe();

    boolean checkOccupancy();

    boolean checkIllumination();

    boolean isValid();

    void validate() throws SpaceUsageTooMuchException, IlluminanceTooMuchException, IlluminanceTooLittleException;


}
