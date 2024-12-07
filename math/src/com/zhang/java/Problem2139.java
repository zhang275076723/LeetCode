package com.zhang.java;

/**
 * @Date 2024/7/3 08:42
 * @Author zsy
 * @Description 得到目标值的最少行动次数 类比Problem397、Problem453、Problem991、Problem1342、Problem1404、Problem2571
 * 你正在玩一个整数游戏。从整数 1 开始，期望得到整数 target 。
 * 在一次行动中，你可以做下述两种操作之一：
 * 递增，将当前整数的值加 1（即， x = x + 1）。
 * 加倍，使当前整数的值翻倍（即，x = 2 * x）。
 * 在整个游戏过程中，你可以使用 递增 操作 任意 次数。
 * 但是只能使用 加倍 操作 至多 maxDoubles 次。
 * 给你两个整数 target 和 maxDoubles ，返回从 1 开始得到 target 需要的最少行动次数。
 * <p>
 * 输入：target = 5, maxDoubles = 0
 * 输出：4
 * 解释：一直递增 1 直到得到 target 。
 * <p>
 * 输入：target = 19, maxDoubles = 2
 * 输出：7
 * 解释：最初，x = 1 。
 * 递增 3 次，x = 4 。
 * 加倍 1 次，x = 8 。
 * 递增 1 次，x = 9 。
 * 加倍 1 次，x = 18 。
 * 递增 1 次，x = 19 。
 * <p>
 * 输入：target = 10, maxDoubles = 4
 * 输出：4
 * 解释：
 * 最初，x = 1 。
 * 递增 1 次，x = 2 。
 * 加倍 1 次，x = 4 。
 * 递增 1 次，x = 5 。
 * 加倍 1 次，x = 10 。
 * <p>
 * 1 <= target <= 109
 * 0 <= maxDoubles <= 100
 */
public class Problem2139 {
    public static void main(String[] args) {
        Problem2139 problem2139 = new Problem2139();
        int target = 19;
        int maxDoubles = 2;
        System.out.println(problem2139.minMoves(target, maxDoubles));
    }

    /**
     * 贪心
     * 逆向思维：1变为target只能乘2，或者加1；则target变为1可以除2，或者减1
     * 当target为偶数，优先除2；当target为奇数，优先减1
     * 时间复杂度O(min(log(target),maxDoubles))，空间复杂度O(1)
     *
     * @param target
     * @param maxDoubles
     * @return
     */
    public int minMoves(int target, int maxDoubles) {
        int count = 0;

        while (target != 1 && maxDoubles > 0) {
            //target为偶数，优先除2
            if (target % 2 == 0) {
                maxDoubles--;
                target = target / 2;
                count++;
            } else {
                //target为奇数，优先减1
                target--;
                count++;
            }
        }

        //target不为1，此时maxDoubles为0，则target只能减1操作
        if (target != 1) {
            count = count + (target - 1);
        }

        return count;
    }
}
