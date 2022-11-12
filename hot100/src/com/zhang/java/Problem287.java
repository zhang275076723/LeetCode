package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/5/26 10:15
 * @Author zsy
 * @Description 寻找重复数 美团面试题 原地哈希类比Problem41、Problem268、Problem448、Offer3 循环链表找环类比Problem141、Problem142
 * 给定一个包含n + 1 个整数的数组nums ，其数字都在[1, n]范围内（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
 * <p>
 * 输入：nums = [1,3,4,2,2]
 * 输出：2
 * <p>
 * 输入：nums = [3,1,3,4,2]
 * 输出：3
 * <p>
 * 1 <= n <= 10^5
 * nums.length == n + 1
 * 1 <= nums[i] <= n
 * nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
 */
public class Problem287 {
    public static void main(String[] args) {
        Problem287 problem287 = new Problem287();
        int[] nums = {1, 3, 4, 2, 2};
        System.out.println(problem287.findDuplicate(nums));
        System.out.println(problem287.findDuplicate2(nums));
        System.out.println(problem287.findDuplicate3(nums));
        System.out.println(problem287.findDuplicate4(nums));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) {
                return num;
            }
            set.add(num);
        }

        return -1;
    }

    /**
     * 原地哈希，原数组作为哈希表
     * 正整数i存放在nums[i-1]的位置上
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findDuplicate2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                //交换时，只能用temp保存nums[nums[i]-1]，如果先保存nums[i]，对nums[i]的修改会导致无法找到nums[nums[i]-1]
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
            //当前nums[i]不在数组索引i+1位置上时，说明有重复元素
            if (nums[i] != i + 1) {
                return nums[i];
            }
        }

        return -1;
    }

    /**
     * 二分查找变形
     * 不是对原数组进行二分查找，而是对1,2,3,...,n-1，这n-1个数构成的升序数组进行二分查找
     * count[i]：原数组中小于等于i的元素个数
     * 如果count[i]大于等于i，说明重复的元素在升序数组当前基准i的左边或就是i；否则，说明重复的元素在升序数组当前基准i的右边
     * 时间复杂度O(nlogn)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findDuplicate3(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }

        //升序数组的最左边元素为1
        int left = 1;
        //升序数组的最右边元素为n-1
        int right = nums.length - 1;
        int mid;
        //统计小于等于当前二分元素值的个数
        int count;
        int result = -1;

        while (left <= right) {
            mid = left + ((right - left) >> 1);
            count = 0;

            //统计小于等于当前二分元素值的个数
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= mid) {
                    count++;
                }
            }

            //重复的元素在升序数组基准的右边
            if (count <= mid) {
                left = mid + 1;
            } else {
                //重复的元素在升序数组基准的左边，或就是基准
                right = mid - 1;
                result = mid;
            }
        }

        return result;
    }

    /**
     * 将数组看成链表，i连接的下一个元素为nums[i]，因为存在相同的元素，所以链表存在环，即转换为找出链表环的入口位置
     * 数组：[1,3,4,2,2] ---> 链表：1->3->[2]->4->[2]->4
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findDuplicate4(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }

        int slow = nums[0];
        int fast = nums[nums[0]];

        //快慢指针未相遇之前，慢指针每次走1步，快指针每次走2步
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        //其中一个指针指向链表头，快慢指针各走1步，直至两指针相遇，共同指向有环的第一个节点
        slow = 0;

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return fast;
    }
}
