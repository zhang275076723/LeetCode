package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/10/5 08:58
 * @Author zsy
 * @Description 给表达式添加运算符 类比Problem679 回溯+剪枝类比
 * 给定一个仅包含数字 0-9 的字符串 num 和一个目标值整数 target ，
 * 在 num 的数字之间添加 二元 运算符（不是一元）+、- 或 * ，返回 所有 能够得到 target 的表达式。
 * 注意，返回表达式中的操作数 不应该 包含前导零。
 * <p>
 * 输入: num = "123", target = 6
 * 输出: ["1+2+3", "1*2*3"]
 * 解释: “1*2*3” 和 “1+2+3” 的值都是6。
 * <p>
 * 输入: num = "232", target = 8
 * 输出: ["2*3+2", "2+3*2"]
 * 解释: “2*3+2” 和 “2+3*2” 的值都是8。
 * <p>
 * 输入: num = "3456237490", target = 9191
 * 输出: []
 * 解释: 表达式 “3456237490” 无法得到 9191 。
 * <p>
 * 1 <= num.length <= 10
 * num 仅含数字
 * -2^31 <= target <= 2^31 - 1
 */
public class Problem282 {
    public static void main(String[] args) {
        Problem282 problem282 = new Problem282();
        String num = "123";
        int target = 6;
        System.out.println(problem282.addOperators(num, target));
    }

    /**
     * 回溯+剪枝
     * 核心思想：保留上一次运算结果，用于运算符的优先级
     * 注意：不能先得到中缀表达式，再根据基本计算器得到运算结果，会超时
     * 时间复杂度O(n*3^n)，空间复杂度O(n) (sb拷贝到结果集合需要O(1)，共3^(n-1)种情况)
     *
     * @param num
     * @param target
     * @return
     */
    public List<String> addOperators(String num, int target) {
        List<String> list = new ArrayList<>();

        //pre：上一次运算结果(考虑运算符的优先级)，result：当前运算结果(即num[0]-num[t]运算结果)，使用long避免int溢出
        //加减法下一个backtrack，pre为当前数字；乘法下一个backtrack，pre为pre*当前数字
        backtrack(0, 0, 0, num, target, new StringBuilder(), list);

        return list;
    }

    private void backtrack(int t, long pre, long curResult, String num, int target, StringBuilder sb, List<String> list) {
        if (t == num.length()) {
            if (curResult == target) {
                list.add(sb.toString());
            }
            return;
        }

        for (int i = t; i < num.length(); i++) {
            //不能有前导0
            if (i > t && num.charAt(t) == '0') {
                return;
            }

            //使用long避免int溢出
            long curNum = Long.parseLong(num.substring(t, i + 1));

            //第一个数字，前面不需要添加加减乘号
            if (t == 0) {
                int len = sb.length();
                sb.append(curNum);
                backtrack(i + 1, curNum, curNum, num, target, sb, list);
                sb.delete(len, sb.length());
            } else {
                //加，前驱pre为curNum
                int len = sb.length();
                sb.append('+').append(curNum);
                backtrack(i + 1, curNum, curResult + curNum, num, target, sb, list);
                sb.delete(len, sb.length());

                //减，前驱pre为-curNum
                sb.append('-').append(curNum);
                backtrack(i + 1, -curNum, curResult - curNum, num, target, sb, list);
                sb.delete(len, sb.length());

                //乘，前驱pre为pre*curNum
                sb.append('*').append(curNum);
                //考虑到运算符的优先级，先减去上一次运算结果，乘法运算符优先级高，需要先计算pre*curNum
                //例如：num=123，当前遍历到3，pre为-2，sb为1-2，此时要*3，需要先计算2*3，再减去2*3的结果6
                backtrack(i + 1, pre * curNum, curResult - pre + pre * curNum, num, target, sb, list);
                sb.delete(len, sb.length());
            }
        }
    }
}
