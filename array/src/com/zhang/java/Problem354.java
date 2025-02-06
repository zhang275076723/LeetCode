package com.zhang.java;

/**
 * @Date 2024/12/2 08:33
 * @Author zsy
 * @Description 俄罗斯套娃信封问题 类比Problem406、Problem975、Problem1340 类比Problem300、Problem376、Problem491、Problem673、Problem674、Problem1143、Problem2407、Problem2771 子序列和子数组类比
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * 注意：不允许旋转信封。
 * <p>
 * 输入：envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * 输出：3
 * 解释：最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
 * <p>
 * 输入：envelopes = [[1,1],[1,1],[1,1]]
 * 输出：1
 * <p>
 * 1 <= envelopes.length <= 10^5
 * envelopes[i].length == 2
 * 1 <= wi, hi <= 10^5
 */
public class Problem354 {
    public static void main(String[] args) {
        Problem354 problem354 = new Problem354();
        int[][] envelopes = {{5, 4}, {6, 4}, {6, 7}, {2, 3}};
        System.out.println(problem354.maxEnvelopes(envelopes));
        System.out.println(problem354.maxEnvelopes2(envelopes));
    }

    /**
     * 排序+动态规划 (二维LIS)
     * envelopes[i][0]由小到大排序，envelopes[i][1]由大到小排序，保证envelopes[i][0]相同的信封中，
     * 不同的envelopes[i][1]不会产生嵌套
     * 对envelopes[i][1]计算LIS，即为信封最大嵌套个数
     * dp[i]：排序后以envelopes[i][1]结尾的最长递增子序列的长度
     * dp[i] = max(dp[j] + 1) (0 <= j < i，envelopes[j][1] < envelopes[i][1])
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param envelopes
     * @return
     */
    public int maxEnvelopes(int[][] envelopes) {
        //envelopes[i][0]由小到大排序，envelopes[i][1]由大到小排序
        heapSort(envelopes);

        int maxLen = 1;
        int[] dp = new int[envelopes.length];
        dp[0] = 1;

        for (int i = 1; i < envelopes.length; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (envelopes[j][1] < envelopes[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    /**
     * 排序+二分查找变形 (二维LIS)
     * envelopes[i][0]由小到大排序，envelopes[i][1]由大到小排序，保证envelopes[i][0]相同的信封中，
     * 不同的envelopes[i][1]不会产生嵌套
     * 对envelopes[i][1]计算LIS，即为信封最大嵌套个数
     * 严格递增数组arr作为nums中的最长递增子序列，遍历nums，通过二分查找找arr中大于等于nums[i]的最小值，
     * 如果arr中不存在大于等于nums[i]的最小值，则nums[i]插入arr末尾；如果存在，则nums[i]替换arr中元素
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     * <p>
     * 例如：
     * nums = [0, 6, 7, 8, 4, 5, 9]
     * 新数组 = [0]
     * 新数组 = [0, 6]
     * 新数组 = [0, 6, 7]
     * 新数组 = [0, 6, 7, 8]
     * 新数组 = [0, 4, 7, 8]
     * 新数组 = [0, 4, 5, 8]
     * 新数组 = [0, 4, 5, 8, 9]
     * 则最长递增子序列长度为5
     *
     * @param envelopes
     * @return
     */
    public int maxEnvelopes2(int[][] envelopes) {
        //envelopes[i][0]由小到大排序，envelopes[i][1]由大到小排序
        heapSort(envelopes);

        //严格递增数组，作为envelopes[i][1]中的最长递增子序列
        int[] arr = new int[envelopes.length];
        arr[0] = envelopes[0][1];
        //初始化arr数组长度为1
        int maxLen = 1;

        for (int i = 1; i < envelopes.length; i++) {
            //arr末尾元素小于envelopes[i][1]，则envelopes[i][1]插入arr末尾，作为最长递增子序列中的元素
            if (arr[maxLen - 1] < envelopes[i][1]) {
                arr[maxLen] = envelopes[i][1];
                maxLen++;
            } else {
                //arr末尾元素大于等于envelopes[i][1]，则二分查找找arr中大于等于envelopes[i][1]的最小值，替换为envelopes[i][1]

                int left = 0;
                int right = maxLen - 1;
                int mid;

                //二分查找找arr中大于等于envelopes[i][1]的最小值
                while (left < right) {
                    mid = left + ((right - left) >> 1);

                    if (arr[mid] < envelopes[i][1]) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                //arr中大于等于envelopes[i][1]的最小值arr[left]替换为envelopes[i][1]
                arr[left] = envelopes[i][1];
            }
        }

        return maxLen;
    }

    private void heapSort(int[][] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int[] temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < heapSize && ((arr[leftIndex][0] > arr[index][0]) ||
                ((arr[leftIndex][0] == arr[index][0]) && arr[leftIndex][1] < arr[index][1]))) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && ((arr[rightIndex][0] > arr[index][0]) ||
                ((arr[rightIndex][0] == arr[index][0]) && arr[rightIndex][1] < arr[index][1]))) {
            index = rightIndex;
        }

        if (index != i) {
            int[] temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
