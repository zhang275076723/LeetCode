package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/1/7 09:02
 * @Author zsy
 * @Description 最佳调度问题的回溯算法
 * 设有n个任务由k个可并行工作的机器来完成，完成任务i需要时间为 ti 。
 * 试设计一个算法找出完成这n个任务的最佳调度，使完成全部任务的时间最早。（要求给出调度方案）。
 * <p>
 * 输入：n = 3, k = 2, work = [2.0,3.1,4.2]
 * 输出：最佳调度时间 = 5.1，最佳调度方案 = 机器0执行work[0]、work[1]，机器1执行work[2]
 */
public class BestSchedule {
    /**
     * 最佳调度时间
     */
    double bestTime = Double.MAX_VALUE;

    /**
     * 最佳调度方案，bestMachineSchedule[0] = [0,1,3]，表示机器0执行work[0]、work[1]、work[3]
     */
    List<Integer>[] bestMachineSchedule;

    public static void main(String[] args) {
        BestSchedule bestSchedule = new BestSchedule();
        int n = 10;
        int k = 4;
        double[] work = {1.0, 4.3, 10.3, 2.4, 5.3, 3.1, 4.9, 2.1, 8.4, 5.9};
        //List<List<Integer>>适用于：arr[][]一维和二维都不确定的情况
        //List<int[]>适用于：arr[][]一维不确定，二维确定的情况
        //List<Integer>[]适用于：arr[][]一维确定，二维不确定的情况
        bestSchedule.bestMachineSchedule = new List[k];

        System.out.println(bestSchedule.find(n, k, work));

        for (int i = 0; i < bestSchedule.bestMachineSchedule.length; i++) {
            System.out.println("机器" + i + ":" + bestSchedule.bestMachineSchedule[i]);
        }
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(k^n)，空间复杂度O(n+k)
     *
     * @param n
     * @param k
     * @param work
     * @return
     */
    public double find(int n, int k, double[] work) {
        List<Integer>[] curMachineSchedule = new List[k];

        //每个机器执行的任务list初始化
        for (int i = 0; i < k; i++) {
            curMachineSchedule[i] = new ArrayList<>();
        }

        //machines[i]：机器i执行任务需要的时间
        backtrack(0, n, k, work, new double[k], curMachineSchedule);

        return bestTime;
    }

    private void backtrack(int t, int n, int k, double[] work, double[] machines, List<Integer>[] curMachineSchedule) {
        if (t == n) {
            //当前调度时间
            double curTime = 0;

            for (double machineTime : machines) {
                curTime = Math.max(curTime, machineTime);
            }

            //当前调度时间小于最佳调度时间时，更新最佳调度时间和最佳调度方案
            if (curTime < bestTime) {
                bestTime = curTime;

                for (int i = 0; i < k; i++) {
                    bestMachineSchedule[i] = new ArrayList<>(curMachineSchedule[i]);
                }
            }

            return;
        }

        for (int i = 0; i < k; i++) {
            //当前机器i执行work[t]之后的执行时间大于等于bestTime，则不会存在更少的最佳调度时间，直接下次循环，相当于剪枝
            if (machines[i] + work[t] >= bestTime) {
                continue;
            }

            machines[i] = machines[i] + work[t];
            curMachineSchedule[i].add(t);

            backtrack(t + 1, n, k, work, machines, curMachineSchedule);

            curMachineSchedule[i].remove(curMachineSchedule[i].size() - 1);
            machines[i] = machines[i] - work[t];
        }
    }
}
