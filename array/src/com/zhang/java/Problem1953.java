package com.zhang.java;

/**
 * @Date 2025/1/10 08:51
 * @Author zsy
 * @Description 你可以工作的最大周数 恒生银行面试题 类比Problem1753、Problem2335
 * 给你 n 个项目，编号从 0 到 n - 1 。
 * 同时给你一个整数数组 milestones ，其中每个 milestones[i] 表示第 i 个项目中的阶段任务数量。
 * 你可以按下面两个规则参与项目中的工作：
 * 每周，你将会完成 某一个 项目中的 恰好一个 阶段任务。
 * 你每周都 必须 工作。
 * 在 连续的 两周中，你 不能 参与并完成同一个项目中的两个阶段任务。
 * 一旦所有项目中的全部阶段任务都完成，或者执行仅剩的一个阶段任务将会导致你违反上面的规则，你将 停止工作。
 * 注意，由于这些条件的限制，你可能无法完成所有阶段任务。
 * 返回在不违反上面规则的情况下你 最多 能工作多少周。
 * <p>
 * 输入：milestones = [1,2,3]
 * 输出：6
 * 解释：一种可能的情形是：
 * - 第 1 周，你参与并完成项目 0 中的一个阶段任务。
 * - 第 2 周，你参与并完成项目 2 中的一个阶段任务。
 * - 第 3 周，你参与并完成项目 1 中的一个阶段任务。
 * - 第 4 周，你参与并完成项目 2 中的一个阶段任务。
 * - 第 5 周，你参与并完成项目 1 中的一个阶段任务。
 * - 第 6 周，你参与并完成项目 2 中的一个阶段任务。
 * 总周数是 6 。
 * <p>
 * 输入：milestones = [5,2,1]
 * 输出：7
 * 解释：一种可能的情形是：
 * - 第 1 周，你参与并完成项目 0 中的一个阶段任务。
 * - 第 2 周，你参与并完成项目 1 中的一个阶段任务。
 * - 第 3 周，你参与并完成项目 0 中的一个阶段任务。
 * - 第 4 周，你参与并完成项目 1 中的一个阶段任务。
 * - 第 5 周，你参与并完成项目 0 中的一个阶段任务。
 * - 第 6 周，你参与并完成项目 2 中的一个阶段任务。
 * - 第 7 周，你参与并完成项目 0 中的一个阶段任务。
 * 总周数是 7 。
 * 注意，你不能在第 8 周参与完成项目 0 中的最后一个阶段任务，因为这会违反规则。
 * 因此，项目 0 中会有一个阶段任务维持未完成状态。
 * <p>
 * n == milestones.length
 * 1 <= n <= 10^5
 * 1 <= milestones[i] <= 10^9
 */
public class Problem1953 {
    public static void main(String[] args) {
        Problem1953 problem1953 = new Problem1953();
        int[] milestones = {5, 2, 1};
        System.out.println(problem1953.numberOfWeeks(milestones));
    }

    /**
     * 贪心
     * 计算milestones中最大项目的任务数max，除去max剩余项目的任务数之和restSum
     * 1、max<=restSum+1，则所有任务都能完成，最多工作周数为max+restSum
     * 2、max>restSum+1，则最大项目中的任务只能完成restSum+1次，其余任务都能完成，最多工作周数为2*restSum+1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param milestones
     * @return
     */
    public long numberOfWeeks(int[] milestones) {
        //milestones中最大项目的任务数
        int max = milestones[0];
        //使用long，避免int溢出
        long sum = 0;

        for (int num : milestones) {
            max = Math.max(max, num);
            sum = sum + num;
        }

        //除去max剩余项目的任务数之和
        //使用long，避免int溢出
        long restSum = sum - max;

        //max<=restSum+1，则所有任务都能完成，最多工作周数为max+restSum，即为sum
        if (max <= restSum + 1) {
            return sum;
        } else {
            //max>restSum+1，则最大项目中的任务只能完成restSum+1次，其余任务都能完成，最多工作周数为2*restSum+1
            return 2 * restSum + 1;
        }
    }
}
