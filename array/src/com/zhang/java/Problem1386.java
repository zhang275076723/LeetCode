package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2024/8/31 08:10
 * @Author zsy
 * @Description 安排电影院座位 类比Problem134、Problem277 哈希表类比
 * 每行共10个位置，左边3个位置、中间4个位置、右边3个位置连在一起
 * 如上图所示，电影院的观影厅中有 n 行座位，行编号从 1 到 n ，且每一行内总共有 10 个座位，列编号从 1 到 10 。
 * 给你数组 reservedSeats ，包含所有已经被预约了的座位。
 * 比如说，reservedSeats[i]=[3,8] ，它表示第 3 行第 8 个座位被预约了。
 * 请你返回 最多能安排多少个 4 人家庭 。4 人家庭要占据 同一行内连续 的 4 个座位。
 * 隔着过道的座位（比方说 [3,3] 和 [3,4]）不是连续的座位，但是如果你可以将 4 人家庭拆成过道两边各坐 2 人，这样子是允许的。
 * <p>
 * 输入：n = 3, reservedSeats = [[1,2],[1,3],[1,8],[2,6],[3,1],[3,10]]
 * 输出：4
 * 解释：上图所示是最优的安排方案，总共可以安排 4 个家庭。蓝色的叉表示被预约的座位，橙色的连续座位表示一个 4 人家庭。
 * <p>
 * 输入：n = 2, reservedSeats = [[2,1],[1,8],[2,6]]
 * 输出：2
 * <p>
 * 输入：n = 4, reservedSeats = [[4,3],[1,4],[4,6],[1,7]]
 * 输出：4
 * <p>
 * 1 <= n <= 10^9
 * 1 <= reservedSeats.length <= min(10*n, 10^4)
 * reservedSeats[i].length == 2
 * 1 <= reservedSeats[i][0] <= n
 * 1 <= reservedSeats[i][1] <= 10
 * 所有 reservedSeats[i] 都是互不相同的。
 */
public class Problem1386 {
    public static void main(String[] args) {
        Problem1386 problem1386 = new Problem1386();
        int n = 3;
        int[][] reservedSeats = {{1, 2}, {1, 3}, {1, 8}, {2, 6}, {3, 1}, {3, 10}};
        System.out.println(problem1386.maxNumberOfFamilies(n, reservedSeats));
        System.out.println(problem1386.maxNumberOfFamilies2(n, reservedSeats));
    }

    /**
     * 哈希表+模拟 (超时)
     * 时间复杂度O(n+m)，空间复杂度O(m) (m=reservedSeats.length)
     *
     * @param n
     * @param reservedSeats
     * @return
     */
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        //key：当前行，value：当前行已经被预定的座位列集合
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for (int[] arr : reservedSeats) {
            if (!map.containsKey(arr[0])) {
                map.put(arr[0], new HashSet<>());
            }

            map.get(arr[0]).add(arr[1]);
        }

        int count = 0;

        for (int i = 1; i <= n; i++) {
            //当前行i没有位置被预定，则当前行可以组成2个4人家庭
            if (!map.containsKey(i)) {
                count = count + 2;
                continue;
            }

            Set<Integer> set = map.get(i);
            //2-5能否安排4人家庭标志位
            boolean flag1 = true;
            //4-7能否安排4人家庭标志位
            boolean flag2 = true;
            //6-9能否安排4人家庭标志位
            boolean flag3 = true;

            if (set.contains(2) || set.contains(3) || set.contains(4) || set.contains(5)) {
                flag1 = false;
            }

            if (set.contains(4) || set.contains(5) || set.contains(6) || set.contains(7)) {
                flag2 = false;
            }

            if (set.contains(6) || set.contains(7) || set.contains(8) || set.contains(9)) {
                flag3 = false;
            }

            if (flag1) {
                count++;
            }

            if (flag3) {
                count++;
            }

            //4-7没有被预定，并且2-5和6-9不能安排4人家庭，则4-7安排4人家庭
            if (flag2 && !flag1 && !flag3) {
                count++;
            }
        }

        return count;
    }

    /**
     * 哈希表+模拟优化
     * 逆向思维，不需要一行一行遍历，只需要遍历预定位置的行，因为不存在预定位置的行，则肯定组成2个4人家庭
     * 时间复杂度O(m)，空间复杂度O(m) (m=reservedSeats.length)
     *
     * @param n
     * @param reservedSeats
     * @return
     */
    public int maxNumberOfFamilies2(int n, int[][] reservedSeats) {
        //key：当前行，value：当前行已经被预定的座位列集合
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for (int[] arr : reservedSeats) {
            if (!map.containsKey(arr[0])) {
                map.put(arr[0], new HashSet<>());
            }

            map.get(arr[0]).add(arr[1]);
        }

        //初始化为2n，逆向思维，不需要一行一行遍历，只需要遍历预定位置的行，因为不存在预定位置的行，则肯定组成2个4人家庭
        int count = 2 * n;

        for (Set<Integer> set : map.values()) {
            //2-5能否安排4人家庭标志位
            boolean flag1 = true;
            //4-7能否安排4人家庭标志位
            boolean flag2 = true;
            //6-9能否安排4人家庭标志位
            boolean flag3 = true;

            if (set.contains(2) || set.contains(3) || set.contains(4) || set.contains(5)) {
                flag1 = false;
            }

            if (set.contains(4) || set.contains(5) || set.contains(6) || set.contains(7)) {
                flag2 = false;
            }

            if (set.contains(6) || set.contains(7) || set.contains(8) || set.contains(9)) {
                flag3 = false;
            }

            //当前行能安排4人家庭的个数
            int curCount = 0;

            if (flag1) {
                curCount++;
            }

            if (flag3) {
                curCount++;
            }

            //4-7没有被预定，并且2-5和6-9不能安排4人家庭，则4-7安排4人家庭
            if (flag2 && !flag1 && !flag3) {
                curCount++;
            }

            //count已经假设当前行能安排2个4人家庭，则需要减去未组成4人家庭的个数
            count = count - (2 - curCount);
        }

        return count;
    }
}
