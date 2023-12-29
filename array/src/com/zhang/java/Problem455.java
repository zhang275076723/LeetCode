package com.zhang.java;

/**
 * @Date 2023/12/31 08:56
 * @Author zsy
 * @Description 分发饼干
 * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
 * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。
 * 如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。
 * 你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
 * <p>
 * 输入: g = [1,2,3], s = [1,1]
 * 输出: 1
 * 解释:
 * 你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
 * 虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
 * 所以你应该输出1。
 * <p>
 * 输入: g = [1,2], s = [1,2,3]
 * 输出: 2
 * 解释:
 * 你有两个孩子和三块小饼干，2个孩子的胃口值分别是1,2。
 * 你拥有的饼干数量和尺寸都足以让所有孩子满足。
 * 所以你应该输出2.
 * <p>
 * 1 <= g.length <= 3 * 10^4
 * 0 <= s.length <= 3 * 10^4
 * 1 <= g[i], s[j] <= 2^31 - 1
 */
public class Problem455 {
    public static void main(String[] args) {
        Problem455 problem455 = new Problem455();
        int[] g = {3, 1, 9, 5};
        int[] s = {1, 10, 7, 2};
        System.out.println(problem455.findContentChildren(g, s));
    }

    /**
     * 排序+双指针
     * 孩子数组和饼干数组都由小到大排序，使g[i]<=s[j]的s[j]最小，这样能够尽可能将饼干分给更多的孩子
     * 时间复杂度O(mlogm+nlogn)，空间复杂度O(logm+logn) (堆排序的空间复杂度为O(logn))
     *
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        //g和s由小到大排序
        heapSort(g);
        heapSort(s);

        //g数组指针
        int i = 0;
        //s数组指针
        int j = 0;

        while (i < g.length && j < s.length) {
            //当前孩子g[i]小于等于当前饼干s[j]，则当前饼干s[j]是满足当前孩子g[i]要求的最小饼干s[j]，i、j指针右移
            if (g[i] <= s[j]) {
                i++;
                j++;
            } else {
                //当前孩子g[i]大于当前饼干s[j]，则当前饼干s[j]不满足当前孩子g[i]要求，j指针右移
                j++;
            }
        }

        return i;
    }

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

        if (leftIndex < heapSize && arr[leftIndex] > arr[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex] > arr[index]) {
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
