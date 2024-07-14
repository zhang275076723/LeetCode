package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/9/10 08:57
 * @Author zsy
 * @Description 用栈操作构建数组 类比Problem946、Offer31
 * 给你一个数组 target 和一个整数 n。每次迭代，需要从  list = { 1 , 2 , 3 ..., n } 中依次读取一个数字。
 * 请使用下述操作来构建目标数组 target ：
 * "Push"：从 list 中读取一个新元素， 并将其推入数组中。
 * "Pop"：删除数组中的最后一个元素。
 * 如果目标数组构建完成，就停止读取更多元素。
 * 题目数据保证目标数组严格递增，并且只包含 1 到 n 之间的数字。
 * 请返回构建目标数组所用的操作序列。
 * 如果存在多个可行方案，返回任一即可。
 * <p>
 * 输入：target = [1,3], n = 3
 * 输出：["Push","Push","Pop","Push"]
 * 解释：
 * 读取 1 并自动推入数组 -> [1]
 * 读取 2 并自动推入数组，然后删除它 -> [1]
 * 读取 3 并自动推入数组 -> [1,3]
 * <p>
 * 输入：target = [1,2,3], n = 3
 * 输出：["Push","Push","Push"]
 * <p>
 * 输入：target = [1,2], n = 4
 * 输出：["Push","Push"]
 * 解释：只需要读取前 2 个数字就可以停止。
 * <p>
 * 1 <= target.length <= 100
 * 1 <= n <= 100
 * 1 <= target[i] <= n
 * target 严格递增
 */
public class Problem1441 {
    public static void main(String[] args) {
        Problem1441 problem1441 = new Problem1441();
        int[] target = {1, 3};
        int n = 3;
        System.out.println(problem1441.buildArray(target, n));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param target
     * @param n
     * @return
     */
    public List<String> buildArray(int[] target, int n) {
        List<String> list = new ArrayList<>();
        int num = 1;

        for (int i = 0; i < target.length; i++) {
            //当前元素不等于target[i]，则当前元素num入栈，再出栈
            while (target[i] != num) {
                list.add("Push");
                list.add("Pop");
                num++;
            }

            list.add("Push");
            num++;
        }

        return list;
    }
}
