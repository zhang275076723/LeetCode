package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/1/6 11:52
 * @Author zsy
 * @Description 随机数索引 类比Problem382
 * 给你一个可能含有 重复元素 的整数数组 nums ，请你随机输出给定的目标数字 target 的索引。
 * 你可以假设给定的数字一定存在于数组中。
 * 实现 Solution 类：
 * Solution(int[] nums) 用数组 nums 初始化对象。
 * int pick(int target) 从 nums 中选出一个满足 nums[i] == target 的随机索引 i 。
 * 如果存在多个有效的索引，则每个索引的返回概率应当相等。
 * <p>
 * 输入
 * ["Solution", "pick", "pick", "pick"]
 * [[[1, 2, 3, 3, 3]], [3], [1], [3]]
 * 输出
 * [null, 4, 0, 2]
 * 解释
 * Solution solution = new Solution([1, 2, 3, 3, 3]);
 * solution.pick(3); // 随机返回索引 2, 3 或者 4 之一。每个索引的返回概率应该相等。
 * solution.pick(1); // 返回 0 。因为只有 nums[0] 等于 1 。
 * solution.pick(3); // 随机返回索引 2, 3 或者 4 之一。每个索引的返回概率应该相等。
 * <p>
 * 1 <= nums.length <= 2 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * target 是 nums 中的一个整数
 * 最多调用 pick 函数 10^4 次
 */
public class Problem398 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 3, 3};
//        Solution solution = new Solution(nums);
        Solution2 solution = new Solution2(nums);
        System.out.println(solution.pick(3));
        System.out.println(solution.pick(1));
        System.out.println(solution.pick(3));
    }

    /**
     * 哈希表
     * 空间换时间
     */
    static class Solution {
        //存储nums中元素和对应元素在nums数组下标索引的list集合
        private final Map<Integer, List<Integer>> map;

        //获取随机值
        private final Random random;

        public Solution(int[] nums) {
            map = new HashMap<>();
            random = new Random();

            for (int i = 0; i < nums.length; i++) {
                if (!map.containsKey(nums[i])) {
                    map.put(nums[i], new ArrayList<>());
                }

                map.get(nums[i]).add(i);
            }
        }

        public int pick(int target) {
            List<Integer> list = map.get(target);
            return list.get(random.nextInt(list.size()));
        }
    }

    /**
     * 蓄水池抽样，从n个元素中随机等概率的抽取k个元素，n未知
     * 时间换空间，适用于nums数组很大，无法将nums元素下标索引全部保存到内存中的情况
     */
    static class Solution2 {
        //含有重复元素的数组
        private final int[] nums;

        //获取随机值
        private final Random random;

        public Solution2(int[] nums) {
            this.nums = nums;
            random = new Random();
        }

        /**
         * 遍历到当前元素，值为target的个数为count，一共有k个target，
         * 选择当前元素的概率为1/k = (1/count)*(count/(count+1))*((count+1)/(count+2))*...*((k-1)/k)
         * (1/count：选择第count个节点，count/(count+1)：不选第count+1个节点，...，(k-1)/k:不选第k个节点，
         * 即得到选择第k个节点的概率为1/k)
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param target
         * @return
         */
        public int pick(int target) {
            int index = -1;
            //nums中值为target的元素个数
            int count = 0;

            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == target) {
                    count++;

                    //选择当前下标索引作为结果
                    if (random.nextInt(count) == 0) {
                        index = i;
                    }
                }
            }

            return index;
        }
    }
}
