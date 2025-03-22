package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2025/5/4 08:56
 * @Author zsy
 * @Description 移除石子的最大得分 类比Problem1953、Problem2335
 * 你正在玩一个单人游戏，面前放置着大小分别为 a、b 和 c 的 三堆 石子。
 * 每回合你都要从两个 不同的非空堆 中取出一颗石子，并在得分上加 1 分。
 * 当存在 两个或更多 的空堆时，游戏停止。
 * 给你三个整数 a 、b 和 c ，返回可以得到的 最大分数 。
 * <p>
 * 输入：a = 2, b = 4, c = 6
 * 输出：6
 * 解释：石子起始状态是 (2, 4, 6) ，最优的一组操作是：
 * - 从第一和第三堆取，石子状态现在是 (1, 4, 5)
 * - 从第一和第三堆取，石子状态现在是 (0, 4, 4)
 * - 从第二和第三堆取，石子状态现在是 (0, 3, 3)
 * - 从第二和第三堆取，石子状态现在是 (0, 2, 2)
 * - 从第二和第三堆取，石子状态现在是 (0, 1, 1)
 * - 从第二和第三堆取，石子状态现在是 (0, 0, 0)
 * 总分：6 分 。
 * <p>
 * 输入：a = 4, b = 4, c = 6
 * 输出：7
 * 解释：石子起始状态是 (4, 4, 6) ，最优的一组操作是：
 * - 从第一和第二堆取，石子状态现在是 (3, 3, 6)
 * - 从第一和第三堆取，石子状态现在是 (2, 3, 5)
 * - 从第一和第三堆取，石子状态现在是 (1, 3, 4)
 * - 从第一和第三堆取，石子状态现在是 (0, 3, 3)
 * - 从第二和第三堆取，石子状态现在是 (0, 2, 2)
 * - 从第二和第三堆取，石子状态现在是 (0, 1, 1)
 * - 从第二和第三堆取，石子状态现在是 (0, 0, 0)
 * 总分：7 分 。
 * <p>
 * 输入：a = 1, b = 8, c = 8
 * 输出：8
 * 解释：最优的一组操作是连续从第二和第三堆取 8 回合，直到将它们取空。
 * 注意，由于第二和第三堆已经空了，游戏结束，不能继续从第一堆中取石子。
 * <p>
 * 1 <= a, b, c <= 10^5
 */
public class Problem1753 {
    public static void main(String[] args) {
        Problem1753 problem1753 = new Problem1753();
        int a = 2;
        int b = 4;
        int c = 6;
        System.out.println(problem1753.maximumScore(a, b, c));
        System.out.println(problem1753.maximumScore2(a, b, c));
    }

    /**
     * 优先队列，大根堆
     * 时间复杂度O(max(a)*log3)=O(10^5)=O(1)，空间复杂度O(3)=O(1)
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int maximumScore(int a, int b, int c) {
        //大根堆，存放三堆石子的个数
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        priorityQueue.offer(a);
        priorityQueue.offer(b);
        priorityQueue.offer(c);

        int score = 0;

        while (priorityQueue.size() >= 2) {
            int max = priorityQueue.poll();
            int mid = priorityQueue.poll();

            score++;

            if (max - 1 > 0) {
                priorityQueue.offer(max - 1);
            }
            if (mid - 1 > 0) {
                priorityQueue.offer(mid - 1);
            }
        }

        return score;
    }

    /**
     * 贪心
     * a、b、c由小到大排序，排序后从小到大的数量分别是a、b、c，
     * 1、a+b<=c，则每次用1个a或b匹配1个c，最大分数为a+b
     * 2、a+b>c，则a和b匹配(a+b-c+1)/2次，此时a+b<=c，即转换为情况1，此时a变为a-(a+b-c+1)/2，b变为b-(a+b-c+1)/2，
     * 最大分数为(a+b-c+1)/2+a-(a+b-c+1)/2+b-(a+b-c+1)/2=a+b-(a+b-c+1)/2
     * 注意：a+b-(a+b-c+1)/2不能继续化简为(a+b+c-1)/2，因为(a+b-c+1)/2是向上取整，合并运算之后，向上取整的效果就不存在了
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int maximumScore2(int a, int b, int c) {
        int max = Math.max(a, Math.max(b, c));
        int min = Math.min(a, Math.min(b, c));

        //a为中间元素
        if (a != max && a != min) {
            b = a;
            a = min;
            c = max;
        } else if (b != max && b != min) {
            //b为中间元素
            a = min;
            c = max;
        } else if (c != max && c != min) {
            //c为中间元素
            b = c;
            a = min;
            c = max;
        }

        if (a + b <= c) {
            return a + b;
        } else {
            //注意：a+b-(a+b-c+1)/2不能继续化简为(a+b+c-1)/2，因为(a+b-c+1)/2是向上取整，合并运算之后，向上取整的效果就不存在了
            return a + b - (a + b - c + 1) / 2;
        }
    }
}
