package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/12 08:16
 * @Author zsy
 * @Description 火柴拼正方形 集合划分类比Problem416、Problem698、Problem1723、Problem2305 状态压缩类比Problem187、Problem294、Problem464、Problem526、Problem638、Problem698、Problem847、Problem1723、Problem1908、Problem2305 记忆化搜索类比 动态规划类比
 * 你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。
 * 你要用 所有的火柴棍 拼成一个正方形。
 * 你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
 * 如果你能使这个正方形，则返回 true ，否则返回 false 。
 * <p>
 * 输入: matchsticks = [1,1,2,2,2]
 * 输出: true
 * 解释: 能拼成一个边长为2的正方形，每边两根火柴。
 * <p>
 * 输入: matchsticks = [3,3,3,3,4]
 * 输出: false
 * 解释: 不能用所有火柴拼成一个正方形。
 * <p>
 * 1 <= matchsticks.length <= 15
 * 1 <= matchsticks[i] <= 10^8
 */
public class Problem473 {
    public static void main(String[] args) {
        Problem473 problem473 = new Problem473();
        int[] matchsticks = {1, 1, 2, 2, 2};
//        int[] matchsticks = {3, 3, 3, 3, 4};
        System.out.println(problem473.makesquare(matchsticks));
        System.out.println(problem473.makesquare2(matchsticks));
        System.out.println(problem473.makesquare3(matchsticks));
    }

