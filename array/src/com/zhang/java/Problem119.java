package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/7/1 11:00
 * @Author zsy
 * @Description 杨辉三角 II 类比Problem118、Problem120
 * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 * <p>
 * 输入: rowIndex = 3
 * 输出: [1,3,3,1]
 * <p>
 * 输入: rowIndex = 0
 * 输出: [1]
 * <p>
 * 输入: rowIndex = 1
 * 输出: [1,1]
 * <p>
 * 0 <= rowIndex <= 33
 */
public class Problem119 {
    public static void main(String[] args) {
        Problem119 problem119 = new Problem119();
        int rowIndex = 3;
        System.out.println(problem119.getRow(rowIndex));
    }

    /**
     * 模拟
     * 时间复杂度O((rowIndex)^2)，空间复杂度O(1)
     *
     * @param rowIndex
     * @return
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<>();
        //第一行杨辉三角初始化
        list.add(1);

        for (int i = 1; i <= rowIndex; i++) {
            //杨辉三角当前行最后一个元素为1
            list.add(1);

            //逆序遍历，避免当前元素影响前一个元素
            for (int j = i - 1; j > 0; j--) {
                list.set(j, list.get(j - 1) + list.get(j));
            }
        }

        return list;
    }
}
