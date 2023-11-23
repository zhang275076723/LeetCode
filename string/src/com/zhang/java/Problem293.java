package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/12/7 08:27
 * @Author zsy
 * @Description 翻转游戏 类比Problem292、Problem294
 * 你和朋友玩一个叫做「翻转游戏」的游戏。游戏规则如下：
 * 给你一个字符串 currentState ，其中只含 '+' 和 '-' 。
 * 你和朋友轮流将 连续 的两个 "++" 反转成 "--" 。
 * 当一方无法进行有效的翻转时便意味着游戏结束，则另一方获胜。
 * 计算并返回 一次有效操作 后，字符串 currentState 所有的可能状态，返回结果可以按 任意顺序 排列。
 * 如果不存在可能的有效操作，请返回一个空列表 [] 。
 * <p>
 * 输入：currentState = "++++"
 * 输出：["--++","+--+","++--"]
 * <p>
 * 输入：currentState = "+"
 * 输出：[]
 * <p>
 * 1 <= currentState.length <= 500
 * currentState[i] 不是 '+' 就是 '-'
 */
public class Problem293 {
    public static void main(String[] args) {
        Problem293 problem293 = new Problem293();
        String currentState = "++++";
        System.out.println(problem293.generatePossibleNextMoves(currentState));
    }

    /**
     * 模拟
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param currentState
     * @return
     */
    public List<String> generatePossibleNextMoves(String currentState) {
        List<String> list = new ArrayList<>();
        char[] arr = currentState.toCharArray();

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == '+' && arr[i + 1] == '+') {
                arr[i] = '-';
                arr[i + 1] = '-';

                list.add(new String(arr));

                arr[i + 1] = '+';
                arr[i] = '+';
            }
        }

        return list;
    }
}
