package com.zhang.java;

/**
 * @Date 2023/8/21 08:20
 * @Author zsy
 * @Description 3n 块披萨 类比Problem198、Problem213 动态规划类比Problem198、Problem213、Problem279、Problem322、Problem343、Problem377、Problem416、Problem474、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1444、Offer14、Offer14_2、CircleBackToOrigin、Knapsack 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem329、Problem509、Problem1340、Problem1444、Offer10、Offer10_2
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
     * dp[i][j]：slices[0]-slices[i]中选j个不相邻元素之和的最大值
     * dp[i][j] = max(dp[i-1][j], dp[i-2][j-1] + slices[i])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param slices
     * @return
     */
    public int maxSizeSlices(int[] slices) {
        if (slices == null || slices.length == 0) {
            return -1;
        }

        //slices[0]-slices[slices.length-2]中选slices.length/3个不相邻元素之和的最大值
        int max1 = maxSizeSlicesInRange(slices, 0, slices.length - 2, slices.length / 3);
        //slices[1]-slices[slices.length-1]中选slices.length/3个不相邻元素之和的最大值
        int max2 = maxSizeSlicesInRange(slices, 1, slices.length - 1, slices.length / 3);

        //两者中较大值，即为slices[0]-slices[slices.length-1]形成一个环中选slices.length/3个不相邻元素之和的最大值
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
        if (slices == null || slices.length == 0) {
            return -1;
        }

        //slices[0]-slices[slices.length-2]中选slices.length/3个不相邻元素之和的最大值
        int max1 = maxSizeSlicesInRange2(slices, 0, slices.length - 2, slices.length / 3);
        //slices[1]-slices[slices.length-1]中选slices.length/3个不相邻元素之和的最大值
        int max2 = maxSizeSlicesInRange2(slices, 1, slices.length - 1, slices.length / 3);

        //两者中较大值，即为slices[0]-slices[slices.length-1]首尾相连形成环中选slices.length/3个不相邻元素之和的最大值
        return Math.max(max1, max2);
    }

    /**
     * 递归+记忆化搜索
     * dp[i][j]：slices[0]-slices[i]中选j个不相邻元素之和的最大值
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param slices
     * @return
     */
    public int maxSizeSlices3(int[] slices) {
        if (slices == null || slices.length == 0) {
            return -1;
        }

        int k = slices.length / 3;

        int[][] dp1 = new int[slices.length][k + 1];
        int[][] dp2 = new int[slices.length][k + 1];

        //dp初始化，初始化为int最小值，
        //dp1表示slices[0]-slices[i]中无法选j个不相邻元素；dp2表示slices[1]-slices[i]中无法选j个不相邻元素
        for (int i = 0; i < slices.length; i++) {
            for (int j = 1; j <= k; j++) {
                dp1[i][j] = Integer.MIN_VALUE;
                dp2[i][j] = Integer.MIN_VALUE;
            }
        }

        //dp初始化
        dp1[0][1] = slices[0];
        dp1[1][1] = Math.max(slices[0], slices[1]);
        dp2[1][1] = slices[1];
        dp2[2][1] = Math.max(slices[1], slices[2]);

        int max1 = dfs(0, slices.length - 2, slices, k, dp1);
        int max2 = dfs(1, slices.length - 1, slices, k, dp2);

        return Math.max(max1, max2);
    }

    /**
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param slices
     * @param left
     * @param right
     * @param k
     * @return
     */
    private int maxSizeSlicesInRange(int[] slices, int left, int right, int k) {
        if (left > right) {
            return -1;
        }

        if (left == right) {
            return slices[left];
        }

        int[][] dp = new int[right - left + 1][k + 1];

        //dp初始化，初始化为int最小值，表示slices[0]-slices[i]中无法选j个不相邻元素
        for (int i = 0; i <= right - left; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = Integer.MIN_VALUE;
            }
        }

        //dp初始化
        dp[0][1] = slices[left];
        dp[1][1] = Math.max(slices[left], slices[left + 1]);

        for (int i = 2; i <= right - left; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 2][j - 1] + slices[left + i]);
            }
        }

        return dp[right - left][k];
    }

    /**
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param slices
     * @param left
     * @param right
     * @param k
     * @return
     */
    private int maxSizeSlicesInRange2(int[] slices, int left, int right, int k) {
        if (left > right) {
            return -1;
        }

        if (left == right) {
            return slices[left];
        }

        int[] p = new int[k + 1];
        int[] q = new int[k + 1];

        //dp初始化，初始化为int最小值，表示slices[0]-slices[i]中无法选j个不相邻元素
        for (int i = 1; i <= k; i++) {
            p[i] = Integer.MIN_VALUE;
            q[i] = Integer.MIN_VALUE;
        }

        //dp[i-2]初始化
        p[1] = slices[left];
        //dp[i-1]初始化
        q[1] = Math.max(slices[left], slices[left + 1]);

        for (int i = 2; i <= right - left; i++) {
            //临时数组，用于滚动数组赋值
            int[] temp = new int[k + 1];

            for (int j = 1; j <= k; j++) {
                temp[j] = Math.max(q[j], p[j - 1] + slices[left + i]);
            }

            //更新dp[i-2]、dp[i-1]
            p = q;
            q = temp;
        }

        return q[k];
    }

    /**
     * nums[left]-nums[right]中选k个不相邻元素之和的最大值
     *
     * @param left
     * @param right
     * @param nums
     * @param k
     * @param dp
     * @return
     */
    private int dfs(int left, int right, int[] nums, int k, int[][] dp) {
        //left、right越界，直接返回int最小值，表示nums[left]-nums[right]中无法选k个不相邻元素之和的最大值
        if (left < 0 || right >= nums.length || left > right) {
            return Integer.MIN_VALUE;
        }

        //nums[left]-nums[right]中选0个不相邻元素之和的最大值为0
        if (k == 0) {
            return 0;
        }

        //已经得到了nums[left]-nums[right]中选k个不相邻元素之和的最大值，直接返回0
        if (dp[right][k] != Integer.MIN_VALUE) {
            return dp[right][k];
        }

        //不选nums[right]，则要从nums[left]-nums[right-1]中选k个不相邻元素之和的最大值
        int notSelected = dfs(left, right - 1, nums, k, dp);
        //选nums[right]，则要从nums[left]-nums[right-2]中选k-1个不相邻元素之和的最大值
        int selected = dfs(left, right - 2, nums, k - 1, dp) + nums[right];

        dp[right][k] = Math.max(notSelected, selected);

        return dp[right][k];
    }
}
