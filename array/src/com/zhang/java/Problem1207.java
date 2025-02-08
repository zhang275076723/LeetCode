package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2025/3/30 08:58
 * @Author zsy
 * @Description 独一无二的出现次数 类比Problem2404
 * 给你一个整数数组 arr，如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
 * <p>
 * 输入：arr = [1,2,2,1,1,3]
 * 输出：true
 * 解释：在该数组中，1 出现了 3 次，2 出现了 2 次，3 只出现了 1 次。没有两个数的出现次数相同。
 * <p>
 * 输入：arr = [1,2]
 * 输出：false
 * 示例 3：
 * <p>
 * 输入：arr = [-3,0,1,-3,1,1,1,-3,10,0]
 * 输出：true
 * <p>
 * 1 <= arr.length <= 1000
 * -1000 <= arr[i] <= 1000
 */
public class Problem1207 {
    public static void main(String[] args) {
        Problem1207 problem1207 = new Problem1207();
        int[] arr = {1, 2, 2, 1, 1, 3};
        System.out.println(problem1207.uniqueOccurrences(arr));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        //存储arr中元素出现的次数集合
        Set<Integer> set = new HashSet<>();

        for (int count : map.values()) {
            if (!set.contains(count)) {
                set.add(count);
            } else {
                //arr中元素出现的次数重复，返回false
                return false;
            }
        }

        //遍历解说，arr中元素出现的次数没有重复，返回true
        return true;
    }
}
