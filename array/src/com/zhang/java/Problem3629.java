package com.zhang.java;

import java.util.*;

/**
 * @Date 2025/12/12 11:43
 * @Author zsy
 * @Description 通过质数传送到达终点的最少跳跃次数 质数类比 因子类比 跳跃问题类比
 * 给你一个长度为 n 的整数数组 nums。
 * 你从下标 0 开始，目标是到达下标 n - 1。
 * 在任何下标 i 处，你可以执行以下操作之一：
 * 移动到相邻格子：跳到下标 i + 1 或 i - 1，如果该下标在边界内。
 * 质数传送：如果 nums[i] 是一个质数 p，你可以立即跳到任何满足 nums[j] % p == 0 的下标 j 处，且下标 j != i 。
 * 返回到达下标 n - 1 所需的 最少 跳跃次数。
 * 质数 是一个大于 1 的自然数，只有两个因子，1 和它本身。
 * <p>
 * 输入: nums = [1,2,4,6]
 * 输出: 2
 * 解释:
 * 一个最优的跳跃序列是：
 * 从下标 i = 0 开始。向相邻下标 1 跳一步。
 * 在下标 i = 1，nums[1] = 2 是一个质数。因此，我们传送到索引 i = 3，因为 nums[3] = 6 可以被 2 整除。
 * 因此，答案是 2。
 * <p>
 * 输入: nums = [2,3,4,7,9]
 * 输出: 2
 * 解释:
 * 一个最优的跳跃序列是：
 * 从下标 i = 0 开始。向相邻下标 i = 1 跳一步。
 * 在下标 i = 1，nums[1] = 3 是一个质数。因此，我们传送到下标 i = 4，因为 nums[4] = 9 可以被 3 整除。
 * 因此，答案是 2。
 * <p>
 * 输入: nums = [4,6,5,8]
 * 输出: 3
 * 解释:
 * 由于无法进行传送，我们通过 0 → 1 → 2 → 3 移动。因此，答案是 3。
 * <p>
 * 1 <= n == nums.length <= 10^5
 * 1 <= nums[i] <= 10^6
 */
public class Problem3629 {
    public static void main(String[] args) {
        Problem3629 problem3629 = new Problem3629();
        int[] nums = {2, 3, 4, 7, 9};
        System.out.println(problem3629.minJumps(nums));
    }

    /**
     * bfs+埃氏筛求质因子
     * 注意：当前方法超时，但正确，通过静态代码块初始化primeFactorList，则不会超时
     * 时间复杂度O(nlogC)，空间复杂度O(nlogC) (C=max(nums[i])=10^6)
     *
     * @param nums
     * @return
     */
    public int minJumps(int[] nums) {
        int n = nums.length;
        //nums中的最大值
        int maxNum = nums[0];

        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }

        //每个数字的质因子集合
        //例如：primeFactorList.get(i)=[2,3]，即数字i包含的质因子为2、3
        List<List<Integer>> primeFactorList = new ArrayList<>();

        for (int i = 0; i <= maxNum; i++) {
            primeFactorList.add(new ArrayList<>());
        }

        for (int i = 2; i <= maxNum; i++) {
            //primeFactorList.get(i)为空，则说明数字i为质数
            if (primeFactorList.get(i).isEmpty()) {
                //注意：和埃氏筛不同，这里j从1开始遍历，求出i的所有倍数i*j都有质因子i
                for (int j = 1; i * j <= maxNum; j++) {
                    primeFactorList.get(i * j).add(i);
                }
            }
        }

        //key：质数nums[i]，value：满足nums[j]%nums[i]==0的下标索引j的集合
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            //nums[i]的质因子prime
            for (int prime : primeFactorList.get(nums[i])) {
                if (!map.containsKey(prime)) {
                    map.put(prime, new ArrayList<>());
                }

                //可以从质因子prime对应nums中的下标索引跳到下标索引i
                map.get(prime).add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        //nums中元素下标索引访问数组
        boolean[] visited = new boolean[n];
        //nums中质数的访问集合
        //如果nums中存在多个相同的质数num，前一个num访问过之后，后面相同质数num就不需要访问，因为相同的质数会跳到相同的元素，直接跳过
        Set<Integer> primeVisitedSet = new HashSet<>();
        queue.offer(0);
        visited[0] = true;

        //从下标索引0跳跃到下标索引n-1的最少跳跃次数
        int step = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int index = queue.poll();

                if (index == n - 1) {
                    return step;
                }

                //往index+1跳
                if (index > 0 && !visited[index - 1]) {
                    queue.offer(index - 1);
                    visited[index - 1] = true;
                }

                //往index-1跳
                if (index < n - 1 && !visited[index + 1]) {
                    queue.offer(index + 1);
                    visited[index + 1] = true;
                }

                //往满足nums[j]%nums[index]==0的下标索引j跳，其中nums[index]为质数
                if (!primeVisitedSet.contains(nums[index]) && map.containsKey(nums[index])) {
                    for (int j : map.get(nums[index])) {
                        if (!visited[j]) {
                            queue.offer(j);
                            visited[j] = true;
                        }
                    }

                    primeVisitedSet.add(nums[index]);
                }
            }

            step++;
        }

        //bfs结束，无法跳跃到下标索引n-1，则返回-1
        return -1;
    }
}
