package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/7/5 09:07
 * @Author zsy
 * @Description 找出前缀异或的原始数组 类比Problem1310、Problem1442、Problem1720
 * 给你一个长度为 n 的 整数 数组 pref 。
 * 找出并返回满足下述条件且长度为 n 的数组 arr ：
 * pref[i] = arr[0] ^ arr[1] ^ ... ^ arr[i].
 * 注意 ^ 表示 按位异或（bitwise-xor）运算。
 * 可以证明答案是 唯一 的。
 * <p>
 * 输入：pref = [5,2,0,3,1]
 * 输出：[5,7,2,3,2]
 * 解释：从数组 [5,7,2,3,2] 可以得到如下结果：
 * - pref[0] = 5
 * - pref[1] = 5 ^ 7 = 2
 * - pref[2] = 5 ^ 7 ^ 2 = 0
 * - pref[3] = 5 ^ 7 ^ 2 ^ 3 = 3
 * - pref[4] = 5 ^ 7 ^ 2 ^ 3 ^ 2 = 1
 * <p>
 * 输入：pref = [13]
 * 输出：[13]
 * 解释：pref[0] = arr[0] = 13
 * <p>
 * 1 <= pref.length <= 10^5
 * 0 <= pref[i] <= 10^6
 */
public class Problem2433 {
    public static void main(String[] args) {
        Problem2433 problem2433 = new Problem2433();
        int[] pref = {5, 2, 0, 3, 1};
        System.out.println(Arrays.toString(problem2433.findArray(pref)));
    }

    /**
     * 模拟
     * pref[i]=pref[i-1]^arr[i]
     * arr[i]=pref[i-1]^pref[i]
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param pref
     * @return
     */
    public int[] findArray(int[] pref) {
        int[] arr = new int[pref.length];
        arr[0] = pref[0];

        for (int i = 1; i < arr.length; i++) {
            arr[i] = pref[i] ^ pref[i - 1];
        }

        return arr;
    }
}
