package com.xc.common.test.stream;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] args){

        System.out.println("start");
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println(new Gson().toJson(filtered));


        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

        List<String> list = new ArrayList<>();
        list.add("武汉加油");
        list.add("中国加油");
        list.add("世界加油");
        list.add("世界加油");

        List<String> list2 = list.stream().distinct().collect(Collectors.toList());
        System.out.println(new Gson().toJson(list2));
        long count = list.stream().distinct().count();
        System.out.println(count);

    }


}
