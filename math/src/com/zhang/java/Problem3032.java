package com.zhang.java;

/**
 * @Date 2025/4/14 08:47
 * @Author zsy
 * @Description 统计各位数字都不同的数字个数 II 类比Problem357
 * 给你两个 正整数 a 和 b ，返回 闭区间 [a, b] 内各位数字都不同的数字个数。
 * <p>
 * 输入：a = 1, b = 20
 * 输出：19
 * 解释：除 11 以外，区间 [1, 20] 内的所有数字的各位数字都不同。因此，答案为 19 。
 * <p>
 * 输入：a = 9, b = 19
 * 输出：10
 * 解释：除 11 以外，区间 [1, 20] 内的所有数字的各位数字都不同。因此，答案为 10 。
 * <p>
 * 输入：a = 80, b = 120
 * 输出：27
 * 解释：区间 [80, 120] 内共有 41 个整数，其中 27 个数字的各位数字都不同。
 * <p>
 * 1 <= a <= b <= 1000
 */
public class Problem3032 {
    public static void main(String[] args) {
        Problem3032 problem3032 = new Problem3032();
        int a = 1;
        int b = 20;
        System.out.println(problem3032.numberCount(a, b));
    }

    /**
     * 模拟
     * 时间复杂度O((b-a)*logb)=O(b-a)，空间复杂度O(10)=O(1)
     *
     * @param a
     * @param b
     * @return
     */
    public int numberCount(int a, int b) {
        int count = 0;

        for (int i = a; i <= b; i++) {
            boolean[] visited = new boolean[10];
            int num = i;
            //当前数字num是否各位数字都不同标志位
            boolean flag = true;

            while (num != 0) {
                int cur = num % 10;

                if (visited[cur]) {
                    flag = false;
                    break;
                }

                visited[cur] = true;
                num = num / 10;
            }

            if (flag) {
                count++;
            }
        }

        return count;
    }
}
