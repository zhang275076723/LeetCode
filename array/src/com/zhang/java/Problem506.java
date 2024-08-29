package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/2/9 09:03
 * @Author zsy
 * @Description 相对名次 类比Problem128、Problem539、Problem561、Problem628、Problem747、Problem1200、Problem1509、Problem2144
 * 给你一个长度为 n 的整数数组 score ，其中 score[i] 是第 i 位运动员在比赛中的得分。
 * 所有得分都 互不相同 。
 * 运动员将根据得分 决定名次 ，其中名次第 1 的运动员得分最高，名次第 2 的运动员得分第 2 高，依此类推。
 * 运动员的名次决定了他们的获奖情况：
 * 名次第 1 的运动员获金牌 "Gold Medal" 。
 * 名次第 2 的运动员获银牌 "Silver Medal" 。
 * 名次第 3 的运动员获铜牌 "Bronze Medal" 。
 * 从名次第 4 到第 n 的运动员，只能获得他们的名次编号（即，名次第 x 的运动员获得编号 "x"）。
 * 使用长度为 n 的数组 answer 返回获奖，其中 answer[i] 是第 i 位运动员的获奖情况。
 * <p>
 * 输入：score = [5,4,3,2,1]
 * 输出：["Gold Medal","Silver Medal","Bronze Medal","4","5"]
 * 解释：名次为 [1st, 2nd, 3rd, 4th, 5th] 。
 * <p>
 * 输入：score = [10,3,8,9,4]
 * 输出：["Gold Medal","5","Bronze Medal","Silver Medal","4"]
 * 解释：名次为 [1st, 5th, 3rd, 2nd, 4th] 。
 * <p>
 * n == score.length
 * 1 <= n <= 10^4
 * 0 <= score[i] <= 10^6
 * score 中的所有值 互不相同
 */
public class Problem506 {
    public static void main(String[] args) {
        Problem506 problem506 = new Problem506();
        int[] score = {10, 3, 8, 9, 4};
        System.out.println(Arrays.toString(problem506.findRelativeRanks(score)));
    }

    /**
     * 排序
     * 时间复杂度O(nlogn)，空间复杂度O(logn) (非递归堆排的空间复杂度O(logn))
     *
     * @param score
     * @return
     */
    public String[] findRelativeRanks(int[] score) {
        //arr[0]：当前运动员分数，arr[1]：当前运动员在score中的下标索引
        int[][] arr = new int[score.length][2];

        for (int i = 0; i < score.length; i++) {
            arr[i] = new int[]{score[i], i};
        }

        //按照分数由大到小排序
        heapSort(arr);

        String[] result = new String[score.length];
        //名次和奖牌的映射map
        Map<Integer, String> map = new HashMap<>();

        map.put(0, "Gold Medal");
        map.put(1, "Silver Medal");
        map.put(2, "Bronze Medal");

        for (int i = 0; i < arr.length; i++) {
            //分数前三名
            if (i < 3) {
                result[arr[i][1]] = map.get(i);
            } else {
                result[arr[i][1]] = (i + 1) + "";
            }
        }

        return result;
    }

    private void heapSort(int[][] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int[] temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[index][0] > arr[leftIndex][0]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[index][0] > arr[rightIndex][0]) {
            index = rightIndex;
        }

        if (index != i) {
            int[] temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
