package com.company.entities;

import com.company.Printable;

public class Lightbulb implements Printable {
    private int illumination;

    public Lightbulb(int illumination)  {
        this.illumination = illumination;
    }

    public int getIllumination() {
        return illumination;
    }

    @Override
    public void print() {
        System.out.print(" " + illumination + " лк,");
    }


    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;

     /* furniture ссылается на null */

        if (object == null)
            return false;

     /* Удостоверимся, что ссылки имеют тот же самый тип */

        if (!(getClass() == object.getClass()))
            return false;
        else {
            Lightbulb tmp = (Lightbulb) object;
            if (tmp.illumination == this.illumination)
                return true;
            else
                return false;
        }
    }
}
