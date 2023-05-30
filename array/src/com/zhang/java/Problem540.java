package com.zhang.java;

/**
 * @Date 2023/5/29 08:37
 * @Author zsy
 * @Description 有序数组中的单一元素 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Problem852、Problem1095、Offer11、Offer53、Offer53_2、Interview_10_03、Interview_10_05
 * 给你一个仅由整数组成的有序数组，其中每个元素都会出现两次，唯有一个数只会出现一次。
 * 请你找出并返回只出现一次的那个数。
 * 你设计的解决方案必须满足 O(log n) 时间复杂度和 O(1) 空间复杂度。
 * <p>
 * 输入: nums = [1,1,2,3,3,4,4,8,8]
 * 输出: 2
 * <p>
 * 输入: nums =  [3,3,7,7,10,11,11]
 * 输出: 10
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^5
 */
public class Problem540 {
    public static void main(String[] args) {
        Problem540 problem540 = new Problem540();
        int[] nums = {3, 3, 7, 7, 10, 11, 11};
        System.out.println(problem540.singleNonDuplicate(nums));
        System.out.println(problem540.singleNonDuplicate2(nums));
        System.out.println(problem540.singleNonDuplicate3(nums));
    }

    /**
     * 模拟
     * 从前往后遍历，每次两个两个元素比较，如果当前元素和下一个元素不相等，则找到了只出现一次的元素，
     * 如果当前元素的下一个元素超过数组下标索引，则当前元素就是只出现一次的元素
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int singleNonDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        for (int i = 0; i < nums.length; i = i + 2) {
            if (i + 1 == nums.length) {
                return nums[i];
            }

            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }

        return -1;
    }

    /**
     * 位运算
     * 相同的两个数异或为0
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int singleNonDuplicate2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int result = 0;

        for (int num : nums) {
            result = result ^ num;
        }

        return result;
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int singleNonDuplicate3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left < right) {
            //mid往左偏，所以nums[mid]和nums[mid + 1]才能比较
            mid = left + ((right - left) >> 1);
            //如果mid为奇数，让mid减1变为偶数，

            //保证当nums[mid]和nums[mid+1]相等时，下标索引小于等于mid+1的元素都出现2次
            if (mid % 2 == 1) {
                mid--;
            }

            //nums[mid]和nums[mid+1]相等，保证下标索引小于等于mid+1的元素都出现2次，只出现1次的元素在mid+1右侧
            if (nums[mid] == nums[mid + 1]) {
                left = mid + 2;
            } else {
                //nums[mid]和nums[mid+1]不相等，只出现1次的元素在mid或mid左侧
                right = mid;
            }
        }

        return nums[left];
    }
}
