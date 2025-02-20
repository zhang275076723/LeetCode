package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2025/4/11 08:39
 * @Author zsy
 * @Description 灯泡开关 II 类比Problem319 状态压缩类比
 * 房间中有 n 只已经打开的灯泡，编号从 1 到 n 。
 * 墙上挂着 4 个开关 。
 * 这 4 个开关各自都具有不同的功能，其中：
 * 开关 1 ：反转当前所有灯的状态（即开变为关，关变为开）
 * 开关 2 ：反转编号为偶数的灯的状态（即 0, 2, 4, ...）
 * 开关 3 ：反转编号为奇数的灯的状态（即 1, 3, ...）
 * 开关 4 ：反转编号为 j = 3k + 1 的灯的状态，其中 k = 0, 1, 2, ...（即 1, 4, 7, 10, ...）
 * 你必须 恰好 按压开关 presses 次。每次按压，你都需要从 4 个开关中选出一个来执行按压操作。
 * 给你两个整数 n 和 presses ，执行完所有按压之后，返回 不同可能状态 的数量。
 * <p>
 * 输入：n = 1, presses = 1
 * 输出：2
 * 解释：状态可以是：
 * - 按压开关 1 ，[关]
 * - 按压开关 2 ，[开]
 * <p>
 * 输入：n = 2, presses = 1
 * 输出：3
 * 解释：状态可以是：
 * - 按压开关 1 ，[关, 关]
 * - 按压开关 2 ，[开, 关]
 * - 按压开关 3 ，[关, 开]
 * <p>
 * 输入：n = 3, presses = 1
 * 输出：4
 * 解释：状态可以是：
 * - 按压开关 1 ，[关, 关, 关]
 * - 按压开关 2 ，[关, 开, 关]
 * - 按压开关 3 ，[开, 关, 开]
 * - 按压开关 4 ，[关, 开, 开]
 * <p>
 * 1 <= n <= 1000
 * 0 <= presses <= 1000
 */
public class Problem672 {
    public static void main(String[] args) {
        Problem672 problem672 = new Problem672();
        int n = 10;
        int presses = 6;
        System.out.println(problem672.flipLights(n, presses));
        System.out.println(problem672.flipLights2(n, presses));
    }

    /**
     * 回溯+剪枝 (超时)
     * 时间复杂度O(n*4^presses)，空间复杂度O(max(n,presses))
     *
     * @param n
     * @param presses
     * @return
     */
    public int flipLights(int n, int presses) {
        //n个灯泡经过4种开关presses次按压后的最终状态字符串集合
        Set<String> set = new HashSet<>();
        //n个灯泡状态数组
        //1：当前灯泡打开，0：当前灯泡关闭
        int[] bulb = new int[n];

        //初始化n个灯泡都已经打开
        for (int i = 0; i < n; i++) {
            bulb[i] = 1;
        }

        dfs(0, n, presses, bulb, set);

        return set.size();
    }

    /**
     * 二进制状态压缩
     * 观察发现每6个灯泡为一组：
     * 编号为6k+1的灯泡受开关1、3、4的影响
     * 编号为6k+2的灯泡受开关1、2的影响
     * 编号为6k+3的灯泡受开关1、3的影响
     * 编号为6k+4的灯泡受开关1、2、4的影响
     * 编号为6k+5的灯泡受开关1、3的影响
     * 编号为6k+6的灯泡受开关1、2的影响
     * 编号为6k+5的灯泡与编号为6k+3的灯泡相同，编号为6k+6的灯泡与编号为6k+2的灯泡相同，则只需要考虑前4个灯泡
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @param presses
     * @return
     */
    public int flipLights2(int n, int presses) {
        //编号为6k+5的灯泡与编号为6k+3的灯泡相同，编号为6k+6的灯泡与编号为6k+2的灯泡相同，则只需要考虑前4个灯泡
        //前4个灯泡在4种开关presses次按压的二进制状态集合
        Set<Integer> set = new HashSet<>();

        //4种开关的二进制状态i
        for (int i = 0; i < (1 << 4); i++) {
            //4种开关的状态数组
            //0：当前开关按压偶数次，1：当前开关按压奇数次
            int[] switcher = new int[4];
            //4种开关在二进制状态i的情况下按压次数为奇数的个数
            int count = 0;

            for (int j = 0; j < 4; j++) {
                switcher[j] = (i >>> j) & 1;

                if (switcher[j] == 1) {
                    count++;
                }
            }

            //4种开关在二进制状态i的情况下按压次数为奇数的个数小于presses次按压，并且两者按压次数的奇偶性相同，当前开关的二进制状态才合法
            if (count <= presses && count % 2 == presses % 2) {
                //前4个灯泡在4种开关二进制状态i的情况下的二进制状态
                int state = 0;

                //编号为6k+1的灯泡受开关1、3、4的影响
                if (n >= 1) {
                    state = state | (switcher[0] ^ switcher[2] ^ switcher[3]);
                }

                //编号为6k+2的灯泡受开关1、2的影响
                if (n >= 2) {
                    state = state | ((switcher[0] ^ switcher[1]) << 1);
                }

                //编号为6k+3的灯泡受开关1、3的影响
                if (n >= 3) {
                    state = state | ((switcher[0] ^ switcher[2]) << 2);
                }

                //编号为6k+4的灯泡受开关1、2、4的影响
                if (n >= 4) {
                    state = state | ((switcher[0] ^ switcher[1] ^ switcher[3]) << 3);
                }

                set.add(state);
            }
        }

        return set.size();
    }

    private void dfs(int t, int n, int presses, int[] bulb, Set<String> set) {
        if (t == presses) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < n; i++) {
                sb.append(bulb[i]);
            }

            set.add(sb.toString());
            return;
        }

        //开关1，反转所有灯状态
        for (int i = 0; i < n; i++) {
            bulb[i] = bulb[i] ^ 1;
        }

        dfs(t + 1, n, presses, bulb, set);

        //重新反转回来，用于其他开关
        for (int i = 0; i < n; i++) {
            bulb[i] = bulb[i] ^ 1;
        }

        //开关2，反转编号为偶数灯状态
        //注意：bulb[0]为编号为1的灯，所以偶数灯下标索引为1、3、5...
        for (int i = 1; i < n; i = i + 2) {
            bulb[i] = bulb[i] ^ 1;
        }

        dfs(t + 1, n, presses, bulb, set);

        //重新反转回来，用于其他开关
        for (int i = 1; i < n; i = i + 2) {
            bulb[i] = bulb[i] ^ 1;
        }

        //开关3，反转编号为奇数灯状态
        //注意：bulb[0]为编号为1的灯，所以奇数灯下标索引为0、2、4...
        for (int i = 0; i < n; i = i + 2) {
            bulb[i] = bulb[i] ^ 1;
        }

        dfs(t + 1, n, presses, bulb, set);

        //重新反转回来，用于其他开关
        for (int i = 0; i < n; i = i + 2) {
            bulb[i] = bulb[i] ^ 1;
        }

        //开关4，反转编号为3k+1灯状态
        //注意：bulb[0]为编号为1的灯，所以奇数灯下标索引为0、3、6...
        for (int i = 0; i < n; i = i + 3) {
            bulb[i] = bulb[i] ^ 1;
        }

        dfs(t + 1, n, presses, bulb, set);

        //重新反转回来，用于其他开关
        for (int i = 0; i < n; i = i + 3) {
            bulb[i] = bulb[i] ^ 1;
        }
    }
}
