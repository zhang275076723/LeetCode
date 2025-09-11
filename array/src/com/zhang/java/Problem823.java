package com.zhang.java;

import java.util.Random;

/**
 * @Date 2024/1/31 08:32
 * @Author zsy
 * @Description 带因子的二叉树 类比Problem96 双指针类比
 * 给出一个含有不重复整数元素的数组 arr ，每个整数 arr[i] 均大于 1。
 * 用这些整数来构建二叉树，每个整数可以使用任意次数。
 * 其中：每个非叶结点的值应等于它的两个子结点的值的乘积。
 * 满足条件的二叉树一共有多少个？答案可能很大，返回 对 10^9 + 7 取余 的结果。
 * <p>
 * 输入: arr = [2, 4]
 * 输出: 3
 * 解释: 可以得到这些二叉树: [2], [4], [4, 2, 2]
 * <p>
 * 输入: arr = [2, 4, 5, 10]
 * 输出: 7
 * 解释: 可以得到这些二叉树: [2], [4], [5], [10], [4, 2, 2], [10, 2, 5], [10, 5, 2]
 * <p>
 * 1 <= arr.length <= 1000
 * 2 <= arr[i] <= 10^9
 * arr 中的所有值 互不相同
 */
public class Problem823 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem823 problem823 = new Problem823();
//        int[] arr = {2, 4, 5, 10};
//        int[] arr = {18, 3, 6, 2};
        //如果直接int相乘会出错，需要转化为long，再相乘
        int[] arr = {18865777, 36451879, 36878647};
        System.out.println(problem823.numFactoredBinaryTrees(arr));
    }

    /**
     * 排序+动态规划+双指针
     * dp[i]：arr由小到大排序后，根节点为的带因子的arr[i]的二叉树的个数
     * dp[i] = sum(dp[left]*dp[right])   (arr[i]==arr[left]*arr[right]，arr[left]==arr[right]，左右子树不能交换)
     * dp[i] = sum(dp[left]*dp[right]*2) (arr[i]==arr[left]*arr[right]，arr[left]!=arr[right]，左右子树可以交换)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int numFactoredBinaryTrees(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //由小到大排序
        quickSort(arr, 0, arr.length - 1);

        //使用long，避免int相乘溢出
        long[] dp = new long[arr.length];
        //使用long，避免int相加溢出
        long result = 0;

        //以arr[i]作为根节点，找arr[i]左边找乘积等于arr[i]的左右子树arr[left]和arr[right]
        for (int i = 0; i < arr.length; i++) {
            //dp初始化，以arr[i]为根只有arr[i]一个节点的个数为1
            dp[i] = 1;

            int left = 0;
            int right = i - 1;

            //注意：arr[left]和arr[right]可以使用多次，所以不只是大于，也要考虑等于
            while (left <= right) {
                //使用long，避免int相乘溢出
                if ((long) arr[left] * arr[right] < arr[i]) {
                    left++;
                } else if ((long) arr[left] * arr[right] > arr[i]) {
                    //使用long，避免int相乘溢出
                    right--;
                } else {
                    //arr[left]和arr[right]相等，则左右子树不能交换
                    if (arr[left] == arr[right]) {
                        dp[i] = (dp[i] + dp[left] * dp[right]) % MOD;
                    } else {
                        //arr[left]和arr[right]不相等，则左右子树可以交换，需要乘以2
                        dp[i] = (dp[i] + dp[left] * dp[right] * 2) % MOD;
                    }

                    left++;
                }
            }

            result = (result + dp[i]) % MOD;
        }

        return (int) result;
    }

    private void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }

    private int partition(int[] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

        int temp = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }

            arr[left] = arr[right];

            while (left < right && arr[left] <= temp) {
                left++;
            }

            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
