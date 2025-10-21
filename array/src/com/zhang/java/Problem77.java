package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/11/8 13:06
 * @Author zsy
 * @Description 组合 类比Problem216、Problem784 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Problem698、Offer17、Offer38
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
        System.out.println(problem77.combine2(n, k));
        System.out.println(problem77.combine3(n, k));
    }

    /**
     * 回溯+剪枝1
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

    /**
     * 回溯+剪枝2
     * 时间复杂度O(k*2^n)，空间复杂度O(k) (一共存在2^n种情况，每种情况需要O(k)添加到结果集合中)
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        backtrack2(1, n, k, new ArrayList<>(), result);

        return result;
    }

    /**
     * 二进制状态压缩
     * 只存在数字1-n，使用长度为n的int整数的每一位表示1-n中元素是否存在，当前位为1，则当前元素存在；当前位为0，则当前元素不存在
     * 时间复杂度O(9*2^9)，空间复杂度O(k)
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine3(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        //只存在数字1-n，使用长度为n的int整数的每一位表示1-n中元素是否存在
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> list = new ArrayList<>();

            //当前状态i从右往左数的第j位为1，则存在数字j+1
            for (int j = 0; j < n; j++) {
                if (((i >>> j) & 1) == 1) {
                    list.add(j + 1);
                }
            }

            //当前状态i对应的元素个数为k，则加入结果集合
            if (list.size() == k) {
                result.add(new ArrayList<>(list));
            }
        }

        return result;
    }

    private void backtrack(int t, int n, int k, List<List<Integer>> result, List<Integer> list) {
        //list中元素个数为k个，即找到了一个k个元素的组合
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

    private void backtrack2(int num, int n, int k, List<Integer> list, List<List<Integer>> result) {
        if (list.size() == k) {
            result.add(new ArrayList<>(list));
            return;
        }

        if (num > n) {
            return;
        }

        //不添加当前元素num
        backtrack2(num + 1, n, k, list, result);

        //添加当前元素num
        list.add(num);
        backtrack2(num + 1, n, k, list, result);
        list.remove(list.size() - 1);
    }
}
