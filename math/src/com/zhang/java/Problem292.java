package com.zhang.java;

/**
 * @Date 2023/12/5 08:45
 * @Author zsy
 * @Description Nim 游戏 字节面试题 类比Problem258、Problem293、Problem294、Problem390、Problem464、Problem486、Problem1823、Offer62
 * 你和你的朋友，两个人一起玩 Nim 游戏：
 * 桌子上有一堆石头。
 * 你们轮流进行自己的回合， 你作为先手 。
 * 每一回合，轮到的人拿掉 1 - 3 块石头。
 * 拿掉最后一块石头的人就是获胜者。
 * 假设你们每一步都是最优解。请编写一个函数，来判断你是否可以在给定石头数量为 n 的情况下赢得游戏。
 * 如果可以赢，返回 true；否则，返回 false 。
 * <p>
 * 输入：n = 4
 * 输出：false
 * 解释：以下是可能的结果:
 * 1. 移除1颗石头。你的朋友移走了3块石头，包括最后一块。你的朋友赢了。
 * 2. 移除2个石子。你的朋友移走2块石头，包括最后一块。你的朋友赢了。
 * 3.你移走3颗石子。你的朋友移走了最后一块石头。你的朋友赢了。
 * 在所有结果中，你的朋友是赢家。
 * <p>
 * 输入：n = 1
 * 输出：true
 * <p>
 * 输入：n = 2
 * 输出：true
 * <p>
 * 1 <= n <= 2^31 - 1
 */
public class Problem292 {
    public static void main(String[] args) {
        Problem292 problem292 = new Problem292();
        int n = 10;
        System.out.println(problem292.canWinNim(n));
        System.out.println(problem292.canWinNim2(n));
    }

    /**
     * 动态规划
     * dp[i]：自己先手石头数量为i的情况下能否赢得游戏
     * dp[i] = !dp[i-1] || !dp[i-2] || !dp[i-3]
     * 自己先手石头数量为i的情况下，拿1-3个石头，使对方先手石头数量为i-1或i-2或i-3的情况下不能赢得游戏，则自己就能赢得游戏
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public boolean canWinNim(int n) {
        if (n <= 3) {
            return true;
        }

        boolean[] dp = new boolean[n + 1];
        dp[1] = true;
        dp[2] = true;
        dp[3] = true;

        for (int i = 4; i <= n; i++) {
            //自己先手石头数量为i的情况下，拿1-3个石头，使对方先手石头数量为i-1或i-2或i-3的情况下不能赢得游戏，则自己就能赢得游戏
            dp[i] = !dp[i - 1] || !dp[i - 2] || !dp[i - 3];
        }

        return dp[n];
    }

    /**
     * 数学
     * 巴什博弈：n个物品，2名玩家轮流拿物品，每次只能拿1-m个物品，最后拿完物品的玩家胜利，只要n不能整除m+1，则先手拿物品的玩家胜利
     * 自己先手石头数量为4的情况下，自己肯定不能赢得游戏，因为自己只能拿走1-3个石头，剩下的石头对方能一次性拿完，对方胜利；
     * 自己先手石头数量为4n的情况下，自己拿走1-3个石头，对手总能拿1-3个石头，使剩下的石头数量为4的倍数，
     * 直至自己先手石头数量为4的情况下，自己拿走1-3个石头，剩下的石头对方能一次性拿完，对方胜利，所以4n的情况下，自己肯定不能赢；
     * 自己先手石头数量不为4n的情况下，自己总能拿走1-3个石头，使剩下的石头数量为4的倍数，即变为对手先手石头数量为4n的情况下，自己肯定能赢
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public boolean canWinNim2(int n) {
        return n % 4 != 0;
    }
}
