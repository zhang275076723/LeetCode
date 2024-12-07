package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/12/6 10:45
 * @Author zsy
 * @Description 快乐数 类比Problem258 类比Problem141、Problem142、Problem160、Problem457、Problem565、Offer52 各种数类比Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49
 * 编写一个算法来判断一个数 n 是不是快乐数。
 * 「快乐数」定义为：
 * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
 * 如果这个过程 结果为 1，那么这个数就是快乐数。
 * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
 * <p>
 * 输入：n = 19
 * 输出：true
 * 解释：
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 * <p>
 * 输入：n = 2
 * 输出：false
 * <p>
 * 1 <= n <= 2^31 - 1
 */
public class Problem202 {
    public static void main(String[] args) {
        Problem202 problem202 = new Problem202();
        int n = 2;
        System.out.println(problem202.isHappy(19));
        System.out.println(problem202.isHappy2(n));
    }

    /**
     * 哈希表
     * 核心思想：快乐数进行变化，最终会回到1；不是快乐数进行变化，会形成一个循环
     * 例如：n=2
     * 2->4->16->37->58->89->145->42->20
     * < /\                           |
     * < | <-------------------------\/
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public boolean isHappy(int n) {
        if (n == 1) {
            return true;
        }

        Set<Integer> set = new HashSet<>();
        set.add(n);

        while (true) {
            n = nextNum(n);

            //n为1，则是快乐数，返回true
            if (n == 1) {
                return true;
            }

            //set中已经存在n，则不是快乐数
            if (set.contains(n)) {
                return false;
            }

            set.add(n);
        }
    }

    /**
     * 快慢指针
     * 核心思想：快乐数进行变化，最终会回到1；不是快乐数进行变化，会形成一个循环
     * 例如：n=2
     * 2->4->16->37->58->89->145->42->20
     * < /\                           |
     * < | <-------------------------\/
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean isHappy2(int n) {
        if (n == 1) {
            return true;
        }

        //快慢指针
        int slow = n;
        int fast = n;

        while (nextNum(fast) != 1 && nextNum(nextNum(fast)) != 1) {
            slow = nextNum(slow);
            fast = nextNum(nextNum(fast));

            //存在环，即快慢指针两数相等，则不是快乐数，返回false
            if (slow == fast) {
                return false;
            }
        }

        //不存在环，即最后回到1，则是快乐数，返回true
        return true;
    }

    /**
     * 得到n每位的平方之和
     *
     * @param n
     * @return
     */
    private int nextNum(int n) {
        int num = 0;

        while (n != 0) {
            num = num + (n % 10) * (n % 10);
            n = n / 10;
        }

        return num;
    }
}
