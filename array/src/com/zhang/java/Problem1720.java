package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/7/6 09:07
 * @Author zsy
 * @Description 解码异或后的数组 类比Problem1310、Problem1442、Problem1734、Problem1829、Problem2433、Problem2588、Problem2683
 * 未知 整数数组 arr 由 n 个非负整数组成。
 * 经编码后变为长度为 n - 1 的另一个整数数组 encoded ，其中 encoded[i] = arr[i] XOR arr[i + 1] 。
 * 例如，arr = [1,0,2,1] 经编码后得到 encoded = [1,2,3] 。
 * 给你编码后的数组 encoded 和原数组 arr 的第一个元素 first（arr[0]）。
 * 请解码返回原数组 arr 。
 * 可以证明答案存在并且是唯一的。
 * <p>
 * 输入：encoded = [1,2,3], first = 1
 * 输出：[1,0,2,1]
 * 解释：若 arr = [1,0,2,1] ，那么 first = 1 且 encoded = [1 XOR 0, 0 XOR 2, 2 XOR 1] = [1,2,3]
 * <p>
 * 输入：encoded = [6,2,7,3], first = 4
 * 输出：[4,2,0,7,4]
 * <p>
 * 2 <= n <= 10^4
 * encoded.length == n - 1
 * 0 <= encoded[i] <= 10^5
 * 0 <= first <= 10^5
 */
public class Problem1720 {
    public static void main(String[] args) {
        Problem1720 problem1720 = new Problem1720();
        int[] encoded = {1, 2, 3};
        int first = 1;
        System.out.println(Arrays.toString(problem1720.decode(encoded, first)));
    }

    /**
     * 模拟
     * arr[i]=encoded[i-1]^arr[i-1]
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param encoded
     * @param first
     * @return
     */
    public int[] decode(int[] encoded, int first) {
        int[] arr = new int[encoded.length + 1];
        arr[0] = first;

        for (int i = 1; i < arr.length; i++) {
            arr[i] = encoded[i - 1] ^ arr[i - 1];
        }

        return arr;
    }
}
