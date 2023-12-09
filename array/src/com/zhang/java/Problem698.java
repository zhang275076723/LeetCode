package com.zhang.java;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/9/14 8:37
 * @Author zsy
 * @Description 划分为k个相等的子集 网易机试题 集合划分类比Problem416、Problem473、Problem1723、Problem2305 分割类比Problem659、Problem725 状态压缩类比Problem187、Problem294、Problem464、Problem473、Problem526、Problem638、Problem847、Problem1723、Problem1908、Problem2305 记忆化搜索类比 动态规划类比 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Offer17、Offer38
 * 给定一个整数数组  nums 和一个正整数 k，找出是否有可能把这个数组分成 k 个非空子集，其总和都相等。
 * <p>
 * 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * 输出： True
 * 说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。
 * <p>
 * 输入: nums = [1,2,3,4], k = 3
 * 输出: false
 * <p>
 * 1 <= k <= len(nums) <= 16
 * 0 < nums[i] < 10000
 * 每个元素的频率在 [1,4] 范围内
 */
public class Problem698 {
    public static void main(String[] args) {
        Problem698 problem698 = new Problem698();
        //(1,4,9) (4,4,6) (2,2,4,6)
//        int[] nums = {4, 4, 4, 6, 1, 2, 2, 9, 4, 6};
//        int k = 3;
//        int[] nums = {2, 2, 2, 2, 3, 4, 5};
//        int k = 4;
        int[] nums = {129, 17, 74, 57, 1421, 99, 92, 285, 1276, 218, 1588, 215, 369, 117, 153, 22};
        int k = 3;
        System.out.println(problem698.canPartitionKSubsets(nums, k));
        System.out.println(problem698.canPartitionKSubsets2(nums, k));
        System.out.println(problem698.canPartitionKSubsets3(nums, k));
    }

