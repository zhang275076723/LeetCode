package com.zhang.java;

/**
 * @Date 2023/8/21 08:20
 * @Author zsy
 * @Description 3n 块披萨 阿里机试题 类比Problem198、Problem213 动态规划类比Problem198、Problem213、Problem256、Problem265、Problem279、Problem322、Problem338、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1444、Problem1473、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem509、Problem1340、Offer10、Offer10_2
 * 给你一个披萨，它由 3n 块不同大小的部分组成，现在你和你的朋友们需要按照如下规则来分披萨：
 * 你挑选 任意 一块披萨。
 * Alice 将会挑选你所选择的披萨逆时针方向的下一块披萨。
 * Bob 将会挑选你所选择的披萨顺时针方向的下一块披萨。
 * 重复上述过程直到没有披萨剩下。
 * 每一块披萨的大小按顺时针方向由循环数组 slices 表示。
 * 请你返回你可以获得的披萨大小总和的最大值。
 * <p>
 * 输入：slices = [1,2,3,4,5,6]
 * 输出：10
 * 解释：选择大小为 4 的披萨，Alice 和 Bob 分别挑选大小为 3 和 5 的披萨。
 * 然后你选择大小为 6 的披萨，Alice 和 Bob 分别挑选大小为 2 和 1 的披萨。你获得的披萨总大小为 4 + 6 = 10 。
 * <p>
 * 输入：slices = [8,9,8,6,1,1]
 * 输出：16
 * 解释：两轮都选大小为 8 的披萨。如果你选择大小为 9 的披萨，你的朋友们就会选择大小为 8 的披萨，这种情况下你的总和不是最大的。
 * <p>
 * 1 <= slices.length <= 500
 * slices.length % 3 == 0
 * 1 <= slices[i] <= 1000
 */
public class Problem1388 {
    //最大值为int最大值除以2，避免相加在int范围内溢出
    private final int INF = Integer.MAX_VALUE / 2;

    public static void main(String[] args) {
        Problem1388 problem1388 = new Problem1388();
        int[] slices = {1, 2, 3, 4, 5, 6};
        System.out.println(problem1388.maxSizeSlices(slices));
        System.out.println(problem1388.maxSizeSlices2(slices));
        System.out.println(problem1388.maxSizeSlices3(slices));
    }

