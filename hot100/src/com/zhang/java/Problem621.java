package com.zhang.java;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @Date 2022/6/16 9:12
 * @Author zsy
 * @Description 任务调度器 字节面试题 类比Problem358、Problem767 优先队列类比
 * 给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。
 * 任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。
 * 在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
 * 然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，
 * 因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
 * 你需要计算完成所有任务所需要的 最短时间 。
 * <p>
 * 输入：tasks = ["A","A","A","B","B","B"], n = 2
 * 输出：8
 * 解释：A -> B -> (待命) -> A -> B -> (待命) -> A -> B
 * 在本示例中，两个相同类型任务之间必须间隔长度为 n = 2 的冷却时间，
 * 而执行一个任务只需要一个单位时间，所以中间出现了（待命）状态。
 * <p>
 * 输入：tasks = ["A","A","A","B","B","B"], n = 0
 * 输出：6
 * 解释：在这种情况下，任何大小为 6 的排列都可以满足要求，因为 n = 0
 * ["A","A","A","B","B","B"]
 * ["A","B","A","B","A","B"]
 * ["B","B","B","A","A","A"]
 * ...
 * 诸如此类
 * <p>
 * 输入：tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
 * 输出：16
 * 解释：一种可能的解决方案是：
 * A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> (待命) -> (待命) -> A -> (待命) -> (待命) -> A
 * <p>
 * 1 <= task.length <= 10^4
 * tasks[i] 是大写英文字母
 * n 的取值范围为 [0, 100]
 */
public class Problem621 {
    public static void main(String[] args) {
        Problem621 problem621 = new Problem621();
//        char[] tasks = {'A', 'A', 'A', 'B', 'B', 'B'};
//        int n = 2;
        char[] tasks = {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int n = 2;
        System.out.println(problem621.leastInterval(tasks, n));
        System.out.println(problem621.leastInterval2(tasks, n));
    }

    /**
     * 优先队列，小根堆
     * 统计tasks中任务需要执行的次数，将需要执行的任务加入小根堆，每次从小根堆出堆下次执行的时间最小的任务，
     * 如果当前任务不能执行，则任务都在冷却，需要等待，time++；如果当前任务可以执行，则更新当前任务下次执行时间和剩余执行次数，time++
     * 时间复杂度O(tasks.length+|Σ|+tasks.length*log|Σ|)，空间复杂度O(|Σ|) (Σ:tasks中任务种类，tasks中只包含大写字母，|Σ|=26)
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }

        //任务需要执行的次数数组
        int[] count = new int[26];
        //当前任务执行后，当前任务下次执行的时间数组
        int[] nextTime = new int[26];

        for (char task : tasks) {
            count[task - 'A']++;
        }

        //小根堆，先按照任务下次执行的时间由小到大排序，再按照任务剩余执行次数由大到小排序，最后按照字典序由小到大排序
        PriorityQueue<Character> priorityQueue = new PriorityQueue<>(new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                if (nextTime[c1 - 'A'] != nextTime[c2 - 'A']) {
                    return nextTime[c1 - 'A'] - nextTime[c2 - 'A'];
                } else {
                    if (count[c1 - 'A'] != count[c2 - 'A']) {
                        return count[c2 - 'A'] - count[c1 - 'A'];
                    } else {
                        return c1 - c2;
                    }
                }
            }
        });

        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                priorityQueue.offer((char) ('A' + i));
            }
        }

        //执行所有任务的最短时间
        int time = 0;

        while (!priorityQueue.isEmpty()) {
            //当前任务
            char nextTask = priorityQueue.peek();

            //当前任务下次执行的时间大于time，则任务都在冷却，需要等待，time++
            if (nextTime[nextTask - 'A'] > time) {
                time++;
            } else {
                //当前任务下次执行的时间小于等于time，则当前任务可以执行
                priorityQueue.poll();

                nextTime[nextTask - 'A'] = nextTime[nextTask - 'A'] + n + 1;
                count[nextTask - 'A']--;

                if (count[nextTask - 'A'] > 0) {
                    priorityQueue.offer(nextTask);
                }

                time++;
            }
        }

        return time;
    }

    /**
     * 模拟
     * 以执行次数最多的任务为基准，执行所有任务的最短时间为(maxExecCount-1)*(n+1)+maxExecSameCount
     * (maxExecCount:执行次数最多的任务的执行次数，maxExecSameCount:执行次数最多的相同任务的个数)
     * 时间复杂度O(tasks.length+|Σ|)，空间复杂度O(|Σ|) (Σ:tasks中任务种类，tasks中只包含大写字母，|Σ|=26)
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval2(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }

        //任务需要执行的次数数组
        int[] count = new int[26];
        //执行次数最多的任务的执行次数
        int maxExecCount = 0;

        for (char task : tasks) {
            count[task - 'A']++;
            maxExecCount = Math.max(maxExecCount, count[task - 'A']);
        }

        //执行次数最多的相同任务的个数
        int maxExecSameCount = 0;

        for (int i = 0; i < 26; i++) {
            if (count[i] == maxExecCount) {
                maxExecSameCount++;
            }
        }

        //如果执行次数最多的任务A、B执行次数为3，n = 2，则最少时间安排为ABxABxAB
        int time = (maxExecCount - 1) * (n + 1) + maxExecSameCount;

        //最少执行时间不能小于tasks的长度
        //例如['A','A','A','B','B','B','C','C','C','D','D','E']，n = 2，所需执行时间不是(3-1)*(2+1)+3=9，而是数组长度12
        return Math.max(time, tasks.length);
    }
}
