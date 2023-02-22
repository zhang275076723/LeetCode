package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/11/8 13:06
 * @Author zsy
 * @Description 组合 回溯+剪枝类比Problem17、Problem39、Problem40、Problem46、Problem47、Problem78、Problem90、Problem97、Offer17、Offer38
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

        backtrack(1, n, k, result, new ArrayList<>());

        return result;
    }

    private void backtrack(int t, int n, int k, List<List<Integer>> result, List<Integer> list) {
        if (list.size() == k) {
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = t; i <= n; i++) {
            list.add(i);
            backtrack(i + 1, n, k, result, list);
            list.remove(list.size() - 1);
        }
    }
}
