package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/8/30 08:09
 * @Author zsy
 * @Description 找到两个数组的前缀公共数组 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem609、Problem763、Problem1500、Problem1640、Offer50 状态压缩类比
 * 给你两个下标从 0 开始长度为 n 的整数排列 A 和 B 。
 * A 和 B 的 前缀公共数组 定义为数组 C ，其中 C[i] 是数组 A 和 B 到下标为 i 之前公共元素的数目。
 * 请你返回 A 和 B 的 前缀公共数组 。
 * 如果一个长度为 n 的数组包含 1 到 n 的元素恰好一次，我们称这个数组是一个长度为 n 的 排列 。
 * <p>
 * 输入：A = [1,3,2,4], B = [3,1,2,4]
 * 输出：[0,2,3,4]
 * 解释：i = 0：没有公共元素，所以 C[0] = 0 。
 * i = 1：1 和 3 是两个数组的前缀公共元素，所以 C[1] = 2 。
 * i = 2：1，2 和 3 是两个数组的前缀公共元素，所以 C[2] = 3 。
 * i = 3：1，2，3 和 4 是两个数组的前缀公共元素，所以 C[3] = 4 。
 * <p>
 * 输入：A = [2,3,1], B = [3,1,2]
 * 输出：[0,1,3]
 * 解释：i = 0：没有公共元素，所以 C[0] = 0 。
 * i = 1：只有 3 是公共元素，所以 C[1] = 1 。
 * i = 2：1，2 和 3 是两个数组的前缀公共元素，所以 C[2] = 3 。
 * <p>
 * 1 <= A.length == B.length == n <= 50
 * 1 <= A[i], B[i] <= n
 * 题目保证 A 和 B 两个数组都是 n 个元素的排列。
 */
public class Problem2657 {
    public static void main(String[] args) {
        Problem2657 problem2657 = new Problem2657();
        int[] A = {1, 3, 2, 4};
        int[] B = {3, 1, 2, 4};
        System.out.println(Arrays.toString(problem2657.findThePrefixCommonArray(A, B)));
        System.out.println(Arrays.toString(problem2657.findThePrefixCommonArray2(A, B)));
    }

    /**
     * 哈希表
     * A[0]-A[i]和B[0]-B[i]中元素a出现次数为2次，则元素a是A[0]-A[i]和B[0]-B[i]中的公共元素
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param A
     * @param B
     * @return
     */
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int[] result = new int[A.length];
        Map<Integer, Integer> map = new HashMap<>();
        //A[0]-A[i]和B[0]-B[i]中的公共元素的个数
        int count = 0;

        for (int i = 0; i < A.length; i++) {
            map.put(A[i], map.getOrDefault(A[i], 0) + 1);
            map.put(B[i], map.getOrDefault(B[i], 0) + 1);

            //A[i]出现次数为2次，则A[i]是A[0]-A[i]和B[0]-B[i]中的公共元素
            if (map.get(A[i]) == 2) {
                count++;
            }

            //B[i]出现次数为2次，则B[i]是A[0]-A[i]和B[0]-B[i]中的公共元素
            //A[i]!=B[i]，避免A[i]和B[i]相同的情况下，count多计算了一次
            if (A[i] != B[i] && map.get(B[i]) == 2) {
                count++;
            }

            result[i] = count;
        }

        return result;
    }

    /**
     * 二进制状态压缩
     * A和B中不同的元素不超过50，则可以使用long表示A和B中出现的元素
     * 时间复杂度O(nlogC)=O(n)，空间复杂度O(1) (C=2^63-1)
     *
     * @param A
     * @param B
     * @return
     */
    public int[] findThePrefixCommonArray2(int[] A, int[] B) {
        int[] result = new int[A.length];
        //A[0]-A[i]中出现的元素表示的状态
        long state1 = 0;
        //B[0]-B[i]中出现的元素表示的状态
        long state2 = 0;

        for (int i = 0; i < A.length; i++) {
            state1 = state1 | (1L << A[i]);
            state2 = state2 | (1L << B[i]);

            //A[0]-A[i]和B[0]-B[i]中共同出现的元素表示的状态
            long state = state1 & state2;
            //state中二进制表示1的个数
            int count = 0;

            while (state != 0) {
                count++;
                state = state & (state - 1);
            }

            result[i] = count;
        }

        return result;
    }
}