    /**
     * 动态规划
     * 原数组分成两个数组，一个是从[0,n-2]，另一个是从[1,n-1]，
     * 如果选择slices[0]，则不能选择slices[n-1]；如果选择slices[n-1]，则不能选择slices[0]
     * dp[i][j]：slices[0]-slices[i]选j个不相邻元素和的最大值
     * dp[i][j] = max(dp[i-1][j], dp[i-2][j-1] + slices[i])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param slices
     * @return
     */
    public int maxSizeSlices(int[] slices) {
        //slices[0]-slices[n-2]选n/3个不相邻元素和的最大值
        int max1 = maxSizeSlicesInRange(slices, 0, slices.length - 2, slices.length / 3);
        //slices[1]-slices[n-1]选n/3个不相邻元素和的最大值
        int max2 = maxSizeSlicesInRange(slices, 1, slices.length - 1, slices.length / 3);

        //两者中的较大值，即为slices[0]-slices[n-1]形成一个环中选n/3个不相邻元素和的最大值
        return Math.max(max1, max2);
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param slices
     * @return
     */
    public int maxSizeSlices2(int[] slices) {
        //slices[0]-slices[n-2]选n/3个不相邻元素和的最大值
        int max1 = maxSizeSlicesInRange2(slices, 0, slices.length - 2, slices.length / 3);
        //slices[1]-slices[n-1]选n/3个不相邻元素和的最大值
        int max2 = maxSizeSlicesInRange2(slices, 1, slices.length - 1, slices.length / 3);

        //两者中的较大值，即为slices[0]-slices[n-1]形成一个环中选n/3个不相邻元素和的最大值
        return Math.max(max1, max2);
    }

    /**
     * 递归+记忆化搜索
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param slices
     * @return
     */
    public int maxSizeSlices3(int[] slices) {
        int k = slices.length / 3;
        int left1 = 0;
        int right1 = slices.length - 2;
        int left2 = 1;
        int right2 = slices.length - 1;
        int count1 = right1 - left1 + 1;
        int count2 = right2 - left2 + 1;
        int[][] dp1 = new int[count1 + 1][k + 1];
        int[][] dp2 = new int[count2 + 1][k + 1];

        for (int i = 0; i <= count1; i++) {
            for (int j = 0; j <= k; j++) {
                //初始化为-1，表示当前dp未访问
                dp1[i][j] = -1;
                dp2[i][j] = -1;
            }
        }

        //slices[0]-slices[n-2]选n/3个不相邻元素和的最大值
        int max1 = dfs(left1, right1, k, slices, dp1);
        //slices[1]-slices[n-1]选n/3个不相邻元素和的最大值
        int max2 = dfs(left2, right2, k, slices, dp2);

        //两者中的较大值，即为slices[0]-slices[n-1]形成一个环中选n/3个不相邻元素和的最大值
        return Math.max(max1, max2);
    }

    /**
     * 动态规划
     * dp[i][j]：slices[left]开始前i个元素选j个不相邻元素和的最大值
     * dp[i][j] = max(dp[i-1][j],slices[left+i-1])              (i == 1)
     * dp[i][j] = max(dp[i-1][j],dp[i-2][j-1]+slices[left+i-1]) (i > 1)
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param slices
     * @param left
     * @param right
     * @param k
     * @return
     */
    private int maxSizeSlicesInRange(int[] slices, int left, int right, int k) {
        int count = right - left + 1;
        int[][] dp = new int[count + 1][k + 1];

        //dp初始化，slices[left]开始前i个元素无法选j个不相邻元素
        for (int i = 0; i <= count; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -INF;
            }
        }

        //dp初始化，slices[left]开始前i个元素选0个不相邻元素和的最大值为0
        for (int i = 0; i <= count; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i <= count; i++) {
            for (int j = 1; j <= k; j++) {
                if (i == 1) {
                    dp[i][j] = Math.max(dp[i - 1][j], slices[left + i - 1]);
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 2][j - 1] + slices[left + i - 1]);
                }
            }
        }

        return dp[count][k];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param slices
     * @param left
     * @param right
     * @param k
     * @return
     */
    private int maxSizeSlicesInRange2(int[] slices, int left, int right, int k) {
        int count = right - left + 1;
        int[] p = new int[k + 1];
        int[] q = new int[k + 1];

        //dp初始化，slices[left]开始前i个元素无法选j个不相邻元素
        for (int i = 0; i <= k; i++) {
            p[i] = -INF;
            q[i] = -INF;
        }

        //dp初始化，slices[left]开始前i个元素选0个不相邻元素和的最大值为0
        p[0] = 0;
        q[0] = 0;

        for (int i = 1; i <= count; i++) {
            int[] temp = new int[k + 1];

            for (int j = 1; j <= k; j++) {
                if (i == 1) {
                    temp[j] = Math.max(q[j], slices[left + i - 1]);
                } else {
                    temp[j] = Math.max(q[j], p[j - 1] + slices[left + i - 1]);
                }
            }

            p = q;
            q = temp;
        }

        return q[k];
    }

    /**
     * slices[left]-slices[right]选k个不相邻元素和的最大值
     *
     * @param left
     * @param right
     * @param k
     * @param slices
     * @param dp
     * @return
     */
    private int dfs(int left, int right, int k, int[] slices, int[][] dp) {
        //slices[left]-slices[right]选0个不相邻元素和的最大值为0
        if (k == 0) {
            dp[right - left + 1][k] = 0;
            return dp[right - left + 1][k];
        }

        //slices[left]开始前0个元素无法选k(k>0)个不相邻元素
        if (right - left + 1 == 0) {
            dp[right - left + 1][k] = -INF;
            return dp[right - left + 1][k];
        }

        //当前dp已访问，直接返回当前dp
        if (dp[right - left + 1][k] != -1) {
            return dp[right - left + 1][k];
        }

        if (right - left + 1 == 1) {
            dp[right - left + 1][k] = Math.max(dfs(left, right - 1, k, slices, dp), slices[right]);
        } else {
            dp[right - left + 1][k] = Math.max(dfs(left, right - 1, k, slices, dp),
                    dfs(left, right - 2, k - 1, slices, dp) + slices[right]);
        }

        return dp[right - left + 1][k];
    }
}
