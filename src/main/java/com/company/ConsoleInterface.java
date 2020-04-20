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
        System.out.println("Loading default inverted index (assuming it has already been built). Please wait...");
        try{ii.loadInvertedIndex();}
        catch(Exception e){
            System.err.println("From ConsoleInterface: From run: Error, cannot load serialized invertedIndex. Rebuilding.");
            ii.build("./src/main/java/com/resources/corpus");
        }

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
                String queryIn = reader.readLine();

                // stem query
                System.out.println("Would you like to stem your query? [y/n]");
                String decision = reader.readLine();
                while (!decision.equals("y") && !decision.equals("n")){
                    System.out.println("Please enter either y or n");
                    decision = reader.readLine();
                }
                if (decision.equals("y")){ queryIn = ps.stem(queryIn);}

                // Select query type
                System.out.println("Would you like to \n " +
                        "1. See the number of times it occurs in a doc \n " +
                        "2. See all occurences (positions and doc)");
                decision = reader.readLine();
                while (!decision.equals("1") && !decision.equals("2")){
                    System.out.println("Unrecognized input, please enter either 1 or 2");
                    decision = reader.readLine();
                }
                if (decision.equals("1")){ii.queryNumberOfOccurences(queryIn);}
                else if (decision.equals("2")){ii.queryAllOccurences(queryIn);}

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
