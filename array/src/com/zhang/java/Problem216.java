package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/3/23 08:51
 * @Author zsy
 * @Description 组合总和 III 类比Problem39、Problem40、Problem377 类比Problem77、Problem784 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem301、Problem377、Problem491、Problem679、Problem698、Offer17、Offer38
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
//        int k = 3;
//        int n = 9;
        int k = 9;
        int n = 45;
        System.out.println(problem216.combinationSum3(k, n));
        System.out.println(problem216.combinationSum3_2(k, n));
        System.out.println(problem216.combinationSum3_3(k, n));
    }

    /**
     * 回溯+剪枝1
     * 时间复杂度O(C(9,k)*k)，空间复杂度O(k) (一共存在C(9,k)种情况，每种情况需要O(k)添加到结果集合中)
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();

        backtrack(1, 0, k, n, new ArrayList<>(), result);

        return result;
    }

    /**
     * 回溯+剪枝2
     * 时间复杂度O(9*2^9)，空间复杂度O(k) (一共存在2^9种情况，每种情况需要O(9)添加到结果集合中)
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3_2(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();

        backtrack2(1, 0, k, n, new ArrayList<>(), result);

        return result;
    }

    /**
     * 二进制状态压缩
     * 只存在数字1-9，使用长度为9的int整数的每一位表示1-9中元素是否存在，当前位为1，则当前元素存在；当前位为0，则当前元素不存在
     * 时间复杂度O(9*2^9)，空间复杂度O(k)
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3_3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();

        //只存在数字1-9，使用长度为9的int整数的每一位表示1-9中元素是否存在
        for (int i = 0; i < (1 << 9); i++) {
            List<Integer> list = new ArrayList<>();
            int sum = 0;

            //当前状态i从右往左数的第j位为1，则存在数字j+1
            for (int j = 0; j < 9; j++) {
                if (((i >>> j) & 1) == 1) {
                    list.add(j + 1);
                    sum = sum + j + 1;
                }

                if (sum > n) {
                    break;
                }
            }

            //当前状态i对应的元素个数为k，并且元素之和为n，则加入结果集合
            if (list.size() == k && sum == n) {
                result.add(list);
            }
        }

        return result;
    }

    private void backtrack(int num, int sum, int k, int n, List<Integer> list, List<List<Integer>> result) {
        //list中已经存在k个元素，则判断元素之和是否等于n
        if (list.size() == k) {
            if (sum == n) {
                result.add(new ArrayList<>(list));
            }
            return;
        }

        for (int i = num; i <= 9; i++) {
            //sum+i已经大于n，则后面的i都不需要遍历，相当于剪枝，直接返回
            if (sum + i > n) {
                return;
            }

            list.add(i);
            backtrack(i + 1, sum + i, k, n, list, result);
            list.remove(list.size() - 1);
        }
    }

    private void backtrack2(int num, int sum, int k, int n, List<Integer> list, List<List<Integer>> result) {
        //注意：要先判断集合中元素个数是否为k，再判断下面这个num是否大于9的if条件
        if (list.size() == k) {
            if (sum == n) {
                result.add(new ArrayList<>(list));
            }
            return;
        }

        if (num > 9 || sum > n) {
            return;
        }

        //不添加当前元素num
        backtrack2(num + 1, sum, k, n, list, result);

        //添加当前元素num
        list.add(num);
        backtrack2(num + 1, sum + num, k, n, list, result);
        list.remove(list.size() - 1);
    }
}
