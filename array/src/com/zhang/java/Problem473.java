package com.zhang.java;

/**
 * @Date 2023/12/12 08:16
 * @Author zsy
 * @Description 火柴拼正方形 集合划分类比Problem416、Problem698 回溯+剪枝类比
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

    private boolean backtrack(int t, int[] bucket, int[] matchsticks, int target) {
        //nums已经遍历完，此时bucket桶中每个元素都为target，存在4个相等的子集，返回true
        if (t == matchsticks.length) {
            return true;
        }

        for (int i = 0; i < bucket.length; i++) {
            //当前桶bucket[i]加上matchsticks[t]大于target，则当前桶无法加上nums[t]，剪枝，直接进行下次循环
            if (bucket[i] + matchsticks[t] > target) {
                continue;
            }

            //当前桶bucket[i]和前一个桶bucket[i-1]相等，则说明前一个桶已经考虑过nums[t]，则当前桶不需要再考虑nums[t]，
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
