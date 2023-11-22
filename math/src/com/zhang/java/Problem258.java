package com.zhang.java;

/**
 * @Date 2023/10/9 08:30
 * @Author zsy
 * @Description 各位相加 类比Problem202、Problem292
 * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
 * <p>
 * 输入: num = 38
 * 输出: 2
 * 解释: 各位相加的过程为：
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * 由于 2 是一位数，所以返回 2。
 * <p>
 * 输入: num = 0
 * 输出: 0
 * <p>
 * 0 <= num <= 2^31 - 1
 */
public class Problem258 {
    public static void main(String[] args) {
        Problem258 problem258 = new Problem258();
        int num = 38;
        System.out.println(problem258.addDigits(num));
        System.out.println(problem258.addDigits2(num));
    }

    /**
     * 模拟
     * 时间复杂度O(lognum)=O(1)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public int addDigits(int num) {
        if (0 <= num && num <= 9) {
            return num;
        }

        int result = num;

        //当前result不在0-9，即result不是一位数，继续将各个位上的数字相加
        while (!(0 <= result && result <= 9)) {
            //result各个位上的数字相加的值
            int next = 0;

            while (result != 0) {
                next = next + result % 10;
                result = result / 10;
            }

            result = next;
        }

        return result;
    }

    /**
     * 数学
     * 一个数xyz可以拆分为：xyz=100*x+10*y+z=99*x+9*y+x+y+z，99、9都能被9整除，
     * 通过模9，过滤掉了99*x+9*y，只剩下x+y+z，即数xyz每一位之和
     * 注意：需要考虑数xyz能被9整除的情况，如果直接模9，结果为0，则需要xyz先减1，模9，此时结果肯定不为0，再加1
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public int addDigits2(int num) {
        if (0 <= num && num <= 9) {
            return num;
        }

        //不能写为return num % 9;
        //需要考虑数xyz能被9整除的情况，如果直接模9，结果为0，则需要xyz先减1，模9，此时结果肯定不为0，再加1
        return (num - 1) % 9 + 1;
    }
}
