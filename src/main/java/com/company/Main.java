package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Hello World");

        ConsoleInterface c = new ConsoleInterface();
        try{
            c.run();
        }catch (Exception e){
            System.out.println("Printing from Main: Console interface exception caught: " + e);
        }




    }
}
