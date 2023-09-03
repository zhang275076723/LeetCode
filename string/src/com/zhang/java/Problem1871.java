package com.zhang.java;

/**
 * @Date 2023/2/28 11:41
 * @Author zsy
 * @Description 跳跃游戏 VII 跳跃问题类比Problem45、Problem55、Problem403、Problem1306、Problem1340、Problem1345、Problem1654、Problem1696、Problem2498 前缀和类比Problem209、Problem325、Problem327、Problem437、Problem523、Problem525、Problem560、Problem862、Problem974、Problem1171、Problem1856、Offer57_2
 * 给你一个下标从 0 开始的二进制字符串 s 和两个整数 minJump 和 maxJump 。
 * 一开始，你在下标 0 处，且该位置的值一定为 '0' 。
 * 当同时满足如下条件时，你可以从下标 i 移动到下标 j 处：
 * i + minJump <= j <= min(i + maxJump, s.length - 1) 且 s[j] == '0'.
 * 如果你可以到达 s 的下标 s.length - 1 处，请你返回 true ，否则返回 false 。
 * <p>
 * 输入：s = "011010", minJump = 2, maxJump = 3
 * 输出：true
 * 解释：
 * 第一步，从下标 0 移动到下标 3 。
 * 第二步，从下标 3 移动到下标 5 。
 * <p>
 * 输入：s = "01101110", minJump = 2, maxJump = 3
 * 输出：false
 * <p>
 * 2 <= s.length <= 10^5
 * s[i] 要么是 '0' ，要么是 '1'
 * s[0] == '0'
 * 1 <= minJump <= maxJump < s.length
 */
public class Problem1871 {
    public static void main(String[] args) {
        Problem1871 problem1871 = new Problem1871();
        String s = "011010";
//        String s = "01101110";
        int minJump = 2;
        int maxJump = 3;
        System.out.println(problem1871.canReach(s, minJump, maxJump));
        System.out.println(problem1871.canReach2(s, minJump, maxJump));
    }

    /**
     * 动态规划
     * dp[i]：能否到达s[i]
     * dp[i] = s[i] == '0' && (dp[i-maxJump]-dp[i-minJump]中任意一个dp为true)
     * 时间复杂度O(n*(maxJump-minJump))，空间复杂度O(n)
     *
     * @param s
     * @param minJump
     * @param maxJump
     * @return
     */
    public boolean canReach(String s, int minJump, int maxJump) {
        if (s == null || s.length() == 0 || s.charAt(0) == '1' || s.charAt(s.length() - 1) == '1') {
            return false;
        }

        boolean[] dp = new boolean[s.length()];
        //初始化，s[0]可到达
        dp[0] = true;

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);

            //s[i]不为0，则不能到达，dp[i]为false，直接进行下次循环
            if (c != '0') {
                continue;
            }

            for (int j = Math.max(0, i - maxJump); j <= i - minJump; j++) {
                //j>=0，s[j]为0，j可到达时，才能从j跳跃到i，i才可到达
                if (s.charAt(j) == '0' && dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length() - 1];
    }

    /**
     * 动态规划+前缀和
     * dp[i]：能否到达s[i]
     * preSum[i]：s[0]-s[i-1]能够跳跃到的个数，即dp[0]-dp[i-1]为true的个数
     * dp[i] = s[i] == '0' && (preSum[i-minJump+1]-preSum[i-maxJump] != 0)
     * (s[i]为0，并且s[i-maxJump]-s[i-minJump]至少有一个0可到达，即s[i]可到达)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param minJump
     * @param maxJump
     * @return
     */
    public boolean canReach2(String s, int minJump, int maxJump) {
        if (s == null || s.length() == 0 || s.charAt(0) == '1' || s.charAt(s.length() - 1) == '1') {
            return false;
        }

        boolean[] dp = new boolean[s.length()];
        //s[0]-s[i-1]能够跳跃到的个数，即dp[0]-dp[i-1]为true的个数
        int[] preSum = new int[s.length() + 1];
        //初始化，s[0]可到达
        dp[0] = true;
        //初始化，dp[0]-dp[0]为true的个数为1个
        preSum[1] = 1;

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);

            //s[i]不为0，则s[i]不可到达
            if (c != '0') {
                //更新前缀和
                preSum[i + 1] = preSum[i];
            } else {
                //s[i]为0，才有可能到达，如果s[i-maxJump]-num[i-minJump]有一个为0，
                //并且s[i-maxJump]-num[i-minJump]可到达，则s[i]可到达

                //preSum左右指针，right指针赋值-1，保证preSum[right+1]包括dp[right]
                int left = Math.max(0, i - maxJump);
                int right = Math.max(-1, i - minJump);

                //preSum[right+1]-preSum[left]不为0，则s[i-maxJump]-s[i-minJump]至少有一个0可到达，则s[i]可到达
                if (preSum[right + 1] - preSum[left] != 0) {
                    dp[i] = true;
                }

                //更新前缀和
                preSum[i + 1] = preSum[i] + (dp[i] ? 1 : 0);
            }
        }

        return dp[s.length() - 1];
    }
}
