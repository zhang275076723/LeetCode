package com.zhang.java;

/**
 * @Date 2024/2/12 08:45
 * @Author zsy
 * @Description 到最近的人的最大距离 类比Problem605、Problem855、Problem1437 双指针类比
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
     * 两个为1的座位的中间位置即为距离最近1的最大距离的座位
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param seats
     * @return
     */
    public int maxDistToClosest(int[] seats) {
        //当前为1的下标索引
        int i = 0;

        while (i < seats.length && seats[i] != 1) {
            i++;
        }

        //距离最近1的最大距离
        //初始化为起始位置到第一个为1的座位的距离
        int max = i;
        //下一个为1的下标索引
        int j = i + 1;

        while (j < seats.length) {
            //两个为1的座位的中间位置即为距离最近1的最大距离的座位
            if (seats[j] == 1) {
                max = Math.max(max, (j - i) / 2);
                i = j;
            }

            j++;
        }

        //考虑最后一个为1的座位到末尾位置的距离
        if (i != seats.length - 1) {
            max = Math.max(max, seats.length - 1 - i);
        }

        return max;
    }
}
