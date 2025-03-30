package com.zhang.java;

/**
 * @Date 2022/11/5 17:19
 * @Author zsy
 * @Description 鸡蛋掉落 Google面试题 字节面试题 腾讯面试题 vivo机试题 类比Problem312、Problem375、Problem1884、Offer62、CircleBackToOrigin
 * 给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
 * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
 * 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。
 * 如果鸡蛋碎了，你就不能再次使用它。
 * 如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
 * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
 * <p>
 * 输入：k = 1, n = 2
 * 输出：2
 * 解释：
 * 鸡蛋从 1 楼掉落。如果它碎了，肯定能得出 f = 0 。
 * 否则，鸡蛋从 2 楼掉落。如果它碎了，肯定能得出 f = 1 。
 * 如果它没碎，那么肯定能得出 f = 2 。
 * 因此，在最坏的情况下我们需要移动 2 次以确定 f 是多少。
 * <p>
 * 输入：k = 2, n = 6
 * 输出：3
 * <p>
 * 输入：k = 3, n = 14
 * 输出：4
 * <p>
 * 1 <= k <= 100
 * 1 <= n <= 10^4
 */
public class Problem887 {
    public static void main(String[] args) {
        Problem887 problem887 = new Problem887();
        int k = 3;
        int n = 14;
        System.out.println(problem887.superEggDrop(k, n));
        System.out.println(problem887.superEggDrop2(k, n));
        System.out.println(problem887.superEggDrop3(k, n));
        System.out.println(problem887.superEggDrop4(2, 4));
    }

