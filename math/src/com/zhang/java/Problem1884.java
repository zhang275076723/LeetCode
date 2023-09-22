package com.zhang.java;

/**
 * @Date 2023/9/23 12:17
 * @Author zsy
 * @Description 鸡蛋掉落-两枚鸡蛋 字节面试题 腾讯面试题 类比Problem312、Problem375、Problem887、Offer62、CircleBackToOrigin
 * 给你 2 枚相同 的鸡蛋，和一栋从第 1 层到第 n 层共有 n 层楼的建筑。
 * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都 会碎 ，从 f 楼层或比它低 的楼层落下的鸡蛋都 不会碎 。
 * 每次操作，你可以取一枚 没有碎 的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。
 * 如果鸡蛋碎了，你就不能再次使用它。
 * 如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
 * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
 * <p>
 * 输入：n = 2
 * 输出：2
 * 解释：我们可以将第一枚鸡蛋从 1 楼扔下，然后将第二枚从 2 楼扔下。
 * 如果第一枚鸡蛋碎了，可知 f = 0；
 * 如果第二枚鸡蛋碎了，但第一枚没碎，可知 f = 1；
 * 否则，当两个鸡蛋都没碎时，可知 f = 2。
 * <p>
 * 输入：n = 100
 * 输出：14
 * 解释：
 * 一种最优的策略是：
 * - 将第一枚鸡蛋从 9 楼扔下。如果碎了，那么 f 在 0 和 8 之间。
 * 将第二枚从 1 楼扔下，然后每扔一次上一层楼，在 8 次内找到 f 。
 * 总操作次数 = 1 + 8 = 9 。
 * - 如果第一枚鸡蛋没有碎，那么再把第一枚鸡蛋从 22 层扔下。
 * 如果碎了，那么 f 在 9 和 21 之间。
 * 将第二枚鸡蛋从 10 楼扔下，然后每扔一次上一层楼，在 12 次内找到 f 。
 * 总操作次数 = 2 + 12 = 14 。
 * - 如果第一枚鸡蛋没有再次碎掉，则按照类似的方法从 34, 45, 55, 64, 72, 79, 85, 90, 94, 97, 99 和 100 楼分别扔下第一枚鸡蛋。
 * 不管结果如何，最多需要扔 14 次来确定 f 。
 * <p>
 * 1 <= n <= 1000
 */
public class Problem1884 {
    public static void main(String[] args) {
        Problem1884 problem1884 = new Problem1884();
        int n = 100;
        System.out.println(problem1884.twoEggDrop(n));
        System.out.println(problem1884.twoEggDrop2(n));
        System.out.println(problem1884.twoEggDrop3(n));
        System.out.println(problem1884.twoEggDrop4(n));
    }

