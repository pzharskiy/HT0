package com.company.entities.furnitures;

import com.company.Printable;
import com.company.exceptions.InvalidSizeOfFurnitureException;

import java.util.Objects;

public abstract class Furniture implements Printable{
    private int size;
    private String name;

    public Furniture(String name, int size) throws InvalidSizeOfFurnitureException {

            if (size>0) {
                this.name = name;
                this.size = size;
            }
            else throw new InvalidSizeOfFurnitureException("Размеры мебели должны быть положительным числом");

    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public void print() {
        System.out.println("\t\t  " + name + " (площадь " + size + " м^2)");
    }

    @Override
    public boolean equals(Object object)
    {
        if(object == this)
            return true;

     /* furniture ссылается на null */

        if(object == null)
            return false;

     /* Удостоверимся, что ссылки имеют тот же самый тип */

        if(!(getClass() == object.getClass()))
            return false;
        else
        {
            Furniture tmp = (Furniture) object;
            if((Objects.equals(tmp.name, this.name)) && (tmp.size == this.size))
                return true;
            else
                return false;
        }
    }
}
