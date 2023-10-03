package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/10/8 08:53
 * @Author zsy
 * @Description 因子的组合 因子类比Problem2427 回溯+剪枝
 * 整数可以被看作是其因子的乘积。
 * 例如：8 = 2 x 2 x 2 = 2 x 4.
 * 请实现一个函数，该函数接收一个整数 n 并返回该整数所有的因子组合。
 * <p>
 * 输入: 1
 * 输出: []
 * <p>
 * 输入: 37
 * 输出: []
 * <p>
 * 输入: 12
 * 输出:
 * [
 * [2, 6],
 * [2, 2, 3],
 * [3, 4]
 * ]
 * <p>
 * 输入: 32
 * 输出:
 * [
 * [2, 16],
 * [2, 2, 8],
 * [2, 2, 2, 4],
 * [2, 2, 2, 2, 2],
 * [2, 4, 4],
 * [4, 8]
 * ]
 * <p>
 * 1 <= n <= 10^7
 */
public class Problem254 {
    public static void main(String[] args) {
        Problem254 problem254 = new Problem254();
        int n = 32;
        System.out.println(problem254.getFactors(n));
    }

    /**
     * 回溯+剪枝
     *
     * @param n
     * @return
     */
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList<>();

        //minFactor：n当前遍历到的最小因子，之后找n的下一个因子都不能小于minFactor，避免得到重复n的因子组合
        backtrack(n, 2, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int t, int minFactor, List<Integer> list, List<List<Integer>> result) {
        //t为1，则找到了乘积为n的因子组合
        if (t == 1) {
            //因子个数超过1个才是乘积为n的因子组合
            if (list.size() > 1) {
                result.add(new ArrayList<>(list));
            }
            return;
        }

        //t不进行因子分解，t本身作为因子
        list.add(t);
        backtrack(1, t, list, result);
        list.remove(list.size() - 1);

        //t进行因子分解，从minFactor开始找t的因子，不能从2开始找因子，避免重复
        for (int i = minFactor; i * i <= t; i++) {
            if (t % i != 0) {
                continue;
            }

            list.add(i);
            backtrack(t / i, i, list, result);
            list.remove(list.size() - 1);
        }
    }
}
