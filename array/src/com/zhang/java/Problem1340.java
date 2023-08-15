package com.zhang.java;

/**
 * @Date 2023/7/21 08:22
 * @Author zsy
 * @Description 跳跃游戏 V 跳跃问题类比Problem45、Problem55、Problem403、Problem1306、Problem1345、Problem1696、Problem1871 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem509、Offer10、Offer10_2
 * 给你一个整数数组 arr 和一个整数 d 。每一步你可以从下标 i 跳到：
 * i + x ，其中 i + x < arr.length 且 0 < x <= d 。
 * i - x ，其中 i - x >= 0 且 0 < x <= d 。
 * 除此以外，你从下标 i 跳到下标 j 需要满足：arr[i] > arr[j] 且 arr[i] > arr[k] ，
 * 其中下标 k 是所有 i 到 j 之间的数字（更正式的，min(i, j) < k < max(i, j)）。
 * 你可以选择数组的任意下标开始跳跃。请你返回你 最多 可以访问多少个下标。
 * 请注意，任何时刻你都不能跳到数组的外面。
 * <p>
 * 输入：arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
 * 输出：4
 * 解释：你可以从下标 10 出发，然后如上图依次经过 10 --> 8 --> 6 --> 7 。
 * 注意，如果你从下标 6 开始，你只能跳到下标 7 处。你不能跳到下标 5 处因为 13 > 9 。
 * 你也不能跳到下标 4 处，因为下标 5 在下标 4 和 6 之间且 13 > 9 。
 * 类似的，你不能从下标 3 处跳到下标 2 或者下标 1 处。
 * <p>
 * 输入：arr = [3,3,3,3,3], d = 3
 * 输出：1
 * 解释：你可以从任意下标处开始且你永远无法跳到任何其他坐标。
 * <p>
 * 输入：arr = [7,6,5,4,3,2,1], d = 1
 * 输出：7
 * 解释：从下标 0 处开始，你可以按照数值从大到小，访问所有的下标。
 * <p>
 * 输入：arr = [7,1,7,1,7,1], d = 2
 * 输出：2
 * <p>
 * 输入：arr = [66], d = 1
 * 输出：1
 * <p>
 * 1 <= arr.length <= 1000
 * 1 <= arr[i] <= 10^5
 * 1 <= d <= arr.length
 */
public class Problem1340 {
    public static void main(String[] args) {
        Problem1340 problem1340 = new Problem1340();
//        int[] arr = {6, 4, 14, 6, 8, 13, 9, 7, 10, 6, 12};
//        int d = 2;
        int[] arr = {7, 6, 5};
        int d = 1;
        System.out.println(problem1340.maxJumps(arr, d));
        System.out.println(problem1340.maxJumps2(arr, d));
    }

    /**
     * 递归+记忆化搜索
     * dp[i]：从i开始最多可以跳跃到的下标索引数量
     * 时间复杂度O(nd)，空间复杂度O(n)
     *
     * @param arr
     * @param d
     * @return
     */
    public int maxJumps(int[] arr, int d) {
        int[] dp = new int[arr.length];
        int max = 1;

        for (int i = 0; i < arr.length; i++) {
            dfs(i, arr, d, dp);
            max = Math.max(max, dp[i]);
        }

        return max;
    }

    /**
     * 排序+动态规划
     * 按照arr数组的高度由小到大排序，即从当前位置开始最多可以跳跃到的下标索引数量，可以由小于当前位置高度，
     * 即当前位置左边的元素，得到当前位置dp
     * dp[i]：从i开始最多可以跳跃到的下标索引数量
     * dp[i] = max(dp[j]+1) (i-d <= j <= i+d，arr[j] < arr[i]，dp[j]先于dp[i]得到)
     * 时间复杂度O(nlogn)，空间复杂度O(n) (排序的时间复杂度O(nlogn))
     *
     * @param arr
     * @param d
     * @return
     */
    public int maxJumps2(int[] arr, int d) {
        //tempArr[0]：当前位置高度，tempArr[1]：当前位置高度在arr数组的下标索引
        int[][] tempArr = new int[arr.length][2];

        for (int i = 0; i < arr.length; i++) {
            tempArr[i] = new int[]{arr[i], i};
        }

        //tempArr按照arr数组的高度由小到大排序
        mergeSort(tempArr, 0, tempArr.length - 1, new int[tempArr.length][2]);

        int[] dp = new int[arr.length];
        int max = 1;

        //按照arr数组的高度由小到大遍历
        for (int i = 0; i < tempArr.length; i++) {
            //当前高度在arr数组的下标索引
            int index = tempArr[i][1];
            //初始化从index开始最多可以跳跃到的下标索引数量为1
            dp[index] = 1;

            //往左跳
            for (int j = index - 1; j >= Math.max(0, index - d); j--) {
                //arr[j]高度大于arr[index]高度，则无法从index跳到j，即j之前的位置也无法跳到，直接跳出循环
                if (arr[j] >= arr[index]) {
                    break;
                }

                dp[index] = Math.max(dp[index], dp[j] + 1);
            }

            //往右跳
            for (int j = index + 1; j <= Math.min(arr.length - 1, index + d); j++) {
                //arr[j]高度大于arr[index]高度，则无法从index跳到j，即j之后的位置也无法跳到，直接跳出循环
                if (arr[j] >= arr[index]) {
                    break;
                }

                dp[index] = Math.max(dp[index], dp[j] + 1);
            }

            max = Math.max(max, dp[index]);
        }

        return max;
    }

    private void dfs(int t, int[] arr, int d, int[] dp) {
        if (dp[t] != 0) {
            return;
        }

        //初始化从t开始最多可以跳跃到的下标索引数量为1，即只能跳到当前下标索引t
        dp[t] = 1;

        //往左跳
        for (int i = t - 1; i >= Math.max(0, t - d); i--) {
            //arr[i]高度大于arr[t]高度，则无法从t跳到i，即i之前的位置也无法跳到，直接跳出循环
            if (arr[i] >= arr[t]) {
                break;
            }

            //更新dp[t]之前要先得到dp[i]
            dfs(i, arr, d, dp);
            dp[t] = Math.max(dp[t], dp[i] + 1);
        }

        //往右跳
        for (int i = t + 1; i <= Math.min(arr.length - 1, t + d); i++) {
            //arr[i]高度大于arr[t]高度，则无法从t跳到i，即i之后的位置也无法跳到，直接跳出循环
            if (arr[i] >= arr[t]) {
                break;
            }

            //更新dp[t]之前要先得到dp[i]
            dfs(i, arr, d, dp);
            dp[t] = Math.max(dp[t], dp[i] + 1);
        }
    }

    private void mergeSort(int[][] arr, int left, int right, int[][] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);

        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[][] arr, int left, int mid, int right, int[][] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i][0] < arr[j][0]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
