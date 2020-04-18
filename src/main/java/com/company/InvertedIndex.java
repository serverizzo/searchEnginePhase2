/*
Inverted index stores a term and all occurrences of that word in other documents, represented by
a ValueObject (which holds the document found in and its position in the document).

note[1]: add term to inverted index (hashtable) if it does not already exist,
otherwise, append another ValueObject to the exisiting ArrayList
 */


package com.company;


import opennlp.tools.stemmer.PorterStemmer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.lang.Object.*;

public class InvertedIndex {

    Map<String, ArrayList<ValueObject>> ht = new Hashtable<String, ArrayList<ValueObject>>();

//    note [1]
    public void add(String keyWord, ValueObject val){
        if (!ht.containsKey(keyWord)) {
            ht.put(keyWord, new ArrayList<ValueObject>());
            ht.get(keyWord).add(val);
        }
        else{
            ht.get(keyWord).add(val);
        }
    }

    public void queryAllOccurences(String keyWord){
        ArrayList<ValueObject> occurences = query(keyWord);
        if (occurences.size() == 0){
            return;
        }

        Object[] objArr = occurences.toArray();
        for (Object e : objArr) {
            ValueObject ve = (ValueObject) e;
            ve.print();
        }
    }

    public boolean queryIsInDoc(String keyWord, String doc){
        ArrayList<ValueObject> occurences = query(keyWord);
        if (occurences.size() == 0){
            return false;
        }
        Object[] objArr = occurences.toArray();
        for (Object e : objArr) {
            ValueObject ve = (ValueObject) e;
            if (ve.val[0].equals(doc)){
                return true;
            }
        }
        return false;
    }

    // get the term from the inverted index
    public ArrayList<ValueObject> query(String keyWord){
        if( !ht.containsKey(keyWord) ){
            System.err.println("From Inverted Index: Query ('" + keyWord + "') does not exsit in inverted index");
            return new ArrayList<ValueObject>();
        }
        ArrayList<ValueObject> arr = ht.get(keyWord);

        return arr;
    }

    public void dump(){
        Object[] sortedKeys = ht.keySet().toArray();
        Arrays.sort(sortedKeys);
        for( Object e: sortedKeys ){
            System.out.print(e.toString());
            for (ValueObject x: ht.get(e)){
                System.out.print(" " + x.toString());
            }
            System.out.println();
        }
    }

    public void saveInvertedIndex(){
        try {
            FileOutputStream fos = new FileOutputStream("./src/main/java/com/savedInvertedIndex/serializedInvertedIndex.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ht);
            oos.close();
        }
        catch (Exception e){
            System.err.println("From InvertedIndex: saveInvertedIndex: " + e);
        }
    }

    public void loadInvertedIndex(){
        try{
            FileInputStream fis = new FileInputStream("./src/main/java/com/savedInvertedIndex/serializedInvertedIndex.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ht = (Hashtable<String, ArrayList<ValueObject>>)ois.readObject();
        }
        catch(Exception e){
            System.err.println("From InvertedIndex: loadInvertedIndex: " + e);
        }
    }

    // Used by build
    private String[] convertToWordArray(String line){
        return line.split(" ");
    }

    //
    public void build(String corpusPath){

        File corpusRoot = new File(corpusPath);
        MultiPurposeFilter filter = new MultiPurposeFilter();
        PorterStemmer ps = new PorterStemmer();

        // Add all words in corpus into Inverted index
        for (File curr : corpusRoot.listFiles()){
            int docPosition = 0;

//            System.out.println(curr.getName());
            // open corpus doc
            Charset charset = Charset.forName("US-ASCII");
            try (BufferedReader reader = Files.newBufferedReader(curr.toPath(), charset)) { // Use a buffered reader to read each line of the document
                String line = null;
                String[] words = null;
                while ((line = reader.readLine()) != null) {                    // get each line of the document
                    words = convertToWordArray(line);                           // convert to line array to use in enhanced for loop
                    for (String keyword: words){
                        if(filter.isCodeToBeFiltered(keyword)){continue;}       // filter code
                        docPosition += 1;
                        keyword.toLowerCase();                                  // normalize keyword
                        if(filter.isStopwordToBeFiltered(keyword)){continue;}   // filter stopwords
                        keyword = ps.stem(keyword);                             // Stem Word


                        add(keyword, new ValueObject(curr.getName(), docPosition)); // add
                    }
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
        saveInvertedIndex();
    }



}
