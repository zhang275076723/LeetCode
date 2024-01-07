package com.zhang.java;

/**
 * @Date 2023/12/17 08:36
 * @Author zsy
 * @Description 公平分发饼干 集合划分类比Problem416、Problem473、Problem698、Problem1723 状态压缩类比Problem187、Problem294、Problem464、Problem473、Problem526、Problem638、Problem698、Problem847、Problem1723、Problem1908 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem644、Problem658、Problem668、Problem719、Problem878、Problem1201、Problem1482、Problem1723、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 给你一个整数数组 cookies ，其中 cookies[i] 表示在第 i 个零食包中的饼干数量。
 * 另给你一个整数 k 表示等待分发零食包的孩子数量，所有 零食包都需要分发。
 * 在同一个零食包中的所有饼干都必须分发给同一个孩子，不能分开。
 * 分发的 不公平程度 定义为单个孩子在分发过程中能够获得饼干的最大总数。
 * 返回所有分发的最小不公平程度。
 * <p>
 * 输入：cookies = [8,15,10,20,8], k = 2
 * 输出：31
 * 解释：一种最优方案是 [8,15,8] 和 [10,20] 。
 * - 第 1 个孩子分到 [8,15,8] ，总计 8 + 15 + 8 = 31 块饼干。
 * - 第 2 个孩子分到 [10,20] ，总计 10 + 20 = 30 块饼干。
 * 分发的不公平程度为 max(31,30) = 31 。
 * 可以证明不存在不公平程度小于 31 的分发方案。
 * <p>
 * 输入：cookies = [6,1,3,2,2,4,1,2], k = 3
 * 输出：7
 * 解释：一种最优方案是 [6,1]、[3,2,2] 和 [4,1,2] 。
 * - 第 1 个孩子分到 [6,1] ，总计 6 + 1 = 7 块饼干。
 * - 第 2 个孩子分到 [3,2,2] ，总计 3 + 2 + 2 = 7 块饼干。
 * - 第 3 个孩子分到 [4,1,2] ，总计 4 + 1 + 2 = 7 块饼干。
 * 分发的不公平程度为 max(7,7,7) = 7 。
 * 可以证明不存在不公平程度小于 7 的分发方案。
 * <p>
 * 2 <= cookies.length <= 8
 * 1 <= cookies[i] <= 10^5
 * 2 <= k <= cookies.length
 */
public class Problem2305 {
    private int result = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Problem2305 problem2305 = new Problem2305();
        int[] cookies = {8, 15, 10, 20, 8};
        int k = 2;
        System.out.println(problem2305.distributeCookies(cookies, k));
        System.out.println(problem2305.distributeCookies2(cookies, k));
        System.out.println(problem2305.distributeCookies3(cookies, k));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(k^n)，空间复杂度O(n+k) (n=cookies.length)
     *
     * @param cookies
     * @param k
     * @return
     */
    public int distributeCookies(int[] cookies, int k) {
        backtrack(0, 0, cookies, new int[k]);

        return result;
    }

    /**
     * 二分查找+回溯+剪枝
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为cookies中最大值，right为cookies元素之和，判断k个孩子分配零食得到的最小饼干数量是否超过mid，
     * 如果超过mid，则分配零食得到的最小饼干数量在mid右边，left=mid+1；
     * 如果不超过mid，则分配零食得到的最小饼干数量在mid或mid左边，right=mid
     * 时间复杂度O(k^n*log(right-left))=O(k^n)，空间复杂度O(n+k) (n=cookies.length，left和right为int范围内的数，log(right-left)<32)
     *
     * @param cookies
     * @param k
     * @return
     */
    public int distributeCookies2(int[] cookies, int k) {
        //二分查找左边界，初始化为cookies中最大值
        int left = cookies[0];
        //二分查找右边界，初始化为cookies元素之和
        int right = 0;
        int mid;

        for (int cookie : cookies) {
            left = Math.max(left, cookie);
            right = right + cookie;
        }

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //k个孩子分配零食得到的最小饼干数量超过mid，则分配零食得到的最小饼干数量在mid右边，left=mid+1
            if (!backtrack(0, cookies, new int[k], mid)) {
                left = mid + 1;
            } else {
                //k个孩子分配零食得到的最小饼干数量不超过mid，则分配零食得到的最小饼干数量在mid或mid左边，right=mid
                right = mid;
            }
        }

