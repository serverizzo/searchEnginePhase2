package com.company;

import opennlp.tools.stemmer.PorterStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInterface {

    public void run() throws IOException {
        InvertedIndex ii = new InvertedIndex();
        PorterStemmer ps = new PorterStemmer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        System.out.println("loading default inverted index (assuming it has already been built). Please wait...");
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

            // query all occurances
            else if (input.equals("-q")){
                System.out.println("Enter word you would like to query");
                String in = reader.readLine();
                System.out.println("Would you like to stem your query? [y/n]");
                while (!in.equals("y") || !in.equals("n")){
                    System.out.println("Please enter either y or n");
                    in = reader.readLine();
                }
                if (in.equals("y")){ii.queryAllOccurences(ps.stem(in));}
                else{ii.queryAllOccurences(in);}
            }

            // help
            else if(input.equals("help")){
                System.out.println("-b: build inverted index");
                System.out.println("-dii: Dump contents of inverted index");
                System.out.println("-q: Query");
            }

            else{
                System.out.println("unrecognized input: " + input);
            }

            System.out.println("Awaiting next command. Type help for more options");
        }
        System.out.println("Exiting program");
    }

}
