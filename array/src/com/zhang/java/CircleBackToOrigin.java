package com.zhang.java;

/**
 * @Date 2023/5/13 08:28
 * @Author zsy
 * @Description 圆环回原点问题 字节面试题 类比Problem70、Problem198、Problem213、Problem403、Offer62 动态规划类比Problem279、Problem322、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem983、Offer14、Offer14_2、Knapsack
 * 圆环上有10个点，编号为0~9。从0点出发，每次可以逆时针和顺时针走一步，问走n步回到0点共有多少种走法。
 * <p>
 * 输入: 2
 * 输出: 2
 * 解释：有2种方案。分别是0->1->0和0->9->0
 */
public class CircleBackToOrigin {
    public static void main(String[] args) {
        CircleBackToOrigin circleBackToOrigin = new CircleBackToOrigin();
        int n = 4;
        System.out.println(circleBackToOrigin.f(n));
    }

    /**
     * 动态规划
     * dp[i][j]：从0出发，走i步到达j的方案数
     * dp[i][j] = dp[i-1][(j-1+arr.length)%arr.length] + dp[i-1][(j+1)%arr.length] (arr.length=10)
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=10，因为圆环上只有10个点)
     *
     * @param n
     * @return
     */
    public int f(int n) {
        int[][] dp = new int[n + 1][10];
        //dp初始化，从0出发，走0步到达0的方案数为1
        dp[0][0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= 9; j++) {
                //从0出发，走i步到达j的方案数 = 从0出发，走i-1步到达j-1的方案数 + 从0出发，走i-1步到达j+1的方案数
                dp[i][j] = dp[i - 1][(j - 1 + 10) % 10] + dp[i - 1][(j + 1) % 10];
            }
        }

        return dp[n][0];
    }
}