    /**
     * 回溯+剪枝，难点在于由大到小排序后剪枝
     * 数组中元素由大到小排序，判断当前元素能否放到其中一个桶中，如果可以，则继续判断下一个元素
     * 时间复杂度O(k^n)，空间复杂度O(k+n) (归并排序空间需要O(n)，递归栈深度O(k))
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (k == 1) {
            return true;
        }

        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //元素之和不能被k整除，则不能划分为k个相等子集，直接返回false
        if (sum % k != 0) {
            return false;
        }

        //每个桶的元素之和
        int target = sum / k;

        //由大到小排序，先将大的元素放入桶中
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        //元素都大于0，最大的元素大于target，则不存在k个相等的子集，返回false
        if (nums[0] > target) {
            return false;
        }

        return backtrack(0, new int[k], nums, target);
    }

    /**
     * 记忆化搜索+二进制状态压缩
     * 二进制访问状态第i位(低位到高位，最低位为第0位)为1，则表示nums[i]已访问；第i位为0，则表示nums[i]未访问
     * 时间复杂度O(n*2^n)，空间复杂度O(2^n) (共2^n种状态，每种状态需要O(n)计算得到)
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets2(int[] nums, int k) {
        if (k == 1) {
            return true;
        }

        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //元素之和不能被k整除，则不能划分为k个相等子集，直接返回false
        if (sum % k != 0) {
            return false;
        }

        //每个桶的元素之和
        int target = sum / k;

        //由小到大排序，如果当前元素加入当前桶中超过target，后面所有的元素都比当前元素大，则都不能加入当前桶中
        Arrays.sort(nums);

        //元素都大于0，最大的元素大于target，则不存在k个相等的子集，返回false
        if (nums[nums.length - 1] > target) {
            return false;
        }

        //key：当前二进制访问状态，value：二进制访问状态key的情况下，能否划分为k个相等的子集
        Map<Integer, Boolean> map = new HashMap<>();

        return dfs(0, 0, nums, target, map);
    }

    /**
     * 动态规划+二进制状态压缩
     * dp[i]：二进制访问状态i的情况下，前j个子集大小都为target，当前小于target的子集大小
     * dp[i] = (dp[j]+nums[k])%target (二进制访问状态j的第k位未访问，置为1之后的二进制访问状态为i，并且二进制访问状态j可达)
     * 时间复杂度O(n*2^n)，空间复杂度O(2^n) (共2^n种状态，每种状态需要O(n)计算得到)
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean canPartitionKSubsets3(int[] nums, int k) {
        if (k == 1) {
            return true;
        }

        int sum = 0;

        for (int num : nums) {
            sum = sum + num;
        }

        //元素之和不能被k整除，则不能划分为k个相等子集，直接返回false
        if (sum % k != 0) {
            return false;
        }

        //每个桶的元素之和
        int target = sum / k;

        //由小到大排序，如果当前元素加入当前桶中超过target，后面所有的元素都比当前元素大，则都不能加入当前桶中
        Arrays.sort(nums);

        //元素都大于0，最大的元素大于target，则不存在k个相等的子集，返回false
        if (nums[nums.length - 1] > target) {
            return false;
        }

        int[] dp = new int[1 << nums.length];

        //dp初始化，-1表示二进制访问状态不可达
        for (int i = 0; i < 1 << nums.length; i++) {
            dp[i] = -1;
        }

        //dp初始化，二进制访问状态0的情况下，当前小于target的子集大小为0
        dp[0] = 0;

        //二进制访问状态i，第k位为1，则nums[k]已访问
        for (int i = 0; i < 1 << nums.length; i++) {
            //二进制访问状态i不可达，直接进行下次循环
            if (dp[i] == -1) {
                continue;
            }

            for (int j = 0; j < nums.length; j++) {
                //二进制访问状态i的第j位为1，即nums[j]已访问，直接进行下次循环
                if (((i >>> j) & 1) == 1) {
                    continue;
                }

                //二进制访问状态i，当前小于target的子集加上nums[j]的大小超过target，又因为nums[j]后面所有的元素都比nums[j]大，
                //都不能加入当前小于target的子集中，则二进制访问状态i^(1<<j)的情况下，不能划分为k个相等的子集，直接跳出循环
                if (dp[i] + nums[j] > target) {
                    break;
                }

                //i^(1<<j)：二进制访问状态i的第j位由0置为1的二进制访问状态
                dp[i ^ (1 << j)] = (dp[i] + nums[j]) % target;
            }
        }

        //遍历结束，所有元素都已访问的二进制访问状态(1<<nums.length)-1的情况下，
        //当前小于target的子集大小为0，则能划分为k个相等的子集
        return dp[(1 << nums.length) - 1] == 0;
    }

    private boolean backtrack(int t, int[] bucket, int[] nums, int target) {
        //nums已经遍历完，此时bucket桶中每个元素都为target，存在k个相等的子集，返回true
        if (t == nums.length) {
            return true;
        }

        for (int i = 0; i < bucket.length; i++) {
            //当前桶bucket[i]加上nums[t]大于target，则当前桶无法加上nums[t]，剪枝，直接进行下次循环
            if (bucket[i] + nums[t] > target) {
                continue;
            }

            //当前桶bucket[i]和前一个桶bucket[i-1]相等，则说明前一个桶已经考虑过nums[t]，则当前桶不需要再考虑nums[t]，
            //剪枝，直接进行下次循环
            if (i > 0 && bucket[i] == bucket[i - 1]) {
                continue;
            }

            bucket[i] = bucket[i] + nums[t];

            //继续往后找nums[t]应该放在哪个桶中，如果找到k个相等的子集，则返回true
            if (backtrack(t + 1, bucket, nums, target)) {
                return true;
            }

            bucket[i] = bucket[i] - nums[t];
        }

        //遍历结束，没有找到k个相等的子集，则返回false
        return false;
    }

    /**
     * @param state
     * @param sum    当前桶中元素之和，如果当前桶中元素大于等于target，则模target，表示下一个桶中小于target元素之和
     * @param nums
     * @param target
     * @param map
     * @return
     */
    private boolean dfs(int state, int sum, int[] nums, int target, Map<Integer, Boolean> map) {
        //所有元素都已访问，则能划分为k个相等的子集，返回true
        if (state == (1 << nums.length) - 1) {
            map.put(state, true);
            return true;
        }

        //已经得到二进制访问状态state的情况下，能否划分为k个相等的子集，直接返回
        if (map.containsKey(state)) {
            return map.get(state);
        }

        for (int i = 0; i < nums.length; i++) {
            //二进制访问状态state的第i位为1，则nums[i]已访问，直接进行下次循环
            if (((state >>> i) & 1) == 1) {
                continue;
            }

            //nums[i]加入当前桶中元素之和超过target，又因为nums[i]后面的元素都比nums[i]大，都不能加入当前桶中，
            //则二进制访问状态state的情况下，不能划分为k个相等的子集，返回false
            if (sum + nums[i] > target) {
                map.put(state, false);
                return false;
            }

            //(sum+nums[i])%target可以当sum+nums[i]>=target时，得到下一个桶中小于target元素之和
            //state^(1<<i)：将二进制访问状态state中未访问的第i位由0置为1，得到第i位置为1的二进制访问状态
            //二进制访问状态state^(1<<i)的情况下，能划分为k个相等的子集，返回true
            if (dfs(state ^ (1 << i), (sum + nums[i]) % target, nums, target, map)) {
                map.put(state, true);
                return true;
            }
        }

        //遍历结束，二进制访问状态state的情况下，不能划分为k个相等的子集，返回false
        map.put(state, false);
        return false;
    }

    /**
     * 由大到小归并排序
     *
     * @param arr
     * @param left
     * @param right
     * @param tempArr
     */
    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] > arr[j]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
