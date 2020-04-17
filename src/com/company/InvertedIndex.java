/*
Inverted index stores a term and all occurrences of that word in other documents, represented by
a ValueObject (which holds the document found in and its position in the document).

note[1]: add term to inverted index (hashtable) if it does not already exist,
otherwise, append another ValueObject to the exisiting ArrayList
 */


package com.company;

import com.sun.jdi.Value;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

// TODO: 4/15/2020 | Serialize and deserialize Inverted Index for storing and future use

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
            System.out.println("From Inverted Index: Query ('" + keyWord + "') does not exsit in inverted index");
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
            FileOutputStream fos = new FileOutputStream("src/com/savedInvertedIndex/serializedInvertedIndex.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ht);
            oos.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void loadInvertedIndex(){
        try{
            FileInputStream fis = new FileInputStream("src/com/savedInvertedIndex/serializedInvertedIndex.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ht = (Hashtable<String, ArrayList<ValueObject>>)ois.readObject();
        }
        catch(Exception e){
            System.out.println("From InvertedIndex: " + e);
        }
    }

    // Used by build
    private String[] convertToWordArray(String line){
        return line.split(" ");
    }

    public void build(String corpusPath){

        File corpusRoot = new File(corpusPath);

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
                    // TODO: FILTER html, css AND js

                    // TODO: FILTER STOPWORDS

                    // TODO: stem words

                    for (String keyword: words){
                        add(keyword, new ValueObject(curr.getName(), docPosition));
                    }
                    docPosition += 1;
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }

        saveInvertedIndex();

    }



}
