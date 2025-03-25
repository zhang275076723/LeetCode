package com.zhang.java;

/**
 * @Date 2025/1/5 08:38
 * @Author zsy
 * @Description 销售价值减少的颜色球 二分查找类比
 * 你有一些球的库存 inventory ，里面包含着不同颜色的球。
 * 一个顾客想要 任意颜色 总数为 orders 的球。
 * 这位顾客有一种特殊的方式衡量球的价值：每个球的价值是目前剩下的 同色球 的数目。
 * 比方说还剩下 6 个黄球，那么顾客买第一个黄球的时候该黄球的价值为 6 。
 * 这笔交易以后，只剩下 5 个黄球了，所以下一个黄球的价值为 5 （也就是球的价值随着顾客购买同色球是递减的）
 * 给你整数数组 inventory ，其中 inventory[i] 表示第 i 种颜色球一开始的数目。
 * 同时给你整数 orders ，表示顾客总共想买的球数目。
 * 你可以按照 任意顺序 卖球。
 * 请你返回卖了 orders 个球以后 最大 总价值之和。
 * 由于答案可能会很大，请你返回答案对 10^9 + 7 取余数 的结果。
 * <p>
 * 输入：inventory = [2,5], orders = 4
 * 输出：14
 * 解释：卖 1 个第一种颜色的球（价值为 2 )，卖 3 个第二种颜色的球（价值为 5 + 4 + 3）。
 * 最大总和为 2 + 5 + 4 + 3 = 14 。
 * <p>
 * 输入：inventory = [3,5], orders = 6
 * 输出：19
 * 解释：卖 2 个第一种颜色的球（价值为 3 + 2），卖 4 个第二种颜色的球（价值为 5 + 4 + 3 + 2）。
 * 最大总和为 3 + 2 + 5 + 4 + 3 + 2 = 19 。
 * <p>
 * 输入：inventory = [2,8,4,10,6], orders = 20
 * 输出：110
 * <p>
 * 输入：inventory = [1000000000], orders = 1000000000
 * 输出：21
 * 解释：卖 1000000000 次第一种颜色的球，总价值为 500000000500000000 。 500000000500000000 对 10^9 + 7 取余为 21 。
 * <p>
 * 1 <= inventory.length <= 10^5
 * 1 <= inventory[i] <= 10^9
 * 1 <= orders <= min(sum(inventory[i]), 10^9)
 */
public class Problem1648 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1648 problem1648 = new Problem1648();
//        int[] inventory = {2, 5};
//        int orders = 4;
        int[] inventory = {73, 98, 35, 100};
        int orders = 178;
        System.out.println(problem1648.maxProfit(inventory, orders));
    }

    /**
     * 二分查找
     * 核心思想：二分查找的结果不是题目要求的结果，而是卖orders个球后的最大inventory[i]
     * 对[left,right]进行二分查找，left为0，right为inventory最大值，计算inventory中元素都小于等于mid所卖球的个数count，
     * 如果count大于orders，则卖order个球的最大总价值对应的inventory中卖球后的最大inventory[i]在mid右边，left=mid+1;
     * 如果count小于等于orders，则卖order个球的最大总价值对应的inventory中卖球后的最大inventory[i]在mid或mid左边，right=mid
     * 得到inventory中卖orders个球后的最大inventory[i]为left，则inventory中大于left的球都卖到剩余left个，
     * 如果卖完之后，卖球的个数小于orders，则剩余需要卖的球以left价格卖出
     * 时间复杂度O(nlog(max(inventory[i])))=O(n)，空间复杂度O(1)
     *
     * @param inventory
     * @param orders
     * @return
     */
    public int maxProfit(int[] inventory, int orders) {
        int max = inventory[0];

        for (int num : inventory) {
            max = Math.max(max, num);
        }

        int left = 0;
        int right = max;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //inventory中元素都小于等于mid所卖球的个数
            //使用long，避免int溢出
            long count = 0;

            //大于mid个数的球都卖到mid个
            for (int num : inventory) {
                if (num > mid) {
                    count = count + num - mid;
                }
            }

            if (count > orders) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        //卖orders个球的最大总价值
        long result = 0;
        //inventory中大于left的个数
        int curCount = 0;

        for (int num : inventory) {
            //购买当前颜色价值[left+1,num]的球，直至剩余left个
            if (num > left) {
                //大于left的球卖的价值分别为num、num-1、...、left+1，共卖num-left个球
                result = (result + (long) (num + left + 1) * (num - left) / 2) % MOD;
                curCount = curCount + num - left;
            }
        }

        //inventory中大于left的个数小于orders，则剩余orders-curCount个球以left价格卖出
        if (curCount < orders) {
            result = (result + (long) (orders - curCount) * left) % MOD;
        }

        return (int) result;
    }
}
