package com.company;

import com.sun.jdi.Value;

import java.io.IOException;
import java.nio.file.*;

public class Tester {

    private static void testValueObject(){
        ValueObject v = new ValueObject("SomeDoc", 53);
        v.print();
        System.out.println(v.toString());
    }


    // Case sensitive
    private static void testInvertedIndex(){
        InvertedIndex ii = new InvertedIndex();
        ValueObject v = new ValueObject("SomeDoc",53);
        ii.add("someKeyWord", v);
        ValueObject v2 = new ValueObject("SomeDoc",54);
        ii.add("someKeyWord", v2);
        ValueObject v3 = new ValueObject("SomeDoc",55);
        ii.add("someKeyWord", v3);


        ii.queryAllOccurences("someKeyWord");
        ii.queryAllOccurences("somethingThatDoseNotExsist");

        System.out.println(ii.queryIsInDoc("someKeyWord", "SomeDoc"));
        System.out.println(ii.queryIsInDoc("someKeyWord", "doc"));
        System.out.println(ii.queryIsInDoc("some", "SomeDoc"));
    }

    public static void testBuildInvertedIndex(){

    }

    public static void printDir(Path p){
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(p)) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }
    }

    public static void testdir(){
        // Current directory is always the root i.e. phase 2.1. (contents are .idea, out, phase2.1.iml, src)
        Path p1 = Paths.get("./");
//        printDir(p1);

        p1 = Paths.get("./src/com/company");
        printDir(p1);
    }

    public static void main(String[] args){
//        testValueObject(); // need to check if it is able to be serialized
//        testInvertedIndex();

//        testBuildInvertedIndex();

        testdir();



    }


}
