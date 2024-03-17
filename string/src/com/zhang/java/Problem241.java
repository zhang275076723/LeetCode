package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/10/7 08:13
 * @Author zsy
 * @Description 为运算表达式设计优先级 类比Problem282、Problem1147 分治法类比Problem95、Problem105、Problem106、Problem108、Problem109、Problem255、Problem395、Problem449、Problem617、Problem654、Problem889、Problem1008、Offer7、Offer33
 * 给你一个由数字和运算符组成的字符串 expression ，按不同优先级组合数字和运算符，计算并返回所有可能组合的结果。
 * 你可以 按任意顺序 返回答案。
 * 生成的测试用例满足其对应输出值符合 32 位整数范围，不同结果的数量不超过 10^4 。
 * <p>
 * 输入：expression = "2-1-1"
 * 输出：[0,2]
 * 解释：
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 * <p>
 * 输入：expression = "2*3-4*5"
 * 输出：[-34,-14,-10,-10,10]
 * 解释：
 * (2*(3-(4*5))) = -34
 * ((2*3)-(4*5)) = -14
 * ((2*(3-4))*5) = -10
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 * <p>
 * 1 <= expression.length <= 20
 * expression 由数字和算符 '+'、'-' 和 '*' 组成。
 * 输入表达式中的所有整数值在范围 [0, 99]
 */
public class Problem241 {
    public static void main(String[] args) {
        Problem241 problem241 = new Problem241();
        String expression = "2*3-4*5";
        System.out.println(problem241.diffWaysToCompute(expression));
    }

    /**
     * 分治法
     * 找一个运算符将expression分为两个表达式，分别往两边找这两个表达式所有可能组合的结果
     * 时间复杂度O(Cn)，空间复杂度O(Cn) (Cn为第n个卡特兰数)
     *
     * @param expression
     * @return
     */
    public List<Integer> diffWaysToCompute(String expression) {
        int index = 0;

        //找expression中的第一个运算符
        while (index < expression.length() && '0' <= expression.charAt(index) && expression.charAt(index) <= '9') {
            index++;
        }

        //expression中不存在运算符，即expression整体为一个数字，直接返回
        if (index == expression.length()) {
            return new ArrayList<Integer>() {{
                add(Integer.parseInt(expression));
            }};
        }

        List<Integer> list = new ArrayList<>();

        //找一个运算符将expression分为两个表达式，分别往两边找这两个表达式所有可能组合的结果
        for (int i = index; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if ('0' <= c && c <= '9') {
                continue;
            }

            //得到表达式expression[0]-expression[i-1]所有可能组合的结果
            List<Integer> leftList = diffWaysToCompute(expression.substring(0, i));
            //得到表达式expression[i+1]-expression[expression.length()-1]所有可能组合的结果
            List<Integer> rightList = diffWaysToCompute(expression.substring(i + 1));

            for (int left : leftList) {
                for (int right : rightList) {
                    //运算符只有+、-、*，没有/
                    if (c == '+') {
                        list.add(left + right);
                    } else if (c == '-') {
                        list.add(left - right);
                    } else if (c == '*') {
                        list.add(left * right);
                    }
                }
            }
        }

        return list;
    }
}
