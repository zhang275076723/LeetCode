package com.zhang.java;

/**
 * @Date 2024/11/20 08:14
 * @Author zsy
 * @Description 股票平滑下跌阶段的数目 类比Problem713、Problem1918 股票类比Problem121、Problem122、Problem123、Problem188、Problem309、Problem714、Problem901、Problem2034、Problem2291、Offer63 滑动窗口类比
 * 给你一个整数数组 prices ，表示一支股票的历史每日股价，其中 prices[i] 是这支股票第 i 天的价格。
 * 一个 平滑下降的阶段 定义为：对于 连续一天或者多天 ，每日股价都比 前一日股价恰好少 1 ，这个阶段第一天的股价没有限制。
 * 请你返回 平滑下降阶段 的数目。
 * <p>
 * 输入：prices = [3,2,1,4]
 * 输出：7
 * 解释：总共有 7 个平滑下降阶段：
 * [3], [2], [1], [4], [3,2], [2,1] 和 [3,2,1]
 * 注意，仅一天按照定义也是平滑下降阶段。
 * <p>
 * 输入：prices = [8,6,7,7]
 * 输出：4
 * 解释：总共有 4 个连续平滑下降阶段：[8], [6], [7] 和 [7]
 * 由于 8 - 6 ≠ 1 ，所以 [8,6] 不是平滑下降阶段。
 * <p>
 * 输入：prices = [1]
 * 输出：1
 * 解释：总共有 1 个平滑下降阶段：[1]
 * <p>
 * 1 <= prices.length <= 10^5
 * 1 <= prices[i] <= 10^5
 */
public class Problem2110 {
    public static void main(String[] args) {
        Problem2110 problem2110 = new Problem2110();
        int[] prices = {3, 2, 1, 4};
        System.out.println(problem2110.getDescentPeriods(prices));
    }

    /**
     * 滑动窗口，双指针
     * prices[left]-prices[right]当日股价都比前一日小1，则以prices[right]为右边界的数组都是平滑下跌阶段，
     * 即prices[left]-prices[right]、prices[left+1]-prices[right]、...、prices[right]-prices[right]都是平滑下跌阶段，
     * 共right-left+1个
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param prices
     * @return
     */
    public long getDescentPeriods(int[] prices) {
        //使用long，避免int溢出
        long count = 0;
        int left = 0;
        int right = 0;

        while (right < prices.length) {
            //prices[left]-prices[right]不是平滑下跌阶段，则更新left为right，即prices[left]-prices[right]长度为1是平滑下跌阶段
            if (left < right && prices[right - 1] - 1 != prices[right]) {
                left = right;
            }

            //prices[left]-prices[right]、prices[left+1]-prices[right]、...、prices[right]-prices[right]都是平滑下跌阶段，
            //共right-left+1个
            count = count + (right - left + 1);
            right++;
        }

        return count;
    }
}
