package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/9/2 08:59
 * @Author zsy
 * @Description 到家的最少跳跃次数 跳跃问题类比Problem45、Problem55、Problem403、Problem1306、Problem1340、Problem1345、Problem1696、Problem1871、Problem2498
 * 有一只跳蚤的家在数轴上的位置 x 处。请你帮助它从位置 0 出发，到达它的家。
 * 跳蚤跳跃的规则如下：
 * 它可以 往前 跳恰好 a 个位置（即往右跳）。
 * 它可以 往后 跳恰好 b 个位置（即往左跳）。
 * 它不能 连续 往后跳 2 次。
 * 它不能跳到任何 forbidden 数组中的位置。
 * 跳蚤可以往前跳 超过 它的家的位置，但是它 不能跳到负整数 的位置。
 * 给你一个整数数组 forbidden ，其中 forbidden[i] 是跳蚤不能跳到的位置，
 * 同时给你整数 a， b 和 x ，请你返回跳蚤到家的最少跳跃次数。
 * 如果没有恰好到达 x 的可行方案，请你返回 -1 。
 * <p>
 * 输入：forbidden = [14,4,18,1,15], a = 3, b = 15, x = 9
 * 输出：3
 * 解释：往前跳 3 次（0 -> 3 -> 6 -> 9），跳蚤就到家了。
 * <p>
 * 输入：forbidden = [8,3,16,6,12,20], a = 15, b = 13, x = 11
 * 输出：-1
 * <p>
 * 输入：forbidden = [1,6,2,14,5,17,4], a = 16, b = 9, x = 7
 * 输出：2
 * 解释：往前跳一次（0 -> 16），然后往回跳一次（16 -> 7），跳蚤就到家了。
 * <p>
 * 1 <= forbidden.length <= 1000
 * 1 <= a, b, forbidden[i] <= 2000
 * 0 <= x <= 2000
 * forbidden 中所有位置互不相同。
 * 位置 x 不在 forbidden 中。
 */
public class Problem1654 {
    public static void main(String[] args) {
        Problem1654 problem1654 = new Problem1654();
//        int[] forbidden = {14, 4, 18, 1, 15};
//        int a = 3;
//        int b = 15;
//        int x = 9;
        int[] forbidden = {1998};
        int a = 1999;
        int b = 2000;
        int x = 2000;
        System.out.println(problem1654.minimumJumps(forbidden, a, b, x));
    }

    /**
     * bfs
     * 难点：确定能够最大跳跃到的右边界，如果不限制右边界，则无法跳出bfs循环
     * 1、a = b时，即只能往右跳跃，能够最大跳跃到的右边界为x
     * 2、a > b时，能够跳跃到的右边界为x+b，不能连续往左跳跃2次，所以整体上每次最少往右跳跃a-b步(先往右跳跃a步，再往左跳跃b步)，
     * 如果超过右边界，往左跳b步，或者先往右跳跃a步，再往左跳跃b步，都大于右边界，无法跳跃到x
     * 3、a < b时，能够最大跳跃到的右边界为max(x,max(forbidden))+a+b，取a>b时的右边界x+b，不能连续往左跳跃2次，
     * 所以整体上每次最少往左跳跃b-a步(先往右跳跃a步，再往左跳跃b步)，如果从x+b能跳跃到x，则需要从x+b先往右跳跃a步，再往左跳跃b步，
     * 即右边界为x+a+b，有可能forbidden数组中的最大值超过x，则右边界取x和forbidden数组中较大值，再加上a，加上b，
     * 综上所述：能够跳跃到的右边界为max(x,max(forbidden))+a+b
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param forbidden
     * @param a
     * @param b
     * @param x
     * @return
     */
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        if (x == 0) {
            return 0;
        }

        //arr[0]：当前跳跃到的下标索引，arr[1]：上一个位置怎样跳跃到当前位置，1：往右跳，-1：往左跳
        Queue<int[]> queue = new LinkedList<>();
        Set<Integer> visitedSet = new HashSet<>();
        //不能跳跃到的下标索引集合，O(1)判断当前位置是否能跳跃到
        Set<Integer> forbiddenSet = new HashSet<>();
        //初始化从0开始跳跃，且只能往右跳跃
        queue.offer(new int[]{0, 1});

        //bfs向外扩展的次数，0跳跃到x的最少跳跃次数
        int count = 0;
        //forbidden数组中的最大值
        int forbiddenMax = forbidden[0];

        for (int i = 0; i < forbidden.length; i++) {
            forbiddenMax = Math.max(forbiddenMax, forbidden[i]);
            forbiddenSet.add(forbidden[i]);
        }

        //x在无法跳跃到的集合中，则x无法跳跃到，返回-1
        if (forbiddenSet.contains(x)) {
            return -1;
        }

        //能够最大跳跃到的右边界，如果超过右边界，则不需要再继续跳跃，如果不限制右边界，则无法跳出bfs循环
        //1、a = b时，即只能往右跳跃，能够最大跳跃到的右边界为x
        //2、a > b时，能够跳跃到的右边界为x+b，不能连续往左跳跃2次，所以整体上每次最少往右跳跃a-b步(先往右跳跃a步，再往左跳跃b步)，
        //如果超过右边界，往左跳b步，或者先往右跳跃a步，再往左跳跃b步，都大于右边界，无法跳跃到x
        //3、a < b时，能够最大跳跃到的右边界为max(x,max(forbidden))+a+b，取a>b时的右边界x+b，不能连续往左跳跃2次，
        //所以整体上每次最少往左跳跃b-a步(先往右跳跃a步，再往左跳跃b步)，如果从x+b能跳跃到x，则需要从x+b先往右跳跃a步，再往左跳跃b步，
        //即右边界为x+a+b，有可能forbidden数组中的最大值超过x，则右边界取x和forbidden数组中较大值，再加上a，加上b，
        //综上所述：能够跳跃到的右边界为max(x,max(forbidden))+a+b
        int rightBound = Math.max(x, forbiddenMax) + a + b;

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次往外扩一层，表示跳一次
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();

                //当前位置小于0，或者位置超过右边界，或者当前位置已访问，或者当前位置不能跳跃到，直接进行下次循环
                if (arr[0] < 0 || arr[0] > rightBound || visitedSet.contains(arr[0]) || forbiddenSet.contains(arr[0])) {
                    continue;
                }

                //当前下标索引等于x，则找到了跳跃到x的最少跳跃次数，返回count
                if (arr[0] == x) {
                    return count;
                }

                //当前下标索引已访问
                visitedSet.add(arr[0]);

                //上一个位置往左跳跃到当前位置，则不能往左跳跃，只能往右跳
                if (arr[1] == -1) {
                    queue.offer(new int[]{arr[0] + a, 1});
                } else {
                    //上一个位置往右跳跃到当前位置，则可以往左跳跃，也可以往右跳
                    queue.offer(new int[]{arr[0] - b, -1});
                    queue.offer(new int[]{arr[0] + a, 1});
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //bfs结束，都没有跳跃到x，返回false
        return -1;
    }
}
