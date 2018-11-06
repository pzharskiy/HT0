package com.company;

import com.company.entities.Catalog;
import com.company.exceptions.AccessException;
import com.company.exceptions.NotExistingDirectoryException;

public class Main {
    public static void main(String[] args) {
        // write your code here
 ////XНЕ ЗАБЫТЬ ПОМЕНЯТЬ КАТАЛОГ НА КОМАНДНУЮ СТРОКУ!!!!!
        String arg[]={"d:/catalogtest2/", "d:/catalogtest1/", "d:/pzharskiy/"};
        try {
            Catalog catalog = new Catalog(arg);
            catalog.printToFile();
            catalog.findDublicates();
            catalog.findDublicatesWithoutCheckSum();
        }
        catch (AccessException  | NotExistingDirectoryException e)
        {
            e.printStackTrace();
        }
    }
}
