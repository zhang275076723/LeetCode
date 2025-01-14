package com.zhang.java;

/**
 * @Date 2025/2/18 08:05
 * @Author zsy
 * @Description 摧毁小行星 类比Problem735
 * 给你一个整数 mass ，它表示一颗行星的初始质量。
 * 再给你一个整数数组 asteroids ，其中 asteroids[i] 是第 i 颗小行星的质量。
 * 你可以按 任意顺序 重新安排小行星的顺序，然后让行星跟它们发生碰撞。
 * 如果行星碰撞时的质量 大于等于 小行星的质量，那么小行星被 摧毁 ，并且行星会 获得 这颗小行星的质量。
 * 否则，行星将被摧毁。
 * 如果所有小行星 都 能被摧毁，请返回 true ，否则返回 false 。
 * <p>
 * 输入：mass = 10, asteroids = [3,9,19,5,21]
 * 输出：true
 * 解释：一种安排小行星的方式为 [9,19,5,3,21] ：
 * - 行星与质量为 9 的小行星碰撞。新的行星质量为：10 + 9 = 19
 * - 行星与质量为 19 的小行星碰撞。新的行星质量为：19 + 19 = 38
 * - 行星与质量为 5 的小行星碰撞。新的行星质量为：38 + 5 = 43
 * - 行星与质量为 3 的小行星碰撞。新的行星质量为：43 + 3 = 46
 * - 行星与质量为 21 的小行星碰撞。新的行星质量为：46 + 21 = 67
 * 所有小行星都被摧毁。
 * <p>
 * 输入：mass = 5, asteroids = [4,9,23,4]
 * 输出：false
 * 解释：
 * 行星无论如何没法获得足够质量去摧毁质量为 23 的小行星。
 * 行星把别的小行星摧毁后，质量为 5 + 4 + 9 + 4 = 22 。
 * 它比 23 小，所以无法摧毁最后一颗小行星。
 * <p>
 * 1 <= mass <= 10^5
 * 1 <= asteroids.length <= 10^5
 * 1 <= asteroids[i] <= 10^5
 */
public class Problem2126 {
    public static void main(String[] args) {
        Problem2126 problem2126 = new Problem2126();
        int mass = 10;
        int[] asteroids = {3, 9, 19, 5, 21};
        System.out.println(problem2126.asteroidsDestroyed(mass, asteroids));
    }

    /**
     * 排序
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param mass
     * @param asteroids
     * @return
     */
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        //由小到大排序
        heapSort(asteroids);

        //当前行星的质量
        //使用long，避免int相加溢出
        long curMass = mass;

        for (int i = 0; i < asteroids.length; i++) {
            if (curMass < asteroids[i]) {
                return false;
            }

            curMass = curMass + asteroids[i];
        }

        return true;
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
