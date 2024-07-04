package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/8/8 08:19
 * @Author zsy
 * @Description 找出数组中的所有孤独数字 类比Problem128
 * 给你一个整数数组 nums 。
 * 如果数字 x 在数组中仅出现 一次 ，且没有 相邻 数字（即，x + 1 和 x - 1）出现在数组中，则认为数字 x 是 孤独数字 。
 * 返回 nums 中的 所有 孤独数字。
 * 你可以按 任何顺序 返回答案。
 * <p>
 * 输入：nums = [10,6,5,8]
 * 输出：[10,8]
 * 解释：
 * - 10 是一个孤独数字，因为它只出现一次，并且 9 和 11 没有在 nums 中出现。
 * - 8 是一个孤独数字，因为它只出现一次，并且 7 和 9 没有在 nums 中出现。
 * - 5 不是一个孤独数字，因为 6 出现在 nums 中，反之亦然。
 * 因此，nums 中的孤独数字是 [10, 8] 。
 * 注意，也可以返回 [8, 10] 。
 * <p>
 * 输入：nums = [1,3,5,3]
 * 输出：[1,5]
 * 解释：
 * - 1 是一个孤独数字，因为它只出现一次，并且 0 和 2 没有在 nums 中出现。
 * - 5 是一个孤独数字，因为它只出现一次，并且 4 和 6 没有在 nums 中出现。
 * - 3 不是一个孤独数字，因为它出现两次。
 * 因此，nums 中的孤独数字是 [1, 5] 。
 * 注意，也可以返回 [5, 1] 。
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^6
 */
public class Problem2150 {
    public static void main(String[] args) {
        Problem2150 problem2150 = new Problem2150();
        int[] nums = {10, 6, 5, 8};
        System.out.println(problem2150.findLonely(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public List<Integer> findLonely(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer> list = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            //当前元素只出现一次，并且map中不存在当前元素加1和当前元素减1，则当前元素是孤独数字
            if (entry.getValue() == 1 && !map.containsKey(entry.getKey() - 1) && !map.containsKey(entry.getKey() + 1)) {
                list.add(entry.getKey());
            }
        }

        return list;
    }
}
