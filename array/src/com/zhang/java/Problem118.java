package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/7/1 10:32
 * @Author zsy
 * @Description 杨辉三角 字节面试题 类比Problem119、Problem120
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 * <p>
 * 输入: numRows = 5
 * 输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 * <p>
 * 输入: numRows = 1
 * 输出: [[1]]
 * <p>
 * 1 <= numRows <= 30
 */
public class Problem118 {
    public static void main(String[] args) {
        Problem118 problem118 = new Problem118();
        int numRows = 5;
        System.out.println(problem118.generate(numRows));
    }

    /**
     * 模拟
     * 时间复杂度O((numRows)^2)，空间复杂度O(1)
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        if (numRows == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        //第一行杨辉三角初始化
        List<Integer> tempList = new ArrayList<>();
        tempList.add(1);
        result.add(tempList);

        for (int i = 1; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            //杨辉三角当前行第一个元素为1
            list.add(1);

            for (int j = 1; j < i; j++) {
                list.add(result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
            }

            //杨辉三角当前行最后一个元素为1
            list.add(1);
            result.add(list);
        }

        return result;
    }
}
