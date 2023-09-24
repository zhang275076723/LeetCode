package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/25 08:27
 * @Author zsy
 * @Description 找到最接近目标值的函数值 类比Problem898、Problem2411、Problem2419、Problem2444、Problem2447、Problem2470 位运算类比
 * < int func(int[] arr, int l, int r) {
 * <   if (r < l) {
 * <     return -1000000000;
 * <   }
 * <   int ans = arr[l];
 * <   for (int i = l + 1; i <= r; i++) {
 * <     ans = ans & arr[i];
 * <   }
 * <   return ans;
 * < }
 * Winston 构造了一个如上所示的函数 func 。
 * 他有一个整数数组 arr 和一个整数 target ，他想找到让 |func(arr, l, r) - target| 最小的 l 和 r 。
 * 请你返回 |func(arr, l, r) - target| 的最小值。
 * 请注意， func 的输入参数 l 和 r 需要满足 0 <= l, r < arr.length 。
 * <p>
 * 输入：arr = [9,12,3,7,15], target = 5
 * 输出：2
 * 解释：所有可能的 [l,r] 数对包括 [[0,0],[1,1],[2,2],[3,3],[4,4],[0,1],[1,2],[2,3],[3,4],[0,2],[1,3],[2,4],[0,3],[1,4],[0,4]]，
 * Winston 得到的相应结果为 [9,12,3,7,15,8,0,3,7,0,0,3,0,0,0] 。
 * 最接近 5 的值是 7 和 3，所以最小差值为 2 。
 * <p>
 * 输入：arr = [1000000,1000000,1000000], target = 1
 * 输出：999999
 * 解释：Winston 输入函数的所有可能 [l,r] 数对得到的函数值都为 1000000 ，所以最小差值为 999999 。
 * <p>
 * 输入：arr = [1,2,4,8,16], target = 0
 * 输出：0
 * <p>
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^6
 * 0 <= target <= 10^7
 */
public class Problem1521 {
    public static void main(String[] args) {
        Problem1521 problem1521 = new Problem1521();
        int[] arr = {9, 12, 3, 7, 15};
        int target = 5;
        System.out.println(problem1521.closestToTarget(arr, target));
        System.out.println(problem1521.closestToTarget2(arr, target));
        System.out.println(problem1521.closestToTarget3(arr, target));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param arr
     * @param target
     * @return
     */
    public int closestToTarget(int[] arr, int target) {
        int result = Math.abs(arr[0] - target);

        for (int i = 0; i < arr.length; i++) {
            //arr[i]-arr[j]的与运算结果
            int andResult = arr[i];

            for (int j = i; j < arr.length; j++) {
                andResult = andResult & arr[j];
                result = Math.min(result, Math.abs(andResult - target));
            }
        }

        return result;
    }

    /**
     * 动态规划
     * dp[j]：从前往后遍历到arr[i]，此时dp[j]表示arr[j]-arr[i]与运算结果 (0 <= j < i)
     * 遍历到arr[i]，从后往前dp[j]和arr[i]进行与运算，此时dp[j]表示arr[j]-arr[i-1]与运算结果，
     * 如果dp[j] == dp[j]&arr[i]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1]与运算结果中出现过，
     * 则arr[0]-arr[i-1]、arr[1]-arr[i-1]...arr[j]-arr[i-1]和arr[i]与运算结果都不会变，
     * 因为随着数组长度变大，与运算结果只减不增，直接跳出循环，遍历下一个arr[i]；
     * 否则，更新dp[j]，此时dp[j]表示arr[j]-arr[i]与运算结果
     * 时间复杂度O(nlogC)=O(nlog32)=O(n)，空间复杂度O(n) (C：arr中的最大元素，arr元素在int范围内)
     * (以arr[i]结尾的子数组与下一个元素求与运算结果，结果只减不增，每个以arr[i]结尾的子数组最多只会有logC种不同的与运算结果，共nlogC种不同的与运算结果)
     *
     * @param arr
     * @param target
     * @return
     */
    public int closestToTarget2(int[] arr, int target) {
        int result = Math.abs(arr[0] - target);
        //dp[j]：从前往后遍历到arr[i]，此时dp[j]表示arr[j]-arr[i]与运算结果 (0 <= j < i)
        int[] dp = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result = Math.min(result, Math.abs(arr[i] - target));
            //arr[i]-arr[i]与运算结果为arr[i]
            dp[i] = arr[i];

            //从后往前dp[j]和arr[i]进行与运算，此时dp[j]表示arr[j]-arr[i-1]与运算结果
            for (int j = i - 1; j >= 0; j--) {
                //如果dp[j] == dp[j]&arr[i]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1]与运算结果中出现过，
                //则arr[0]-arr[i-1]、arr[1]-arr[i-1]...arr[j]-arr[i-1]和arr[i]与运算结果都不会变，
                //因为随着数组长度变大，与运算结果只减不增，直接跳出循环，遍历下一个arr[i]；
                if (dp[j] == (dp[j] & arr[i])) {
                    break;
                }

                //更新dp[j]，此时dp[j]表示arr[j]-arr[i]与运算结果
                dp[j] = dp[j] & arr[i];
                //更新result
                result = Math.min(result, Math.abs(dp[j] - target));
            }
        }

        return result;
    }

