package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/2/5 08:03
 * @Author zsy
 * @Description 跳跃游戏 III 跳跃问题类比Problem45、Problem55、Problem403、Problem1340、Problem1345、Problem1696、Problem1871 dfs类比Offer33、Problem1008
 * 这里有一个非负整数数组 arr，你最开始位于该数组的起始下标 start 处。
 * 当你位于下标 i 处时，你可以跳到 i + arr[i] 或者 i - arr[i]。
 * 请你判断自己是否能够跳到对应元素值为 0 的 任一 下标处。
 * 注意，不管是什么情况下，你都无法跳到数组之外。
 * <p>
 * 输入：arr = [4,2,3,0,3,1,2], start = 5
 * 输出：true
 * 解释：
 * 到达值为 0 的下标 3 有以下可能方案：
 * 下标 5 -> 下标 4 -> 下标 1 -> 下标 3
 * 下标 5 -> 下标 6 -> 下标 4 -> 下标 1 -> 下标 3
 * <p>
 * 输入：arr = [4,2,3,0,3,1,2], start = 0
 * 输出：true
 * 解释：
 * 到达值为 0 的下标 3 有以下可能方案：
 * 下标 0 -> 下标 4 -> 下标 1 -> 下标 3
 * <p>
 * 输入：arr = [3,0,2,1,2], start = 2
 * 输出：false
 * 解释：无法到达值为 0 的下标 1 处。
 * <p>
 * 1 <= arr.length <= 5 * 10^4
 * 0 <= arr[i] < arr.length
 * 0 <= start < arr.length
 */
public class Problem1306 {
    public static void main(String[] args) {
        Problem1306 problem1306 = new Problem1306();
        int[] arr = {4, 2, 3, 0, 3, 1, 2};
        int start = 5;
        System.out.println(problem1306.canReach(arr, start));
        System.out.println(problem1306.canReach2(arr, start));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach(int[] arr, int start) {
        if (arr == null || arr.length == 0) {
            return false;
        }

        return dfs(start, arr, new boolean[arr.length]);
    }

    /**
     * bfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach2(int[] arr, int start) {
        if (arr == null || arr.length == 0) {
            return false;
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[arr.length];
        queue.offer(start);

        while (!queue.isEmpty()) {
            int index = queue.poll();

            if (index < 0 || index >= arr.length || visited[index]) {
                continue;
            }

            if (arr[index] == 0) {
                return true;
            }

            visited[index] = true;

            queue.offer(index - arr[index]);
            queue.offer(index + arr[index]);
        }

        //遍历结束，都没有跳跃到值为0的位置，返回false
        return false;
    }

    private boolean dfs(int t, int[] arr, boolean[] visited) {
        if (t < 0 || t >= arr.length || visited[t]) {
            return false;
        }

        //当前位置为0，即可以跳跃到值为0的位置
        if (arr[t] == 0) {
            return true;
        }

        visited[t] = true;

        //从当前位置，往前跳跃arr[t]步，或往后跳跃arr[t]步，看哪种情况能够跳跃到值为0的位置
        return dfs(t - arr[t], arr, visited) || dfs(t + arr[t], arr, visited);
    }
}
