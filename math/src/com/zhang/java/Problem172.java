package com.zhang.java;

/**
 * @Date 2023/7/31 08:10
 * @Author zsy
 * @Description 阶乘后的零 小红书面试题 模拟类比Problem60、Problem233、Problem400、Offer43、Offer44
 * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
 * 提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
 * <p>
 * 输入：n = 3
 * 输出：0
 * 解释：3! = 6 ，不含尾随 0
 * <p>
 * 输入：n = 5
 * 输出：1
 * 解释：5! = 120 ，有一个尾随 0
 * <p>
 * 输入：n = 0
 * 输出：0
 * <p>
 * 0 <= n <= 10^4
 */
public class Problem172 {
    public static void main(String[] args) {
        Problem172 problem172 = new Problem172();
        int n = 10;
        System.out.println(problem172.trailingZeroes(n));
        System.out.println(problem172.trailingZeroes2(n));
    }

    /**
     * 模拟
     * 核心思想：1-n中每出现一个因子2和因子5，即1-n相乘末尾出现一个0
     * 因为因子2出现的次数大于因子5出现的次数，所以只需要统计1-n中每个数因子5出现的次数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int trailingZeroes(int n) {
        if (n == 0) {
            return 0;
        }

        int count = 0;

        for (int i = 1; i <= n; i++) {
            int j = i;
            //统计当前数j存在因子5的个数
            while (j % 5 == 0) {
                count++;
                j = j / 5;
            }
        }

        return count;
    }

    /**
     * 模拟优化
     * 核心思想：1-n中每出现一个因子2和因子5，即1-n相乘末尾出现一个0
     * 因为因子2出现的次数大于因子5出现的次数，所以只需要统计1-n中每个数因子5出现的次数
     * 每隔5个数，出现一次因子5；每隔25个数，出现一次因子5；每隔125个数，出现一次因子5...
     * 统计完1-n中每隔5个数出现的因子5之后，n除以5，相当于统计每隔25个数出现的因子5
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int trailingZeroes2(int n) {
        if (n == 0) {
            return 0;
        }

        int count = 0;

        while (n != 0) {
            //每隔5个数，出现一次因子5
            count = count + n / 5;
            //统计完1-n中每隔5个数出现的因子5之后，n除以5，相当于统计每隔25个数出现的因子5
            n = n / 5;
        }

        return count;
    }
}
