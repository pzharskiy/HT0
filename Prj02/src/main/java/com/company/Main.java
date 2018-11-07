package com.company;

import com.company.entities.Catalog;
import com.company.exceptions.AccessException;
import com.company.exceptions.NotExistingDirectoryException;

public class Main {
    public static void main(String[] args) {
        // write your code here
        try {
            Catalog catalog = new Catalog(args);
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
