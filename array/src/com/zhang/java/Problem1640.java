package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/10/1 08:40
 * @Author zsy
 * @Description 能否连接形成数组 类比Problem134 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem763、Offer50
 * 给你一个整数数组 arr ，数组中的每个整数 互不相同 。
 * 另有一个由整数数组构成的数组 pieces，其中的整数也 互不相同 。
 * 请你以 任意顺序 连接 pieces 中的数组以形成 arr 。
 * 但是，不允许 对每个数组 pieces[i] 中的整数重新排序。
 * 如果可以连接 pieces 中的数组形成 arr ，返回 true ；否则，返回 false 。
 * <p>
 * 输入：arr = [15,88], pieces = [[88],[15]]
 * 输出：true
 * 解释：依次连接 [15] 和 [88]
 * <p>
 * 输入：arr = [49,18,16], pieces = [[16,18,49]]
 * 输出：false
 * 解释：即便数字相符，也不能重新排列 pieces[0]
 * <p>
 * 输入：arr = [91,4,64,78], pieces = [[78],[4,64],[91]]
 * 输出：true
 * 解释：依次连接 [91]、[4,64] 和 [78]
 * <p>
 * 1 <= pieces.length <= arr.length <= 100
 * sum(pieces[i].length) == arr.length
 * 1 <= pieces[i].length <= arr.length
 * 1 <= arr[i], pieces[i][j] <= 100
 * arr 中的整数 互不相同
 * pieces 中的整数 互不相同（也就是说，如果将 pieces 扁平化成一维数组，数组中的所有整数互不相同）
 */
public class Problem1640 {
    public static void main(String[] args) {
        Problem1640 problem1640 = new Problem1640();
        int[] arr = {91, 4, 64, 78};
        int[][] pieces = {{78}, {4, 64}, {91}};
        System.out.println(problem1640.canFormArray(arr, pieces));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @param pieces
     * @return
     */
    public boolean canFormArray(int[] arr, int[][] pieces) {
        //key：pieces[i][0]，value：数组int[i]
        Map<Integer, int[]> map = new HashMap<>();

        for (int i = 0; i < pieces.length; i++) {
            map.put(pieces[i][0], pieces[i]);
        }

        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                return false;
            }

            int[] piece = map.get(arr[i]);

            //判断当前piece中元素是否在arr中连续
            for (int j = 0; j < piece.length; j++) {
                if (arr[i + j] != piece[j]) {
                    return false;
                }
            }

            //i右移piece.length-1，表示将piece遍历完，下一次i指向起点为i+piece.length
            i = i + piece.length - 1;
        }

        return true;
    }
}
