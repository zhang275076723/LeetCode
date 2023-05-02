package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/3/23 08:51
 * @Author zsy
 * @Description 组合总和 III 回溯+剪枝类比Problem17、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem90、Problem97、Problem377、Problem679、Offer17、Offer38
 * 找出所有相加之和为 n 的 k 个数的组合，且满足下列条件：
 * 只使用数字1到9
 * 每个数字 最多使用一次
 * 返回 所有可能的有效组合的列表 。该列表不能包含相同的组合两次，组合可以以任何顺序返回。
 * <p>
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 * 解释:
 * 1 + 2 + 4 = 7
 * 没有其他符合的组合了。
 * <p>
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 * 解释:
 * 1 + 2 + 6 = 9
 * 1 + 3 + 5 = 9
 * 2 + 3 + 4 = 9
 * 没有其他符合的组合了。
 * <p>
 * 输入: k = 4, n = 1
 * 输出: []
 * 解释: 不存在有效的组合。
 * 在[1,9]范围内使用4个不同的数字，我们可以得到的最小和是1+2+3+4 = 10，因为10 > 1，没有有效的组合。
 * <p>
 * 2 <= k <= 9
 * 1 <= n <= 60
 */
public class Problem216 {
    public static void main(String[] args) {
        Problem216 problem216 = new Problem216();
        int k = 3;
        int n = 9;
        System.out.println(problem216.combinationSum3(k, n));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(C(9,k)*k)，空间复杂度O(k)
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(1, k, n, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int t, int k, int n, int sum, List<Integer> list, List<List<Integer>> result) {
        //list中已经存在k个元素，则判断元素之和是否等于n
        if (list.size() == k) {
            if (sum == n) {
                result.add(new ArrayList<>(list));
            }
            return;
        }

        for (int i = t; i <= 9; i++) {
            //sum+i已经大于n，则后面的i都不需要遍历，相当于剪枝，直接返回
            if (sum + i > n) {
                return;
            }

            list.add(i);
            backtrack(i + 1, k, n, sum + i, list, result);
            list.remove(list.size() - 1);
        }
    }
}
