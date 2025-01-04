package com.zhang.java;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Date 2023/3/22 10:23
 * @Author zsy
 * @Description 存在重复元素 III 类比Problem363 有序集合类比Problem352、Problem363、Problem855、Problem981、Problem1146、Problem1348、Problem1912、Problem2034、Problem2071、Problem2349、Problem2353、Problem2502、Problem2590 滑动窗口类比Problem3、Problem30、Problem76、Problem209、Problem219、Problem239、Problem340、Problem438、Problem485、Problem487、Problem567、Problem632、Problem643、Problem713、Problem1004、Problem1456、Problem1839、Problem2062、Offer48、Offer57_2、Offer59
 * 给你一个整数数组 nums 和两个整数 indexDiff 和 valueDiff 。
 * 找出满足下述条件的下标对 (i, j)：
 * i != j,
 * abs(i - j) <= indexDiff
 * abs(nums[i] - nums[j]) <= valueDiff
 * 如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 输入：nums = [1,2,3,1], indexDiff = 3, valueDiff = 0
 * 输出：true
 * 解释：可以找出 (i, j) = (0, 3) 。
 * 满足下述 3 个条件：
 * i != j --> 0 != 3
 * abs(i - j) <= indexDiff --> abs(0 - 3) <= 3
 * abs(nums[i] - nums[j]) <= valueDiff --> abs(1 - 1) <= 0
 * <p>
 * 输入：nums = [1,5,9,1,5,9], indexDiff = 2, valueDiff = 3
 * 输出：false
 * 解释：尝试所有可能的下标对 (i, j) ，均无法满足这 3 个条件，因此返回 false 。
 * <p>
 * 2 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 1 <= indexDiff <= nums.length
 * 0 <= valueDiff <= 10^9
 */
public class Problem220 {
    public static void main(String[] args) {
        Problem220 problem220 = new Problem220();
        int[] nums = {1, 0, 1, 1};
        int k = 1;
        int t = 2;
        System.out.println(problem220.containsNearbyAlmostDuplicate(nums, k, t));
    }

    /**
     * 滑动窗口，双指针+有序集合
     * 滑动窗口保证滑动窗口中元素和右指针的下标索引之差小于等于k，有序集合保证O(logk)得到有序集合中大于等于nums[i]-t的最小元素
     * 时间复杂度O(nlog(indexDiff))，空间复杂度O(indexDiff)
     *
     * @param nums
     * @param indexDiff
     * @param valueDiff
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        //TreeSet中才有ceiling()、floor()，TreeSet添加、删除、查找的时间复杂度都为O(logn)
        //ceiling(x)：返回set中大于等于x的最小元素，如果不存在返回null
        //floor(x)：返回set中小于等于x的最大元素，如果不存在返回null
        //由小到大排序的集合
        TreeSet<Integer> set = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });
        int left = 0;
        int right = 0;

        while (right < nums.length) {
            //通过TreeSet在O(logn)快速找到，set中大于等于nums[right]-valueDiff的最小元素
            //也可以写成Integer num = set.floor(nums[right] + valueDiff);
            Integer num = set.ceiling(nums[right] - valueDiff);

            //num存在，并且nums[right]和num差的绝对值小于等于valueDiff，返回true
            if (num != null && Math.abs(nums[right] - num) <= valueDiff) {
                return true;
            }

            //nums[right]加入set，右指针右移
            set.add(nums[right]);
            right++;

            //保持滑动窗口大小不超过k，即保证滑动窗口中元素和右指针的下标索引之差小于等于k，
            //当不满足滑动窗口大小时nums[left]从set中移除，左指针右移
            if (right - left > indexDiff) {
                set.remove(nums[left]);
                left++;
            }
        }

        return false;
    }
}
