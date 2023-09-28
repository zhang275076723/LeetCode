package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/10 08:45
 * @Author zsy
 * @Description 将数组拆分成斐波那契序列 字节面试题 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49 回溯+剪枝类比
 * 给定一个数字字符串 num，比如 "123456579"，我们可以将它分成「斐波那契式」的序列 [123, 456, 579]。
 * 形式上，斐波那契式 序列是一个非负整数列表 f，且满足：
 * 0 <= f[i] < 2^31 ，（也就是说，每个整数都符合 32 位 有符号整数类型）
 * f.length >= 3
 * 对于所有的0 <= i < f.length - 2，都有 f[i] + f[i + 1] = f[i + 2]
 * 另外，请注意，将字符串拆分成小块时，每个块的数字一定不要以零开头，除非这个块是数字 0 本身。
 * 返回从 num 拆分出来的任意一组斐波那契式的序列块，如果不能拆分则返回 []。
 * <p>
 * 输入：num = "1101111"
 * 输出：[11,0,11,11]
 * 解释：输出[110,1,111]也可以。
 * <p>
 * 输入: num = "112358130"
 * 输出: []
 * 解释: 无法拆分。
 * <p>
 * 输入："0123"
 * 输出：[]
 * 解释：每个块的数字不能以零开头，因此 "01"，"2"，"3" 不是有效答案。
 * <p>
 * 1 <= num.length <= 200
 * num 中只含有数字
 */
public class Problem842 {
    public static void main(String[] args) {
        Problem842 problem842 = new Problem842();
        String num = "1101111";
        System.out.println(problem842.splitIntoFibonacci(num));
        System.out.println(problem842.splitIntoFibonacci2(num));
    }

    /**
     * 模拟
     * 确定前两个斐波那契式数，根据前两个斐波那契式数，得到下一个斐波那契式数，判断能否构成num
     * 时间复杂度O(n^3)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public List<Integer> splitIntoFibonacci(String num) {
        if (num == null || num.length() < 3) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < num.length() - 2; i++) {
            //第一个斐波那契式数num1长度超过1的情况下不能出现前导0
            if (i > 0 && num.charAt(0) == '0') {
                break;
            }

            //第一个斐波那契式数num[0]-num[i]
            String num1 = num.substring(0, i + 1);

            //num1只能在int范围内，使用long避免int溢出
            if (Long.parseLong(num1) > Integer.MAX_VALUE) {
                break;
            }

            list.add(Integer.parseInt(num1));

            for (int j = i + 1; j < num.length() - 1; j++) {
                //第二个斐波那契式数num2长度超过1的情况下不能出现前导0
                if (j > i + 1 && num.charAt(i + 1) == '0') {
                    break;
                }

                //第二个斐波那契式数num[i+1]-num[j]
                String num2 = num.substring(i + 1, j + 1);

                //num2只能在int范围内，使用long避免int溢出
                if (Long.parseLong(num2) > Integer.MAX_VALUE) {
                    break;
                }

                list.add(Integer.parseInt(num2));

                //判断num1和num2能否构成num
                if (isValid(num, num1, num2, list)) {
                    return list;
                }

                //num2从结果集合list中移除
                list.remove(list.size() - 1);
            }

            //num2从结果集合list中移除
            list.remove(list.size() - 1);
        }

        return new ArrayList<>();
    }

    /**
     * 回溯+剪枝
     *
     * @param num
     * @return
     */
    public List<Integer> splitIntoFibonacci2(String num) {
        if (num == null || num.length() < 3) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();

        backtrack(0, num, "", "", list);

        return list;
    }

    /**
     * num1和num2能否构成num
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param num
     * @param num1
     * @param num2
     * @param list
     * @return
     */
    private boolean isValid(String num, String num1, String num2, List<Integer> list) {
        //临时结果集合，存放当前遍历到的斐波那契式数
        List<Integer> tempList = new ArrayList<>();
        int index = num1.length() + num2.length();

        while (index < num.length()) {
            //num1和num2相加得到的下一个斐波那契式数，使用long避免int溢出
            String nextNum1 = Long.parseLong(num1) + Long.parseLong(num2) + "";

            //nextNum1只能在int范围内，使用long避免int溢出
            if (Long.parseLong(nextNum1) > Integer.MAX_VALUE) {
                return false;
            }

            //num字符串中和nextNum1相同长度的下一个斐波那契式数长度超过num范围，则num1和num2无法构成num，返回false
            if (index + nextNum1.length() > num.length()) {
                return false;
            }

            //num字符串中和nextNum1相同长度的下一个斐波那契式数
            String nextNum2 = num.substring(index, index + nextNum1.length());

            //nextNum1和nextNum2不相等，则num1和num2无法构成num，返回false
            if (!nextNum1.equals(nextNum2)) {
                return false;
            }

            //新得到的斐波那契式数加入tempList
            tempList.add(Integer.parseInt(nextNum1));
            //指针右移
            index = index + nextNum1.length();
            //更新当前两个斐波那契式数
            num1 = num2;
            num2 = nextNum1;
        }

        //num1和num2能构成num，则tempList中元素加入list中
        for (int i = 0; i < tempList.size(); i++) {
            list.add(tempList.get(i));
        }

        return true;
    }

    /**
     * @param t
     * @param num
     * @param num1 第一个斐波那契式数，找下一个斐波那契式数时，第二个斐波那契式数作为第一个斐波那契式数
     * @param num2 第二个斐波那契式数，找下一个斐波那契式数时，当前斐波那契式数作为第二个斐波那契式数
     * @param list
     * @return
     */
    private boolean backtrack(int t, String num, String num1, String num2, List<Integer> list) {
        //num遍历结束，至少有3个斐波那契式数num才能拆分
        if (t == num.length()) {
            return list.size() >= 3;
        }

        for (int i = t; i < num.length(); i++) {
            //当前斐波那契式数长度超过1的情况下不能出现前导0
            if (i > t && num.charAt(t) == '0') {
                return false;
            }

            //下一个斐波那契式数num[t]-num[i]
            String nextNum = num.substring(t, i + 1);

            //nextNum只能在int范围内，使用long避免int溢出
            if (Long.parseLong(nextNum) > Integer.MAX_VALUE) {
                return false;
            }

            list.add(Integer.parseInt(nextNum));

            //第一个斐波那契式数不存在
            if ("".equals(num1)) {
                if (backtrack(i + 1, num, nextNum, num2, list)) {
                    return true;
                }
            } else if ("".equals(num2)) {
                //第二个斐波那契式数不存在

                if (backtrack(i + 1, num, num1, nextNum, list)) {
                    return true;
                }
            } else {
                //两个斐波那契式数都存在

                //num1和num2相加得到的下一个斐波那契式数，使用long避免int溢出
                String sum = Long.parseLong(num1) + Long.parseLong(num2) + "";

                if (nextNum.equals(sum) && backtrack(i + 1, num, num2, nextNum, list)) {
                    return true;
                }
            }

            list.remove(list.size() - 1);
        }

        //遍历结束不存在num1和num2能构成num，返回false
        return false;
    }
}
