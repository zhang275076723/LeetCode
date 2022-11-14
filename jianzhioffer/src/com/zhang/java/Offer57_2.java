package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/4/4 16:36
 * @Author zsy
 * @Description 和为s的连续正数序列 类比Problem209、Problem437、Problem560、Offer57
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
     * 时间复杂度O(n^(3/2))，空间复杂度O(1)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence(int target) {
        if (target == 1) {
            return null;
        }

        List<int[]> list = new ArrayList<>();

        for (int i = 1; i <= target / 2; i++) {
            int sum = i;

            for (int j = i + 1; j <= target / 2 + 1; j++) {
                sum = sum + j;

                if (sum == target) {
                    int[] temp = new int[j - i + 1];
                    for (int k = 0; k < temp.length; k++) {
                        temp[k] = i + k;
                    }
                    list.add(temp);
                    break;
                } else if (sum > target) {
                    break;
                }
            }
        }

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 暴力优化
     * 如果i到j满足和为target，根据(i+j)(j-i+1)/2=target，得到j
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence2(int target) {
        if (target == 1) {
            return null;
        }

        List<int[]> list = new ArrayList<>();

        for (int i = 1; i <= target / 2; i++) {
            //避免溢出
            long delta = 1 + 4 * ((long) i * i - i + 2L * target);

            //如果delta小于等于0，说明j不存在
            if (delta <= 0) {
                continue;
            }

            int sqrtDelta = (int) Math.sqrt(delta);

            //如果delta开方为整数，说明j存在
            if ((long) sqrtDelta * sqrtDelta == delta) {
                int j = (-1 + sqrtDelta) / 2;
                int[] temp = new int[j - i + 1];

                for (int k = 0; k < temp.length; k++) {
                    temp[k] = i + k;
                }

                list.add(temp);
            }
        }

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 滑动窗口，双指针
     * 看到连续子数组，想到滑动窗口和前缀和 (滑动窗口不适合有负数的情况)
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence3(int target) {
        if (target == 1) {
            return null;
        }

        List<int[]> list = new ArrayList<>();
        int left = 1;
        int right = 1;
        int sum = 0;

        while (right <= target / 2 + 1) {
            sum = sum + right;

            if (sum == target) {
                int[] temp = new int[right - left + 1];
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = left + i;
                }
                list.add(temp);
                sum = sum - left;
                left++;
            } else if (sum > target) {
                while (sum > target) {
                    sum = sum - left;
                    left++;
                }
                if (sum == target) {
                    int[] temp = new int[right - left + 1];
                    for (int i = 0; i < temp.length; i++) {
                        temp[i] = left + i;
                    }
                    list.add(temp);
                    sum = sum - left;
                    left++;
                }
            }

            right++;
        }

        int[][] result = new int[list.size()][];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 前缀和
     * 看到连续子数组，想到滑动窗口和前缀和 (滑动窗口不适合有负数的情况)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence4(int target) {
        if (target == 1) {
            return null;
        }

        //key：当前前缀和的值，value：当前前缀和的最后一个元素值
        Map<Integer, Integer> map = new HashMap<>();
        //用于1到k相加满足和为target的情况
        map.put(0, 0);

        List<int[]> list = new ArrayList<>();
        //当前前缀和
        int sum = 0;

        for (int i = 1; i <= target / 2 + 1; i++) {
            sum = sum + i;

            //map中存在key为pre - target的前缀和，说明有满足子数组之和为target的情况
            if (map.containsKey(sum - target)) {
                int[] temp = new int[i - map.get(sum - target)];

                for (int j = 0; j < temp.length; j++) {
                    temp[j] = map.get(sum - target) + j + 1;
                }

                list.add(temp);
            }

            map.put(sum, i);
        }

        return list.toArray(new int[list.size()][]);
    }
}