        return left;
    }

    /**
     * 动态规划+二进制状态压缩
     * sum[i]：二进制访问状态i的情况下，一个孩子得到的饼干数量
     * dp[i][j]：前i个孩子(从0开始)分配零食的二进制访问状态j的情况下，得到饼干的最小数量
     * sum[i] = sum[j] + jobs[k] (i的二进制表示的最低位1置为0得到j(j=i&(i-1))，并且i的最低位1是从右往左数的第k位)
     * dp[i][j] = min(max(dp[i-1][k],sum[j-k])) (k为二进制访问状态j的子状态)
     * 时间复杂度O(k*3^n)，空间复杂度O(k*2^n)
     * (k个1的二进制访问状态有C(n,k)种，每个k个1的二进制访问状态有2^k个子状态，共∑(C(n,k)*2^k)=∑(C(n,k)*2^k*1^(n-k))=3^n个子状态，二项式定理)
     *
     * @param cookies
     * @param k
     * @return
     */
    public int distributeCookies3(int[] cookies, int k) {
        //零食的个数
        int n = cookies.length;
        //sum[i]：二进制访问状态i的情况下，一个孩子得到的饼干数量，即对应cookies相加
        int[] sum = new int[1 << n];
        //dp[i][j]：前i个孩子(从0开始)分配零食的二进制访问状态j的情况下，得到饼干的最小数量
        int[][] dp = new int[k][1 << n];

        for (int i = 1; i < 1 << n; i++) {
            //i&(i-1)：i表示的二进制数中最低位的1置为0表示的数
            //i-(i&(i-1))：i表示的二进制数中最低位的1表示的数
            int j = i - (i & (i - 1));
            //j表示的二进制数中的1是从右往左的第几位(从0开始)
            int index = 0;

            while (j != 1) {
                index++;
                j = j >>> 1;
            }

            sum[i] = sum[i & (i - 1)] + cookies[index];
        }

        //dp初始化，二进制访问状态i的情况下，一个孩子得到饼干的数量为sum[i]
        for (int i = 0; i < 1 << n; i++) {
            dp[0][i] = sum[i];
        }

        //前i个孩子(从0开始)
        for (int i = 1; i < k; i++) {
            //分配零食的二进制访问状态j
            for (int j = 0; j < 1 << n; j++) {
                dp[i][j] = Integer.MAX_VALUE;

                //m为二进制状态j的所有子状态
                //(m-1)&j：得到二进制状态j的所有子状态
                for (int m = j; m > 0; m = (m - 1) & j) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[i - 1][m], sum[j - m]));
                }
            }
        }

        return dp[k - 1][(1 << n) - 1];
    }

    /**
     * @param t
     * @param curMax  分配完第t-1个零食孩子得到的最小饼干数量
     * @param cookies
     * @param child   每个孩子分配零食得到饼干的数量数组
     */
    private void backtrack(int t, int curMax, int[] cookies, int[] child) {
        if (t == cookies.length) {
            result = Math.min(result, curMax);
            return;
        }

        for (int i = 0; i < child.length; i++) {
            //孩子i分配零食cookies[t]之后的饼干数量大于等于result，则孩子i分配零食cookies[t]的最小饼干数量肯定不会小于minTime，
            //剪枝，直接进行下次循环
            if (child[i] + cookies[t] >= result) {
                continue;
            }

            //当前孩子i和前一个孩子i-1分配饼干的数量相等，则说明前一个孩子i-1已经考虑过零食cookies[t]，则当前孩子i不需要再考虑零食cookies[t]，
            //剪枝，直接进行下次循环
            if (i > 0 && child[i] == child[i - 1]) {
                continue;
            }

            child[i] = child[i] + cookies[t];

            backtrack(t + 1, Math.max(curMax, child[i]), cookies, child);

            child[i] = child[i] - cookies[t];
        }
    }

    /**
     * k个孩子分配零食得到的最小饼干数量是否超过limit
     * 超过limit，返回false；不超过limit，返回true
     *
     * @param t
     * @param cookies
     * @param child
     * @param limit
     * @return
     */
    private boolean backtrack(int t, int[] cookies, int[] child, int limit) {
        //k个孩子分配零食得到的最小饼干数量不超过limit，返回true
        if (t == cookies.length) {
            return true;
        }

        for (int i = 0; i < child.length; i++) {
            if (child[i] + cookies[t] > limit) {
                continue;
            }

            //当前孩子i和前一个孩子i-1分配饼干的数量相等，则说明前一个孩子i-1已经考虑过零食cookies[t]，则当前孩子i不需要再考虑零食cookies[t]，
            //剪枝，直接进行下次循环
            if (i > 0 && child[i] == child[i - 1]) {
                continue;
            }

            child[i] = child[i] + cookies[t];

            if (backtrack(t + 1, cookies, child, limit)) {
                return true;
            }

            child[i] = child[i] - cookies[t];
        }

        //遍历结束，则k个孩子分配零食得到的最小饼干数量超过limit，返回false
        return false;
    }
}
