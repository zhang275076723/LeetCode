package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/3 08:49
 * @Author zsy
 * @Description 连续差相同的数字 类比Problem1291 回溯+剪枝类比
 * 返回所有长度为 n 且满足其每两个连续位上的数字之间的差的绝对值为 k 的 非负整数 。
 * 请注意，除了 数字 0 本身之外，答案中的每个数字都 不能 有前导零。
 * 例如，01 有一个前导零，所以是无效的；但 0 是有效的。
 * 你可以按 任何顺序 返回答案。
 * <p>
 * 输入：n = 3, k = 7
 * 输出：[181,292,707,818,929]
 * 解释：注意，070 不是一个有效的数字，因为它有前导零。
 * <p>
 * 输入：n = 2, k = 1
 * 输出：[10,12,21,23,32,34,43,45,54,56,65,67,76,78,87,89,98]
 * <p>
 * 输入：n = 2, k = 0
 * 输出：[11,22,33,44,55,66,77,88,99]
 * <p>
 * 输入：n = 2, k = 2
 * 输出：[13,20,24,31,35,42,46,53,57,64,68,75,79,86,97]
 * <p>
 * 2 <= n <= 9
 * 0 <= k <= 9
 */
public class Problem967 {
    public static void main(String[] args) {
        Problem967 problem967 = new Problem967();
        int n = 3;
        int k = 7;
        System.out.println(Arrays.toString(problem967.numsSameConsecDiff(n, k)));
        System.out.println(Arrays.toString(problem967.numsSameConsecDiff2(n, k)));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(2^n)，空间复杂度O(2^n)
     *
     * @param n
     * @param k
     * @return
     */
    public int[] numsSameConsecDiff(int n, int k) {
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            //last：当前数字的最后一位，len：当前数字的长度
            backtrack(i, i, 1, n, k, list);
        }

        int[] result = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * bfs
     * 时间复杂度O(2^n)，空间复杂度O(2^n)
     *
     * @param n
     * @param k
     * @return
     */
    public int[] numsSameConsecDiff2(int n, int k) {
        List<Integer> list = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();

        for (int i = 1; i <= 9; i++) {
            queue.offer(new Pos(i, i, 1));
        }

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            //数字的最后一位不能超过[0,9]的范围，并且数字的长度不能超过n
            if (pos.last < 0 || pos.last > 9 || pos.len > n) {
                continue;
            }

            if (pos.len == n) {
                list.add(pos.num);
                continue;
            }

            queue.offer(new Pos(pos.num * 10 + pos.last + k, pos.last + k, pos.len + 1));

            //避免k为0，重复添加
            if (k != 0) {
                queue.offer(new Pos(pos.num * 10 + pos.last - k, pos.last - k, pos.len + 1));
            }
        }

        int[] result = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    private void backtrack(int t, int last, int len, int n, int k, List<Integer> list) {
        if (last < 0 || last > 9) {
            return;
        }

        if (len == n) {
            list.add(t);
            return;
        }

        backtrack(t * 10 + last - k, last - k, len + 1, n, k, list);

        //避免k为0，重复添加
        if (k != 0) {
            backtrack(t * 10 + last + k, last + k, len + 1, n, k, list);
        }
    }

    /**
     * bfs节点
     */
    private static class Pos {
        private int num;
        //当前数字的最后一位
        private int last;
        //当前数字的长度
        private int len;

        public Pos(int num, int last, int len) {
            this.num = num;
            this.last = last;
            this.len = len;
        }
    }
}
