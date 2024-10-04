package com.zhang.java;

/**
 * @Date 2024/2/12 08:45
 * @Author zsy
 * @Description 到最近的人的最大距离 类比Problem855 类比Problem1437 双指针类比
 * 给你一个数组 seats 表示一排座位，其中 seats[i] = 1 代表有人坐在第 i 个座位上，
 * seats[i] = 0 代表座位 i 上是空的（下标从 0 开始）。
 * 至少有一个空座位，且至少有一人已经坐在座位上。
 * 亚历克斯希望坐在一个能够使他与离他最近的人之间的距离达到最大化的座位上。
 * 返回他到离他最近的人的最大距离。
 * <p>
 * 输入：seats = [1,0,0,0,1,0,1]
 * 输出：2
 * 解释：
 * 如果亚历克斯坐在第二个空位（seats[2]）上，他到离他最近的人的距离为 2 。
 * 如果亚历克斯坐在其它任何一个空位上，他到离他最近的人的距离为 1 。
 * 因此，他到离他最近的人的最大距离是 2 。
 * <p>
 * 输入：seats = [1,0,0,0]
 * 输出：3
 * 解释：
 * 如果亚历克斯坐在最后一个座位上，他离最近的人有 3 个座位远。
 * 这是可能的最大距离，所以答案是 3 。
 * <p>
 * 输入：seats = [0,1]
 * 输出：1
 * <p>
 * 2 <= seats.length <= 2 * 10^4
 * seats[i] 为 0 或 1
 * 至少有一个 空座位
 * 至少有一个 座位上有人
 */
public class Problem849 {
    public static void main(String[] args) {
        Problem849 problem849 = new Problem849();
        int[] seats = {1, 0, 0, 0, 1, 0, 1};
        System.out.println(problem849.maxDistToClosest(seats));
    }

    /**
     * 双指针
     * 距离最近1的最大距离为开始到第一个1的距离，最后一个1到末尾的距离，相邻两个1之间的距离的一半中的最大值
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param seats
     * @return
     */
    public int maxDistToClosest(int[] seats) {
        //当前为1的下标索引
        int left = 0;

        while (left < seats.length && seats[left] != 1) {
            left++;
        }

        //距离最近1的最大距离
        //初始化为seats[0]到seats[left]的距离
        int max = left;
        //下一个为1的下标索引
        int right = left + 1;

        while (right < seats.length) {
            while (right < seats.length && seats[right] != 1) {
                right++;
            }

            //最后一个1到末尾之间选择座位距离最近1的最大距离
            if (right == seats.length) {
                max = Math.max(max, seats.length - left - 1);
            } else {
                //相邻两个1之间选择座位距离最近1的最大距离
                max = Math.max(max, (right - left) / 2);
            }

            left = right;
            right = left + 1;
        }

        return max;
    }
}
