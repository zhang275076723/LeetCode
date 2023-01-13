package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/4 16:36
 * @Author zsy
 * @Description 和为s的连续正数序列 滑动窗口类比Problem3、Problem76、Problem209、Problem239、Problem438、Problem567、Offer48、Offer59 前缀和类比Problem209、Problem437、Problem560
 * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
 * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
 * 1 <= target <= 10^5
 * <p>
 * 输入：target = 9
 * 输出：[[2,3,4],[4,5]]
 * <p>
 * 输入：target = 15
 * 输出：[[1,2,3,4,5],[4,5,6],[7,8]]
 * <p>
 * 1 <= target <= 10^5
 */
public class Offer57_2 {
    public static void main(String[] args) {
        Offer57_2 offer57_2 = new Offer57_2();
        System.out.println(Arrays.deepToString(offer57_2.findContinuousSequence(15)));
        System.out.println(Arrays.deepToString(offer57_2.findContinuousSequence2(15)));
        System.out.println(Arrays.deepToString(offer57_2.findContinuousSequence3(15)));
        System.out.println(Arrays.deepToString(offer57_2.findContinuousSequence4(15)));
    }

    /**
     * 暴力
     * 时间复杂度O(n^(3/2))，空间复杂度O(1) (n=target)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence(int target) {
        //和为target至少包含两个数，target为1，直接返回null
        if (target == 1) {
            return null;
        }

        //List<List<Integer>>适用于：arr[][]一维和二维都不确定的情况
        //List<int[]>适用于：arr[][]一维不确定，二维确定的情况
        //List<Integer>[]适用于：arr[][]一维确定，二维不确定的情况
        List<int[]> list = new ArrayList<>();

        for (int i = 1; i <= target / 2; i++) {
            int sum = 0;

            for (int j = i; j <= target / 2 + 1; j++) {
                sum = sum + j;

                if (sum == target) {
                    int[] arr = new int[j - i + 1];
                    for (int k = 0; k <= arr.length; k++) {
                        arr[k] = i + k;
                    }
                    list.add(arr);
                }

                //从i-j之和已经大于等于target，j就不需要往后继续寻找，直接跳出循环
                if (sum >= target) {
                    break;
                }
            }
        }

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 暴力优化
     * 如果i到j满足和为target，根据(i+j)(j-i+1)/2=target，解方程组得到正整数j
     * 时间复杂度O(n)，空间复杂度O(1) (n=target)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence2(int target) {
        //和为target至少包含两个数，target为1，直接返回null
        if (target == 1) {
            return null;
        }

        //List<List<Integer>>适用于：arr[][]一维和二维都不确定的情况
        //List<int[]>适用于：arr[][]一维不确定，二维确定的情况
        //List<Integer>[]适用于：arr[][]一维确定，二维不确定的情况
        List<int[]> list = new ArrayList<>();

        for (int i = 1; i <= target / 2; i++) {
            //使用long，避免int溢出
            long delta = 1 + 4 * ((long) i * i - i + 2L * target);

            //如果delta小于等于0，说明不存在i对应的j，继续下次循环
            if (delta <= 0) {
                continue;
            }

            int sqrtDelta = (int) Math.sqrt(delta);

            //如果delta开方为整数，说明j存在
            if ((long) sqrtDelta * sqrtDelta == delta) {
                int j = (-1 + sqrtDelta) / 2;
                int[] arr = new int[j - i + 1];

                for (int k = 0; k <= arr.length; k++) {
                    arr[k] = i + k;
                }

                list.add(arr);
            }
        }

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 滑动窗口，双指针 (注意：滑动窗口不适合有负数的情况，有负数前缀和仍然可用)
     * 左指针left为连续序列的左边界，右指针right为连续序列的右边界，
     * 如果当前连续序列之和sum小于target，右指针右移right++，sum加上right；
     * 如果当前连续序列之和sum大于target，sum减去left，左指针右移left++；
     * 如果当前连续序列之和sum等于target，则找到了一种满足要求的情况，加入结果集合，sum减去left，左指针右移left++
     * 时间复杂度O(n)，空间复杂度O(1) (n=target)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence3(int target) {
        //和为target至少包含两个数，target为1，直接返回null
        if (target == 1) {
            return null;
        }

        //List<List<Integer>>适用于：arr[][]一维和二维都不确定的情况
        //List<int[]>适用于：arr[][]一维不确定，二维确定的情况
        //List<Integer>[]适用于：arr[][]一维确定，二维不确定的情况
        List<int[]> list = new ArrayList<>();
        int left = 1;
        int right = 1;
        int sum = 1;

        //right最多为target/2+1，再往右移动连续序列之和超过target，不存在和为target的情况
        while (right <= target / 2 + 1) {
            //sum等于target，left-right加入结果集合，sum减去left，左指针右移left++
            if (sum == target) {
                int[] arr = new int[right - left + 1];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = left + i;
                }
                list.add(arr);
                sum = sum - left;
                left++;
            } else if (sum > target) {
                //sum大于target，sum减去left，左指针右移left++
                sum = sum - left;
                left++;
            } else {
                //sum小于target，右指针右移right++，sum加上right
                right++;
                sum = sum + right;
            }
        }

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 前缀和
     * 看到连续子数组，想到滑动窗口和前缀和 (注意：滑动窗口不适合有负数的情况，有负数前缀和仍然可用)
     * 时间复杂度O(n)，空间复杂度O(n) (n=target)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence4(int target) {
        //和为target至少包含两个数，target为1，直接返回null
        if (target == 1) {
            return null;
        }

        //List<List<Integer>>适用于：arr[][]一维和二维都不确定的情况
        //List<int[]>适用于：arr[][]一维不确定，二维确定的情况
        //List<Integer>[]适用于：arr[][]一维确定，二维不确定的情况
        List<int[]> list = new ArrayList<>();
        //当前前缀和
        int sum = 0;
        //key：当前区间和，value：当前区间和的最后一个元素值
        Map<Integer, Integer> map = new HashMap<>();
        //用于1到k的连续序列之和为target的情况
        map.put(0, 0);

        for (int i = 1; i <= target / 2 + 1; i++) {
            sum = sum + i;

            //map中key存在为pre-target的区间和，则map.get(sum-target)+1到i的连续序列之和为target
            if (map.containsKey(sum - target)) {
                int[] temp = new int[i - map.get(sum - target)];

                for (int j = 0; j < temp.length; j++) {
                    temp[j] = map.get(sum - target) + 1 + j;
                }

                list.add(temp);
            }

            //将当前1到i的连续序列之和sum和最后一个元素i，加入map中
            map.put(sum, i);
        }

        return list.toArray(new int[list.size()][]);
    }
}
