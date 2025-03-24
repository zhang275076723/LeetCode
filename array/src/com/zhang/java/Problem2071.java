package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Date 2025/1/3 08:48
 * @Author zsy
 * @Description 你可以安排的最多任务数目 华为机试题 有序集合类比Problem220、Problem352、Problem363、Problem855、Problem981、Problem1146、Problem1348、Problem1912、Problem2034、Problem2349、Problem2353、Problem2502、Problem2590 二分查找类比
 * 给你 n 个任务和 m 个工人。
 * 每个任务需要一定的力量值才能完成，需要的力量值保存在下标从 0 开始的整数数组 tasks 中，第 i 个任务需要 tasks[i] 的力量才能完成。
 * 每个工人的力量值保存在下标从 0 开始的整数数组 workers 中，第 j 个工人的力量值为 workers[j] 。
 * 每个工人只能完成 一个 任务，且力量值需要 大于等于 该任务的力量要求值（即 workers[j] >= tasks[i]）。
 * 除此以外，你还有 pills 个神奇药丸，可以给 一个工人的力量值 增加 strength 。
 * 你可以决定给哪些工人使用药丸，但每个工人 最多 只能使用 一片 药丸。
 * 给你下标从 0 开始的整数数组tasks 和 workers 以及两个整数 pills 和 strength ，请你返回 最多 有多少个任务可以被完成。
 * <p>
 * 输入：tasks = [3,2,1], workers = [0,3,3], pills = 1, strength = 1
 * 输出：3
 * 解释：
 * 我们可以按照如下方案安排药丸：
 * - 给 0 号工人药丸。
 * - 0 号工人完成任务 2（0 + 1 >= 1）
 * - 1 号工人完成任务 1（3 >= 2）
 * - 2 号工人完成任务 0（3 >= 3）
 * <p>
 * 输入：tasks = [5,4], workers = [0,0,0], pills = 1, strength = 5
 * 输出：1
 * 解释：
 * 我们可以按照如下方案安排药丸：
 * - 给 0 号工人药丸。
 * - 0 号工人完成任务 0（0 + 5 >= 5）
 * <p>
 * 输入：tasks = [10,15,30], workers = [0,10,10,10,10], pills = 3, strength = 10
 * 输出：2
 * 解释：
 * 我们可以按照如下方案安排药丸：
 * - 给 0 号和 1 号工人药丸。
 * - 0 号工人完成任务 0（0 + 10 >= 10）
 * - 1 号工人完成任务 1（10 + 10 >= 15）
 * <p>
 * 输入：tasks = [5,9,8,5,9], workers = [1,6,4,2,6], pills = 1, strength = 5
 * 输出：3
 * 解释：
 * 我们可以按照如下方案安排药丸：
 * - 给 2 号工人药丸。
 * - 1 号工人完成任务 0（6 >= 5）
 * - 2 号工人完成任务 2（4 + 5 >= 8）
 * - 4 号工人完成任务 3（6 >= 5）
 * <p>
 * n == tasks.length
 * m == workers.length
 * 1 <= n, m <= 5 * 10^4
 * 0 <= pills <= m
 * 0 <= tasks[i], workers[j], strength <= 10^9
 */
public class Problem2071 {
    public static void main(String[] args) {
        Problem2071 problem2071 = new Problem2071();
//        int[] tasks = {3, 2, 1};
//        int[] workers = {0, 3, 3};
//        int pills = 1;
//        int strength = 1;
        int[] tasks = {5, 9, 8, 5, 9};
        int[] workers = {1, 6, 4, 2, 6};
        int pills = 1;
        int strength = 5;
        System.out.println(problem2071.maxTaskAssign(tasks, workers, pills, strength));
    }

