package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/6/9 20:49
 * @Author zsy
 * @Description 找到所有数组中消失的数字 类比Problem41、Problem287、Offer3
 * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。
 * 请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
 * <p>
 * 输入：nums = [4,3,2,7,8,2,3,1]
 * 输出：[5,6]
 * <p>
 * 输入：nums = [1,1]
 * 输出：[2]
 * <p>
 * n == nums.length
 * 1 <= n <= 10^5
 * 1 <= nums[i] <= n
 */
public class Problem448 {
    public static void main(String[] args) {
        Problem448 problem448 = new Problem448();
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
//        System.out.println(problem448.findDisappearedNumbers(nums));
        System.out.println(problem448.findDisappearedNumbers2(nums));
    }

    /**
     * 原地哈希
     * 将nums[i]放到nums[nums[i]-1]，例如将元素3放到数组索引下标2的位置
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != nums[nums[i] - 1]) {
                //交换时，只能用temp保存nums[nums[i]-1]，如果先保存nums[i]，对nums[i]的修改会导致无法找到nums[nums[i]-1]
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //nums[i]不和i+1相等时，说明i+1缺失
            if (nums[i] != i + 1) {
                list.add(i + 1);
            }
        }

        return list;
    }

    /**
     * 原地哈希2
     * 对nums[i]，让nums[nums[i]-1]+n，因为元素都在1-n之间，增加之后，这些数大于n，如果nums[i]小于等于n，说明i+1缺失
     * 例如：[4, 3, 2, 7, 8, 2, 3, 1]
     * 加n之后数组：[4+8, 3+8+8, 2+8+8, 7+8, 8, 2, 3+8, 1+8]
     * 则说明[5, 6]缺失，因为nums[4]和nums[5]小于等于n
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int n = nums.length;
        for (int i = 0; i < nums.length; i++) {
            //+n之后，nums[i]的值会溢出，所以要取余
            int index = (nums[i]-1) % n;
            nums[index] = nums[index] + n;
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //nums[i]小于等于n，说明i+1缺失
            if (nums[i] <= n) {
                list.add(i + 1);
            }
        }

        return list;
    }
}
