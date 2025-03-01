package com.zhang.java;

/**
 * @Date 2023/12/25 08:46
 * @Author zsy
 * @Description 最多能完成排序的块 类比Problem768、Problem1375 数组中的动态规划类比Problem53、Problem135、Problem152、Problem238、Problem724、Problem768、Problem845、Problem1749、Offer42、Offer66、FindLeftBiggerRightLessIndex
 * 给定一个长度为 n 的整数数组 arr ，它表示在 [0, n - 1] 范围内的整数的排列。
 * 我们将 arr 分割成若干 块 (即分区)，并对每个块单独排序。
 * 将它们连接起来后，使得连接的结果和按升序排序后的原数组相同。
 * 返回数组能分成的最多块数量。
 * <p>
 * 输入: arr = [4,3,2,1,0]
 * 输出: 1
 * 解释:
 * 将数组分成2块或者更多块，都无法得到所需的结果。
 * 例如，分成 [4, 3], [2, 1, 0] 的结果是 [3, 4, 0, 1, 2]，这不是有序的数组。
 * <p>
 * 输入: arr = [1,0,2,3,4]
 * 输出: 4
 * 解释:
 * 我们可以把它分成两块，例如 [1, 0], [2, 3, 4]。
 * 然而，分成 [1, 0], [2], [3], [4] 可以得到最多的块数。
 * 对每个块单独排序后，结果为 [0, 1], [2], [3], [4]
 * <p>
 * n == arr.length
 * 1 <= n <= 10
 * 0 <= arr[i] < n
 * arr 中每个元素都 不同
 */
public class Problem769 {
    public static void main(String[] args) {
        Problem769 problem769 = new Problem769();
        int[] arr = {1, 0, 2, 3, 4};
        System.out.println(problem769.maxChunksToSorted(arr));
        System.out.println(problem769.maxChunksToSorted2(arr));
    }

    /**
     * 贪心
     * 从左往右遍历，记录当前遍历到元素的最大值max，如果max等于当前下标索引i，则可以在下标索引i和i+1之间分割
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int maxChunksToSorted(int[] arr) {
        //最多能划分的区间个数
        int count = 0;
        //当前遍历到元素的最大值，即arr[0]-arr[i]的最大值
        int max = 0;

        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);

            //max等于当前下标索引i，则可以在下标索引i和i+1之间分割
            if (max == i) {
                count++;
            }
        }

        return count;
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
    public int maxChunksToSorted2(int[] arr) {
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
