package test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * <doc>https://www.ibm.com/developerworks/cn/java/j-java8idioms11/index.html</doc>
 * 函数管道的惰性计算、并行执行（并行流使用了与系统上的核心数一样多的线程）：
 * Stream 累积并组合或融合中间操作，然后执行它们。它仅执行满足最终操作所必需的工作。因为中间操作被融合，
 * 所以流对管道中数据的处理方式存在一个重要区别：Stream 不会像命令式代码一样执行数据集合上的每个函数，
 * 而是执行每个元素上的函数的融合集合，但仅在需要时执行。
 *
 *  函数管道中的所有 lambda 表达式和闭包都必须是纯的
 * 函数纯度有两个规则：
 * 函数不会更改任何元素。
 * 函数不依赖于任何可能更改的元素。
 * 纯函数绝不会在执行期间引起更改或发生更改。
 * Created by zhuguangchuan on 2018/4/17.
 * javap -c TestLambda >> TestLambda.txt
 */
public class TestLambda {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        int[] factor = new int[] { 2 };
        Stream<Integer> stream = numbers.stream()
                .map(e -> e * factor[0]);

        //factor[0] 是可变的，从创建闭包到最终计算它的过程中，该值可以是任何值
        //由于惰性计算，作为参数传递给 map 的闭包只在调用 forEach 后才会计算
        factor[0] = 0;

        stream.forEach(System.out::println);
    }
}
