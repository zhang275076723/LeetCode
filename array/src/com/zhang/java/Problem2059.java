package com.zhang.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @Date 2024/12/30 08:44
 * @Author zsy
 * @Description 转化数字的最小运算数 类比Problem815、Problem1654、LCP09 双向bfs类比Problem126、Problem127、Problem433、Problem752、Problem815、Problem1345
 * 给你一个下标从 0 开始的整数数组 nums ，该数组由 互不相同 的数字组成。
 * 另给你两个整数 start 和 goal 。
 * 整数 x 的值最开始设为 start ，你打算执行一些运算使 x 转化为 goal 。
 * 你可以对数字 x 重复执行下述运算：
 * 如果 0 <= x <= 1000 ，那么，对于数组中的任一下标 i（0 <= i < nums.length），可以将 x 设为下述任一值：
 * x + nums[i]
 * x - nums[i]
 * x ^ nums[i]（按位异或 XOR）
 * 注意，你可以按任意顺序使用每个 nums[i] 任意次。
 * 使 x 越过 0 <= x <= 1000 范围的运算同样可以生效，但该该运算执行后将不能执行其他运算。
 * 返回将 x = start 转化为 goal 的最小操作数；如果无法完成转化，则返回 -1 。
 * <p>
 * 输入：nums = [2,4,12], start = 2, goal = 12
 * 输出：2
 * 解释：
 * 可以按 2 → 14 → 12 的转化路径进行，只需执行下述 2 次运算：
 * - 2 + 12 = 14
 * - 14 - 2 = 12
 * <p>
 * 输入：nums = [3,5,7], start = 0, goal = -4
 * 输出：2
 * 解释：
 * 可以按 0 → 3 → -4 的转化路径进行，只需执行下述 2 次运算：
 * - 0 + 3 = 3
 * - 3 - 7 = -4
 * 注意，最后一步运算使 x 超过范围 0 <= x <= 1000 ，但该运算仍然可以生效。
 * <p>
 * 输入：nums = [2,8,16], start = 0, goal = 1
 * 输出：-1
 * 解释：
 * 无法将 0 转化为 1
 * <p>
 * 1 <= nums.length <= 1000
 * -10^9 <= nums[i], goal <= 10^9
 * 0 <= start <= 1000
 * start != goal
 * nums 中的所有整数互不相同
 */
public class Problem2059 {
    public static void main(String[] args) {
        Problem2059 problem2059 = new Problem2059();
        int[] nums = {2, 4, 12};
        int start = 2;
        int goal = 12;
        System.out.println(problem2059.minimumOperations(nums, start, goal));
        System.out.println(problem2059.minimumOperations2(nums, start, goal));
    }

    /**
     * bfs
     * 时间复杂度O(C*n)，空间复杂度O(C) (C=1001，即可以进行转换的范围)
     *
     * @param nums
     * @param start
     * @param goal
     * @return
     */
    public int minimumOperations(int[] nums, int start, int goal) {
        if (start == goal) {
            return 0;
        }

        Queue<Integer> queue = new LinkedList<>();
        //当前数字访问数组，因为数字只在[0,1000]范围内进行加、减、异或运算，所以访问数组长度为1001
        boolean[] visited = new boolean[1001];

        queue.offer(start);
        visited[start] = true;

        //start转换为goal的最小操作数
        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int num = queue.poll();

                for (int j = 0; j < nums.length; j++) {
                    int nextNum1 = num + nums[j];
                    int nextNum2 = num - nums[j];
                    int nextNum3 = num ^ nums[j];

                    if (nextNum1 == goal || nextNum2 == goal || nextNum3 == goal) {
                        return count + 1;
                    }

                    //nextNum1在[0,1000]范围内，并且nextNum1未被访问，才加入队列
                    if (nextNum1 >= 0 && nextNum1 <= 1000 && !visited[nextNum1]) {
                        queue.offer(nextNum1);
                        visited[nextNum1] = true;
                    }

                    //nextNum2在[0,1000]范围内，并且nextNum2未被访问，才加入队列
                    if (nextNum2 >= 0 && nextNum2 <= 1000 && !visited[nextNum2]) {
                        queue.offer(nextNum2);
                        visited[nextNum2] = true;
                    }

                    //nextNum3在[0,1000]范围内，并且nextNum3未被访问，才加入队列
                    if (nextNum3 >= 0 && nextNum3 <= 1000 && !visited[nextNum3]) {
                        queue.offer(nextNum3);
                        visited[nextNum3] = true;
                    }
                }
            }

            //bfs每次往外扩一层，最小操作数加1
            count++;
        }

        //bfs遍历结束，则start无法转换为goal，返回-1
        return -1;
    }

    /**
     * 双向bfs
     * 从start和goal同时开始bfs，bfs每次往外扩一层，将当前队列当前层中所有元素转换后的元素加入当前队列中，
     * 直至一个队列中包含了另一个队列中的数字，即双向bfs相交，或者全部遍历完都没有找到target，返回-1
     * 注意：双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
     * 时间复杂度O(C*n)，空间复杂度O(C) (C=1001，即可以进行转换的范围)
     *
     * @param nums
     * @param start
     * @param goal
     * @return
     */
    public int minimumOperations2(int[] nums, int start, int goal) {
        if (start == goal) {
            return 0;
        }

        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();
        Set<Integer> visitedSet1 = new HashSet<>();
        Set<Integer> visitedSet2 = new HashSet<>();

        queue1.offer(start);
        queue2.offer(goal);
        visitedSet1.add(start);
        visitedSet2.add(goal);

        //start转换为goal的最小操作数
        int count = 0;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
            if (queue1.size() > queue2.size()) {
                Queue<Integer> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                Set<Integer> tempSet = visitedSet1;
                visitedSet1 = visitedSet2;
                visitedSet2 = tempSet;
            }

            int size = queue1.size();

            for (int i = 0; i < size; i++) {
                int num = queue1.poll();

                for (int j = 0; j < nums.length; j++) {
                    int nextNum1 = num + nums[j];
                    int nextNum2 = num - nums[j];
                    int nextNum3 = num ^ nums[j];

                    //visitedSet2中存在num转换得到的数字，即双向bfs相交，则找到了start转换为goal的最小操作数，返回count+1
                    if (visitedSet2.contains(nextNum1) || visitedSet2.contains(nextNum2) || visitedSet2.contains(nextNum3)) {
                        return count + 1;
                    }

                    //nextNum1在[0,1000]范围内，并且nextNum1未被访问，才加入队列
                    if (nextNum1 >= 0 && nextNum1 <= 1000 && !visitedSet1.contains(nextNum1)) {
                        queue1.offer(nextNum1);
                        visitedSet1.add(nextNum1);
                    }

                    //nextNum2在[0,1000]范围内，并且nextNum2未被访问，才加入队列
                    if (nextNum2 >= 0 && nextNum2 <= 1000 && !visitedSet1.contains(nextNum2)) {
                        queue1.offer(nextNum2);
                        visitedSet1.add(nextNum2);
                    }

                    //nextNum3在[0,1000]范围内，并且nextNum3未被访问，才加入队列
                    if (nextNum3 >= 0 && nextNum3 <= 1000 && !visitedSet1.contains(nextNum3)) {
                        queue1.offer(nextNum3);
                        visitedSet1.add(nextNum3);
                    }
                }
            }

            //bfs每次往外扩一层，最小操作数加1
            count++;
        }

        //bfs遍历结束，则start无法转换为goal，返回-1
        return -1;
    }
}
