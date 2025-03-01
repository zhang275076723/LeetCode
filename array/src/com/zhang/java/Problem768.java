package com.zhang.java;

/**
 * @Date 2023/12/26 08:40
 * @Author zsy
 * @Description 最多能完成排序的块 II 类比Problem769、Problem1375 数组中的动态规划类比Problem53、Problem135、Problem152、Problem238、Problem724、Problem769、Problem845、Problem1749、Offer42、Offer66、FindLeftBiggerRightLessIndex
 * 给你一个整数数组 arr 。
 * 将 arr 分割成若干 块 ，并将这些块分别进行排序。
 * 之后再连接起来，使得连接的结果和按升序排序后的原数组相同。
 * 返回能将数组分成的最多块数？
 * <p>
 * 输入：arr = [5,4,3,2,1]
 * 输出：1
 * 解释：
 * 将数组分成2块或者更多块，都无法得到所需的结果。
 * 例如，分成 [5, 4], [3, 2, 1] 的结果是 [4, 5, 1, 2, 3]，这不是有序的数组。
 * <p>
 * 输入：arr = [2,1,3,4,4]
 * 输出：4
 * 解释：
 * 可以把它分成两块，例如 [2, 1], [3, 4, 4]。
 * 然而，分成 [2, 1], [3], [4], [4] 可以得到最多的块数。
 * <p>
 * 1 <= arr.length <= 2000
 * 0 <= arr[i] <= 10^8
 */
public class Problem768 {
    public static void main(String[] args) {
        Problem768 problem768 = new Problem768();
        int[] arr = {1, 3, 1, 4, 7};
        System.out.println(problem768.maxChunksToSorted(arr));
    }

    /**
     * 动态规划
     * left[i]：arr[0]-arr[i]的最大值
     * right[i]：arr[i]-arr[arr.length-1]的最小值
     * left[i] = max(left[i-1],arr[i])
     * right[i] = min(right[i+1],arr[i])
     * left[i]<=right[i+1]，则arr[0]-arr[i]中的最大值left[i]排序后放在下标索引i，
     * arr[i+1]-arr[arr.length-1]中的最小值排序后放在下标索引i+1，则可以在下标索引i和i+1之间分割
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int maxChunksToSorted(int[] arr) {
        int[] left = new int[arr.length];
        int[] right = new int[arr.length];
        //left和right数组初始化
        left[0] = arr[0];
        right[arr.length - 1] = arr[arr.length - 1];

        for (int i = 1; i < arr.length; i++) {
            left[i] = Math.max(left[i - 1], arr[i]);
        }

        for (int i = arr.length - 2; i >= 0; i--) {
            right[i] = Math.min(right[i + 1], arr[i]);
        }

        //最多能划分的区间个数
        //注意：从1开始，整个区间也作为一个块
        int count = 1;

        for (int i = 0; i < arr.length - 1; i++) {
            //left[i]<=right[i+1]，则arr[0]-arr[i]中的最大值left[i]排序后放在下标索引i，
            //arr[i+1]-arr[arr.length-1]中的最小值排序后放在下标索引i+1，则可以在下标索引i和i+1之间分割
            if (left[i] <= right[i + 1]) {
                count++;
            }
        }

        return count;
    }
}
