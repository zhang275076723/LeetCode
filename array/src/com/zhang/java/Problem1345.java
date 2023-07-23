package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/5 08:34
 * @Author zsy
 * @Description 跳跃游戏 IV 跳跃问题类比Problem45、Problem55、Problem403、Problem1306、Problem1340、Problem1696、Problem1871 双向bfs类比Problem126、Problem127
 * 给你一个整数数组 arr ，你一开始在数组的第一个元素处（下标为 0）。
 * 每一步，你可以从下标 i 跳到下标 i + 1 、i - 1 或者 j ：
 * i + 1 需满足：i + 1 < arr.length
 * i - 1 需满足：i - 1 >= 0
 * j 需满足：arr[i] == arr[j] 且 i != j
 * 请你返回到达数组最后一个元素的下标处所需的 最少操作次数 。
 * 注意：任何时候你都不能跳到数组外面。
 * <p>
 * 输入：arr = [100,-23,-23,404,100,23,23,23,3,404]
 * 输出：3
 * 解释：那你需要跳跃 3 次，下标依次为 0 --> 4 --> 3 --> 9 。下标 9 为数组的最后一个元素的下标。
 * <p>
 * 输入：arr = [7]
 * 输出：0
 * 解释：一开始就在最后一个元素处，所以你不需要跳跃。
 * <p>
 * 输入：arr = [7,6,9,6,9,6,9,7]
 * 输出：1
 * 解释：你可以直接从下标 0 处跳到下标 7 处，也就是数组的最后一个元素处。
 * <p>
 * 1 <= arr.length <= 5 * 10^4
 * -10^8 <= arr[i] <= 10^8
 */
public class Problem1345 {
    public static void main(String[] args) {
        Problem1345 problem1345 = new Problem1345();
        int[] arr = {100, -23, -23, 404, 100, 23, 23, 23, 3, 404};
        System.out.println(problem1345.minJumps(arr));
        System.out.println(problem1345.minJumps2(arr));
    }

    /**
     * bfs (bfs确保最先得到跳跃到末尾元素的最少跳跃次数)
     * 从下标索引0开始dfs，bfs每次往外扩一层，表示跳一次，将和当前元素相邻元素和值相同的元素加入队列，直至到达末尾元素
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int minJumps(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        //map存放数组中相同元素的下标索引
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], new ArrayList<>());
            }

            List<Integer> list = map.get(arr[i]);
            list.add(i);
        }

        //bfs向外扩展的次数，得到跳跃到末尾元素的最少跳跃次数
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[arr.length];
        queue.offer(0);

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次往外扩一层，表示跳一次
            for (int i = 0; i < size; i++) {
                int index = queue.poll();

                //当前下标索引超出数组范围，或已被visited访问，直接进行下次循环
                if (index < 0 || index >= arr.length || visited[index]) {
                    continue;
                }

                //可以跳跃到最后一个位置，直接返回
                if (index == arr.length - 1) {
                    return count;
                }

                //当前索引下标已被访问
                visited[index] = true;

                //当前元素相邻元素加入队列
                queue.offer(index - 1);
                queue.offer(index + 1);

                //当前元素值相同的元素索引下标加入队列
                if (map.containsKey(arr[index])) {
                    List<Integer> list = map.get(arr[index]);

                    for (int j = 0; j < list.size(); j++) {
                        //和index同值的下标索引
                        int sameValueIndex = list.get(j);
                        //将所有未访问和index同值的下标索引加入queue
                        if (!visited[sameValueIndex]) {
                            queue.offer(sameValueIndex);
                        }
                    }

                    //当前元素值相同的元素索引下标加入队列之后，不需要再遍历当前元素值相同的元素，从map中移除，相当于剪枝
                    map.remove(arr[index]);
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //无法跳跃到最后一个位置，返回-1
        return -1;
    }

    /**
     * 双向bfs (bfs确保最先得到跳跃到末尾元素的最少跳跃次数)
     * 从下标索引0和数组末尾下标索引同时开始bfs，bfs每次往外扩一层，表示跳一次，当前元素相邻元素和值相同元素的索引下标加入队列，
     * 直至一个队列遍历到了另一个队列已经遍历过的下标索引，则两个队列相交，找到了跳跃到末尾元素的最少跳跃次数
     * 优化：优先遍历两个队列中元素较少的队列，能够加快查询速度
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param arr
     * @return
     */
    public int minJumps2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        //map存放数组中相同元素的下标索引
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], new ArrayList<>());
            }

            List<Integer> list = map.get(arr[i]);
            list.add(i);
        }

        //双向bfs向外扩展的次数，得到跳跃到末尾元素的最少跳跃次数
        int count = 0;
        //从前往后遍历的队列，即从0开始遍历
        Queue<Integer> queue1 = new LinkedList<>();
        //从后往前遍历的队列，即从arr.length-1开始遍历
        Queue<Integer> queue2 = new LinkedList<>();
        //从前往后遍历的访问数组
        boolean[] visited1 = new boolean[arr.length];
        //从后往前遍历的访问数组
        boolean[] visited2 = new boolean[arr.length];
        queue1.offer(0);
        queue2.offer(arr.length - 1);
        //注意：双向bfs，必须先将首尾节点在对应的访问数组中设置为已访问，不能每次出队元素的时候再标记节点已访问
        visited1[0] = true;
        visited2[arr.length - 1] = true;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //优化：优先遍历两个队列中存储单词较少的队列，这样遍历的单词少，能够加快查询速度
            //从前往后遍历的queue1中元素索引下标数量大于从后往前遍历的queue2中元素索引下标数量，两个队列和两个访问数组交换，
            //queue1是两个队列中存储元素索引下标较少的队列，每次只遍历queue1
            if (queue1.size() > queue2.size()) {
                Queue<Integer> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                boolean[] tempVisited = visited1;
                visited1 = visited2;
                visited2 = tempVisited;
            }

            int size = queue1.size();

            //每次往外扩一层，表示跳一次
            for (int i = 0; i < size; i++) {
                int index = queue1.poll();

                //index在visited2中已被访问，双向dfs已经相交，则已经得到跳跃到末尾元素的最少跳跃次数
                if (visited2[index]) {
                    return count;
                }

                //未被访问的index-1加入queue1，并在visited1设置为已访问
                if (index - 1 >= 0 && !visited1[index - 1]) {
                    queue1.offer(index - 1);
                    visited1[index - 1] = true;
                }

                //未被访问的index+1加入queue1，并在visited1设置为已访问
                if (index + 1 < arr.length && !visited1[index + 1]) {
                    queue1.offer(index + 1);
                    visited1[index + 1] = true;
                }

                //当前元素值相同的元素索引下标加入队列
                if (map.containsKey(arr[index])) {
                    List<Integer> list = map.get(arr[index]);

                    for (int j = 0; j < list.size(); j++) {
                        //和index同值的下标索引
                        int sameValueIndex = list.get(j);
                        //将所有未访问和index同值的下标索引加入queue，并在visited1设置为已访问
                        if (!visited1[sameValueIndex]) {
                            queue1.offer(sameValueIndex);
                            visited1[sameValueIndex] = true;
                        }
                    }

                    //当前元素值相同的元素索引下标加入队列之后，不需要再遍历当前元素值相同的元素，从map中移除，相当于剪枝
                    map.remove(arr[index]);
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //无法跳跃到最后一个位置，返回-1
        return -1;
    }
}
