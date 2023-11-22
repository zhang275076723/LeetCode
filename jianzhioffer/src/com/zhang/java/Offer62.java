package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/4/8 8:33
 * @Author zsy
 * @Description 圆圈中最后剩下的数字 华为面试题 字节面试题 类比Problem292 类比Problem312、Problem375、Problem887、Problem1887、CircleBackToOrigin 同Problem1823
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
     * 时间复杂度((m%n)*n)，空间复杂度O(n)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            queue.offer(i);
        }

        while (queue.size() != 1) {
            //每次移除第m个元素，则m-1个元素依次出队再入队，(m-1)%queue.size()保证一次遍历能够移除元素
            for (int i = 0; i < (m - 1) % queue.size(); i++) {
                queue.offer(queue.poll());
            }

            queue.poll();
        }

        return queue.peek();
    }

    /**
     * 数学，约瑟夫环
     * f(n,m)：n个元素每次移除第m个元素，最后剩下元素在原先n个元素中的下标索引
     * f(n,m) = (f(n-1,m) + m) % n
     * n个元素移除第m个元素，剩余n-1个元素，此时n-1个元素由原来n个元素整体左移m位得到，
     * 假设n个元素每次移除第m个元素，最后剩下元素在原先n个元素中的下标索引为A，则移除第m个元素，剩余n-1个元素，
     * 此时A也左移了m位，即f(n,m)左移m位得到f(n-1,m)，即f(n,m)=(f(n-1,m)+m)%n
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining2(int n, int m) {
        return f(n, m);
    }

    /**
     * 数学，约瑟夫环的非递归形式
     * f(n,m)：n个元素每次移除第m个元素，最后剩下元素在原先n个元素中的下标索引
     * f(n,m) = (f(n-1,m) + m) % n
     * n个元素移除第m个元素，剩余n-1个元素，此时n-1个元素由原来n个元素整体左移m位得到，
     * 假设n个元素每次移除第m个元素，最后剩下元素在原先n个元素中的下标索引为A，则移除第m个元素，剩余n-1个元素，
     * 此时A也左移了m位，即f(n,m)左移m位得到f(n-1,m)，即f(n,m)=(f(n-1,m)+m)%n
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining3(int n, int m) {
        //只有一个元素，该元素即为最后剩下的元素，下标索引为0
        int index = 0;

        //因为每次移除第m个元素，i-1个元素由原来i个元素整体左移m位得到，所以i-1个元素整体右移m位得到i个元素
        for (int i = 2; i <= n; i++) {
            index = (index + m) % i;
        }

        return index;
    }

    private int f(int n, int m) {
        //只有一个元素，该元素即为最后剩下的元素，下标索引为0
        if (n == 1) {
            return 0;
        }

        return (f(n - 1, m) + m) % n;
    }
}
