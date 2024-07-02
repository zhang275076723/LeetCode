package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/6/26 08:35
 * @Author zsy
 * @Description 子数组异或查询 类比Problem1442、Problem1720、Problem1734、Problem1829、Problem2433、Problem2588、Problem2683 前缀和类比 位运算类比
 * 有一个正整数数组 arr，现给你一个对应的查询数组 queries，其中 queries[i] = [Li, Ri]。
 * 对于每个查询 i，请你计算从 Li 到 Ri 的 XOR 值（即 arr[Li] xor arr[Li+1] xor ... xor arr[Ri]）作为本次查询的结果。
 * 并返回一个包含给定查询 queries 所有结果的数组。
 * <p>
 * 输入：arr = [1,3,4,8], queries = [[0,1],[1,2],[0,3],[3,3]]
 * 输出：[2,7,14,8]
 * 解释：
 * 数组中元素的二进制表示形式是：
 * 1 = 0001
 * 3 = 0011
 * 4 = 0100
 * 8 = 1000
 * 查询的 XOR 值为：
 * [0,1] = 1 xor 3 = 2
 * [1,2] = 3 xor 4 = 7
 * [0,3] = 1 xor 3 xor 4 xor 8 = 14
 * [3,3] = 8
 * <p>
 * 输入：arr = [4,8,2,10], queries = [[2,3],[1,3],[0,0],[0,3]]
 * 输出：[8,0,4,4]
 * <p>
 * 1 <= arr.length <= 3 * 10^4
 * 1 <= arr[i] <= 10^9
 * 1 <= queries.length <= 3 * 10^4
 * queries[i].length == 2
 * 0 <= queries[i][0] <= queries[i][1] < arr.length
 */
public class Problem1310 {
    public static void main(String[] args) {
        Problem1310 problem1310 = new Problem1310();
        int[] arr = {1, 3, 4, 8};
        int[][] queries = {{0, 1}, {1, 2}, {0, 3}, {3, 3}};
        System.out.println(Arrays.toString(problem1310.xorQueries(arr, queries)));
    }

    /**
     * 异或前缀和
     * preSum[i]：arr[0]-arr[i-1]异或运算结果
     * preSum[left]^preSum[right]=(arr[0]^arr[1]^...^arr[left-1])^(arr[0]^arr[1]^...^arr[left]^...^arr[right-1])=
     * arr[left]^arr[left+1]^...^arr[right-1]
     * 时间复杂度O(n+m)，空间复杂度O(n) (n=arr.length，m=queries.length)
     *
     * @param arr
     * @param queries
     * @return
     */
    public int[] xorQueries(int[] arr, int[][] queries) {
        int[] preSum = new int[arr.length + 1];

        for (int i = 0; i < arr.length; i++) {
            preSum[i + 1] = preSum[i] ^ arr[i];
        }

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            result[i] = preSum[right + 1] ^ preSum[left];
        }

        return result;
    }
}
