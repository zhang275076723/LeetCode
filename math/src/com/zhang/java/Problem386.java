package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/9/13 8:28
 * @Author zsy
 * @Description 字典序排数 字典序类比Problem440、Offer17
 * 给你一个整数 n ，按字典序返回范围 [1, n] 内所有整数。
 * 你必须设计一个时间复杂度为 O(n) 且使用 O(1) 额外空间的算法。
 * <p>
 * 输入：n = 13
 * 输出：[1,10,11,12,13,2,3,4,5,6,7,8,9]
 * <p>
 * 输入：n = 2
 * 输出：[1,2]
 * <p>
 * 1 <= n <= 5 * 10^4
 */
public class Problem386 {
    public static void main(String[] args) {
        Problem386 problem386 = new Problem386();
        int n = 25;
        System.out.println(problem386.lexicalOrder(n));
        System.out.println(problem386.lexicalOrder2(n));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n)，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public List<Integer> lexicalOrder(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        //设置每个数的起始值，从1-9
        for (int i = 1; i <= 9; i++) {
            backtrack(i, n, list);
        }

        return list;
    }

    /**
     * 迭代
     * 假设当前值为num，如果num*10<=n，则num的下一个字典序为num*10；
     * 否则，如果num%10==9，或者num+1>n，则num末尾值字典序已经查询完毕，num=num/10，继续查找当前num的下一个字典序
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public List<Integer> lexicalOrder2(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        int num = 1;

        for (int i = 1; i <= n; i++) {
            list.add(num);

            //当前num的下一个字典序为num*10
            if (num * 10 <= n) {
                num = num * 10;
            } else {
                //num大于等于n，或者num末尾为9，说明num末尾值字典序已经查询完毕，查询num末尾位的前一位
                while (num >= n || num % 10 == 9) {
                    num = num / 10;
                }
                num++;
            }
        }

        return list;
    }

    private void backtrack(int t, int n, List<Integer> list) {
        if (t > n) {
            return;
        }

        list.add(t);

        for (int i = 0; i <= 9; i++) {
            backtrack(t * 10 + i, n, list);
        }
    }
}
