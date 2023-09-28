package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/9/9 09:30
 * @Author zsy
 * @Description 顺次数 类比Problem967 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Offer10、Offer49 回溯+剪枝类比
 * 我们定义「顺次数」为：每一位上的数字都比前一位上的数字大 1 的整数。
 * 请你返回由 [low, high] 范围内所有顺次数组成的 有序 列表（从小到大排序）。
 * <p>
 * 输出：low = 100, high = 300
 * 输出：[123,234]
 * <p>
 * 输出：low = 1000, high = 13000
 * 输出：[1234,2345,3456,4567,5678,6789,12345]
 * <p>
 * 10 <= low <= high <= 10^9
 */
public class Problem1291 {
    public static void main(String[] args) {
        Problem1291 problem1291 = new Problem1291();
        int low = 1000;
        int high = 13000;
        System.out.println(problem1291.sequentialDigits(low, high));
        System.out.println(problem1291.sequentialDigits2(low, high));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param low
     * @param high
     * @return
     */
    public List<Integer> sequentialDigits(int low, int high) {
        if (low > high) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            //last：当前数字的最后一位
            backtrack(i, i, low, high, list);
        }

        //得到的顺次数无序，需要从小到大排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        return list;
    }

    /**
     * bfs
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param low
     * @param high
     * @return
     */
    public List<Integer> sequentialDigits2(int low, int high) {
        if (low > high) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        Queue<Pos> queue = new LinkedList<>();

        for (int i = 1; i <= 9; i++) {
            queue.offer(new Pos(i, i));
        }

        while (!queue.isEmpty()) {
            Pos pos = queue.poll();

            if (pos.last < 0 || pos.last > 9) {
                continue;
            }

            //当前顺次数在[low,high]范围内，加入结果集合
            if (low <= pos.num && pos.num <= high) {
                list.add(pos.num);
            }

            queue.offer(new Pos(pos.num * 10 + pos.last + 1, pos.last + 1));
        }

        //得到的顺次数无序，需要从小到大排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        return list;
    }

    private void backtrack(int t, int last, int low, int high, List<Integer> list) {
        //当前数超过high，或者末尾数字超过9，则超过范围，直接返回
        if (t > high || last > 9) {
            return;
        }

        //当前数在[low,high]范围内，加入结果集合
        if (t >= low) {
            list.add(t);
        }

        backtrack(t * 10 + last + 1, last + 1, low, high, list);
    }

    /**
     * bfs节点
     */
    private static class Pos {
        private int num;
        //当前数字的最后一位
        private int last;

        public Pos(int num, int last) {
            this.num = num;
            this.last = last;
        }
    }
}
