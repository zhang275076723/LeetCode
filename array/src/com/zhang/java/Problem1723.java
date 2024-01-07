package com.zhang.java;

/**
 * @Date 2023/12/16 08:48
 * @Author zsy
 * @Description 完成所有工作的最短时间 美团机试题 美团面试题 集合划分类比Problem416、Problem473、Problem698、Problem2305 状态压缩类比Problem187、Problem294、Problem464、Problem473、Problem526、Problem638、Problem698、Problem847、Problem1908、Problem2305 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem644、Problem658、Problem668、Problem719、Problem878、Problem1201、Problem1482、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 给你一个整数数组 jobs ，其中 jobs[i] 是完成第 i 项工作要花费的时间。
 * 请你将这些工作分配给 k 位工人。所有工作都应该分配给工人，且每项工作只能分配给一位工人。
 * 工人的 工作时间 是完成分配给他们的所有工作花费时间的总和。
 * 请你设计一套最佳的工作分配方案，使工人的 最大工作时间 得以 最小化 。
 * 返回分配方案中尽可能 最小 的 最大工作时间 。
 * <p>
 * 输入：jobs = [3,2,3], k = 3
 * 输出：3
 * 解释：给每位工人分配一项工作，最大工作时间是 3 。
 * <p>
 * 输入：jobs = [1,2,4,7,8], k = 2
 * 输出：11
 * 解释：按下述方式分配工作：
 * 1 号工人：1、2、8（工作时间 = 1 + 2 + 8 = 11）
 * 2 号工人：4、7（工作时间 = 4 + 7 = 11）
 * 最大工作时间是 11 。
 * <p>
 * 1 <= k <= jobs.length <= 12
 * 1 <= jobs[i] <= 10^7
 */
public class Problem1723 {
    private int minTime = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Problem1723 problem1723 = new Problem1723();
        int[] jobs = {1, 2, 4, 7, 8};
        int k = 2;
        System.out.println(problem1723.minimumTimeRequired(jobs, k));
        System.out.println(problem1723.minimumTimeRequired2(jobs, k));
        System.out.println(problem1723.minimumTimeRequired3(jobs, k));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(k^n)，空间复杂度O(n+k) (n=jobs.length)
     *
     * @param jobs
     * @param k
     * @return
     */
    public int minimumTimeRequired(int[] jobs, int k) {
        backtrack(0, 0, new int[k], jobs);

        return minTime;
    }

    /**
     * 二分查找+回溯+剪枝
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为jobs中最大值，right为jobs元素之和，判断k个工人完成任务需要的最小时间是否超过mid，
     * 如果超过mid，则完成任务需要的最小时间在mid右边，left=mid+1；
     * 如果不超过mid，则完成任务需要的最小时间在mid或mid左边，right=mid
     * 时间复杂度O(k^n*log(right-left))=O(k^n)，空间复杂度O(n+k) (n=jobs.length，left和right为int范围内的数，log(right-left)<32)
     *
     * @param jobs
     * @param k
     * @return
     */
    public int minimumTimeRequired2(int[] jobs, int k) {
        //二分查找左边界，初始化为jobs中最大值
        int left = jobs[0];
        //二分查找右边界，初始化为jobs元素之和
        int right = 0;
        int mid;

        for (int time : jobs) {
            left = Math.max(left, time);
            right = right + time;
        }

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //k个工人完成任务需要的最小时间超过mid，则完成任务需要的最小时间在mid右边，left=mid+1
            if (!backtrack(0, new int[k], jobs, mid)) {
                left = mid + 1;
            } else {
                //k个工人完成任务需要的最小时间不超过mid，则完成任务需要的最小时间在mid或mid左边，right=mid
                right = mid;
            }
        }