    /**
     * 动态规划
     * curArr[0]：从前往后遍历到arr[i]，arr[j]-arr[i]与运算结果 (curArr[1] <= j <= curArr[2])
     * curArr[1]：arr[j]-arr[i]与运算结果为curArr[0]的第一个下标索引j
     * curArr[2]：arr[j]-arr[i]与运算结果为curArr[0]的最后一个下标索引j
     * 遍历到arr[i]，从后往前遍历list中数组，curArr[0]和arr[i]求与运算结果，此时curArr[0]表示arr[j]-arr[i-1]与运算结果(curArr[1] <= j <= curArr[2])
     * 如果curArr[0] == curArr[0]&arr[i]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1](curArr[1] <= j <= curArr[2])与运算结果中出现过，
     * 则arr[0]-arr[i-1]、arr[1]-arr[i-1]...arr[j]-arr[i-1]和arr[i]与运算结果都不会变，
     * 因为随着数组长度变大，与运算结果只减不增，直接跳出循环，遍历下一个arr[i]；
     * 否则，更新curArr[0]，此时curArr[0]表示arr[j]-arr[i]与运算结果(curArr[1] <= j <= curArr[2])，
     * 通过curArr[0]更新result
     * 时间复杂度O(nlogC)=O(nlog32)=O(n)，空间复杂度O(logC)=O(log32)=O(1) (C：arr中的最大元素，arr元素在int范围内)
     * (以arr[i]结尾的子数组与下一个元素求与运算结果，结果只减不增，每个以arr[i]结尾的子数组最多只会有logC种不同的与运算结果，共nlogC种不同的与运算结果)
     *
     * @param arr
     * @param target
     * @return
     */
    public int closestToTarget3(int[] arr, int target) {
        int result = Math.abs(arr[0] - target);
        //curArr[0]：从前往后遍历到arr[i]，arr[j]-arr[i]与运算结果 (curArr[1] <= j <= curArr[2])
        //curArr[1]：arr[j]-arr[i]与运算结果为curArr[0]的第一个下标索引j
        //curArr[2]：arr[j]-arr[i]与运算结果为curArr[0]的最后一个下标索引j
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            result = Math.min(result, Math.abs(arr[i] - target));

            //需要和arr[i]求与运算结果的curArr个数
            int size = list.size();
            //arr[i]-arr[i]与运算结果为arr[i]，与运算结果为arr[i]的第一个和最后一个下标索引j都为i，作为新的curArr加入list集合中
            list.add(new int[]{arr[i], i, i});
            //从后往前遍历到的curArr数组的前一个curArr数组，用于合并两个curArr
            int[] preArr = list.get(list.size() - 1);

            //从后往前遍历list中数组，curArr[0]和arr[i]求与运算结果，此时curArr[0]表示arr[j]-arr[i-1]与运算结果(curArr[1] <= j <= curArr[2])
            for (int j = size - 1; j >= 0; j--) {
                //list中当前数组
                int[] curArr = list.get(j);

                //如果curArr[0] == curArr[0]&arr[i]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1](curArr[1] <= j <= curArr[2])与运算结果中出现过，
                //则arr[0]-arr[i-1]、arr[1]-arr[i-1]...arr[j]-arr[i-1]和arr[i]与运算结果都不会变，
                //因为随着数组长度变大，与运算结果只减不增，直接跳出循环，遍历下一个arr[i]
                if (curArr[0] == (curArr[0] & arr[i])) {
                    //如果当前数组与运算结果curArr[0]和前一个数组与运算结果preArr[0]相等，则合并两个curArr
                    if (curArr[0] == preArr[0]) {
                        curArr[2] = preArr[2];
                        list.remove(j + 1);
                    }

                    break;
                }

                //更新curArr[0]，此时curArr[0]表示arr[j]-arr[i]与运算结果(curArr[1] <= j <= curArr[2])
                curArr[0] = curArr[0] & arr[i];

                result = Math.min(result, Math.abs(curArr[0] - target));

                //如果当前数组与运算结果curArr[0]和前一个数组与运算结果preArr[0]相等，则合并两个curArr
                if (curArr[0] == preArr[0]) {
                    curArr[2] = preArr[2];
                    list.remove(j + 1);
                }

                //更新前一个数组preArr，用于下次循环
                preArr = curArr;
            }
        }

        return result;
    }

    /**
     * 求arr[l]-arr[r]或运算结果
     *
     * @param arr
     * @param l
     * @param r
     * @return
     */
    private int func(int[] arr, int l, int r) {
        if (r < l) {
            return -1000000000;
        }

        int ans = arr[l];

        for (int i = l + 1; i <= r; i++) {
            ans = ans & arr[i];
        }

        return ans;
    }
}
