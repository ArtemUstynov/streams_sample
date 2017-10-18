package com.singlemind;


import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Practice {
    public static void main(String[] args) {
        /*
        Creating infinetly long stream of numbers
         */
    //    Stream<BigInteger> integers =
    //           Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE)).limit(1000000) ;
        /**
        Count words longer, than given length from within a file
         */
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(
                    Paths.get ("src/doc.txt")), StandardCharsets. UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stream<String> words= Stream.of(contents.split("\\PL+"));
        long count = words
                .filter(w -> w.length() > 12)
                .count ();
        System.out.println(count);
        words.close();

        /**getting last word by alphabetic order*/
        Stream<String> words1= Stream.of(contents.split("\\PL+"));
        Optional<String> largest = words1.max(String::compareToIgnoreCase);
        System.out.println("first 5 largest: " + largest.get());

        /** outputs first 5 words larger than 15 letters*/
        Stream<String> wordsLarger= Stream.of(contents.split("\\PL+"));
        wordsLarger.filter(w -> w.length()>15).limit(5).forEach(System.out::println);

        /**Comparing time to run in parallel and in single stream*/
        long time0 = System.currentTimeMillis();
        Stream<String> wordsSingleStream= Stream.of(contents.split("\\PL+"));
        long count1 = wordsSingleStream
                .filter(w -> w.length() > 12)
                .count ();
        System.out.println("\n\n\nChecking speed "+count);

        long time1 = System.currentTimeMillis();
        long finalTime = time1-time0;
        System.out.println("Run time =  "+finalTime);
///PARALEL
         time0 = System.currentTimeMillis();
        Stream<String> wordsParalel= Stream.of(contents.split("\\PL+")).parallel();
        long count2 = wordsParalel
                .filter(w -> w.length() > 12)
                .count ();
        System.out.println("\n\n\nChecking speed "+count2);

         time1 = System.currentTimeMillis();
         finalTime = time1-time0;
        System.out.println("Run time parallel =  "+finalTime);
        
        /**Incrementing stream of values by 1 */
        int[] values = { 1, 4, 9, 16 };
        IntStream.of(values).forEach(value -> System.out.println(value+=1));

        /** Find how many unique words exists, ignoring the case*/
        ArrayList<String> lowerCase = new ArrayList<>();
        Stream<String> wordsCount= Stream.of(contents.split("\\PL+"));
        wordsCount.distinct().forEach(e->lowerCase.add(e.toLowerCase()));

        System.out.println("\nUnique words "+lowerCase.stream().distinct().count() +"\n\n");
        /** output all unique words */
        lowerCase.stream().distinct().forEach(System.out::println);

        /**Find how many repetitions of a given word*/
        Stream<String> countUsage= Stream.of(contents.split("\\PL+"));
        long usage = countUsage.filter(e->e.toLowerCase().equals("millions")).count();
        System.out.println("\nWord was used : "+ String.valueOf(usage));

        /**Find strings, where word was used*/
        Stream<String> findSting= Stream.of(contents.split("\\r?\\n"));
        ArrayList<String> stringWithWord = new ArrayList<>();
        findSting.filter(e->e.toLowerCase().contains("millions")).forEach(stringWithWord::add);
        System.out.println("\nWord was found in this strings :");
        for (String s:stringWithWord ) {
            System.out.println(s);
        }

    }
}
