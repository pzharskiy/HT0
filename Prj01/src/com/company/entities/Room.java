package com.company.entities;

import com.company.RoomInterface;
import com.company.entities.furnitures.FoldingFurniture;
import com.company.entities.furnitures.Furniture;
import com.company.exceptions.*;

import java.util.ArrayList;
import java.util.List;


public class Room implements RoomInterface {
    private int number;
    private int square;
    private int illumination;
    private int windows;
    private int occupiedArea;
    private boolean valid;
    private static final int MAX_ILLUMINATION = 4000;
    private static final int MIN_ILLUMINATION = 300;
    private static final double MAX_OCCUPANCY = 0.7;

    /*Переменные для хранения списков мебели и лампочек*/
    private List<Furniture> furnitures = new ArrayList<Furniture>();
    private List<Lightbulb> lightbulbs = new ArrayList<Lightbulb>();

    public Room(int number, int square, int countOfWindows) {
            this.number = number;
            this.square = square;
            this.windows = countOfWindows;
            this.illumination = countOfWindows * 700;
            this.valid = false;
        }

    @Override
    public void add(Furniture furniture) {
        furnitures.add(furniture);
        //Если мебель мягкая, то добавляем к занимаемой площади ее максимальные размеры, иначе - размеры твердой мебели
        if (furniture instanceof FoldingFurniture) occupiedArea += ((FoldingFurniture) furniture).getMaxSize();
        else occupiedArea += furniture.getSize();
    }

    @Override
    public void add(Lightbulb lightbulb) {
        illumination += lightbulb.getIllumination();
        lightbulbs.add(lightbulb);
    }

    @Override
    public void change(Furniture furniture, Furniture updateFurniture) throws LackOfFurnitureException {

        if (furnitures.contains(furniture)) {
            furnitures.set(furnitures.indexOf(furniture), updateFurniture);
        } else {
            throw new LackOfFurnitureException("Запрашиваемый объект не найден или не существует");
        }
    }

    @Override
    public void change(Lightbulb lightbulb, Lightbulb updateLightbulb) throws LackOfLightbulbException {

        if (lightbulbs.contains(lightbulb)) {

            lightbulbs.set(lightbulbs.indexOf(lightbulb), updateLightbulb);
        } else {
            throw new LackOfLightbulbException("Запрашиваемый объект не найден или не существует");
        }

    }

    @Override
    public void delete(Furniture furniture) throws LackOfFurnitureException {

        if (furnitures.contains(furniture)) {
            furnitures.remove(furniture);
        } else {
            throw new LackOfFurnitureException("Запрашиваемый объект не найден или не существует");
        }

    }

    @Override
    public void delete(Lightbulb lightbulb) throws LackOfLightbulbException {

        if (lightbulbs.contains(lightbulb)) {
            lightbulbs.remove(lightbulb);
        } else {
            throw new LackOfLightbulbException("Запрашиваемый объект не найден или не существует");
        }
    }

    @Override
    public boolean checkOccupancy() {

        if (((double) occupiedArea / square) <= MAX_OCCUPANCY) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkIllumination() {

        if ((illumination <= MAX_ILLUMINATION) && (illumination >= MIN_ILLUMINATION)) {
            return true;
        } else return false;
    }

    @Override
    public void describe() {
        System.out.println("\tКомата №" + number);
        printLightbulds();
        System.out.print("\t\tПлощадь = " + square + " м^2 ");
        printFurnitures();

    }

    public int getNumber() {
        return number;
    }

    @Override
    public void validate() throws SpaceUsageTooMuchException, IlluminanceTooMuchException, IlluminanceTooLittleException {
        /*Проверка заполненности и светимости*/
        if ((!checkOccupancy()) || (!checkIllumination())) {
            /*Если нарушена хотя бы одно, то проверяется что именно и выбрасывается соответсвенное исключение*/
            valid = false;

            if (!checkOccupancy()) {
                throw new SpaceUsageTooMuchException("Превышена допустимая заполненность помещения (более 70% площади) в комнате №" + number);
            }


        if (illumination < MIN_ILLUMINATION) {

            throw new IlluminanceTooLittleException("Светимость ниже допустимой в помещении (менее 300 лк) в комнате №" + number);
        }
        if (illumination > MAX_ILLUMINATION) {
            throw new IlluminanceTooMuchException("Превышена допустимая светимость в помещении (более 4000 лк) в комнате №" + number);
        }
    }
        else {
        valid = true;
    }

}

    @Override
    public boolean isValid() {

        validate();
        return valid;
    }

    private void printFurnitures() {
        /*Если мебели нет*/
        if (furnitures.isEmpty()) {
            System.out.println("(свободно 100%)");
            System.out.println("\t\tМебели нет");
        } else {
            System.out.print("(занято ");
            printOccupancy();
            System.out.println("\t\tМебель: ");
            for (Furniture furniture : furnitures
                    ) {
                furniture.print();
            }
        }
    }

    private void printOccupancy() {
        int minSquare = 0;
        int maxSquare = 0;
        for (Furniture furniture : furnitures
                ) {

            minSquare += furniture.getSize();
            /*Если мебель - мягкая*/
            if (furniture instanceof FoldingFurniture) {
                maxSquare += ((FoldingFurniture) furniture).getMaxSize();
            }
            /*Иначе*/
            else {
                maxSquare += furniture.getSize();
            }

        }
        /*Если среди мебели присутствует мягкая, то */
        if (maxSquare > minSquare) {
            System.out.println(minSquare + "-" + maxSquare + " м^2, гарантировано свободно " + (square - maxSquare) + " м^2 или " + (100 * (square - maxSquare) / square) + "% площади)");
        } else
            System.out.println(minSquare + " м^2, свободно " + (square - minSquare) + " м^2 или " + (100 * (square - minSquare) / square) + "% площади)");

    }

    private void printLightbulds() {

        System.out.print("\t\tОсвещенность = " + illumination + " (" + windows + " окна по 700лк");
        if (lightbulbs.isEmpty()) {
            System.out.println(")");
        } else {
            System.out.print(", лампочки:");
            for (Lightbulb lightbulb : lightbulbs
                    ) {
                lightbulb.print();
            }
            System.out.print("\b");
            System.out.println(")");
        }
    }
}
