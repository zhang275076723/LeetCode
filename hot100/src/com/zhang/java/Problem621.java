package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/6/16 9:12
 * @Author zsy
 * @Description 任务调度器 类比Problem659
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
        char[] tasks = {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int n = 2;
        System.out.println(problem621.leastInterval(tasks, n));
        System.out.println(problem621.leastInterval2(tasks, n));
    }

    /**
     * 模拟
     * 每次调度不在冷却中，且剩余执行次数最多的任务，如果任务都在冷却中，则将处于冷却的所有任务冷却时间都减1
     * 时间复杂度O(n * |Σ|)，空间复杂度O(|Σ|) (n:tasks.length，Σ:任务数组中任务种类，|Σ|=26)
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }

        //任务和需要执行次数map
        Map<Character, Integer> countMap = new HashMap<>();
        //任务和冷却时间map
        Map<Character, Integer> timeMap = new HashMap<>();

        for (char task : tasks) {
            countMap.put(task, countMap.getOrDefault(task, 0) + 1);
            timeMap.put(task, 0);
        }

        //已调度任务的数量
        int count = 0;
        //调度所有任务所花费的时间
        int time = 0;

        while (count < tasks.length) {
            //任务都是大写字母，初始化当前任务为一个小写字母，表示不存在
            char curTask = 'a';

            //判断是否存在不在冷却中，且剩余执行次数大于0的任务
            for (Map.Entry<Character, Integer> entry : timeMap.entrySet()) {
                //Integer之间的比较不能用==，而应该用equals()；Integer和int之间的比较可以使用==
                if (entry.getValue() == 0 && countMap.get(entry.getKey()) > 0) {
                    curTask = entry.getKey();
                    break;
                }
            }

            //所有任务都在冷却中，将剩余执行次数大于0的任务冷却时间减1
            if (curTask == 'a') {
                time++;
                for (Map.Entry<Character, Integer> entry : timeMap.entrySet()) {
                    if (countMap.get(entry.getKey()) > 0) {
                        timeMap.put(entry.getKey(), entry.getValue() - 1);
                    }
                }
            } else {
                //找不在冷却中，且剩余执行次数最多的任务
                for (Map.Entry<Character, Integer> entry : timeMap.entrySet()) {
                    //Integer之间的比较不能用==，而应该用equals()；Integer和int之间的比较可以使用==
                    if (countMap.get(entry.getKey()) > countMap.get(curTask) && entry.getValue().equals(0)) {
                        curTask = entry.getKey();
                    }
                }

                //执行当前任务，并设置当前任务的剩余执行次数和冷却时间
                countMap.put(curTask, countMap.get(curTask) - 1);
                timeMap.put(curTask, n);
                time++;
                count++;

                //剩余执行次数大于0且在冷却的非当前任务冷却时间减1
                for (Map.Entry<Character, Integer> entry : timeMap.entrySet()) {
                    if (entry.getKey() != curTask && countMap.get(entry.getKey()) > 0 && entry.getValue() > 0) {
                        timeMap.put(entry.getKey(), entry.getValue() - 1);
                    }
                }
            }
        }

        return time;
    }

    /**
     * 贪心
     * 以需要执行次数最多的任务为基准，执行所有任务的最短时间为(maxExecCount-1)*(n+1)+sameCount
     * (maxExecCount:执行次数最多的任务，sameCount:具有最多执行次数任务的数量)
     * 时间复杂度O(n + |Σ|)，空间复杂度O(|Σ|) (n:tasks.length，Σ:任务数组中任务种类，|Σ|=26)
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval2(char[] tasks, int n) {
        if (n == 0) {
            return tasks.length;
        }

        //任务和需要执行次数map
        Map<Character, Integer> countMap = new HashMap<>();
        //最多任务的执行次数
        int maxExecCount = 0;

        for (char task : tasks) {
            countMap.put(task, countMap.getOrDefault(task, 0) + 1);
            maxExecCount = Math.max(maxExecCount, countMap.get(task));
        }

        //执行次数是maxExecute的任务数量
        int maxExecSameCount = 0;

        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            //Integer之间的比较不能用==，而应该用equals()；Integer和int之间的比较可以使用==
            if (entry.getValue().equals(maxExecCount)) {
                maxExecSameCount++;
            }
        }

        //如果最多任务A、B执行次数为3，n = 2，则最少时间安排为ABxABxAB
        int time = (maxExecCount - 1) * (n + 1) + maxExecSameCount;

        //最少执行时间最少为数组的长度，取算出的执行时间time和数组长度的最大值为所需执行时间
        //例如['A','A','A','B','B','B','C','C','C','D','D','E']，n = 2，所需执行时间不是(3-1)*(2+1)+3=9，而是数组长度12
        return Math.max(time, tasks.length);
    }
}
