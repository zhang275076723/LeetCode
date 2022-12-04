package com.zhang.java;

import java.util.*;

/**
 * @Date 2022/9/9 8:43
 * @Author zsy
 * @Description 分割数组为连续子序列 类比Problem460、Offer61
 * 给你一个按升序排序的整数数组 num（可能包含重复数字），
 * 请你将它们分割成一个或多个长度至少为 3 的子序列，其中每个子序列都由连续整数组成。
 * 如果可以完成上述分割，则返回 true ；否则，返回 false 。
 * <p>
 * 输入: [1,2,3,3,4,5]
 * 输出: True
 * 解释:
 * 你可以分割出这样两个连续子序列 :
 * 1, 2, 3
 * 3, 4, 5
 * <p>
 * 输入: [1,2,3,3,4,4,5,5]
 * 输出: True
 * 解释:
 * 你可以分割出这样两个连续子序列 :
 * 1, 2, 3, 4, 5
 * 3, 4, 5
 * <p>
 * 输入: [1,2,3,4,4,5]
 * 输出: False
 * <p>
 * 1 <= nums.length <= 10000
 */
public class Problem659 {
    public static void main(String[] args) {
        Problem659 problem659 = new Problem659();
        int[] nums = {1, 2, 3, 3, 4, 4, 5, 5};
        System.out.println(problem659.isPossible(nums));
        System.out.println(problem659.isPossible2(nums));
    }

    /**
     * 哈希表+小根堆
     * 哈希表key：连续子序列的结尾元素，value：存放当前连续子序列长度的小根堆
     * 如果当前元素为x，如果存在以x-1结尾的连续子序列，则从以x-1结尾的小根堆中取出长度最小的子序列，长度+1，放入以x结尾，长度+1的连续子序列小根堆中
     * 遍历数组完之后，判断哈希表中是否存在长度小于3的子序列，如果存在，返回false；不存在，返回true
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public boolean isPossible(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        //key：连续子序列的结尾元素，value：以key结尾的连续子序列的小根堆
        Map<Integer, Queue<Integer>> map = new HashMap<>();

        for (int num : nums) {
            //map中存在以num-1结尾的连续子序列小根堆，取出小根堆中长度最小的连续子序列，放入长度+1的小根堆中
            if (map.containsKey(num - 1)) {
                Queue<Integer> queue = map.get(num - 1);
                //取出小根堆中长度最小的连续子序列
                int length = queue.poll();

                //以num-1结尾的小根堆为空，从哈希表中移除
                if (queue.isEmpty()) {
                    map.remove(num - 1);
                }

                //map中存在以num结尾的连续子序列小根堆
                if (map.containsKey(num)) {
                    Queue<Integer> nextQueue = map.get(num);
                    nextQueue.offer(length + 1);
                } else {
                    //map中不存在以num结尾的连续子序列小根堆，创建以num结尾的小根堆
                    Queue<Integer> nextQueue = new PriorityQueue<>(new Comparator<Integer>() {
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            return o1 - o2;
                        }
                    });

                    nextQueue.offer(length + 1);
                    map.put(num, nextQueue);
                }
            } else {
                //map中不存在以num-1结尾的连续子序列小根堆

                //map中存在以num结尾的连续子序列小根堆
                if (map.containsKey(num)) {
                    Queue<Integer> nextQueue = map.get(num);
                    nextQueue.offer(1);
                } else {
                    //map中不存在以num结尾的连续子序列小根堆，创建以num结尾的小根堆
                    Queue<Integer> nextQueue = new PriorityQueue<>(new Comparator<Integer>() {
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            return o1 - o2;
                        }
                    });

                    nextQueue.offer(1);
                    map.put(num, nextQueue);
                }
            }
        }

        //判断哈希表中小根堆是否存在长度小于3的连续子序列
        for (Map.Entry<Integer, Queue<Integer>> entry : map.entrySet()) {
            if (entry.getValue().peek() < 3) {
                return false;
            }
        }

        //连续子序列长度都大于等于3，返回true
        return true;
    }

    /**
     * 双哈希表
     * 一个哈希表存放元素和其剩余个数，另一个哈希表存放以num结尾的长度不小于3的连续子序列和其个数
     * 一个元素num，先判断是否存在以num-1结尾的长度不小于3的连续子序列，如果有，则两者可以拼接成以num结尾的长度不小于3的连续子序列；
     * 如果不满足，判断是否存在元素num+1和num+2，如果有，则num、num+1、num+2可以拼接成以num+2结尾的长度不小于3的连续子序列；
     * 如果不满足，直接返回false
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public boolean isPossible2(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }

        //存放元素和其剩余个数的map
        Map<Integer, Integer> countMap = new HashMap<>();
        //存放以num结尾的长度不小于3的连续子序列和其个数的map
        Map<Integer, Integer> endMap = new HashMap<>();

        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        for (int num : nums) {
            int count = countMap.getOrDefault(num, 0);

            //当前元素num还有剩余，则可以进行拼接判断
            if (count > 0) {
                //存在以num结尾的长度不小于3的连续子序列
                if (endMap.containsKey(num - 1) && endMap.get(num - 1) > 0) {
                    countMap.put(num, count - 1);
                    endMap.put(num - 1, endMap.get(num - 1) - 1);
                    endMap.put(num, endMap.getOrDefault(num, 0) + 1);
                } else if (countMap.containsKey(num + 1) && countMap.containsKey(num + 2) &&
                        countMap.get(num + 1) > 0 && countMap.get(num + 2) > 0) {
                    //存在num、num+1、num+2，可以拼接成以num+2结尾的长度不小于3的连续子序列
                    countMap.put(num, count - 1);
                    countMap.put(num + 1, countMap.get(num + 1) - 1);
                    countMap.put(num + 2, countMap.get(num + 2) - 1);
                    endMap.put(num + 2, endMap.getOrDefault(num + 2, 0) + 1);
                } else {
                    //当前元素num无法拼接，返回false
                    return false;
                }
            }
        }

        return true;
    }
}