    /**
     * 动态规划1
     * dp[i][j]：i层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋的次数
     * dp[i][j] = min(max(dp[m-1][j-1],dp[i-m][j]) + 1) (1 <= m <= i)
     * 假设在第m层楼扔鸡蛋，如果鸡蛋碎了，则需要在[1,m-1]，m-1层楼扔j-1个鸡蛋找临界楼层，即dp[m-1][j-1]；
     * 如果鸡蛋没碎，则需要在[m+1,i]，i-m层楼扔j个鸡蛋找临界楼层，即dp[i-m][j]
     * 时间复杂度O(n^2*k)，空间复杂度O(nk)
     *
     * @param k
     * @param n
     * @return
     */
    public int superEggDrop(int k, int n) {
        int[][] dp = new int[n + 1][k + 1];

        //dp初始化，i层楼扔1个鸡蛋，需要从1楼开始一层一层往上扔，找到临界楼层最少扔鸡蛋i次
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        //dp初始化，1层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋1次
        for (int j = 1; j <= k; j++) {
            dp[1][j] = 1;
        }

        //楼层从2楼开始往上递增
        for (int i = 2; i <= n; i++) {
            //鸡蛋从2个开始往上递增
            for (int j = 2; j <= k; j++) {
                //初始化为int最大值，则可以取到扔鸡蛋的最佳楼层m
                dp[i][j] = Integer.MAX_VALUE;

                //当前从第m层楼扔鸡蛋
                for (int m = 1; m <= i; m++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[m - 1][j - 1], dp[i - m][j]) + 1);
                }
            }
        }

        return dp[n][k];
    }

    /**
     * 动态规划1+二分查找优化
     * dp[i][j]：i层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋的次数
     * dp[i][j] = min(max(dp[m-1][j-1],dp[i-m][j]) + 1) (1 <= m <= i)
     * 固定i、j，dp[m-1][j-1]关于m单调递增，dp[i-m][j]关于m单调递减，两者函数图像的交点即为min(max(dp[m-1][j-1],dp[i-m][j]))，
     * 则通过二分查找求min(max(dp[m-1][j-1],dp[i-m][j]))
     * 时间复杂度O(n*k*logn)，空间复杂度O(nk)
     *
     * @param k
     * @param n
     * @return
     */
    public int superEggDrop2(int k, int n) {
        int[][] dp = new int[n + 1][k + 1];

        //dp初始化，i层楼扔1个鸡蛋，需要从1楼开始一层一层往上扔，找到临界楼层最少扔鸡蛋i次
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        //dp初始化，1层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋1次
        for (int j = 1; j <= k; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= k; j++) {
                //二分查找左边界，初始化为1，i层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋1次
                int left = 1;
                //二分查找右边界，初始化为i，i层楼扔j个鸡蛋，找到临界楼层最多扔鸡蛋i次
                int right = i;
                int mid;
                //i层楼扔j个鸡蛋，从哪一层楼扔鸡蛋是最佳选择
                int result = left;

                //mid层楼扔鸡蛋，鸡蛋碎dp[mid-1][j-1]，鸡蛋没碎dp[i-mid][j]
                while (left <= right) {
                    mid = left + ((right - left) >> 1);

                    if (dp[mid - 1][j - 1] == dp[i - mid][j]) {
                        result = mid;
                        break;
                    } else if (dp[mid - 1][j - 1] < dp[i - mid][j]) {
                        result = mid;
                        left = mid + 1;
                    } else {
                        result = mid;
                        right = mid - 1;
                    }
                }

                //i层楼扔j个鸡蛋，从result层楼扔鸡蛋是最佳选择
                dp[i][j] = Math.max(dp[result - 1][j - 1], dp[i - result][j]) + 1;
            }
        }

        return dp[n][k];
    }

    /**
     * 动态规划2
     * dp[i][j]：扔i次j个鸡蛋，找到最高的临界楼层
     * dp[i][j] = dp[i-1][j-1] + dp[i-1][j] + 1
     * 无论在哪层楼扔鸡蛋，如果鸡蛋碎了，扔i-1次j-1个鸡蛋，则在当前扔鸡蛋的楼层之下找，即dp[i-1][j-1]；
     * 如果鸡蛋没碎，扔i-1次j个鸡蛋，则在当前扔鸡蛋的楼层之上找，即dp[i-1][j]
     * 时间复杂度O(nk)，空间复杂度O(nk)
     *
     * @param k
     * @param n
     * @return
     */
    public int superEggDrop3(int k, int n) {
        //1层楼扔k个鸡蛋，找到最高的临界楼层需要扔1次
        if (n == 1) {
            return 1;
        }

        //n层楼扔1个鸡蛋，找到最高的临界楼层需要扔n次
        if (k == 1) {
            return n;
        }

        //最多扔n次，最多扔k个鸡蛋
        int[][] dp = new int[n + 1][k + 1];

        //dp初始化，扔i次1个鸡蛋，找到最高的临界楼层为i层
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        //dp初始化，扔1次k个鸡蛋，找到最高的临界楼层为1层
        for (int j = 1; j <= k; j++) {
            dp[1][j] = 1;
        }

        //最多扔n次
        for (int i = 2; i <= n; i++) {
            //最多扔k个鸡蛋
            for (int j = 2; j <= k; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j] + 1;

                //扔i次j个鸡蛋，找到最高的临界楼层大于等于n，则找到了n层楼扔k个鸡蛋，找到最高的临界楼层需要的最少操作次数i
                if (dp[i][j] >= n) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 动态规划2优化，使用滚动数组
     * 时间复杂度O(nk)，空间复杂度O(k)
     *
     * @param k
     * @param n
     * @return
     */
    public int superEggDrop4(int k, int n) {
        //1层楼扔k个鸡蛋，找到最高的临界楼层需要扔1次
        if (n == 1) {
            return 1;
        }

        //n层楼扔1个鸡蛋，找到最高的临界楼层需要扔n次
        if (k == 1) {
            return n;
        }

        //最多扔n次，最多扔k个鸡蛋
        int[] dp = new int[k + 1];

        //dp初始化，扔1次i个鸡蛋，找到最高的临界楼层为1层
        for (int i = 1; i <= k; i++) {
            dp[i] = 1;
        }

        //最多扔n次
        for (int i = 2; i <= n; i++) {
            //最多扔k个鸡蛋，只能从后往前遍历，避免dp[j]影响dp[j+1]
            for (int j = k; j >= 2; j--) {
                dp[j] = dp[j] + dp[j - 1] + 1;

                //扔i次j个鸡蛋，找到最高的临界楼层大于等于n，则找到了n层楼扔k个鸡蛋，找到最高的临界楼层需要的最少操作次数i
                if (dp[j] >= n) {
                    return i;
                }
            }

            //dp初始化，扔i次1个鸡蛋，找到最高的临界楼层为i
            //只能放在最后，避免修改后的dp[1]影响dp[2]
            dp[1] = i;
        }

        return -1;
    }
}
