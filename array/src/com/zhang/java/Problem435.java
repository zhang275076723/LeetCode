package com.zhang.java;


/**
 * @Date 2022/11/19 09:19
 * @Author zsy
 * @Description 无重叠区间 区间类比Problem56、Problem57、Problem228、Problem252、Problem253、Problem406、Problem436、Problem763、Problem986、Problem1288 动态规划类比Problem300
 * 给定一个区间的集合 intervals ，其中 intervals[i] = [starti, endi] 。
 * 返回 需要移除区间的最小数量，使剩余区间互不重叠 。
 * <p>
 * 输入: intervals = [[1,2],[2,3],[3,4],[1,3]]
 * 输出: 1
 * 解释: 移除 [1,3] 后，剩下的区间没有重叠。
 * <p>
 * 输入: intervals = [ [1,2], [1,2], [1,2] ]
 * 输出: 2
 * 解释: 你需要移除两个 [1,2] 来使剩下的区间没有重叠。
 * <p>
 * 输入: intervals = [ [1,2], [2,3] ]
 * 输出: 0
 * 解释: 你不需要移除任何区间，因为它们已经是无重叠的了。
 * <p>
 * 1 <= intervals.length <= 10^5
 * intervals[i].length == 2
 * -5 * 10^4 <= starti < endi <= 5 * 10^4
 */
public class Problem435 {
    public static void main(String[] args) {
        Problem435 problem435 = new Problem435();
        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        System.out.println(problem435.eraseOverlapIntervals(intervals));
        System.out.println(problem435.eraseOverlapIntervals2(intervals));
    }

    /**
     * 动态规划
     * dp[i]：以intervals[i]结尾的区间，所包含的最多不重叠区间个数
     * dp[i] = max(dp[i], dp[j]+1) (0 <= j < i，且intervals[i]和intervals[j]区间不重叠)
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        //按照左区间intervals[i][0]由小到大排序
        heapSort(intervals);

        int[] dp = new int[intervals.length];
        //不重叠区间个数
        int max = 1;

        for (int i = 0; i < intervals.length; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                //区间intervals[j]和区间intervals[i]不重叠
                if (intervals[j][1] <= intervals[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            max = Math.max(max, dp[i]);
        }

        //需要移除区间的最小数量为区间个数减去不重叠区间个数
        return intervals.length - max;
    }

    /**
     * 按照左区间interval[i][0]由小到大排序，统计不重叠区间个数，
     * 当存在重叠区间时，更新区间右边界为两者中最小值，保证距离下一个区间最远，尽可能和下一个区间不重叠
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals2(int[][] intervals) {
        //按照左区间intervals[i][0]由小到大排序
        heapSort(intervals);

        //不重叠区间个数
        int count = 1;
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            //当重叠时，取两者中最小值更新右边界，保证下一个区间和右边界不重叠
            if (intervals[i][0] < end) {
                end = Math.min(end, intervals[i][1]);
            } else {
                count++;
                end = intervals[i][1];
            }
        }

        return intervals.length - count;
    }

    private void heapSort(int[][] nums) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            heapify(nums, i, nums.length);
        }

        for (int i = nums.length - 1; i > 0; i--) {
            int[] temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            heapify(nums, 0, i);
        }
    }

    private void heapify(int[][] nums, int i, int heapSize) {
        int index = i;

        if (2 * i + 1 < heapSize && nums[2 * i + 1][0] > nums[index][0]) {
            index = 2 * i + 1;
        }

        if (2 * i + 2 < heapSize && nums[2 * i + 2][0] > nums[index][0]) {
            index = 2 * i + 2;
        }

        if (index != i) {
            int[] temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;

            heapify(nums, index, heapSize);
        }
    }
}
