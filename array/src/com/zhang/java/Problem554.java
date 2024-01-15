package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/5/7 08:54
 * @Author zsy
 * @Description 砖墙 哈希表类比Problem1、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem763、Problem1640、Offer50
 * 你的面前有一堵矩形的、由 n 行砖块组成的砖墙。
 * 这些砖块高度相同（也就是一个单位高）但是宽度不同。每一行砖块的宽度之和相等。
 * 你现在要画一条 自顶向下 的、穿过 最少 砖块的垂线。
 * 如果你画的线只是从砖块的边缘经过，就不算穿过这块砖。
 * 你不能沿着墙的两个垂直边缘之一画线，这样显然是没有穿过一块砖的。
 * 给你一个二维数组 wall ，该数组包含这堵墙的相关信息。
 * 其中，wall[i] 是一个代表从左至右每块砖的宽度的数组。
 * 你需要找出怎样画才能使这条线 穿过的砖块数量最少 ，并且返回 穿过的砖块数量 。
 * <p>
 * 输入：wall = [[1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]]
 * 输出：2
 * <p>
 * 输入：wall = [[1],[1],[1]]
 * 输出：3
 * <p>
 * n == wall.length
 * 1 <= n <= 10^4
 * 1 <= wall[i].length <= 10^4
 * 1 <= sum(wall[i].length) <= 2 * 10^4
 * 对于每一行 i ，sum(wall[i]) 是相同的
 * 1 <= wall[i][j] <= 2^31 - 1
 */
public class Problem554 {
    public static void main(String[] args) {
        Problem554 problem554 = new Problem554();
        List<List<Integer>> wall = new ArrayList<>();
        //第 1 行的间隙有 [1,3,5]
        //第 2 行的间隙有 [3,4]
        //第 3 行的间隙有 [1,4]
        //第 4 行的间隙有 [2]
        //第 5 行的间隙有 [3,4]
        //第 6 行的间隙有 [1,4,5]
        List<Integer> list1 = Arrays.asList(1, 2, 2, 1);
        List<Integer> list2 = Arrays.asList(3, 1, 2);
        List<Integer> list3 = Arrays.asList(1, 3, 2);
        List<Integer> list4 = Arrays.asList(2, 4);
        List<Integer> list5 = Arrays.asList(3, 1, 2);
        List<Integer> list6 = Arrays.asList(1, 3, 1, 1);
        wall.add(list1);
        wall.add(list2);
        wall.add(list3);
        wall.add(list4);
        wall.add(list5);
        wall.add(list6);
        System.out.println(problem554.leastBricks(wall));
    }

    /**
     * 哈希表
     * 直线经过某一砖块，不是穿过砖块，就是从砖块边缘经过，所以砖墙的高度等于自上而下的直线穿过砖块的数量加上从砖块边缘经过的数量
     * 统计每个位置的直线从砖块边缘经过的数量，砖墙的高度减去直线从砖块边缘经过的最大数量，得到直线穿过砖块的最小数量
     * 时间复杂度O(mn)，空间复杂度O(mn) (n=wall.size(),m=max(wall.get(i).size()))
     *
     * @param wall
     * @return
     */
    public int leastBricks(List<List<Integer>> wall) {
        //key：从左边界开始，距离左边界为key的直线，value：当前直线从砖块边缘经过的数量，
        //key=1，value=2，表示从左边界开始，距离左边界为1的直线，从砖块边缘经过的数量为2
        Map<Integer, Integer> map = new HashMap<>();

        for (List<Integer> list : wall) {
            //从左边界开始，距离左边界的距离，即得到从左边开始的每一个从砖块边缘经过的数量
            int sum = 0;
            //最后一个砖块的宽度不能统计，因为最后一个砖块是墙的右边界
            for (int i = 0; i < list.size() - 1; i++) {
                sum = sum + list.get(i);
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }

        //直线从砖块边缘经过的最大数量
        int max = 0;

        for (int count : map.values()) {
            max = Math.max(max, count);
        }

        //砖墙的高度减去直线从砖块边缘经过的最大数量，得到直线穿过砖块的最小数量
        return wall.size() - max;
    }
}
