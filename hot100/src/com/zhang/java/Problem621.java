package com.zhang.java;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Date 2022/6/16 9:12
 * @Author zsy
 * @Description 任务调度器 字节面试题 类比Problem2365 类比Problem1834、Problem1882、Problem2402 类比Problem358、Problem767 优先队列类比
 * 给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。
 * 其中每个字母表示一种不同种类的任务。
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
     * 优先队列，大根堆+队列
     * 操作系统调度算法：轮询机制(round robin)
     * 统计tasks中任务需要执行的次数，将需要执行的任务加入大根堆，每轮的执行时间为n+1，每轮每个任务只能执行一次，
     * 每轮中需要的执行次数最多的circle个任务从大根堆出堆，加入队列中，队列中任务执行后如果需要的执行次数大于0，则重新加入大根堆中，
     * 如果大根堆为空，则当前轮需要的执行时间为加入队列中的任务个数；如果大根堆非空，则当前轮需要的执行时间为n+1
     * 时间复杂度O(tasks.length+|Σ|+n*log|Σ|)，空间复杂度O(|Σ|) (Σ:tasks中任务种类，tasks中只包含大写字母，|Σ|=26)
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }

        //任务需要执行的次数数组，只包含大写英文字母，即最多只存在26个任务
        int[] countArr = new int[26];

        for (char task : tasks) {
            countArr[task - 'A']++;
        }

        //大根堆，先按照任务需要的执行次数由大到小排序，再按照任务字典序由小到大排序
        PriorityQueue<Task> priorityQueue = new PriorityQueue<>(new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.execCount != task2.execCount) {
                    return task2.execCount - task1.execCount;
                } else {
                    return task1.name - task2.name;
                }
            }
        });

        for (int i = 0; i < 26; i++) {
            if (countArr[i] > 0) {
                priorityQueue.offer(new Task((char) ('A' + i), countArr[i]));
            }
        }

        //执行所有任务的最短时间
        int time = 0;
        //每轮的执行时间，每轮每个任务只能执行一次
        int circleTime = n + 1;

        while (!priorityQueue.isEmpty()) {
            //存储每轮中执行的任务的队列，每轮每个任务只能执行一次
            Queue<Task> queue = new LinkedList<>();
            //队列的大小，即当前轮中执行的任务个数
            int count = 0;

            //每轮中需要的执行次数最多的circleTime个任务从大根堆出堆，加入队列中
            for (int i = 0; i < circleTime; i++) {
                if (!priorityQueue.isEmpty()) {
                    Task task = priorityQueue.poll();
                    queue.offer(task);
                    count++;
                }
            }

            //队列中任务执行后如果需要的执行次数大于0，则重新加入大根堆中
            while (!queue.isEmpty()) {
                Task task = queue.poll();
                task.execCount--;

                if (task.execCount > 0) {
                    priorityQueue.offer(task);
                }
            }

            //大根堆为空，则当前轮需要的执行时间为加入队列中的任务个数
            if (priorityQueue.isEmpty()) {
                time = time + count;
            } else {
                //大根堆非空，则当前轮需要的执行时间为circleTime
                time = time + circleTime;
            }
        }

        return time;
    }

    /**
     * 贪心
     * 只考虑执行次数最多的任务A，每执行一次A，在下次执行A之前，需要执行其他任务n次，即每轮需要n+1时间，
     * 最后一次执行A不需要再执行其他任务，即执行所有任务的最短时间为(maxExecCount-1)*(n+1)+sameMaxExecCount
     * (maxExecCount:执行次数最多的任务的执行次数，sameMaxExecCount:执行次数最多的相同任务的个数)
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
        int[] countArr = new int[26];
        //执行次数最多的任务的执行次数
        int maxExecCount = 0;
        //执行次数最多的相同任务的个数
        int sameMaxExecCount = 0;

        for (char task : tasks) {
            countArr[task - 'A']++;
            maxExecCount = Math.max(maxExecCount, countArr[task - 'A']);
        }

        for (int i = 0; i < 26; i++) {
            if (countArr[i] == maxExecCount) {
                sameMaxExecCount++;
            }
        }

        //如果执行次数最多的任务A、B执行次数为3，n = 2，则最少时间安排为ABxABxAB
        int time = (maxExecCount - 1) * (n + 1) + sameMaxExecCount;

        //最少执行时间不能小于tasks的长度
        //例如['A','A','A','B','B','B','C','C','C','D','D','E']，n = 2，所需执行时间不是(3-1)*(2+1)+3=9，而是数组长度12
        return Math.max(time, tasks.length);
    }

    private static class Task {
        //任务名
        private char name;
        //当前任务需要的执行次数
        private int execCount;

        public Task(char name, int execCount) {
            this.name = name;
            this.execCount = execCount;
        }
    }
}
