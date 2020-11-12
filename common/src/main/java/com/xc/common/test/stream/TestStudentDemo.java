package com.xc.common.test.stream;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.nio.file.OpenOption;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStudentDemo {
    public static void main(String[] args){
        System.out.println("start");

        List<TestStudentModel> studentList = new ArrayList<>();
        for(int x=1;x<=10;x++){
            studentList.add(new TestStudentModel(x,"name"+x,x,x*10.1));
        }
        studentList.add(new TestStudentModel(11,"name10",10,11*10.1));
        System.out.println(new Gson().toJson(studentList));

        //方式一
        //返回一个顺序流
        Stream<TestStudentModel> stream=studentList.stream();
        //返回一个并行流
        Stream<TestStudentModel> stream2=studentList.parallelStream();


        //方式二
        //获取一个整形Stream
        int[] arr=new int[]{1,2,3,45,};
        IntStream intStream=Arrays.stream(arr);
        //获取一个Student对象的Stream
        TestStudentModel[] arrr2=new TestStudentModel[studentList.size()];
        studentList.toArray(arrr2);
        Stream<TestStudentModel> stream3=Arrays.stream(arrr2);

        //方式三
        Stream<Integer> stream31=Stream.of(1,2,3,4,5);
        Stream<TestStudentModel> stream32=Stream.of(
                new TestStudentModel(1,"name1",1,1*10.1),
                new TestStudentModel(2,"name2",2,1*10.1),
                new TestStudentModel(3,"name3",3,1*10.1)
        );


        //读取第一个
        Optional<TestStudentModel> fmodel1=stream2.findFirst();
        System.out.println(new Gson().toJson(fmodel1));
        Map<Integer,TestStudentModel> map=studentList.stream().collect(Collectors.toMap(TestStudentModel::getId,e->e));
        System.out.println(new Gson().toJson(map));
        //只获取ID
        List<Integer> list1=studentList.parallelStream().map(TestStudentModel::getId).collect(Collectors.toList());
        System.out.println(new Gson().toJson(list1));
        //按id分组
        Map<Integer,List<TestStudentModel>> map1=studentList.stream().collect(Collectors.groupingBy(TestStudentModel::getId));
        System.out.println(new Gson().toJson(map1));


        boolean b1=studentList.stream().anyMatch(e->(e.getId()==110));
        System.out.println(new Gson().toJson(b1));
        boolean b2=studentList.stream().noneMatch(e->(e.getId()==110));
        System.out.println(new Gson().toJson(b2));
        //studentList.parallelStream().map(List::size)

        //返回第一个id=10的对象
        Optional<TestStudentModel> f1=studentList.parallelStream().filter(e->(e.getId()==10)).findAny();
        System.out.println(new Gson().toJson(f1));
        //返回age=11的对象集合
        List<TestStudentModel> list11=studentList.parallelStream().filter(e->(e.getAge()==10)).collect(Collectors.toList());
        System.out.println(new Gson().toJson(list11));

    }
}
