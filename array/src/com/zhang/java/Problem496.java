package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Date 2022/11/17 16:57
 * @Author zsy
 * @Description 下一个更大元素 I 类比Problem503、Problem556 单调栈类比Problem42、Problem84、Problem316、Problem321、Problem402、Problem503、Problem739、Problem1019
 * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
 * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
 * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定 nums2[j] 的 下一个更大元素。
 * 如果不存在下一个更大元素，那么本次查询的答案是 -1 。
 * 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
 * <p>
 * 输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出：[-1,3,-1]
 * 解释：nums1 中每个值的下一个更大元素如下所述：
 * - 4 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
 * - 1 ，用加粗斜体标识，nums2 = [1,3,4,2]。下一个更大元素是 3 。
 * - 2 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
 * <p>
 * 输入：nums1 = [2,4], nums2 = [1,2,3,4].
 * 输出：[3,-1]
 * 解释：nums1 中每个值的下一个更大元素如下所述：
 * - 2 ，用加粗斜体标识，nums2 = [1,2,3,4]。下一个更大元素是 3 。
 * - 4 ，用加粗斜体标识，nums2 = [1,2,3,4]。不存在下一个更大元素，所以答案是 -1 。
 * <p>
 * 1 <= nums1.length <= nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 10^4
 * nums1和nums2中所有整数 互不相同
 * nums1 中的所有整数同样出现在 nums2 中
 */
public class Problem496 {
    public static void main(String[] args) {
        Problem496 problem496 = new Problem496();
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};
        System.out.println(Arrays.toString(problem496.nextGreaterElement(nums1, nums2)));
        System.out.println(Arrays.toString(problem496.nextGreaterElement2(nums1, nums2)));
    }

    /**
     * 暴力
     * 时间复杂度O(mn)，空间复杂度O(n) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
        //key：nums2中元素，value：nums2中当前元素下标索引
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], i);
        }

        for (int i = 0; i < nums1.length; i++) {
            result[i] = -1;

            for (int j = map.get(nums1[i]) + 1; j < nums2.length; j++) {
                if (nums1[i] < nums2[j]) {
                    result[i] = nums2[j];
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 单调栈 (求当前元素之后，比当前元素大或小的元素，就要想到单调栈)
     * 时间复杂度O(m+n)，空间复杂度O(n) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
        //单调递减栈，存放nums2中元素下标索引
        Stack<Integer> stack = new Stack<>();
        //key：nums2中元素，value：比nums2中当前元素大的第一个右边元素
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && nums2[stack.peek()] < nums2[i]) {
                int index = stack.pop();
                map.put(nums2[index], nums2[i]);
            }

            stack.push(i);
        }

        int[] result = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            result[i] = map.getOrDefault(nums1[i], -1);
        }

        return result;
    }
}
