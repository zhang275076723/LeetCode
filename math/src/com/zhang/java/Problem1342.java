package com.zhang.java;

/**
 * @Date 2024/6/30 08:21
 * @Author zsy
 * @Description 将数字变成 0 的操作次数 类比Problem397、Problem453、Problem991、Problem1404、Problem2139、Problem2571 位运算类比
 * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。
 * 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
 * <p>
 * 输入：num = 14
 * 输出：6
 * 解释：
 * 步骤 1) 14 是偶数，除以 2 得到 7 。
 * 步骤 2） 7 是奇数，减 1 得到 6 。
 * 步骤 3） 6 是偶数，除以 2 得到 3 。
 * 步骤 4） 3 是奇数，减 1 得到 2 。
 * 步骤 5） 2 是偶数，除以 2 得到 1 。
 * 步骤 6） 1 是奇数，减 1 得到 0 。
 * <p>
 * 输入：num = 8
 * 输出：4
 * 解释：
 * 步骤 1） 8 是偶数，除以 2 得到 4 。
 * 步骤 2） 4 是偶数，除以 2 得到 2 。
 * 步骤 3） 2 是偶数，除以 2 得到 1 。
 * 步骤 4） 1 是奇数，减 1 得到 0 。
 * <p>
 * 输入：num = 123
 * 输出：12
 * <p>
 * 0 <= num <= 10^6
 */
public class Problem1342 {
    public static void main(String[] args) {
        Problem1342 problem1342 = new Problem1342();
        int num = 14;
        System.out.println(problem1342.numberOfSteps(num));
        System.out.println(problem1342.numberOfSteps2(num));
    }

    /**
     * 模拟
     * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public int numberOfSteps(int num) {
        int count = 0;

        while (num != 0) {
            //num为偶数，则num变为num/2
            if (num % 2 == 0) {
                num = num / 2;
            } else {
                //num为奇数，则num减1
                num--;
            }

            count++;
        }

        return count;
    }

    /**
     * 位运算
     * 当num为偶数时，除2相当于num二进制表示的值右移1位；当num为奇数时，减1相当于num二进制表示的值的末尾1置为0
     * num变为0的操作次数=num二进制中1的个数+num二进制的长度-1
     * 时间复杂度O(log(num))=O(1)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public int numberOfSteps2(int num) {
        //num为0的特殊情况
        if (num == 0) {
            return 0;
        }

        //num二进制中1的个数
        int count = 0;
        int n = num;

        while (n != 0) {
            count++;
            n = n & (n - 1);
        }

        //num二进制的长度
        int length = 0;
        n = num;

        while (n != 0) {
            length++;
            n = n >>> 1;
        }

        return count + length - 1;
    }
}
