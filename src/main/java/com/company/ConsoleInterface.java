package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface {

    public void run() throws IOException {
        InvertedIndex ii = new InvertedIndex();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        System.out.println("loading default inverted index (assuming it has already been built)");
        ii.loadInvertedIndex();
        System.out.println("Beginning console interface. Type quit to terminate or help for help");
        while (!input.equals("quit")){
            input = reader.readLine();

            // Build -b
            if (input.equals("-b")){
                System.out.println("executing build, enter a directory or press enter to use default");
                input = reader.readLine();
                if (input.equals("")){
                    ii.build("./src/main/java/com/resources/corpus");
                }else{
                    ii.build(input);
                }
                System.out.println("complete");
            }

            // dump inverted index -dii
            else if (input.equals("-dii")){
                ii.dump();
            }

            // help
            else if(input.equals("help")){
                System.out.println("-b: build inverted index");
                System.out.println("-dii: Dump contents of inverted index");
            }

            else{
                System.out.println("unrecognized input: " + input);
            }
        }
    }

}
