package com.zhang.java;

/**
 * @Date 2024/3/30 08:11
 * @Author zsy
 * @Description 统计字典序元音字符串的数目 类比Problem62 矩阵快速幂类比Problem70、Problem509、Problem1137、Offer10、Offer10_2 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1704、Problem1839、Problem2062、Problem2063、Problem2559、Problem2586、Problem2785
 * 给你一个整数 n，请返回长度为 n 、仅由元音 (a, e, i, o, u) 组成且按 字典序排列 的字符串数量。
 * 字符串 s 按 字典序排列 需要满足：对于所有有效的 i，s[i] 在字母表中的位置总是与 s[i+1] 相同或在 s[i+1] 之前。
 * <p>
 * 输入：n = 1
 * 输出：5
 * 解释：仅由元音组成的 5 个字典序字符串为 ["a","e","i","o","u"]
 * <p>
 * 输入：n = 2
 * 输出：15
 * 解释：仅由元音组成的 15 个字典序字符串为
 * ["aa","ae","ai","ao","au","ee","ei","eo","eu","ii","io","iu","oo","ou","uu"]
 * 注意，"ea" 不是符合题意的字符串，因为 'e' 在字母表中的位置比 'a' 靠后
 * <p>
 * 输入：n = 33
 * 输出：66045
 * <p>
 * 1 <= n <= 50
 */
public class Problem1641 {
    public static void main(String[] args) {
        Problem1641 problem1641 = new Problem1641();
        int n = 33;
        System.out.println(problem1641.countVowelStrings(n));
        System.out.println(problem1641.countVowelStrings2(n));
        System.out.println(problem1641.countVowelStrings3(n));
        System.out.println(problem1641.countVowelStrings4(n));
    }

    /**
     * 动态规划
     * dp[i][j]：长度为i，并且以第j个元音结尾按照字典序排列的元音字符串个数
     * dp[i][j] = sum(dp[i-1][k]) (0 <= k <= j)
     * 时间复杂度O(n*|Σ|^2)=O(n)，空间复杂度O(n*|Σ|)=O(n) (|Σ|=5，只包含5个小写元音)
     *
     * @param n
     * @return
     */
    public int countVowelStrings(int n) {
        int[][] dp = new int[n + 1][5];

        //dp初始化，长度为1，并且以第j个元音结尾按照字典序排列的元音字符串个数为1
        for (int j = 0; j < 5; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k <= j; k++) {
                    dp[i][j] = dp[i][j] + dp[i - 1][k];
                }
            }
        }

        //长度为n，并且按照字典序排列的元音字符串个数
        int count = 0;

        for (int j = 0; j < 5; j++) {
            count = count + dp[n][j];
        }

        return count;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n*|Σ|^2)=O(n)，空间复杂度O(|Σ|)=O(1) (|Σ|=5，只包含5个小写元音)
     *
     * @param n
     * @return
     */
    public int countVowelStrings2(int n) {
        int[] dp = new int[5];

        //dp初始化，长度为1，并且以第i个元音结尾按照字典序排列的元音字符串个数为1
        for (int i = 0; i < 5; i++) {
            dp[i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            //注意：只能逆序遍历，因为小的dp会影响大的dp，如果先将小的dp更新，则大的dp不能得到正确结果
            for (int j = 4; j >= 0; j--) {
                //注意：k小于j，而不是dp[i][j]中的k小于等于j
                for (int k = 0; k < j; k++) {
                    dp[j] = dp[j] + dp[k];
                }
            }
        }

        //长度为n，并且按照字典序排列的元音字符串个数
        int count = 0;

        for (int i = 0; i < 5; i++) {
            count = count + dp[i];
        }

        return count;
    }

    /**
     * 动态规划+矩阵快速幂
     * dp[i][j]：长度为i，并且以第j个元音结尾按照字典序排列的元音字符串个数
     * dp[i][j] = sum(dp[i-1][k]) (0 <= k <= j)
     * [dp[n][4]]         [1 1 1 1 1] ^ (n-1)         [dp[1][4]]
     * [dp[n][3]]         [0 1 1 1 1]                 [dp[1][3]]
     * [dp[n][2]]    =    [0 0 1 1 1]        *        [dp[1][2]]
     * [dp[n][1]]         [0 0 0 1 1]                 [dp[1][1]]
     * [dp[n][0]]         [0 0 0 0 1]                 [dp[1][0]]
     * 时间复杂度O(logn*|Σ|^3)=O(logn)，空间复杂度O(|Σ|^2)=O(1) (|Σ|=5，只包含5个小写元音)
     *
     * @param n
     * @return
     */
    public int countVowelStrings3(int n) {
        int[][] result = {
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {0, 0, 1, 1, 1},
                {0, 0, 0, 1, 1},
                {0, 0, 0, 0, 1}
        };

        result = quickPow(result, n - 1);
        result = multiply(result, new int[][]{{1}, {1}, {1}, {1}, {1}});

        //长度为n，并且按照字典序排列的元音字符串个数
        int count = 0;

        for (int i = 0; i < 5; i++) {
            count = count + result[i][0];
        }

        return count;
    }

    /**
     * 组合数学
     * n个球放入m个盒子，盒子不能为空：C(n-1,m-1)，n个球中间有n-1个空槽，选择其中m-1个空槽作为隔断
     * n个球放入m个盒子，盒子可以为空：C(n+m-1,m-1)，n个球和m-1个隔断共n+m-1个元素，选择其中m-1个元素作为隔断
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int countVowelStrings4(int n) {
        //C(n+5-1,5-1)=C(n+4,4)
        return (n + 4) * (n + 3) * (n + 2) * (n + 1) / (1 * 2 * 3 * 4);
    }

    /**
     * 二维矩阵快速幂
     * 时间复杂度O(logn*|Σ|^3)=O(logn)，空间复杂度O(1) (|Σ|=5，只包含5个小写元音)
     *
     * @param a
     * @param n
     * @return
     */
    private int[][] quickPow(int[][] a, int n) {
        int[][] result = new int[a.length][a.length];

        //result初始化，初始化为单位矩阵
        for (int i = 0; i < a.length; i++) {
            result[i][i] = 1;
        }

        while (n != 0) {
            if ((n & 1) == 1) {
                result = multiply(result, a);
            }

            a = multiply(a, a);
            n = n >>> 1;
        }

        return result;
    }

    /**
     * 矩阵a和矩阵b相乘
     * 时间复杂度O(a.length*b[0].length*a[0].length)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    private int[][] multiply(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] = result[i][j] + a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }
}
