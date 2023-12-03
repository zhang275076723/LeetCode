package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/10 08:13
 * @Author zsy
 * @Description Nim 游戏 II 类比Problem292、Problem293、Problem294、Problem464、Problem486 状态压缩类比Problem187、Problem294、Problem464、Problem473、Problem526、Problem638、Problem698、Problem847 记忆化搜索类比
 * Alice 和 Bob 交替进行一个游戏，由 Alice 先手。
 * 在游戏中，共有 n 堆石头。在每个玩家的回合中，玩家需要 选择 任一非空石头堆，从中移除任意 非零 数量的石头。
 * 如果不能移除任意的石头，就输掉游戏，同时另一人获胜。
 * 给定一个整数数组 piles ，piles[i] 为 第 i 堆石头的数量，如果 Alice 能获胜返回 true ，反之返回 false 。
 * Alice 和 Bob 都会采取 最优策略 。
 * <p>
 * 输入：piles = [1]
 * 输出：true
 * 解释：只有一种可能的情况：
 * - 第一回合，Alice 移除了第 1 堆中 1 块石头。piles = [0]。
 * - 第二回合，Bob 没有任何石头可以移除。Alice 获胜。
 * <p>
 * 输入：piles = [1,1]
 * 输出：false
 * 解释：可以证明，Bob一定能获胜。一种可能的情况：
 * - 第一回合，Alice 移除了第 1 堆中 1 块石头。 piles = [0,1]。
 * - 第二回合，Bob 移除了第 2 堆中 1 块石头。 piles = [0,0]。
 * - 第三回合，Alice 没有任何石头可以移除。Bob 获胜。
 * <p>
 * 输入：piles = [1,2,3]
 * 输出：false
 * 解释：可以证明，Bob一定能获胜。一种可能的情况：
 * - 第一回合，Alice 移除了第 3 堆中 3 块石头。 piles = [1,2,0]。
 * - 第二回合，Bob 移除了第 2 堆中 1 块石头。 piles = [1,1,0]。
 * - 第三回合，Alice 移除了第 1 堆中 1 块石头。piles = [0,1,0]。
 * - 第四回合，Bob 移除了第 2 堆中 1 块石头。 piles = [0,0,0]。
 * - 第三回合，Alice 没有任何石头可以移除。Bob 获胜。
 * <p>
 * n == piles.length
 * 1 <= n <= 7
 * 1 <= piles[i] <= 7
 */
public class Problem1908 {
    public static void main(String[] args) {
        Problem1908 problem1908 = new Problem1908();
        int[] piles = {1, 2, 3};
        System.out.println(problem1908.nimGame(piles));
    }

    /**
     * 记忆化搜索+二进制状态压缩
     * 最多7堆石头，每堆石头数量不超过7，如果使用数组存储每堆石头访问状态需要O(7)，将长度为7的数组用二进制形式表示需要O(1)，
     * 每堆石头数量只有0-7，共8种情况，每堆石头只需要3bit就能表示，则长度为7的数组需要21bit来表示，即int就能表示长度为7的数组
     * 时间复杂度O(n*8^n)，空间复杂度O(8^n) (共8^n种状态，每种状态需要O(1)存储)
     *
     * @param piles
     * @return
     */
    public boolean nimGame(int[] piles) {
        //key：piles中每堆石头剩余数量的二进制状态，value：当前玩家以当前剩余石头开始游戏能否获胜
        Map<Integer, Boolean> map = new HashMap<>();
        //piles中每堆石头剩余数量的二进制状态，每3位表示当前堆中石头剩余的数量
        int state = 0;

        for (int i = 0; i < piles.length; i++) {
            //每堆石头数量只有0-7，共8种情况，每堆石头只需要3bit就能表示
            state = (state << 3) + piles[i];
        }

        return dfs(state, piles.length, map);
    }

    private boolean dfs(int state, int n, Map<Integer, Boolean> map) {
        //已经得到了当前玩家以当前每堆剩余石头开始游戏能否获胜，直接返回map.get(state)
        if (map.containsKey(state)) {
            return map.get(state);
        }

        //遍历每一堆石头，移除当前堆中的石头
        //第i堆石头，即为piles[n-i-1]堆中的石头
        for (int i = 0; i < n; i++) {
            //当前堆剩余的石头数量，每堆石头3bit表示
            int count = (state >>> (i * 3)) & 0b111;

            //当前玩家从第i堆移除的石头数量
            for (int j = 1; j <= count; j++) {
                //当前堆移除j个石头，得到下一个二进制状态
                int nextState = state - (j << (i * 3));

                //对手以nextState开始游戏失败，则自己以state开始游戏获胜，返回true
                if (!dfs(nextState, n, map)) {
                    map.put(state, true);
                    return true;
                }
            }
        }

        //遍历结束当前玩家以state开始游戏失败，返回false
        map.put(state, false);
        return false;
    }
}
