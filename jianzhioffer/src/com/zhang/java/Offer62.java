package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/8 8:33
 * @Author zsy
 * @Description 圆圈中最后剩下的数字 华为面试题
 * 0, 1, ···, n-1这n个数字排成一个圆圈，从数字0开始，
 * 每次从这个圆圈里删除第m个数字（删除后从下一个数字开始计数）。求出这个圆圈里剩下的最后一个数字。
 * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，
 * 则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
 * <p>
 * 输入: n = 5, m = 3
 * 输出: 3
 * <p>
 * 输入: n = 10, m = 17
 * 输出: 2
 * <p>
 * 1 <= n <= 10^5
 * 1 <= m <= 10^6
 */
public class Offer62 {
    public static void main(String[] args) {
        Offer62 offer62 = new Offer62();
        System.out.println(offer62.lastRemaining(5, 3));
        System.out.println(offer62.lastRemaining2(5, 3));
        System.out.println(offer62.lastRemaining3(5, 3));
    }


    /**
     * 模拟
     * 将元素放在集合中，每次移除当前位置的之后的第m个元素
     * 时间复杂度(n^2)，空间复杂度O(n)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        //存放0到n-1，n个数
        List<Integer> list = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        //当前要删除的元素索引
        int index = 0;

        while (list.size() > 1) {
            index = (index + m - 1) % list.size();
            list.remove(index);
        }

        return list.get(0);
    }

    /**
     * 动态规划，约瑟夫环
     * f(n,m)：n个元素每次移除第m个元素，最后剩下的元素索引下标
     * f(n,m) = (m % n + f(n-1,m)) % n = (m + f(n-1,m)) % n
     * (n个元素移除第m%n个元素，剩下n-1个元素，从第m%n个元素开始，n-1个元素最后剩下的元素索引下标为f(n-1,m)，
     * 即(m % n + f(n-1,m)) % n，为n个元素最后剩下的元素索引下标)
     * 时间复杂度(n)，空间复杂度O(n)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining2(int n, int m) {
        return f(n, m);
    }

    /**
     * 动态规划优化，约瑟夫环
     * 公式：第i轮元素索引 = (第i+1轮元素索引 + m) % 第i轮元素个数
     * 第一轮：0 1 2 3 4 取出2
     * 第二轮：3 4 0 1   取出0
     * 第三轮：1 3 4     取出4
     * 第四轮：1 3       取出1
     * 第五轮：3         取出3
     * 最后一轮元素索引为0，即为要求的元素，可以推出第四轮元素的索引为(0 + 3) % 2 = 1
     * 根据第四轮元素索引为1，可以推出第三轮元素的索引为(1 + 3) % 3 = 1
     * 根据第三轮元素索引为1，可以推出第二轮元素的索引为(1 + 3) % 4 = 0
     * 根据第二轮元素索引为1，可以推出第一轮元素的索引为(0 + 3) % 5 = 3
     * 根据第一轮元素索引为3，可以得到要求的元素为3
     * 时间复杂度(n)，空间复杂度O(1)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining3(int n, int m) {
        int index = 0;

        // 最后一轮剩下2个人，所以从2开始反推
        for (int i = 2; i <= n; i++) {
            index = (index + m) % i;
        }

        return index;
    }

    private int f(int n, int m) {
        if (n == 1) {
            return 0;
        }

        return (m + f(n - 1, m)) % n;
    }
}
