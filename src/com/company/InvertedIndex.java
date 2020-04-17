/*
Inverted index stores a term and all occurrences of that word in other documents, represented by
a ValueObject (which holds the document found in and its position in the document).

note[1]: add term to inverted index (hashtable) if it does not already exist,
otherwise, append another ValueObject to the exisiting ArrayList
 */


package com.company;

import com.sun.jdi.Value;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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


}
