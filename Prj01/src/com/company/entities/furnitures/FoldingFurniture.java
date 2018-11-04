package com.company.entities.furnitures;

import com.company.exceptions.InvalidSizeOfFurnitureException;

public abstract class FoldingFurniture extends Furniture {
    private int maxSize;

    public FoldingFurniture(String name, int size, int maxSize) throws InvalidSizeOfFurnitureException  {
        super(name, size);

            if (maxSize<=0) {
                throw new InvalidSizeOfFurnitureException("Размеры мебели должны быть положительным числом");
            }
            if (maxSize<size) {throw new InvalidSizeOfFurnitureException("Максимальные размеры мебели должны быть больше, чем размеры мебели в сложенном виде");}
            else { this.maxSize = maxSize;}

    }

    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void print() {
        System.out.println("\t\t  " + getName() + " (площадь от " + getSize() + " м^2 до " + getMaxSize() + " м^2)");
    }
}
