package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2022/12/4 09:52
 * @Author zsy
 * @Description 区间列表的交集 类比Problem56、Problem57、Problem228、Problem252、Problem253、Problem406、Problem435、Problem436、Problem763、Problem1288
 * 给定两个由一些 闭区间 组成的列表，firstList 和 secondList ，
 * 其中 firstList[i] = [starti, endi] 而 secondList[j] = [startj, endj] 。
 * 每个区间列表都是成对 不相交 的，并且 已经排序 。
 * 返回这 两个区间列表的交集 。
 * 形式上，闭区间 [a, b]（其中 a <= b）表示实数 x 的集合，而 a <= x <= b 。
 * 两个闭区间的 交集 是一组实数，要么为空集，要么为闭区间。
 * 例如，[1, 3] 和 [2, 4] 的交集为 [2, 3] 。
 * <p>
 * 输入：firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,24],[25,26]]
 * 输出：[[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
 * <p>
 * 输入：firstList = [[1,3],[5,9]], secondList = []
 * 输出：[]
 * <p>
 * 输入：firstList = [], secondList = [[4,8],[10,12]]
 * 输出：[]
 * <p>
 * 输入：firstList = [[1,7]], secondList = [[3,10]]
 * 输出：[[3,7]]
 * <p>
 * 0 <= firstList.length, secondList.length <= 1000
 * firstList.length + secondList.length >= 1
 * 0 <= starti < endi <= 10^9
 * endi < starti+1
 * 0 <= startj < endj <= 10^9
 * endj < startj+1
 */
public class Problem986 {
    public static void main(String[] args) {
        Problem986 problem986 = new Problem986();
        int[][] firstList = {{0, 2}, {5, 10}, {13, 23}, {24, 25}};
        int[][] secondList = {{1, 5}, {8, 12}, {15, 24}, {25, 26}};
        System.out.println(Arrays.deepToString(problem986.intervalIntersection(firstList, secondList)));
    }

    /**
     * 双指针
     * 两个指针分别指向两个数组中的区间，如果两个区间相交，则加入结果集合，指针后移；不存在相交，靠前的区间指针后移
     * 时间复杂度O(m+n)，空间复杂度O(m+n)
     *
     * @param firstList
     * @param secondList
     * @return
     */
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        if (firstList == null) {
            return secondList;
        }

        if (secondList == null) {
            return firstList;
        }

        List<int[]> list = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < firstList.length && j < secondList.length) {
            int firstLeft = firstList[i][0];
            int firstRight = firstList[i][1];
            int secondLeft = secondList[j][0];
            int secondRight = secondList[j][1];

            //两个区间不相交，firstList区间在左
            if (firstRight < secondLeft) {
                i++;
            } else if (secondRight < firstLeft) {
                //两个区间不相交，secondList区间在左

                j++;
            } else {
                //两个区间相交

                //firstList区间在前面
                if (firstLeft <= secondLeft) {
                    if (firstRight <= secondRight) {
                        list.add(new int[]{secondLeft, firstRight});
                        i++;
                    } else {
                        list.add(new int[]{secondLeft, secondRight});
                        j++;
                    }
                } else {
                    //secondList区间在前面

                    if (firstRight <= secondRight) {
                        list.add(new int[]{firstLeft, firstRight});
                        i++;
                    } else {
                        list.add(new int[]{firstLeft, secondRight});
                        j++;
                    }
                }
            }
        }

        return list.toArray(new int[list.size()][]);
    }
}
