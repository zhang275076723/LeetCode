package com.zhang.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2023/9/1 08:44
 * @Author zsy
 * @Description 错误的集合 原地哈希类比Problem41、Problem268、Problem287、Problem442、Problem448、Problem1528、Offer3 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 集合 s 包含从 1 到 n 的整数。不幸的是，因为数据错误，导致集合里面某一个数字复制了成了集合里面的另外一个数字的值，
 * 导致集合 丢失了一个数字 并且 有一个数字重复 。
 * 给定一个数组 nums 代表了集合 S 发生错误后的结果。
 * 请你找出重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
 * <p>
 * 输入：nums = [1,2,2,4]
 * 输出：[2,3]
 * <p>
 * 输入：nums = [1,1]
 * 输出：[1,2]
 * <p>
 * 2 <= nums.length <= 10^4
 * 1 <= nums[i] <= 10^4
 */
public class Problem645 {
    public static void main(String[] args) {
        Problem645 problem645 = new Problem645();
        int[] nums = {1, 2, 2, 4};
        System.out.println(Arrays.toString(problem645.findErrorNums(nums)));
        System.out.println(Arrays.toString(problem645.findErrorNums2(nums)));
        //因为原地哈希修改了原数组位置，所以需要重新赋值
        nums = new int[]{1, 2, 2, 4};
        System.out.println(Arrays.toString(problem645.findErrorNums3(nums)));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int[] findErrorNums(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int[] result = new int[2];
        int index = 0;
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            //set中存在当前元素num，则num是重复的元素，加入result中
            if (set.contains(num)) {
                result[index] = num;
                index++;
            }
            set.add(num);
        }

        //找1-n中丢失的元素
        for (int i = 1; i <= nums.length; i++) {
            //set中不存在当前元素i，则i是重复的元素，加入result中
            if (!set.contains(i)) {
                result[index] = i;
                return result;
            }
        }

        //没有找到，返回null
        return null;
    }

    /**
     * 原地哈希，原数组作为哈希表，下标索引i处放置的nums[i]等于i+1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] findErrorNums2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        for (int i = 0; i < nums.length; i++) {
            //nums[i]和nums[nums[i]-1]不相等时，nums[i]和nums[nums[i]-1]进行交换
            while (nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            //nums[i]不等于i+1，则nums[i]为重复的元素，i+1为缺失的元素
            if (nums[i] != i + 1) {
                result[0] = nums[i];
                result[1] = i + 1;
            }
        }

        return result;
    }

    /**
     * 位运算
     * 1-n中缺失了一个数，并且重复了一个数，数组元素和1-n进行异或，即缺失的数出现1次，重复的数出现3次，其他数都出现2次，
     * 异或结果即为缺失的数和重复的数异或结果，根据异或结果二进制中由低位到高位不同的一位，即二进制为1的一位表示的数，
     * 根据这个数将数组元素和1-n分为两部分，两部分元素分别进行异或，得到缺失的数和重复的数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] findErrorNums3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        //nums数组元素和1-n中异或结果，即缺失的数和重复的数异或结果
        int result = 0;

        for (int num : nums) {
            result = result ^ num;
        }

        for (int i = 1; i <= nums.length; i++) {
            result = result ^ i;
        }

        //result二进制中由低位到高位不同的一位表示的数，根据bit将数组元素和1-n分为两部分
        int bit = 1;

        //由低位到高位不同的一位，即二进制为1的一位表示的数
        while ((result & bit) == 0) {
            bit = bit << 1;
        }

        //1-n中重复的数和缺失的数
        int num1 = 0;
        int num2 = 0;

        //根据bit将数组元素分为两部分，分别进行异或
        for (int num : nums) {
            if ((num & bit) == 0) {
                num1 = num1 ^ num;
            } else {
                num2 = num2 ^ num;
            }
        }

        //根据bit将1-n分为两部分，分别进行异或
        for (int i = 1; i <= nums.length; i++) {
            if ((i & bit) == 0) {
                num1 = num1 ^ i;
            } else {
                num2 = num2 ^ i;
            }
        }

        //确定num1和num2哪个是1-n中重复的数和缺失的数
        for (int num : nums) {
            if (num1 == num) {
                return new int[]{num1, num2};
            } else if (num2 == num) {
                return new int[]{num2, num1};
            }
        }

        //没有找到，返回null
        return null;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
