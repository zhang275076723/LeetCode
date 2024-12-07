package com.zhang.java;

/**
 * @Date 2025/2/3 08:12
 * @Author zsy
 * @Description 数组嵌套 类比Problem141、Problem142、Problem160、Problem202、Problem457、Offer52
 * 索引从0开始长度为N的数组A，包含0到N - 1的所有整数。
 * 找到最大的集合S并返回其大小，其中 S[i] = {A[i], A[A[i]], A[A[A[i]]], ... }且遵守以下的规则。
 * 假设选择索引为i的元素A[i]为S的第一个元素，S的下一个元素应该是A[A[i]]，
 * 之后是A[A[A[i]]]... 以此类推，不断添加直到S出现重复的元素。
 * <p>
 * 输入: A = [5,4,0,3,1,6,2]
 * 输出: 4
 * 解释:
 * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
 * 其中一种最长的 S[K]:
 * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
 * <p>
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] < nums.length
 * A中不含有重复的元素。
 */
public class Problem565 {
    public static void main(String[] args) {
        Problem565 problem565 = new Problem565();
        int[] nums = {5, 4, 0, 3, 1, 6, 2};
        System.out.println(problem565.arrayNesting(nums));
        System.out.println(problem565.arrayNesting2(new int[]{0, 2, 1}));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int arrayNesting(int[] nums) {
        int result = 0;
        boolean[] visited = new boolean[nums.length];

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }

            int index = i;
            //从nums[i]开始的循环大小
            int curSize = 0;

            while (!visited[index]) {
                visited[index] = true;
                curSize++;
                index = nums[index];
            }

            result = Math.max(result, curSize);
        }

        return result;
    }

    /**
     * 模拟+原地标记
     * nums[i]都大于等于0，原数组可以作为访问数组，nums[i]访问过，则设置nums[i]为-1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int arrayNesting2(int[] nums) {
        int result = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == -1) {
                continue;
            }

            int index = i;
            //从nums[i]开始的循环大小
            int curSize = 0;

            while (nums[index] != -1) {
                //先记录index的下一个位置的下标索引，避免nums[index]置为-1之后无法得到下一个位置
                int nextIndex = nums[index];
                nums[index] = -1;
                index = nextIndex;
                curSize++;
            }

            result = Math.max(result, curSize);
        }

        return result;
    }
}