        return left;
    }

    /**
     * 动态规划+二进制状态压缩
     * sum[i]：二进制访问状态i的情况下，一个工人完成任务需要的时间
     * dp[i][j]：前i个工人(从0开始)分配任务的二进制访问状态j的情况下，完成任务需要的最小时间1
     * sum[i] = sum[j] + jobs[k] (i的二进制表示的最低位1置为0得到j(j=i&(i-1))，并且i的最低位1是从右往左数的第k位)
     * dp[i][j] = min(max(dp[i-1][k],sum[j-k])) (k为二进制访问状态j的子状态)
     * 时间复杂度O(k*3^n)，空间复杂度O(k*2^n)
     * (k个1的二进制访问状态有C(n,k)种，每个k个1的二进制访问状态有2^k个子状态，共∑(C(n,k)*2^k)=∑(C(n,k)*2^k*1^(n-k))=3^n个子状态，二项式定理)
     *
     * @param jobs
     * @param k
     * @return
     */
    public int minimumTimeRequired3(int[] jobs, int k) {
        //任务的个数
        int n = jobs.length;
        //sum[i]：二进制访问状态i的情况下，一个工人完成任务需要的时间，即对应jobs相加
        int[] sum = new int[1 << n];
        //dp[i][j]：前i个工人(从0开始)分配任务的二进制访问状态j的情况下，完成任务需要的最小时间
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

            sum[i] = sum[i & (i - 1)] + jobs[index];
        }

        //dp初始化，二进制访问状态i的情况下，一个工人完成任务需要的时间为sum[i]
        for (int i = 0; i < 1 << n; i++) {
            dp[0][i] = sum[i];
        }

        //前i个工人(从0开始)
        for (int i = 1; i < k; i++) {
            //分配任务的二进制访问状态j
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
     * @param time   分配完第t-1个任务工人完成任务需要的最小时间
     * @param worker 每个工人分配任务完成需要的时间数组
     * @param jobs
     */
    private void backtrack(int t, int time, int[] worker, int[] jobs) {
        if (t == jobs.length) {
            minTime = Math.min(minTime, time);
            return;
        }

        for (int i = 0; i < worker.length; i++) {
            //工人i执行任务jobs[t]之后的执行时间大于等于minTime，则工人i执行任务jobs[t]的最小时间肯定不会小于minTime，
            //剪枝，直接进行下次循环
            if (worker[i] + jobs[t] >= minTime) {
                continue;
            }

            //当前工人i和前一个工人i-1执行任务时间相等，则说明前一个工人i-1已经考虑过任务jobs[t]，则当前工人i不需要再考虑任务jobs[t]，
            //剪枝，直接进行下次循环
            if (i > 0 && worker[i] == worker[i - 1]) {
                continue;
            }

            worker[i] = worker[i] + jobs[t];

            backtrack(t + 1, Math.max(time, worker[i]), worker, jobs);

            worker[i] = worker[i] - jobs[t];
        }
    }

    /**
     * k个工人完成任务需要的最小时间是否超过limit
     * 超过limit，返回false；不超过limit，返回true
     *
     * @param t
     * @param worker
     * @param jobs
     * @param limit
     * @return
     */
    private boolean backtrack(int t, int[] worker, int[] jobs, int limit) {
        //k个工人完成任务需要的最小时间不超过limit，返回true
        if (t == jobs.length) {
            return true;
        }

        for (int i = 0; i < worker.length; i++) {
            if (worker[i] + jobs[t] > limit) {
                continue;
            }

            //当前工人i和前一个工人i-1执行任务时间相等，则说明前一个工人i-1已经考虑过任务jobs[t]，则当前工人i不需要再考虑任务jobs[t]，
            //剪枝，直接进行下次循环
            if (i > 0 && worker[i] == worker[i - 1]) {
                continue;
            }

            worker[i] = worker[i] + jobs[t];

            if (backtrack(t + 1, worker, jobs, limit)) {
                return true;
            }

            worker[i] = worker[i] - jobs[t];
        }

        //遍历结束，则k个工人完成任务需要的最小时间超过limit，返回false
        return false;
    }
}
