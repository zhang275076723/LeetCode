package com.zhang.java;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Date 2023/12/20 08:20
 * @Author zsy
 * @Description 消除游戏 类比Problem258、Problem292、Problem293、Problem294、Problem464、Problem486、Problem1823、Offer62
 * 列表 arr 由在范围 [1, n] 中的所有整数组成，并按严格递增排序。请你对 arr 应用下述算法：
 * 从左到右，删除第一个数字，然后每隔一个数字删除一个，直到到达列表末尾。
 * 重复上面的步骤，但这次是从右到左。也就是，删除最右侧的数字，然后剩下的数字每隔一个删除一个。
 * 不断重复这两步，从左到右和从右到左交替进行，直到只剩下一个数字。
 * 给你整数 n ，返回 arr 最后剩下的数字。
 * <p>
 * 输入：n = 9
 * 输出：6
 * 解释：
 * arr = [1, 2, 3, 4, 5, 6, 7, 8, 9]
 * arr = [2, 4, 6, 8]
 * arr = [2, 6]
 * arr = [6]
 * <p>
 * 输入：n = 1
 * 输出：1
 * <p>
 * 1 <= n <= 10^9
 */
public class Problem390 {
    public static void main(String[] args) {
        Problem390 problem390 = new Problem390();
        int n = 12;
        System.out.println(problem390.lastRemaining(n));
        System.out.println(problem390.lastRemaining2(n));
    }

    /**
     * 暴力
     * 使用队列模拟每隔一个元素删除一个元素，最后剩下的元素即为最后结果
     * 时间复杂度O(n)，空间复杂度O(n) (n+n/2+n/4+...+2+1=(1-2^(logn))/(1-2)=n，等差数列)
     *
     * @param n
     * @return
     */
    public int lastRemaining(int n) {
        Deque<Integer> queue = new LinkedList<>();
        //当前遍历的顺序，初始化为1表示从前往后遍历
        int flag = 1;

        for (int i = 1; i <= n; i++) {
            queue.offerLast(i);
        }

        while (queue.size() > 1) {
            int size = queue.size();
            Deque<Integer> queue2 = new LinkedList<>();

            for (int i = 0; i < size; i = i + 2) {
                if (flag == 1) {
                    queue.pollFirst();
                    //queue首移除元素之后还有元素，才能将queue中元素入queue2中
                    if (!queue.isEmpty()) {
                        queue2.offerLast(queue.pollFirst());
                    }
                } else {
                    queue.pollLast();
                    //queue尾移除元素之后还有元素，才能将queue中元素入queue2中
                    if (!queue.isEmpty()) {
                        queue2.offerFirst(queue.pollLast());
                    }
                }
            }

            flag = -flag;
            queue = queue2;
        }

        return queue.peekLast();
    }

    /**
     * 模拟
     * 每遍历一遍，都删除一半的元素，并且相邻元素相差的步长每次都乘2，
     * 从左往右遍历，第一个元素要删除；从右往左遍历，只有当前元素的个数为奇数时，第一个元素才会删除，
     * 此时第一个元素为result加上相邻元素相差的步长step，直至剩余一个元素时，即为最后结果
     * 时间复杂度O(logn)，空间复杂度O(1)
     * <p>
     * 例如：1 2 3 4 5 6 7 8 9 10 11 12  result = 1, step = 1, flag = 1, n = 12
     * 1、从左往右遍历：2 4 6 8 10 12     result = 2, step = 2, flag = 2, n = 6
     * 2、从右往左遍历：2 6 10            result = 2, step = 4, flag = 1, n = 3
     * 3、从左往右遍历：6                 result = 6, step = 8, flag = 2, n = 1
     * 4、只剩一个元素，即为最后结果
     *
     * @param n
     * @return
     */
    public int lastRemaining2(int n) {
        //初始化第一个元素为1，因为1-n从左往右第一个元素为1
        int result = 1;
        //相邻元素相差的步长
        int step = 1;
        //当前遍历的顺序，初始化为1表示从前往后遍历
        int flag = 1;

        while (n > 1) {
            //从左往右遍历，第一个元素要删除；从右往左遍历，只有当前元素的个数为奇数时，第一个元素才会删除，
            //此时第一个元素为result加上相邻元素相差的步长step
            if (flag == 1 || n % 2 == 1) {
                result = result + step;
            }

            n = n / 2;
            step = step * 2;
            flag = -flag;
        }

        return result;
    }
}
