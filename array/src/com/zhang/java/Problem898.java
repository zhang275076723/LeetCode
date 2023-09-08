package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2023/8/26 08:54
 * @Author zsy
 * @Description 子数组按位或操作 猿辅导机试题 类比Problem134 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 我们有一个非负整数数组 arr 。
 * 对于每个（连续的）子数组 sub = [arr[i], arr[i + 1], ..., arr[j]] （ i <= j），
 * 我们对 sub 中的每个元素进行按位或操作，获得结果 arr[i] | arr[i + 1] | ... | arr[j] 。
 * 返回可能结果的数量。
 * 多次出现的结果在最终答案中仅计算一次。
 * <p>
 * 输入：arr = [0]
 * 输出：1
 * 解释：
 * 只有一个可能的结果 0 。
 * <p>
 * 输入：arr = [1,1,2]
 * 输出：3
 * 解释：
 * 可能的子数组为 [1]，[1]，[2]，[1, 1]，[1, 2]，[1, 1, 2]。
 * 产生的结果为 1，1，2，1，3，3 。
 * 有三个唯一值，所以答案是 3 。
 * <p>
 * 输入：arr = [1,2,4]
 * 输出：6
 * 解释：
 * 可能的结果是 1，2，3，4，6，以及 7 。
 * <p>
 * 1 <= nums.length <= 5 * 10^4
 * 0 <= nums[i] <= 10^9
 */
public class Problem898 {
    public static void main(String[] args) {
        Problem898 problem898 = new Problem898();
//        int[] arr = {1, 1, 2};
        int[] arr = {1, 11, 6, 11};
        System.out.println(problem898.subarrayBitwiseORs(arr));
        System.out.println(problem898.subarrayBitwiseORs2(arr));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int subarrayBitwiseORs(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        //存放子数组不同的或运算结果
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            //nums[i]-nums[j]或运算结果
            int orResult = 0;

            for (int j = i; j < arr.length; j++) {
                orResult = orResult | arr[j];
                set.add(orResult);
            }
        }

        return set.size();
    }

    /**
     * 暴力优化，剪枝
     * 遍历到当前元素arr[i]，以arr[i]结尾的子数组依次往前和arr[j]进行或运算，此时arr[j]表示arr[j]-arr[i-1]或运算结果，
     * 如果arr[j]和arr[i]或运算结果等于arr[j]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1]或运算结果中出现过，
     * 则同样也在arr[0]-arr[i-1]、arr[1]-arr[i-1]...arr[j-1]-arr[i-1]或运算结果中出现过，直接进行下次循环；
     * 否则，更新arr[j]，此时arr[j]表示arr[j]-arr[i]或运算结果
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int subarrayBitwiseORs2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        //存放子数组不同的或运算结果
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);

            //从后往前arr[j]和arr[i]进行或运算，此时arr[j]表示arr[j]-arr[i-1]或运算结果
            for (int j = i - 1; j >= 0; j--) {
                //arr[j]和arr[i]或运算结果等于arr[j]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1]或运算结果中出现过，
                //则同样也在arr[0]-arr[i-1]、arr[1]-arr[i-1]...arr[j-1]-arr[i-1]或运算结果中出现过，直接进行下次循环
                if ((arr[j] | arr[i]) == arr[j]) {
                    break;
                }

                //更新arr[j]，此时arr[j]表示arr[j]-arr[i]或运算结果
                arr[j] = arr[j] | arr[i];
                //当前arr[j]表示arr[j]-arr[i]或运算结果，没有出现过，加入set
                set.add(arr[j]);
            }
        }

        return set.size();
    }
}
