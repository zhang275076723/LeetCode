package com.zhang.java;

/**
 * @Date 2022/11/18 17:59
 * @Author zsy
 * @Description 下一个更大元素 III 类比Problem496、Problem503 类比Problem31、Problem738
 * 给你一个正整数 n ，请你找出符合条件的最小整数，其由重新排列 n 中存在的每位数字组成，并且其值大于 n 。
 * 如果不存在这样的正整数，则返回 -1 。
 * 注意 ，返回的整数应当是一个 32 位整数 ，如果存在满足题意的答案，但不是 32 位整数 ，同样返回 -1 。
 * <p>
 * 输入：n = 12
 * 输出：21
 * <p>
 * 输入：n = 21
 * 输出：-1
 * <p>
 * 1 <= n <= 2^31 - 1
 */
public class Problem556 {
    public static void main(String[] args) {
        Problem556 problem556 = new Problem556();
        int n = 1332;
        System.out.println(problem556.nextGreaterElement(n));
    }

    /**
     * 模拟
     * 将数字n转化为数组，从右往左遍历，找最长递减序列，将最长递减序列翻转，
     * 最长递减序列的前一个元素和序列中第一个大于当前元素进行交换
     * 时间复杂度O(logn)，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public int nextGreaterElement(int n) {
        char[] nums = (n + "").toCharArray();

        int i = nums.length - 1;

        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        //nums为递减序列，没有下一个更大元素，返回-1
        if (i == 0) {
            return -1;
        }

        //翻转递减序列
        reverse(nums, i, nums.length - 1);

        //递减序列的前一个元素下标索引
        int j = i - 1;

        for (int k = i; k < nums.length; k++) {
            //nums[j]和第一个大于nums[j]的元素交换
            if (nums[j] < nums[k]) {
                char temp = nums[j];
                nums[j] = nums[k];
                nums[k] = temp;
                break;
            }
        }

        int result = 0;

        for (char num : nums) {
            //溢出处理
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && (num - '0') > Integer.MAX_VALUE % 10)) {
                return -1;
            }

            result = result * 10 + num - '0';
        }

        return result;
    }

    private void reverse(char[] nums, int left, int right) {
        while (left < right) {
            char temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