    /**
     * 回溯+剪枝，难点在于由大到小排序后剪枝
     * 数组中元素由大到小排序，判断当前元素能否放到其中一个桶中，如果可以，则继续判断下一个元素
     * 时间复杂度O(k^n)，空间复杂度O(k+logn) (k=4，共4个桶，堆排序空间需要O(logn)，递归栈深度O(k))
     *
     * @param matchsticks
     * @return
     */
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks.length < 4) {
            return false;
        }

        int sum = 0;

        for (int len : matchsticks) {
            sum = sum + len;
        }

        //元素之和不能被4整除，则不能划分为4个子集，直接返回false
        if (sum % 4 != 0) {
            return false;
        }

        //每个桶的元素之和
        int target = sum / 4;

        //由大到小排序，先将大的元素放入桶中
        heapSort(matchsticks);

        //元素都大于0，最大的元素大于target，则不存在4个相等的子集，返回false
        if (matchsticks[0] > target) {
            return false;
        }

        return backtrack(0, new int[4], matchsticks, target);
    }

    /**
     * 记忆化搜索+二进制状态压缩
     * 二进制访问状态第i位(低位到高位，最低位为第0位)为1，则表示matchsticks[i]已访问；第i位为0，则表示matchsticks[i]未访问
     * 时间复杂度O(n*2^n)，空间复杂度O(2^n) (共2^n种状态，每种状态需要O(n)计算得到)
     *
     * @param matchsticks
     * @return
     */
    public boolean makesquare2(int[] matchsticks) {
        if (matchsticks.length < 4) {
            return false;
        }

        int sum = 0;

        for (int len : matchsticks) {
            sum = sum + len;
        }

        //元素之和不能被4整除，则不能划分为4个子集，直接返回false
        if (sum % 4 != 0) {
            return false;
        }

        //每个桶的元素之和
        int target = sum / 4;

        //由小到大排序，如果当前元素加入当前桶中超过target，后面所有的元素都比当前元素大，则都不能加入当前桶中
        Arrays.sort(matchsticks);

        //元素都大于0，最大的元素大于target，则不存在4个相等的子集，返回false
        if (matchsticks[matchsticks.length - 1] > target) {
            return false;
        }

        //key：当前二进制访问状态，value：二进制访问状态key的情况下，能否划分为4个相等的子集
        Map<Integer, Boolean> map = new HashMap<>();

        return dfs(0, 0, matchsticks, target, map);
    }

    /**
     * 动态规划+二进制状态压缩
     * dp[i]：二进制访问状态i的情况下，前j个子集大小都为target，当前小于target的子集大小
     * dp[i] = (dp[j]+matchsticks[k])%target (二进制访问状态j的第k位未访问，置为1之后的二进制访问状态为i，并且二进制访问状态j可达)
     * 时间复杂度O(n*2^n)，空间复杂度O(2^n) (共2^n种状态，每种状态需要O(n)计算得到)
     *
     * @param matchsticks
     * @return
     */
    public boolean makesquare3(int[] matchsticks) {
        if (matchsticks.length < 4) {
            return false;
        }

        int sum = 0;

        for (int len : matchsticks) {
            sum = sum + len;
        }

        //元素之和不能被4整除，则不能划分为4个子集，直接返回false
        if (sum % 4 != 0) {
            return false;
        }

        //每个桶的元素之和
        int target = sum / 4;

        //由小到大排序，如果当前元素加入当前桶中超过target，后面所有的元素都比当前元素大，则都不能加入当前桶中
        Arrays.sort(matchsticks);

        //元素都大于0，最大的元素大于target，则不存在4个相等的子集，返回false
        if (matchsticks[matchsticks.length - 1] > target) {
            return false;
        }

        int[] dp = new int[1 << matchsticks.length];

        //dp初始化，-1表示二进制访问状态不可达
        for (int i = 0; i < 1 << matchsticks.length; i++) {
            dp[i] = -1;
        }

        //dp初始化，二进制访问状态0的情况下，当前小于target的子集大小为0
        dp[0] = 0;

        //二进制访问状态i，第k位为1，则matchsticks[k]已访问
        for (int i = 0; i < 1 << matchsticks.length; i++) {
            //二进制访问状态i不可达，直接进行下次循环
            if (dp[i] == -1) {
                continue;
            }

            for (int j = 0; j < matchsticks.length; j++) {
                //二进制访问状态i的第j位为1，即matchsticks[j]已访问，直接进行下次循环
                if (((i >>> j) & 1) == 1) {
                    continue;
                }

                //二进制访问状态i，当前小于target的子集加上matchsticks[j]的大小超过target，又因为matchsticks[j]后面所有的元素都比matchsticks[j]大，
                //都不能加入当前小于target的子集中，则二进制访问状态i^(1<<j)的情况下，不能划分为4个相等的子集，直接跳出循环
                if (dp[i] + matchsticks[j] > target) {
                    break;
                }

                //i^(1<<j)：二进制访问状态i的第j位由0置为1的二进制访问状态
                dp[i ^ (1 << j)] = (dp[i] + matchsticks[j]) % target;
            }
        }

        //遍历结束，所有元素都已访问的二进制访问状态(1<<matchsticks.length)-1的情况下，
        //当前小于target的子集大小为0，则能划分为4个相等的子集
        return dp[(1 << matchsticks.length) - 1] == 0;
    }

    private boolean backtrack(int t, int[] bucket, int[] matchsticks, int target) {
        //matchsticks已经遍历完，此时bucket桶中每个元素都为target，存在4个相等的子集，返回true
        if (t == matchsticks.length) {
            return true;
        }

        for (int i = 0; i < bucket.length; i++) {
            //当前桶bucket[i]加上matchsticks[t]大于target，则当前桶无法加上matchsticks[t]，剪枝，直接进行下次循环
            if (bucket[i] + matchsticks[t] > target) {
                continue;
            }

            //当前桶bucket[i]和前一个桶bucket[i-1]相等，则说明前一个桶已经考虑过matchsticks[t]，则当前桶不需要再考虑matchsticks[t]，
            //剪枝，直接进行下次循环
            if (i > 0 && bucket[i] == bucket[i - 1]) {
                continue;
            }

            bucket[i] = bucket[i] + matchsticks[t];

            //继续往后找matchsticks[t+1]应该放在哪个桶中，如果找到4个相等的子集，则返回true
            if (backtrack(t + 1, bucket, matchsticks, target)) {
                return true;
            }

            bucket[i] = bucket[i] - matchsticks[t];
        }

        //遍历结束，没有找到4个相等的子集，则返回false
        return false;
    }

    /**
     * @param state
     * @param sum         当前桶中元素之和，如果当前桶中元素大于等于target，则模target，表示下一个桶中小于target元素之和
     * @param matchsticks
     * @param target
     * @param map
     * @return
     */
    private boolean dfs(int state, int sum, int[] matchsticks, int target, Map<Integer, Boolean> map) {
        //所有元素都已访问，则能划分为4个相等的子集，返回true
        if (state == (1 << matchsticks.length) - 1) {
            map.put(state, true);
            return true;
        }

        //已经得到二进制访问状态state的情况下，能否划分为4个相等的子集，直接返回
        if (map.containsKey(state)) {
            return map.get(state);
        }

        for (int i = 0; i < matchsticks.length; i++) {
            //二进制访问状态state的第i位为1，则matchsticks[i]已访问，直接进行下次循环
            if (((state >>> i) & 1) == 1) {
                continue;
            }

            //matchsticks[i]加入当前桶中元素之和超过target，又因为matchsticks[i]后面的元素都比matchsticks[i]大，都不能加入当前桶中，
            //则二进制访问状态state的情况下，不能划分为4个相等的子集，返回false
            if (sum + matchsticks[i] > target) {
                map.put(state, false);
                return false;
            }

            //(sum+matchsticks[i])%target可以当sum+matchsticks[i]>=target时，得到下一个桶中小于target元素之和
            //state^(1<<i)：将二进制访问状态state中未访问的第i位由0置为1，得到第i位置为1的二进制访问状态
            //二进制访问状态state^(1<<i)的情况下，能划分为4个相等的子集，返回true
            if (dfs(state ^ (1 << i), (sum + matchsticks[i]) % target, matchsticks, target, map)) {
                map.put(state, true);
                return true;
            }
        }

        //遍历结束，二进制访问状态state的情况下，不能划分为4个相等的子集，返回false
        map.put(state, false);
        return false;
    }

    /**
     * 由大到小堆排
     *
     * @param arr
     */
    private void heapSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[leftIndex] < arr[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex] < arr[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
