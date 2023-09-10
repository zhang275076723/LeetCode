package com.zhang.java;

/**
 * @Date 2023/9/9 08:18
 * @Author zsy
 * @Description 完美数 各种数类比
 * 对于一个 正整数，如果它和除了它自身以外的所有 正因子 之和相等，我们称它为 「完美数」。
 * 给定一个 整数 n， 如果是完美数，返回 true；否则返回 false。
 * <p>
 * 输入：num = 28
 * 输出：true
 * 解释：28 = 1 + 2 + 4 + 7 + 14
 * 1, 2, 4, 7, 和 14 是 28 的所有正因子。
 * <p>
 * 输入：num = 7
 * 输出：false
 * <p>
 * 1 <= num <= 10^8
 */
public class Problem507 {
    public static void main(String[] args) {
        Problem507 problem507 = new Problem507();
        int num = 28;
        System.out.println(problem507.checkPerfectNumber(num));
    }

    /**
     * 模拟
     * num除了自身之外所有的正因子之和，判断是否等于num
     * 时间复杂度O(num^(1/2))，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public boolean checkPerfectNumber(int num) {
        if (num == 1) {
            return false;
        }

        //num除了自身之外所有的正因子之和，初始化为1
        int sum = 1;
        int sqrt = (int) Math.sqrt(num);

        for (int i = 2; i <= sqrt; i++) {
            if (num % i == 0) {
                //i和num/i相等，相同因子只能累加1次
                if (i == num / i) {
                    sum = sum + i;
                } else {
                    sum = sum + i + num / i;
                }
            }
        }

        return sum == num;
    }
}
