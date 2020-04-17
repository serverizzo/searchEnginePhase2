package com.company;

import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World");

        BuildInvertedIndex bii = new BuildInvertedIndex();
        bii.build(args[0]);


    }
}
