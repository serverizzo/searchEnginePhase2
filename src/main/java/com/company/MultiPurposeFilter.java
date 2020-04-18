package com.company;

import java.io.*;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class MultiPurposeFilter {

    Set<String> stopwords = new HashSet<String>();
    public MultiPurposeFilter(){
        stopwords = load();
    }

    public void build(){
        File stopwordPath = new File("./src/main/java/com/resources/stopwordList/stopwords_en.txt");
        try{
            Scanner s = new Scanner(stopwordPath);
            while(s.hasNextLine()){
                    this.stopwords.add(s.nextLine());
                }
            save();
            }catch(Exception e){
            System.err.println("From MultiPurposeFilter: During build: " + e);
        }
    }

    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream("./src/main/java/com/savedStopwordList/serializedStopword.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(stopwords);
            oos.close();
        }
        catch (Exception e){
            System.err.println("From MultiPurposeFilter: During save: " + e);
            return;
        }
        System.out.println("From MultiPurposeFilter: During save: Successfully saved");
    }

    public HashSet<String> load(){
        File serializedStopWordPath = new File("./src/main/java/com/savedStopwordList/serializedStopword.txt");

        try{
            FileInputStream fis = new FileInputStream(serializedStopWordPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (HashSet<String>)ois.readObject();
        } catch(Exception e){
            System.err.println("From MultiPurposeFilter: During load: " + e);
        }
        return null;
    }

    public void dump(){
        Object[] arr = this.stopwords.toArray();
        for(Object o : arr){
            System.out.println(o);
        }
    }


    public boolean isCodeToBeFiltered(String word){

        return false;
    }

    public boolean isStopwordToBeFiltered(String word){
        return this.stopwords.contains(word);
    }
}
