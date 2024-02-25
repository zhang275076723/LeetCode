package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/14 08:50
 * @Author zsy
 * @Description 优美的排列 II 类比Problem526 类比Problem38、Problem481、Problem1286
 * 给你两个整数 n 和 k ，请你构造一个答案列表 answer ，该列表应当包含从 1 到 n 的 n 个不同正整数，并同时满足下述条件：
 * 假设该列表是 answer = [a1, a2, a3, ... , an] ，那么列表 [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|]
 * 中应该有且仅有 k 个不同整数。
 * 返回列表 answer 。如果存在多种答案，只需返回其中 任意一种 。
 * <p>
 * 输入：n = 3, k = 1
 * 输出：[1, 2, 3]
 * 解释：[1, 2, 3] 包含 3 个范围在 1-3 的不同整数，并且 [1, 1] 中有且仅有 1 个不同整数：1
 * <p>
 * 输入：n = 3, k = 2
 * 输出：[1, 3, 2]
 * 解释：[1, 3, 2] 包含 3 个范围在 1-3 的不同整数，并且 [2, 1] 中有且仅有 2 个不同整数：1 和 2
 * <p>
 * 1 <= k < n <= 10^4
 */
public class Problem667 {
    public static void main(String[] args) {
        Problem667 problem667 = new Problem667();
//        int n = 8;
//        int k = 4;
        int n = 3;
        int k = 1;
        System.out.println(Arrays.toString(problem667.constructArray(n, k)));
        System.out.println(Arrays.toString(problem667.constructArray2(n, k)));
    }

    /**
     * 回溯+剪枝(超时)
     * 时间复杂度O(n!)，空间复杂度O(n)
     *
     * @param n
     * @param k
     * @return
     */
    public int[] constructArray(int n, int k) {
        int[] result = new int[n];

        backtrack(0, n, k, result, new boolean[n + 1], new HashMap<>());

        return result;
    }

    /**
     * 模拟
     * 1-n按照(1,2,...,n)排序，则相邻元素差的绝对值都为1；1-n按照(1,n,2,n-1,3,...)排序，则相邻元素差的绝对值为1、2、...、n-1，
     * 如果要k个不同的相邻元素差的绝对值，则按照(1,2,...,n-k,n,n-k+1,n-1,n-k+2,...)排序，n-k个相邻元素差的绝对值为1，
     * 剩余相邻元素差的绝对值为2、3、...、k
     * 例如：n=8，k=4，则按照(1,2,3,4,8,5,7,6)排序
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @param k
     * @return
     */
    public int[] constructArray2(int n, int k) {
        int[] result = new int[n];
        int index = 0;

        //相邻元素差的绝对值为1
        for (int i = 1; i <= n - k; i++) {
            result[index] = i;
            index++;
        }

        int i = n - k + 1;
        int j = n;

        //剩余相邻元素差的绝对值为2、3、...、k
        while (i <= j) {
            result[index] = j;
            j--;
            index++;

            if (i <= j) {
                result[index] = i;
                i++;
                index++;
            }
        }

        return result;
    }

    private boolean backtrack(int t, int n, int k, int[] result, boolean[] visited, Map<Integer, Integer> map) {
        if (t < n && map.size() > k) {
            return false;
        }

        if (t == n) {
            return map.size() == k;
        }

        for (int i = 1; i <= n; i++) {
            if (visited[i]) {
                continue;
            }

            result[t] = i;
            visited[i] = true;

            if (t > 0) {
                int abs = Math.abs(result[t] - result[t - 1]);
                map.put(abs, map.getOrDefault(abs, 0) + 1);
            }

            if (backtrack(t + 1, n, k, result, visited, map)) {
                return true;
            }

            if (t > 0) {
                int abs = Math.abs(result[t] - result[t - 1]);
                map.put(abs, map.get(abs) - 1);
                if (map.get(abs) == 0) {
                    map.remove(abs);
                }
            }

            visited[i] = false;
            result[t] = -1;
        }

        return false;
    }
}
