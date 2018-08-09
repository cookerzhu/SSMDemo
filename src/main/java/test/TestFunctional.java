package test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 在 Java 程序中采用函数式方法和语法有许多好处：代码简洁，更富于表达，不易出错，更容易并行化，而且通常比面向对象的代码更容易理解
 *
 * lambda 表达式是匿名函数 ( 只有参数列表和主体) (parameter list) -> body 在java中表达的是函数式接口
 *
 * 函数接口有 3 条重要法则：
 * 1、  一个函数接口只有一个抽象方法。
 * 2、  在 Object 类中属于公共方法的抽象方法不会被视为单一抽象方法。
 * 3、  函数接口可以有默认方法和静态方法。
 * 任何满足单一抽象方法法则的接口，都会被自动视为函数接口。这包括 Runnable 和 Callable 等传统接口，以及您自己构建的自定义接口。
 * Created by zhuguangchuan on 2018/4/17.
 */
public class TestFunctional {
    public static void main(String[] args) {
        Map<String, Integer> pageVisits = new HashMap<>();

        String page = "https://agiledeveloper.com";

        incrementPageVisit(pageVisits, page);
        pageVisits.forEach((k,v) -> System.out.println(k+":"+v) );

        IntStream.range(1,4).forEach(i->System.out.println(i));

        Runnable r = ()-> System.out.println("");

        Thread thread = new Thread(r);
    }

    public static void incrementPageVisit(Map<String, Integer> pageVisits, String page) {
        pageVisits.merge(page, 1, (oldValue, value) -> oldValue + value);
    }
}
