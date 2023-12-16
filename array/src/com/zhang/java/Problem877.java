package com.zhang.java;

/**
 * @Date 2023/12/22 08:16
 * @Author zsy
 * @Description 石子游戏 类比Problem337 类比Problem486 类比Problem292、Problem293、Problem294、Problem390、Problem464、Problem486、Problem1908 动态规划类比 记忆化搜索类比
 * Alice 和 Bob 用几堆石子在做游戏。一共有偶数堆石子，排成一行；每堆都有 正 整数颗石子，数目为 piles[i] 。
 * 游戏以谁手中的石子最多来决出胜负。石子的 总数 是 奇数 ，所以没有平局。
 * Alice 和 Bob 轮流进行，Alice 先开始 。 每回合，玩家从行的 开始 或 结束 处取走整堆石头。
 * 这种情况一直持续到没有更多的石子堆为止，此时手中 石子最多 的玩家 获胜 。
 * 假设 Alice 和 Bob 都发挥出最佳水平，当 Alice 赢得比赛时返回 true ，当 Bob 赢得比赛时返回 false 。
 * <p>
 * 输入：piles = [5,3,4,5]
 * 输出：true
 * 解释：
 * Alice 先开始，只能拿前 5 颗或后 5 颗石子 。
 * 假设他取了前 5 颗，这一行就变成了 [3,4,5] 。
 * 如果 Bob 拿走前 3 颗，那么剩下的是 [4,5]，Alice 拿走后 5 颗赢得 10 分。
 * 如果 Bob 拿走后 5 颗，那么剩下的是 [3,4]，Alice 拿走后 4 颗赢得 9 分。
 * 这表明，取前 5 颗石子对 Alice 来说是一个胜利的举动，所以返回 true 。
 * <p>
 * 输入：piles = [3,7,2,3]
 * 输出：true
 * <p>
 * 2 <= piles.length <= 500
 * piles.length 是 偶数
 * 1 <= piles[i] <= 500
 * sum(piles[i]) 是 奇数
 */
public class Problem877 {
    public static void main(String[] args) {
        Problem877 problem877 = new Problem877();
        int[] piles = {5, 3, 4, 5};
        System.out.println(problem877.stoneGame(piles));
        System.out.println(problem877.stoneGame2(piles));
        System.out.println(problem877.stoneGame3(piles));
        System.out.println(problem877.stoneGame4(piles));
    }

    /**
     * dfs
     * 时间复杂度O(2^n)，空间复杂度O(n) (共O(2^n)种状态，dfs栈的深度O(n))
     *
     * @param piles
     * @return
     */
    public boolean stoneGame(int[] piles) {
        //当前玩家先手得到的石头减去对手得到的石头的最大值大于0，则当前玩家胜利
        //因为石头总数为奇数，所以不存在两人的石头数量相等的情况
        return dfs(0, piles.length - 1, piles) > 0;
    }

    /**
     * 递归+记忆化搜索
     * dp[i][j]：piles[i]-piles[j]范围内，当前玩家先手得到的石头减去对手得到的石头的最大值
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param piles
     * @return
     */
    public boolean stoneGame2(int[] piles) {
        int[][] dp = new int[piles.length][piles.length];

        //dp初始化，，初始化为int最大值表示当前情况还没有考虑
        for (int i = 0; i < piles.length; i++) {
            for (int j = 0; j < piles.length; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        //当前玩家先手得到的石头减去对手得到的石头的最大值大于0，则当前玩家胜利
        //因为石头总数为奇数，所以不存在两人的石头数量相等的情况
        return dfs(0, piles.length - 1, piles, dp) > 0;
    }

    /**
     * 动态规划
     * dp[i][j]：piles[i]-piles[j]范围内，当前玩家先手得到的石头减去对手得到的石头的最大值
     * dp[i][j] = max(piles[i]-dp[i+1][j],piles[j]-dp[i][j-1])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param piles
     * @return
     */
    public boolean stoneGame3(int[] piles) {
        int[][] dp = new int[piles.length][piles.length];

        //dp初始化，piles[i]-piles[i]范围内，当前玩家先手得到的石头减去对手得到的石头的最大值为piles[i]
        for (int i = 0; i < piles.length; i++) {
            dp[i][i] = piles[i];
        }

        //当前数组长度i
        for (int i = 2; i <= piles.length; i++) {
            //长度i情况下的起始下标索引j，即当前数组piles[j]-piles[j+i-1]
            for (int j = 0; j <= piles.length - i; j++) {
                //当前玩家先手拿piles[j]得到的石头减去对手得到的石头的最大值
                int max1 = piles[j] - dp[j + 1][j + i - 1];
                //当前玩家先手拿piles[j+i-1]得到的石头减去对手得到的石头的最大值
                int max2 = piles[j + i - 1] - dp[j][j + i - 2];

                dp[j][j + i - 1] = Math.max(max1, max2);
            }
        }

        //当前玩家先手得到的石头减去对手得到的石头的最大值大于0，则当前玩家胜利
        //因为石头总数为奇数，所以不存在两人的石头数量相等的情况
        return dp[0][piles.length - 1] > 0;
    }

    /**
     * 数学
     * piles长度为偶数，并且piles元素之和为奇数，即奇数和偶数下标索引元素之和不相等，
     * 先手玩家选择奇数或偶数下标索引的石头，则后手玩家只能选择偶数或奇数下标索引的石头，
     * 所以先手玩家只要选择奇数或偶数下标索引之和中较大的石头数量，就一定能赢
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param piles
     * @return
     */
    public boolean stoneGame4(int[] piles) {
        //先手玩家只要选择奇数或偶数下标索引之和中较大的石头数量，就一定能赢
        return true;
    }

    /**
     * piles[left]-piles[right]范围内，当前玩家先手得到的石头减去对手得到的石头的最大值
     *
     * @param left
     * @param right
     * @param piles
     * @return
     */
    private int dfs(int left, int right, int[] piles) {
        if (left > right) {
            return 0;
        }

        if (left == right) {
            return piles[left];
        }

        //当前玩家先手拿piles[left]得到的石头减去对手得到的石头的最大值
        int max1 = piles[left] - dfs(left + 1, right, piles);
        //当前玩家先手拿piles[right]得到的石头减去对手得到的石头的最大值
        int max2 = piles[right] - dfs(left, right - 1, piles);

        return Math.max(max1, max2);
    }

    /**
     * 记忆化搜索，piles[left]-piles[right]范围内，当前玩家先手得到的石头减去对手得到的石头的最大值
     *
     * @param left
     * @param right
     * @param piles
     * @param dp
     * @return
     */
    private int dfs(int left, int right, int[] piles, int[][] dp) {
        if (left > right) {
            return 0;
        }

        if (left == right) {
            dp[left][right] = piles[left];
            return dp[left][right];
        }

        //已经得到piles[left]-piles[right]范围内，当前玩家先手得到的石头减去对手得到的石头的最大值，直接返回dp[left][right]
        if (dp[left][right] != Integer.MAX_VALUE) {
            return dp[left][right];
        }

        //当前玩家先手拿piles[left]得到的石头减去对手得到的石头的最大值
        int max1 = piles[left] - dfs(left + 1, right, piles, dp);
        //当前玩家先手拿piles[right]得到的石头减去对手得到的石头的最大值
        int max2 = piles[right] - dfs(left, right - 1, piles, dp);

        return Math.max(max1, max2);
    }
}
