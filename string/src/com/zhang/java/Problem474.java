package com.zhang.java;

/**
 * @Date 2023/7/22 08:31
 * @Author zsy
 * @Description 一和零 动态规划类比Problem198、Problem213、Problem279、Problem322、Problem343、Problem377、Problem416、Problem494、Problem518、Problem746、Problem983、Problem1340、Problem1388、Problem1444、Offer14、Offer14_2、Offer60、CircleBackToOrigin、Knapsack
 * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
 * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
 * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
 * <p>
 * 输入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3
 * 输出：4
 * 解释：最多有 5 个 0 和 3 个 1 的最大子集是 {"10","0001","1","0"} ，因此答案是 4 。
 * 其他满足题意但较小的子集包括 {"0001","1"} 和 {"10","1","0"} 。{"111001"} 不满足题意，因为它含 4 个 1 ，大于 n 的值 3 。
 * <p>
 * 输入：strs = ["10", "0", "1"], m = 1, n = 1
 * 输出：2
 * 解释：最大的子集是 {"0", "1"} ，所以答案是 2 。
 * <p>
 * 1 <= strs.length <= 600
 * 1 <= strs[i].length <= 100
 * strs[i] 仅由 '0' 和 '1' 组成
 * 1 <= m, n <= 100
 */
public class Problem474 {
    public static void main(String[] args) {
        Problem474 problem474 = new Problem474();
        String[] strs = {"10", "0001", "111001", "1", "0"};
        int m = 5;
        int n = 3;
        System.out.println(problem474.findMaxForm(strs, m, n));
        System.out.println(problem474.findMaxForm2(strs, m, n));
    }

    /**
     * 动态规划 01背包
     * dp[i][j][k]：strs[0]-strs[i-1]中使用j个0和k个1得到的最大字符串数量
     * dp[i][j][k] = dp[i-1][j][k]                          (j < m || k < n) (m：strs[i-1]中0的数量，n：strs[i-1]中1的数量)
     * dp[i][j][k] = max(dp[i-1][j][k],dp[i-1][j-m][k-n]+1) (j >= m && k >= n)
     * 时间复杂度O(lpmn)，空间复杂度O(lmn) (l=strs.length，p=max(strs[i].length()))
     *
     * @param strs
     * @param m
     * @param n
     * @return
     */
    public int findMaxForm(String[] strs, int m, int n) {
        int[][][] dp = new int[strs.length + 1][m + 1][n + 1];

        for (int i = 1; i <= strs.length; i++) {
            //strs[i-1]中0的数量
            int zeroCount = findStrZero(strs[i - 1]);
            //strs[i-1]中1的数量
            int oneCount = strs[i - 1].length() - zeroCount;

            //注意：j、k都要从0开始遍历，因为strs中0和1的数量可能为0
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    //j或k的数量小于strs[i-1]中0或1的数量，则不能选strs[i-1]
                    if (j < zeroCount || k < oneCount) {
                        dp[i][j][k] = dp[i - 1][j][k];
                    } else {
                        //取选或不选strs[i-1]两者中的较大值
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - zeroCount][k - oneCount] + 1);
                    }
                }
            }
        }

        return dp[strs.length][m][n];
    }

    /**
     * 动态规划优化，使用滚动数组
     * dp[j][k]：strs[0]-strs[i-1]中使用j个0和k个1得到的最大字符串数量
     * dp[j][k] = dp[j][k]                     (j < m || k < n) (m：strs[i-1]中0的数量，n：strs[i-1]中1的数量)
     * dp[j][k] = max(dp[j][k],dp[j-m][k-n]+1) (j >= m && k >= n)
     * 时间复杂度O(lpmn)，空间复杂度O(mn) (l=strs.length，p=max(strs[i].length()))
     *
     * @param strs
     * @param m
     * @param n
     * @return
     */
    public int findMaxForm2(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= strs.length; i++) {
            //strs[i-1]中0的数量
            int zeroCount = findStrZero(strs[i - 1]);
            //strs[i-1]中1的数量
            int oneCount = strs[i - 1].length() - zeroCount;

            //当前dp[j][k]会使用到前面的dp，所以逆序遍历
            //注意：j、k都要遍历到0，因为strs中0和1的数量可能为0
            for (int j = m; j >= 0; j--) {
                for (int k = n; k >= 0; k--) {
                    if (j >= zeroCount && k >= oneCount) {
                        dp[j][k] = Math.max(dp[j][k], dp[j - zeroCount][k - oneCount] + 1);
                    }
                }
            }
        }

        return dp[m][n];
    }

    /**
     * 统计str中字符'0'的个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param str
     * @return
     */
    private int findStrZero(String str) {
        int count = 0;

        for (char c : str.toCharArray()) {
            if (c == '0') {
                count++;
            }
        }

        return count;
    }
}