    /**
     * 动态规划1
     * dp[i][j]：i层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋的次数
     * dp[i][j] = min(max(dp[k-1][j-1],dp[i-k][j]) + 1) (1 <= k <= i)
     * 假设在第k层楼扔鸡蛋，如果鸡蛋碎了，则需要在[1,k-1]，k-1层楼扔j-1个鸡蛋找临界楼层，即dp[k-1][j-1]；
     * 如果鸡蛋没碎，则需要在[k+1,i]，i-k层楼j个鸡蛋找临界楼层，即dp[i-k][j]
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int twoEggDrop(int n) {
        int[][] dp = new int[n + 1][3];

        //dp初始化，i层楼扔1个鸡蛋，需要从1楼开始一层一层往上扔，找到临界楼层最少扔鸡蛋i次
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        //dp初始化，1层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋1次
        for (int j = 1; j <= 2; j++) {
            dp[1][j] = 1;
        }

        //楼层从2楼开始往上递增
        for (int i = 2; i <= n; i++) {
            //鸡蛋从2个开始往上递增
            for (int j = 2; j <= 2; j++) {
                //初始化为int最大值，则可以取到扔鸡蛋的最佳楼层k
                dp[i][j] = Integer.MAX_VALUE;

                //当前从第k层楼扔鸡蛋
                for (int k = 1; k <= i; k++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k - 1][j - 1], dp[i - k][j]) + 1);
                }
            }
        }

        return dp[n][2];
    }

    /**
     * 动态规划1+二分查找
     * dp[i][j]：i层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋的次数
     * dp[i][j] = min(max(dp[k-1][j-1],dp[i-k][j]) + 1) (1 <= k <= i)
     * 固定i、j，dp[k-1][j-1]关于k单调递增，dp[i-k][j]关于k单调递减，两者函数图像的交点即为min(max(dp[k-1][j-1],dp[i-k][j]))，
     * 则通过二分查找求min(max(dp[k-1][j-1],dp[i-k][j]))
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int twoEggDrop2(int n) {
        int[][] dp = new int[n + 1][3];

        //dp初始化，i层楼扔1个鸡蛋，需要从1楼开始一层一层往上扔，找到临界楼层最少扔鸡蛋i次
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        //dp初始化，1层楼扔j个鸡蛋，找到临界楼层最少扔鸡蛋1次
        for (int j = 1; j <= 2; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= 2; j++) {
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

        return dp[n][2];
    }

    /**
     * 动态规划2
     * dp[i][j]：扔i次j个鸡蛋，找到临界楼层的最高楼层
     * dp[i][j] = dp[i-1][j-1] + dp[i-1][j] + 1
     * 无论在哪层楼扔鸡蛋，如果鸡蛋碎了，扔i-1次j-1个鸡蛋，则在当前扔鸡蛋的楼层之下找，即dp[i-1][j-1]；
     * 如果鸡蛋没碎，扔i-1次j个鸡蛋，则在当前扔鸡蛋的楼层之上找，即dp[i-1][j]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int twoEggDrop3(int n) {
        //1层楼扔2个鸡蛋，找到临界楼层需要扔1次
        if (n == 1) {
            return 1;
        }

        //最多扔n次，最多扔2个鸡蛋
        int[][] dp = new int[n + 1][3];

        //dp初始化，扔i次1个鸡蛋，找到临界楼层的最高楼层为i层
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }

        //dp初始化，扔1次k个鸡蛋，找到临界楼层的最高楼层为1层
        for (int j = 1; j <= 2; j++) {
            dp[1][j] = 1;
        }

        //最多扔n次
        for (int i = 2; i <= n; i++) {
            //最多扔2个鸡蛋
            for (int j = 2; j <= 2; j++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j] + 1;

                //扔i次j个鸡蛋，找到临界楼层的最高楼层大于等于n，则找到了n层楼扔2个鸡蛋，找到临界楼层需要的最少操作次数i
                if (dp[i][j] >= n) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * 动态规划2优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int twoEggDrop4(int n) {
        //1层楼扔2个鸡蛋，找到临界楼层需要扔1次
        if (n == 1) {
            return 1;
        }

        //最多扔n次，最多扔2个鸡蛋
        int[] dp = new int[3];

        //dp初始化，扔1次i个鸡蛋，找到临界楼层的最高楼层为1层
        for (int i = 1; i <= 2; i++) {
            dp[i] = 1;
        }

        //最多扔n次
        for (int i = 2; i <= n; i++) {
            //最多扔2个鸡蛋，只能从后往前遍历，避免dp[j]影响dp[j+1]
            for (int j = 2; j >= 2; j--) {
                dp[j] = dp[j] + dp[j - 1] + 1;

                //扔i次j个鸡蛋，找到临界楼层的最高楼层大于等于n，则找到了n层楼扔2个鸡蛋，找到临界楼层需要的最少操作次数i
                if (dp[j] >= n) {
                    return i;
                }
            }

            //dp初始化，扔i次1个鸡蛋，找到临界楼层的最高楼层为i
            //只能放在最后，避免修改后的dp[1]影响dp[2]
            dp[1] = i;
        }

        return -1;
    }
}
