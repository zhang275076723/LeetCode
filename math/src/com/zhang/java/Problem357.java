package com.zhang.java;

/**
 * @Date 2023/10/5 08:30
 * @Author zsy
 * @Description 统计各位数字都不同的数字个数 类比Problem3032 类比Problem386、Problem440、Offer17 回溯+剪枝类比 动态规划类比
 * 给你一个整数 n ，统计并返回各位数字都不同的数字 x 的个数，其中 0 <= x < 10^n 。
 * <p>
 * 输入：n = 2
 * 输出：91
 * 解释：答案应为除去 11、22、33、44、55、66、77、88、99 外，在 0 ≤ x < 100 范围内的所有数字。
 * <p>
 * 输入：n = 0
 * 输出：1
 * <p>
 * 0 <= n <= 8
 */
public class Problem357 {
    public static void main(String[] args) {
        Problem357 problem357 = new Problem357();
        int n = 2;
        System.out.println(problem357.countNumbersWithUniqueDigits(n));
        System.out.println(problem357.countNumbersWithUniqueDigits2(n));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(10^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int countNumbersWithUniqueDigits(int n) {
        return backtrack(0, 0, n, new boolean[10]);
    }

    /**
     * 模拟
     * n=1，则共10种情况
     * n=2，第1位为0，则第2位为n=1的情况；第1位为1-9，第2位为0-9，则共9*9种情况，总共10+9*9种情况
     * n=3，第1位为0，第2位为0，则第3位为n=1的情况；第1位为0，第2位和第3位为n=2的情况；第1位为1-9，第2位和第3位为0-9，
     * 则共9*9*8，总共10+9*9+9*9*8种情况
     * ...
     * 依次类推，从1-n依次计算，得到长度为n的情况
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int countNumbersWithUniqueDigits2(int n) {
        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return 10;
        }

        //n从2开始考虑，初始化n=1共10种情况
        int count = 10;
        //n位数字中第1位不为0的情况，即9*9*8*7*...*
        int temp = 9;

        for (int i = 2; i <= n; i++) {
            temp = temp * (10 - i + 1);
            count = count + temp;
        }

        return count;
    }

//    /**
//     * 动态规划
//     * dp[i]：[0,10^i)中各位数字都不同的数字的个数
//     * dp[i] = dp[i-1] + (dp[i-1]-dp[i-2])*(10-i+1)
//     * dp[i]包括[0,10^(i-1))中各位数字都不同的数字的个数，即dp[i-1]，和[10^(i-1),10^i)中各位数字都不同的数字的个数，
//     * 即[10^(i-2),10^(i-1))中各位数字都不同的数字的个数，即dp[i-1]-dp[i-2]，乘以(10-i+1)，
//     * 因为[10^(i-2),10^(i-1))中各位数字都不同的数字占0-9中10位数字的i-1位，还剩(10-i+1)位不同的数字可以选择
//     * 时间复杂度O(n)，空间复杂度O(n)
//     * <p>
//     * 例如：n=3，dp[3]包括[0,100)中各位数字都不同的数字的个数dp[2]，和[100,1000)中各位数字都不同的数字的个数组成，
//     * [100,1000)中各位数字都不同的数字的个数由[10,100)中各位数字都不同的数字拼接上和[10,100)中各位数字都不同的数字组成，
//     * [10,100)中各位数字都不同的数字的个数dp[2]-dp[1]，和[10,100)中各位数字都不同的数字的个数10-3+1，
//     * 即为(dp[2]-dp[1])*(10-3+1)，dp[3]=dp[2]+(dp[2]-dp[1])*(10-3+1)
//     *
//     * @param n
//     * @return
//     */
//    public int countNumbersWithUniqueDigits3(int n) {
//        if (n == 0) {
//            return 1;
//        }
//
//        int[] dp = new int[n + 1];
//        dp[0] = 1;
//        dp[1] = 10;
//
//        for (int i = 2; i <= n; i++) {
//            dp[i] = dp[i - 1] + (dp[i - 1] - dp[i - 2]) * (10 - i + 1);
//        }
//
//        return dp[n];
//    }

    private int backtrack(int num, int len, int n, boolean[] visited) {
        if (len == n) {
            return 1;
        }

        int count = 0;

        for (int i = 0; i <= 9; i++) {
            //数字i已访问，则直接进行下次循环
            if (visited[i]) {
                continue;
            }

            //数字i为0，并且当前数字为0，则不能添加前导0
            if (i == 0 && num == 0) {
                count = count + backtrack(num, len + 1, n, visited);
            } else {
                visited[i] = true;
                count = count + backtrack(num * 10 + i, len + 1, n, visited);
                visited[i] = false;
            }
        }

        return count;
    }
}
