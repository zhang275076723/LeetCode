package com.zhang.java;

/**
 * @Date 2025/1/4 08:19
 * @Author zsy
 * @Description 同时运行 N 台电脑的最长时间 阿里机试题 二分查找类比
 * 你有 n 台电脑。
 * 给你整数 n 和一个下标从 0 开始的整数数组 batteries ，其中第 i 个电池可以让一台电脑 运行 batteries[i] 分钟。
 * 你想使用这些电池让 全部 n 台电脑 同时 运行。
 * 一开始，你可以给每台电脑连接 至多一个电池 。
 * 然后在任意整数时刻，你都可以将一台电脑与它的电池断开连接，并连接另一个电池，你可以进行这个操作 任意次 。
 * 新连接的电池可以是一个全新的电池，也可以是别的电脑用过的电池。断开连接和连接新的电池不会花费任何时间。
 * 注意，你不能给电池充电。
 * 请你返回你可以让 n 台电脑同时运行的 最长 分钟数。
 * <p>
 * 阿里题目：给定五个数，每次选择四个数减一，直到减为0，求最大的操作次数。
 * 输入：nums = [8,5,4,4,3]
 * 输出：5
 * 解释：
 * 每次都挑出来最大的4个减
 * 第一次：7 4 3 3 3
 * 第二次：6 3 3 2 2
 * 第三次：5 2 2 2 1
 * 第四次：4 1 1 1 1
 * 第五次：3 1 0 0 0
 * <p>
 * 输入：n = 2, batteries = [3,3,3]
 * 输出：4
 * 解释：
 * 一开始，将第一台电脑与电池 0 连接，第二台电脑与电池 1 连接。
 * 2 分钟后，将第二台电脑与电池 1 断开连接，并连接电池 2 。注意，电池 0 还可以供电 1 分钟。
 * 在第 3 分钟结尾，你需要将第一台电脑与电池 0 断开连接，然后连接电池 1 。
 * 在第 4 分钟结尾，电池 1 也被耗尽，第一台电脑无法继续运行。
 * 我们最多能同时让两台电脑同时运行 4 分钟，所以我们返回 4 。
 * <p>
 * 输入：n = 2, batteries = [1,1,1,1]
 * 输出：2
 * 解释：
 * 一开始，将第一台电脑与电池 0 连接，第二台电脑与电池 2 连接。
 * 一分钟后，电池 0 和电池 2 同时耗尽，所以你需要将它们断开连接，并将电池 1 和第一台电脑连接，电池 3 和第二台电脑连接。
 * 1 分钟后，电池 1 和电池 3 也耗尽了，所以两台电脑都无法继续运行。
 * 我们最多能让两台电脑同时运行 2 分钟，所以我们返回 2 。
 * <p>
 * 1 <= n <= batteries.length <= 10^5
 * 1 <= batteries[i] <= 10^9
 */
public class Problem2141 {
    public static void main(String[] args) {
        Problem2141 problem2141 = new Problem2141();
//        int n = 2;
//        int[] batteries = {3, 3, 3};
        int n = 4;
        int[] batteries = {8, 5, 4, 4, 3};
        System.out.println(problem2141.maxRunTime(n, batteries));
    }

    /**
     * 二分查找
     * 对[left,right]进行二分查找，left为0，right为batteries元素之和/n，判断n台电脑能否同时运行mid分钟，
     * 如果n台电脑能同时运行mid分钟，则n台电脑能同时运行的最大时间在mid或mid右边，left=mid;
     * 如果n台电脑不能同时运行mid分钟，则n台电脑能同时运行的最大时间在mid左边，right=mid-1
     * 时间复杂度O(m*log(sum(batteries[i])/n))=O(m)，空间复杂度O(1) (m=batteries.length)
     *
     * @param n
     * @param batteries
     * @return
     */
    public long maxRunTime(int n, int[] batteries) {
        long sum = 0;

        for (int num : batteries) {
            sum = sum + num;
        }

        long left = 0;
        long right = sum / n;
        long mid;

        while (left < right) {
            //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
            mid = left + ((right - left) >> 1) + 1;

            //batteries中电池运行mid分钟的总时间
            long time = 0;

            for (int num : batteries) {
                //当前电池最多只能使用mid分钟
                time = time + Math.min(num, mid);
            }

            //batteries中电池运行mid分钟的总时间time小于n台电脑同时运行mid分钟需要的时间n*mid，则n台电脑不能同时运行mid分钟
            if (time < n * mid) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }

        return left;
    }
}
