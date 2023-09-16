package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/9 08:28
 * @Author zsy
 * @Description 自除数 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49
 * 自除数 是指可以被它包含的每一位数整除的数。
 * 例如，128 是一个 自除数 ，因为 128 % 1 == 0，128 % 2 == 0，128 % 8 == 0。
 * 自除数 不允许包含 0 。
 * 给定两个整数 left 和 right ，返回一个列表，列表的元素是范围 [left, right] 内所有的 自除数 。
 * <p>
 * 输入：left = 1, right = 22
 * 输出：[1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
 * <p>
 * 输入：left = 47, right = 85
 * 输出：[48,55,66,77]
 * <p>
 * 1 <= left <= right <= 10^4
 */
public class Problem728 {
    public static void main(String[] args) {
        Problem728 problem728 = new Problem728();
        int left = 47;
        int right = 85;
        System.out.println(problem728.selfDividingNumbers(left, right));
    }

    /**
     * 模拟
     * 时间复杂度O((right-left+1)*log(right))=O(right-left+1)，空间复杂度O(1) (right为int类型，log(right)=1)
     *
     * @param left
     * @param right
     * @return
     */
    public List<Integer> selfDividingNumbers(int left, int right) {
        if (left > right) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        for (int i = left; i <= right; i++) {
            int num = i;
            //当前数i是否是自除数标志位
            boolean flag = true;

            while (num != 0) {
                //当前数i由低到高的当前位
                int cur = num % 10;

                //当前位cur为0，或者当前数i不能整除当前位cur，则不是自除数，直接跳出循环
                if (cur == 0 || i % cur != 0) {
                    flag = false;
                    break;
                }

                num = num / 10;
            }

            if (flag) {
                list.add(i);
            }
        }

        return list;
    }
}
