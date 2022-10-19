package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2022/9/26 8:32
 * @Author zsy
 * @Description 青蛙过河 华为面试题、微信面试题 类比Problem55
 * 一只青蛙想要过河。
 * 假定河流被等分为若干个单元格，并且在每一个单元格内都有可能放有一块石子（也有可能没有）。
 * 青蛙可以跳上石子，但是不可以跳入水中。
 * 给你石子的位置列表 stones（用单元格序号 升序 表示），
 * 请判定青蛙能否成功过河（即能否在最后一步跳至最后一块石子上）。
 * 开始时，青蛙默认已站在第一块石子上，并可以假定它第一步只能跳跃 1 个单位（即只能从单元格 1 跳至单元格 2 ）。
 * 如果青蛙上一步跳跃了 k 个单位，那么它接下来的跳跃距离只能选择为 k - 1、k 或 k + 1 个单位。
 * 另请注意，青蛙只能向前方（终点的方向）跳跃。
 * <p>
 * 输入：stones = [0,1,3,5,6,8,12,17]
 * 输出：true
 * 解释：青蛙可以成功过河，按照如下方案跳跃：跳 1 个单位到第 2 块石子, 然后跳 2 个单位到第 3 块石子,
 * 接着 跳 2 个单位到第 4 块石子, 然后跳 3 个单位到第 6 块石子, 跳 4 个单位到第 7 块石子,
 * 最后，跳 5 个单位到第 8 个石子（即最后一块石子）。
 * <p>
 * 输入：stones = [0,1,2,3,4,8,9,11]
 * 输出：false
 * 解释：这是因为第 5 和第 6 个石子之间的间距太大，没有可选的方案供青蛙跳跃过去。
 * <p>
 * 2 <= stones.length <= 2000
 * 0 <= stones[i] <= 2^31 - 1
 * stones[0] == 0
 * stones 按严格升序排列
 */
public class Problem403 {
    private boolean flag = false;

    public static void main(String[] args) {
        Problem403 problem403 = new Problem403();
        int[] stones = {0, 1, 3, 5, 6, 8, 12, 17};
        System.out.println(problem403.canCross(stones));
        System.out.println(problem403.canCross2(stones));
    }

    /**
     * 回溯+剪枝+记忆化搜索
     * 时间复杂度O(n^2)，空间复杂度O(n^2) (每个石头有n种状态，最多有n^2种状态，set集合最大为n^2)
     *
     * @param stones
     * @return
     */
    public boolean canCross(int[] stones) {
        if (stones == null || stones.length < 2) {
            return true;
        }

        //dp[i][j]：跳跃到stones[i]，并且上次跳跃j步到达stones[i]
        backtrack(0, 0, stones, new boolean[stones.length][stones.length]);

        return flag;
    }

    /**
     * 动态规划
     * dp[i][k]：能否跳跃到stones[i]，且上次跳跃k步到达stones[i]
     * dp[i][j] = dp[j][jump-1] || dp[j][jump] || dp[j][jump+1] (stone[j]为跳跃到stones[i]的前一块石头)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param stones
     * @return
     */
    public boolean canCross2(int[] stones) {
        if (stones == null || stones.length < 2) {
            return true;
        }

        boolean[][] dp = new boolean[stones.length][stones.length];
        dp[0][0] = true;

        //当前石头stone[i]
        for (int i = 1; i < stones.length; i++) {
            //跳跃到stone[i]的前一块石头stone[j]
            for (int j = 0; j < i; j++) {
                //从stone[j]跳跃到stone[i]的步数
                int jump = stones[i] - stones[j];

                //从stone[j]最多只能跳j+1步到达stone[i]
                //因为跳的最远情况为stone[0]跳1步到stone[1]，stone[1]跳2步到stone[2]，stone[2]跳3步到stone[3]，...，stone[j]跳j+1步到stone[j+1]
                if (j + 1 >= jump) {
                    dp[i][jump] = dp[j][jump - 1] || dp[j][jump] || dp[j][jump + 1];

                    //能够跳跃到最后一个石头，返回true
                    if (i == stones.length - 1 && dp[i][jump]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void backtrack(int t, int jump, int[] stones, boolean[][] dp) {
        if (t == stones.length - 1) {
            flag = true;
            return;
        }

        if (flag) {
            return;
        }


        //跳跃到stone[t]，且上次跳跃了jump步到达stone[t]，标记当前路径已经遍历，如果之后再次以jump步到达t，说明此路不通，直接返回
        dp[t][jump] = true;

        for (int i = t + 1; i < stones.length; i++) {
            //只有跳跃范围在[jump-1,jump+1]范围内的石头才能跳
            if (stones[i] - stones[t] >= jump - 1 && stones[i] - stones[t] <= jump + 1) {
                //之前跳跃到过stone[i]，且跳跃到stone[i]之前的上一步跳跃了stones[i]-stones[t]步到达stone[i]，说明本次路径不通
                if (dp[i][stones[i] - stones[t]]) {
                    continue;
                }

                backtrack(i, stones[i] - stones[t], stones, dp);
            }

            //不能从stones[t]跳到stone[i]，说明之后石头都不能跳到，剪枝，直接返回
            if (stones[i] - stones[t] > jump + 1) {
                return;
            }
        }
    }
}