    /**
     * 排序+二分查找+有序集合
     * 核心思想：判断最大的k个工人能否完成最小的k个任务
     * tasks和workers由小到大排序，对[left,right]进行二分查找，left为0，right为min(tasks.length,workers.length)，判断最多使用pills个药丸能否完成mid个任务，
     * 如果最多使用pills个药丸能完成mid个任务，则最多完成的任务个数在mid或mid右边，left=mid;
     * 如果最多使用pills个药丸不能完成mid个任务，则最多完成的任务个数在mid左边，right=mid-1
     * 时间复杂度O(mlogm+nlogn+min(m,n)*(log(min(m,n)))^2)，空间复杂度O(logm+logn+min(m,n)) (m=tasks.length，n=workers.length)
     * (tasks排序需要O(mlogm)时间，workers排序需要O(nlogn)时间，二分查找次数为log(min(m,n))，每次判断需要O(klogk)时间，二分查找需要O(min(m,n)*(log(min(m,n)))^2))
     * (tasks排序需要O(logm)空间，workers排序需要O(logn)空间，有序集合需要O(min(m,n))空间)
     *
     * @param tasks
     * @param workers
     * @param pills
     * @param strength
     * @return
     */
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        int left = 0;
        //最多只能完成tasks和workers数组长度中的较小值个任务
        int right = Math.min(tasks.length, workers.length);
        int mid;

        //tasks由小到大排序
        Arrays.sort(tasks);
        //workers由小到大排序
        Arrays.sort(workers);

        while (left < right) {
            //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
            mid = left + ((right - left) >> 1) + 1;

            if (canFinish(tasks, workers, mid, pills, strength)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    /**
     * 判断最多使用pills个药丸能否完成k个任务
     * 核心思想：判断最大的k个工人能否完成最小的k个任务
     * 时间复杂度O(klogk)，空间复杂度O(k)
     *
     * @param tasks    任务已经由小到大排序
     * @param workers  工人已经由小到大排序
     * @param k        需要完成的任务个数
     * @param pills    可以使用的药丸个数
     * @param strength 药丸增加的力量值
     * @return
     */
    private boolean canFinish(int[] tasks, int[] workers, int k, int pills, int strength) {
        //有序集合，按照工人的力量值由小到大存储最大的k个工人
        //key：工人的力量值，value：当前力量值的工人个数
        TreeMap<Integer, Integer> treeMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        for (int i = workers.length - k; i < workers.length; i++) {
            treeMap.put(workers[i], treeMap.getOrDefault(workers[i], 0) + 1);
        }

        //判断最小的k个任务能否由最大的k个工人完成
        //注意：任务tasks[i]只能由大到小遍历
        for (int i = k - 1; i >= 0; i--) {
            //最大工人不吃药能够完成当前任务，则最大工人完成当前任务
            if (treeMap.lastEntry().getKey() >= tasks[i]) {
                //treeMap中最大工人
                Map.Entry<Integer, Integer> entry = treeMap.lastEntry();
                treeMap.put(entry.getKey(), entry.getValue() - 1);

                //注意：不能写成entry.getValue() == 0，因为treeMap中发生了修改，但entry中没有修改
                if (treeMap.get(entry.getKey()) == 0) {
                    treeMap.remove(entry.getKey());
                }
            } else {
                //最大的工人不吃药不能完成当前任务，则treeMap中吃药后大于等于当前任务的最小工人完成当前任务

                //treeMap中吃药后大于等于当前任务的最小工人
                Map.Entry<Integer, Integer> entry = treeMap.ceilingEntry(tasks[i] - strength);

                //吃药也不能完成当前任务，返回false
                if (entry == null || pills == 0) {
                    return false;
                }

                pills--;
                treeMap.put(entry.getKey(), entry.getValue() - 1);

                //注意：不能写成entry.getValue() == 0，因为treeMap中发生了修改，但entry中没有修改
                if (treeMap.get(entry.getKey()) == 0) {
                    treeMap.remove(entry.getKey());
                }
            }
        }

        //遍历结束，则最多使用pills个药丸能完成k个任务，返回true
        return true;
    }
}
