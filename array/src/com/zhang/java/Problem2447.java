package com.zhang.java;


import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/13 08:00
 * @Author zsy
 * @Description 最大公因数等于 K 的子数组数目 类比Problem898、Problem2411、Problem2470 最大公因数和最小公倍数类比
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 nums 的子数组中元素的最大公因数等于 k 的子数组数目。
 * 子数组 是数组中一个连续的非空序列。
 * 数组的最大公因数 是能整除数组中所有元素的最大整数。
 * <p>
 * 输入：nums = [9,3,1,2,6,3], k = 3
 * 输出：4
 * 解释：nums 的子数组中，以 3 作为最大公因数的子数组如下：
 * - [9,(3),1,2,6,3]
 * - [(9,3),1,2,6,3]
 * - [9,3,1,2,6,(3)]
 * - [9,3,1,2,(6,3)]
 * <p>
 * 输入：nums = [4], k = 7
 * 输出：0
 * 解释：不存在以 7 作为最大公因数的子数组。
 * <p>
 * 1 <= nums.length <= 1000
 * 1 <= nums[i], k <= 10^9
 */
public class Problem2447 {
    public static void main(String[] args) {
        Problem2447 problem2447 = new Problem2447();
        int[] nums = {9, 3, 1, 2, 6, 3};
        int k = 3;
        System.out.println(problem2447.subarrayGCD(nums, k));
        System.out.println(problem2447.subarrayGCD2(nums, k));
    }

    /**
     * 暴力
     * 时间复杂度O(n*(n+logC))=O(n^2)，空间复杂度O(1) (C：nums中的最大数，nums元素在int范围内)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarrayGCD(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]的最大公因数，初始化为nums[i]
            int gcd = nums[i];

            for (int j = i; j < nums.length; j++) {
                gcd = gcd(gcd, nums[j]);

                //nums[i]-nums[j]的最大公因数不能整除k，则nums[i]-nums[j+1]、nums[i]-nums[j+2]...nums[i]-nums[nums.length-1]
                //的最大公因数也不能整除k，因为随着数组长度变大，nums[i]-nums[j]的最大公因数只减不增，但都为nums[i]的因子
                if (gcd % k != 0) {
                    break;
                }

                if (gcd == k) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 动态规划
     * arr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]的最大公因数 (arr[1] <= j <= arr[2])
     * arr[1]：nums[j]-nums[i]的最大公因数为arr[0]的第一个下标索引j
     * arr[2]：nums[j]-nums[i]的最大公因数为arr[0]的最后一个下标索引j
     * 遍历到nums[i]，从后往前遍历list中数组，arr[0]和nums[i]求最大公因数，此时arr[0]表示nums[j]-nums[i-1]的最大公因数(arr[1] <= j <= arr[2])
     * 如果arr[0] == gcd(arr[0],nums[i])，则说明nums[j]-nums[i-1](arr[1] <= j <= arr[2])和nums[i]的最大公因数仍为arr[0]，
     * 则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]的最大公因数都不会变，
     * 因为随着数组长度变大，最大公因数只减不增，直接跳出循环，统计以nums[i]结尾子数组的最大公因数为k的个数，遍历下一个nums[i]；
     * 否则，更新arr[0]，此时arr[0]表示nums[j]-nums[i]的最大公因数(arr[1] <= j <= arr[2])，统计以nums[i]结尾子数组的最大公因数为k的个数
     * 时间复杂度O(nlogC)=O(nlog32)=O(n)，空间复杂度O(logC)=O(log32)=O(1) (C：nums中的最大元素，nums元素在int范围内)
     * (以nums[i]结尾的子数组与下一个元素求最大公因数，结果只减不增，每个以nums[i]结尾的子数组最多只会有logC种不同的最大公因数，共nlogC种不同的最大公因数)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarrayGCD2(int[] nums, int k) {
        int count = 0;
        //arr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]的最大公因数 (arr[1] <= j <= arr[2])
        //arr[1]：nums[j]-nums[i]的最大公因数为arr[0]的第一个下标索引j
        //arr[2]：nums[j]-nums[i]的最大公因数为arr[0]的最后一个下标索引j
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //nums[i]不能整除k，k不是nums[i]的因子，则以nums[i]结尾子数组的最大公因数都不为k，清空list集合，
            //直接进行下次循环，遍历下一个nums[i]
            if (nums[i] % k != 0) {
                list = new ArrayList<>();
                continue;
            }

            //需要和nums[i]求最大公因数的arr个数
            int size = list.size();
            //nums[i]-nums[i]的最大公因数为nums[i]，最大公因数为nums[i]的第一个和最后一个下标索引j都为i，作为新的arr加入list集合中
            list.add(new int[]{nums[i], i, i});
            //从后往前遍历到的arr数组的前一个arr数组，用于合并两个arr
            int[] preArr = list.get(list.size() - 1);

            //从后往前遍历list中数组，arr[0]和nums[i]求最大公因数，此时arr[0]表示nums[j]-nums[i-1]的最大公因数(arr[1] <= j <= arr[2])
            for (int j = size - 1; j >= 0; j--) {
                //当前arr数组
                int[] arr = list.get(j);

                //如果arr[0] == gcd(arr[0],nums[i])，则说明nums[j]-nums[i-1](arr[1] <= j <= arr[2])和nums[i]的最大公因数仍为arr[0]，
                //则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]的最大公因数都不会变，
                //因为随着数组长度变大，最大公因数只减不增，直接跳出循环，统计以nums[i]结尾子数组的最大公因数为k的个数，遍历下一个nums[i]
                if (arr[0] == gcd(arr[0], nums[i])) {
                    //如果当前数组的最大公因数arr[0]和前一个数组的最大公因数preArr[0]相等，则合并两个arr
                    if (arr[0] == preArr[0]) {
                        arr[2] = preArr[2];
                        list.remove(j + 1);
                    }

                    break;
                }

                //更新arr[0]，此时arr[0]表示nums[j]-nums[i]的最大公因数(arr[1] <= j <= arr[2])
                arr[0] = gcd(arr[0], nums[i]);

                //如果当前数组的最大公因数arr[0]和前一个数组的最大公因数preArr[0]相等，则合并两个arr
                if (arr[0] == preArr[0]) {
                    arr[2] = preArr[2];
                    list.remove(j + 1);
                }

                //更新前一个数组preArr，用于下次循环
                preArr = arr;
            }

            //从前往后遍历到nums[i]，统计list中以nums[i]结尾子数组的最大公因数为k的个数
            for (int j = 0; j < list.size(); j++) {
                int[] arr = list.get(j);

                if (arr[0] == k) {
                    count = count + arr[2] - arr[1] + 1;
                }
            }
        }

        return count;
    }

    /**
     * 非递归，辗转相除法得到a和b的最大公因数
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
}
