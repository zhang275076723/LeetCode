package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/13 08:46
 * @Author zsy
 * @Description 最小公倍数为 K 的子数组数目 类比Problem898、Problem1521、Problem2411、Problem2419、Problem2444、Problem2447 最大公因数和最小公倍数类比
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 nums 的 子数组 中满足 元素最小公倍数为 k 的子数组数目。
 * 子数组 是数组中一个连续非空的元素序列。
 * 数组的最小公倍数 是可被所有数组元素整除的最小正整数。
 * <p>
 * 输入：nums = [3,6,2,7,1], k = 6
 * 输出：4
 * 解释：以 6 为最小公倍数的子数组是：
 * - [(3,6),2,7,1]
 * - [(3,6,2),7,1]
 * - [3,(6),2,7,1]
 * - [3,(6,2),7,1]
 * <p>
 * 输入：nums = [3], k = 2
 * 输出：0
 * 解释：不存在以 2 为最小公倍数的子数组。
 * <p>
 * 1 <= nums.length <= 1000
 * 1 <= nums[i], k <= 1000
 */
public class Problem2470 {
    public static void main(String[] args) {
        Problem2470 problem2470 = new Problem2470();
        int[] nums = {3, 6, 2, 7, 1};
        int k = 6;
        System.out.println(problem2470.subarrayLCM(nums, k));
        System.out.println(problem2470.subarrayLCM2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n*(n+logC))=O(n^2)，空间复杂度O(1) (C：nums中的最大数，nums元素在int范围内)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarrayLCM(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]的最小公倍数，初始化为nums[i]
            int lcm = nums[i];

            for (int j = i; j < nums.length; j++) {
                lcm = lcm(lcm, nums[j]);

                //k不能整除nums[i]-nums[j]的最小公倍数，则k不能整除nums[i]-nums[j+1]、nums[i]-nums[j+2]...nums[i]-nums[nums.length-1]
                //的最小公倍数，因为随着数组长度变大，nums[i]-nums[j]的最小公倍数只增不减，但都为nums[i]的倍数
                if (k % lcm != 0) {
                    break;
                }

                if (lcm == k) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 动态规划
     * curArr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]的最小公倍数 (curArr[1] <= j <= curArr[2])
     * curArr[1]：nums[j]-nums[i]的最小公倍数为curArr[0]的第一个下标索引j
     * curArr[2]：nums[j]-nums[i]的最小公倍数为curArr[0]的最后一个下标索引j
     * 遍历到nums[i]，从后往前遍历list中数组，curArr[0]和nums[i]求最小公倍数，此时curArr[0]表示nums[j]-nums[i-1]的最小公倍数(curArr[1] <= j <= curArr[2])
     * 如果curArr[0] == lcm(curArr[0],nums[i])，则说明nums[j]-nums[i-1](curArr[1] <= j <= curArr[2])和nums[i]的最小公倍数仍为curArr[0]，
     * 则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]的最小公倍数都不会变，
     * 因为随着数组长度变大，最小公倍数只增不减，直接跳出循环，统计以nums[i]结尾子数组的最小公倍数为k的个数，遍历下一个nums[i]；
     * 否则，更新curArr[0]，此时curArr[0]表示nums[j]-nums[i]的最小公倍数(curArr[1] <= j <= curArr[2])，
     * 统计以nums[i]结尾子数组的最小公倍数为k的个数
     * 时间复杂度O(nlogC)=O(n*32)=O(n)，空间复杂度O(logC)=O(log32)=O(1) (C：nums中的最大元素，nums元素在int范围内)
     * (以nums[i]结尾的子数组与下一个元素求最小公倍数，结果只增不减，每个以nums[i]结尾的子数组最多只会有logC种不同的最小公倍数，共nlogC种不同的最小公倍数)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarrayLCM2(int[] nums, int k) {
        int count = 0;
        //curArr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]的最小公倍数 (curArr[1] <= j <= curArr[2])
        //curArr[1]：nums[j]-nums[i]的最小公倍数为curArr[0]的第一个下标索引j
        //curArr[2]：nums[j]-nums[i]的最小公倍数为curArr[0]的最后一个下标索引j
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //k不能整除nums[i]，nums[i]不是k的因子，则以nums[i]结尾子数组的最小公倍数都不为k，清空list集合，
            //直接进行下次循环，遍历下一个nums[i]
            if (k % nums[i] != 0) {
                list = new ArrayList<>();
                continue;
            }

            //需要和nums[i]求最小公倍数的arr个数
            int size = list.size();
            //nums[i]-nums[i]的最小公倍数为nums[i]，最小公倍数为nums[i]的第一个和最后一个下标索引j都为i，作为新的arr加入list集合中
            list.add(new int[]{nums[i], i, i});
            //从后往前遍历到的arr数组的前一个arr数组，用于合并两个arr
            int[] preArr = list.get(list.size() - 1);

            //从后往前遍历list中数组，curArr[0]和nums[i]求最小公倍数，此时curArr[0]表示nums[j]-nums[i-1]的最小公倍数(curArr[1] <= j <= curArr[2])
            for (int j = size - 1; j >= 0; j--) {
                //list中当前数组
                int[] curArr = list.get(j);

                //如果curArr[0] == lcm(curArr[0],nums[i])，则说明nums[j]-nums[i-1](curArr[1] <= j <= curArr[2])和nums[i]的最小公倍数仍为curArr[0]，
                //则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]的最小公倍数都不会变，
                //因为随着数组长度变大，最小公倍数只增不减，直接跳出循环，统计以nums[i]结尾子数组的最小公倍数为k的个数，遍历下一个nums[i]
                if (curArr[0] == lcm(curArr[0], nums[i])) {
                    //如果当前数组的最小公倍数curArr[0]和前一个数组的最小公倍数preArr[0]相等，则合并两个arr
                    if (curArr[0] == preArr[0]) {
                        curArr[2] = preArr[2];
                        list.remove(j + 1);
                    }

                    break;
                }

                //更新curArr[0]，此时curArr[0]表示nums[j]-nums[i]的最小公倍数(curArr[1] <= j <= curArr[2])
                curArr[0] = lcm(curArr[0], nums[i]);

                //如果当前数组的最小公倍数arr[0]和前一个数组的最小公倍数preArr[0]相等，则合并两个arr
                if (curArr[0] == preArr[0]) {
                    curArr[2] = preArr[2];
                    list.remove(j + 1);
                }

                //更新前一个数组preArr，用于下次循环
                preArr = curArr;
            }

            //从前往后遍历到nums[i]，统计list中以nums[i]结尾子数组的最小公倍数为k的个数
            for (int j = 0; j < list.size(); j++) {
                int[] curArr = list.get(j);

                if (curArr[0] == k) {
                    count = count + curArr[2] - curArr[1] + 1;
                }
            }
        }

        return count;
    }

    /**
     * 非递归，辗转相除法得到a和b的最大公因数 (gcd：greatest common divisor)
     * 例如：a=36，b=24
     * 36%24=12 ----> a=24，b=12
     * 24%12=0  ----> a=12，b=0
     * 当b为0时，a即为最大公因数
     * 时间复杂度O(logn)=O(log32)=O(1)，空间复杂度O(1) (n：a、b的范围，a、b都在int范围内)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        //当b为0时，a即为最大公因数
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    /**
     * 得到a和b的最小公倍数 (lcm：least common multiple)
     * a和b的最小公倍数=a*b/(a和b的最大公因数)
     * 时间复杂度O(logn)=O(log32)=O(1)，空间复杂度O(1) (n：a、b的范围，a、b都在int范围内)
     *
     * @param a
     * @param b
     * @return
     */
    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
}
