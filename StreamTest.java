package org.apache.http.examples.nio.client;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import static  java.util.stream.Collectors.*;
import java.util.stream.Stream;

/**
 * Created by jinyangyang on 08/04/2017 5:40 PM.
 * 用于学习Collectors的源代码
 */
public class StreamTest {
    public static void main(String[] args) {

        String[] names = {"jyy", "zll", "jyy", "jyy", "zll", "jxb"};

        Stream<String> peoples = Stream.of(names);

        Map<String,List<String>> personByName = peoples.collect(groupingBy( name -> name ));

        System.out.println(personByName);

        peoples = Stream.of(names);

        Map<String,IntSummaryStatistics> personnameandCount =  peoples.collect(
                groupingBy( Function.identity(), summarizingInt( (t -> 1)) ) );

        System.out.println(personnameandCount);

        //采用counting
        peoples = Stream.of(names);
        Map<String,Long> results1 = peoples.collect(
                groupingBy( Function.identity(), counting() ) );
        System.out.println("results1=>"+results1);

        //采用toMap的方式
        peoples = Stream.of(names);
        Map<String,Integer> results = peoples.collect(toMap(Function.identity(),(t -> 1),
                (s, a) -> s + a));
        System.out.println("results=>"+results);

        //reducing 内部真正调用的
        peoples = Stream.of(names);
        Map<String,Integer> results2 = peoples.collect(
                groupingBy( Function.identity(), reducing(0,(t-> 1) ,( (acc,c) -> acc +c ) ) ) );
        System.out.println("results2=>"+results2);

    }
}
