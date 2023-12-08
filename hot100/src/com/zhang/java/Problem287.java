package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/5/26 10:15
 * @Author zsy
 * @Description 寻找重复数 美团面试题 原地哈希类比Problem41、Problem268、Problem442、Problem448、Problem645、Offer3 循环链表找环类比Problem141、Problem142 二分查找类比Problem4、Problem378、Problem410、Problem644、Problem658、Problem1201、Problem1482、Problem1723、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
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
     * 原地哈希，原数组作为哈希表，正整数i和nums[i-1]建立映射关系
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
            //nums[i]和nums[nums[i]-1]不相等时，元素进行交换
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
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
     * 对[left,right]进行二分查找，left为数组中最小值，right为数组中最大值，统计数组中小于等于mid的个数count，
     * 如果count小于等于mid，则重复元素在mid右边，left=mid+1；
     * 如果count大于mid，则重复元素在mid或mid左边，right=mid
     * 时间复杂度O(nlog(right-left))=O(n)，空间复杂度O(1) (left:数组中最小值，right:数组中最大值)
     *
     * @param nums
     * @return
     */
    public int findDuplicate3(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }

        //二分查找左边界，初始化为nums中最小元素1
        int left = 1;
        //二分查找右边界，初始化为nums中最大元素n-1
        int right = nums.length - 1;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);
            //小于等于mid的个数
            int count = 0;

            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }

            //count小于等于mid，说明重复元素在mid右边
            if (count <= mid) {
                left = mid + 1;
            } else {
                //count大于mid，说明重复元素在mid或mid左边
                right = mid;
            }
        }

        return left;
    }

    /**
     * 循环链表，快慢指针
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

        //快慢指针分别指向的数组下标索引
        int slow = nums[0];
        int fast = nums[0];

        //快慢指针未相遇之前，慢指针每次走1步，快指针每次走2步
        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];

            if (slow == fast) {
                break;
            }
        }

        //其中一个指针指向链表头，快慢指针每次走1步，直至两指针相遇，共同指向环的第一个节点
        slow = nums[0];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        //环的第一个节点即为重复元素
        return slow;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
