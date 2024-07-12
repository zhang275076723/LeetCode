package com.zhang.java;

/**
 * @Date 2023/4/29 10:34
 * @Author zsy
 * @Description 加油站 字节面试题 网易机试题 类比Problem871 类比Problem277、Problem898、Problem1386、Problem1640
 * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。
 * 你从其中的一个加油站出发，开始时油箱为空。
 * 给定两个整数数组 gas 和 cost ，如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。
 * 如果存在解，则 保证 它是 唯一 的。
 * <p>
 * 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
 * 输出: 3
 * 解释:
 * 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
 * 开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
 * 开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
 * 开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
 * 开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
 * 开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
 * 因此，3 可为起始索引。
 * <p>
 * 输入: gas = [2,3,4], cost = [3,4,3]
 * 输出: -1
 * 解释:
 * 你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
 * 我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
 * 开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
 * 开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
 * 你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
 * 因此，无论怎样，你都不可能绕环路行驶一周。
 * <p>
 * gas.length == n
 * cost.length == n
 * 1 <= n <= 10^5
 * 0 <= gas[i], cost[i] <= 10^4
 */
public class Problem134 {
    public static void main(String[] args) {
        Problem134 problem134 = new Problem134();
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        System.out.println(problem134.canCompleteCircuit(gas, cost));
        System.out.println(problem134.canCompleteCircuit2(gas, cost));
    }

    /**
     * 暴力
     * 从数组中每个位置作为起点，判断是否能绕环路行驶一周
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        //i作为起点开始遍历
        for (int i = 0; i < gas.length; i++) {
            //当前剩余汽油量
            int remainGas = gas[i];
            //当前遍历到的下标索引
            int j = i;

            while (remainGas - cost[j] >= 0) {
                remainGas = remainGas - cost[j] + gas[(j + 1) % gas.length];
                j = (j + 1) % gas.length;

                //能够回到起始下标索引i，则能绕环路行驶一周，返回i
                if (j == i) {
                    return i;
                }
            }
        }

        //所有的起始位置都不能绕环路行驶一周，返回-1
        return -1;
    }

    /**
     * 贪心
     * 下标索引i作为起点无法到达下标索引j，则i到j之间的任意下标索引k都不能作为起点，下次将j+1作为起点继续判断
     * (因为从i开始时，有剩余汽油的情况下都无法到达j，则i到j之间任意下标索引k作为起点，更不可能到达j，即不能作为起点)
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit2(int[] gas, int[] cost) {
        //i作为起点开始遍历
        for (int i = 0; i < gas.length; i++) {
            //当前剩余汽油量
            int remainGas = gas[i];
            //当前遍历到的下标索引
            int j = i;

            while (remainGas - cost[j] >= 0) {
                remainGas = remainGas - cost[j] + gas[(j + 1) % gas.length];
                j = (j + 1) % gas.length;

                //能够回到起始下标索引i，则能绕环路行驶一周，返回i
                if (j == i) {
                    return i;
                }
            }

            //当前遍历到的下标索引j小于起始下标索引i，因为是从0开始遍历，此时0-i都无法作为起点，j在0-i之间，
            //则说明所有的起始位置都不能绕环路行驶一周，返回-1
            if (j < i) {
                return -1;
            }

            //从i无法到j，则i到j之间的任意下标索引k作为起点都无法到达j，下一次起点为j+1
            i = j;
        }

        //所有的起始位置都不能绕环路行驶一周，返回-1
        return -1;
    }
}
