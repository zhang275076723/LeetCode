package com.zhang.java;

/**
 * @Date 2024/10/20 10:08
 * @Author zsy
 * @Description 完成旅途的最少时间 二分查找类比
 * 给你一个数组 time ，其中 time[i] 表示第 i 辆公交车完成 一趟旅途 所需要花费的时间。
 * 每辆公交车可以 连续 完成多趟旅途，也就是说，一辆公交车当前旅途完成后，可以 立马开始 下一趟旅途。
 * 每辆公交车 独立 运行，也就是说可以同时有多辆公交车在运行且互不影响。
 * 给你一个整数 totalTrips ，表示所有公交车 总共 需要完成的旅途数目。
 * 请你返回完成 至少 totalTrips 趟旅途需要花费的 最少 时间。
 * <p>
 * 输入：time = [1,2,3], totalTrips = 5
 * 输出：3
 * 解释：
 * - 时刻 t = 1 ，每辆公交车完成的旅途数分别为 [1,0,0] 。
 * 已完成的总旅途数为 1 + 0 + 0 = 1 。
 * - 时刻 t = 2 ，每辆公交车完成的旅途数分别为 [2,1,0] 。
 * 已完成的总旅途数为 2 + 1 + 0 = 3 。
 * - 时刻 t = 3 ，每辆公交车完成的旅途数分别为 [3,1,1] 。
 * 已完成的总旅途数为 3 + 1 + 1 = 5 。
 * 所以总共完成至少 5 趟旅途的最少时间为 3 。
 * <p>
 * 输入：time = [2], totalTrips = 1
 * 输出：2
 * 解释：
 * 只有一辆公交车，它将在时刻 t = 2 完成第一趟旅途。
 * 所以完成 1 趟旅途的最少时间为 2 。
 * <p>
 * 1 <= time.length <= 10^5
 * 1 <= time[i], totalTrips <= 10^7
 */
public class Problem2187 {
    public static void main(String[] args) {
        Problem2187 problem2187 = new Problem2187();
        int[] time = {1, 2, 3};
        int totalTrips = 5;
        System.out.println(problem2187.minimumTime(time, totalTrips));
    }

    /**
     * 二分查找变形
     * 对[left,right]进行二分查找，left为1，right为time最小值*totalTrips，统计mid时刻所有公交车完成旅途的个数count，
     * 如果count小于totalTrips，则完成至少totalTrips趟旅途需要花费的最少时间在mid右边，left=mid+1；
     * 如果count大于等于totalTrips，则完成至少totalTrips趟旅途需要花费的最少时间在mid或mid左边，right=mid
     * 时间复杂度O(n*log(min(time[i])*totalTrips-1))=O(n)，空间复杂度O(1)
     *
     * @param time
     * @param totalTrips
     * @return
     */
    public long minimumTime(int[] time, int totalTrips) {
        int min = time[0];

        for (int num : time) {
            min = Math.min(min, num);
        }

        //使用long，避免int溢出
        long left = 1;
        long right = (long) min * totalTrips;
        long mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //mid时刻所有公交车完成旅途的个数
            //使用long，避免int溢出
            long count = 0;

            for (int num : time) {
                count = count + mid / num;
            }

            if (count < totalTrips) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
