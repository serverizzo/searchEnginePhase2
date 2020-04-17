/*
    Note: using BuildInvertedIndex will overwrite any inverted indexs that already exsit in a specific filepath
 */

package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class BuildInvertedIndex {

    public String[] convertToWordArray(String line){
        return line.split(" ");
    }

    public void build(String corpusPath){

        File corpusRoot = new File(corpusPath);
        InvertedIndex ii = new InvertedIndex();


        // Add all words in corpus into Inverted index
        for (File curr : corpusRoot.listFiles()){
//            String keyword = null;
            int docPosition = 0;
            String docFoundIn = curr.getName();

//            System.out.println(curr.getName());
            // open corpus doc
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedReader reader = Files.newBufferedReader(curr.toPath(), charset)) { // Use a buffered reader to read each line of the document
                String line = null;
                String[] words = null;
                while ((line = reader.readLine()) != null) {    // get each line of the document
                    words = convertToWordArray(line);
                    // TODO: FILTER STOPWORDS

                    for (String keyword: words){
                        ii.add(keyword, new ValueObject(curr.getName(), docPosition));
                    }
                    docPosition += 1;
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }

        ii.saveInvertedIndex();

    }



}
