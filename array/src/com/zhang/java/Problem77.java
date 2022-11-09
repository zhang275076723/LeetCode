package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/11/8 13:06
 * @Author zsy
 * @Description 组合 类比Problem17、Problem78、Problem90
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * 你可以按 任何顺序 返回答案。
 * <p>
 * 输入：n = 4, k = 2
 * 输出：
 * [
 * [2,4],
 * [3,4],
 * [2,3],
 * [1,2],
 * [1,3],
 * [1,4],
 * ]
 * <p>
 * 输入：n = 1, k = 1
 * 输出：[[1]]
 * <p>
 * 1 <= n <= 20
 * 1 <= k <= n
 */
public class Problem77 {
    public static void main(String[] args) {
        Problem77 problem77 = new Problem77();
        int n = 4;
        int k = 2;
        System.out.println(problem77.combine(n, k));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(C(n,k)*k)，空间复杂度O(k)
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        backtrack(0, 1, n, k, result, new ArrayList<>());

        return result;
    }

    /**
     * @param t      list集合添加的元素个数，用于剪枝判断
     * @param start  当前起始元素
     * @param n
     * @param k
     * @param result
     * @param list
     */
    private void backtrack(int t, int start, int n, int k, List<List<Integer>> result, List<Integer> list) {
        //添加的元素个数等于k时，加入结果集合list，剪枝
        if (t == k) {
            result.add(new ArrayList<>(list));
            return;
        }

        //当前还能添加的元素个数不满足添加k个元素，则剪枝
        if (start + k - t - 1 > n) {
            return;
        }

        //从当前元素往后找
        for (int i = start; i <= n; i++) {
            list.add(i);

            backtrack(t + 1, i + 1, n, k, result, list);

            list.remove(list.size() - 1);
        }
    }
}
