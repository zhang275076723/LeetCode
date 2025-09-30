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
     * 核心思想：保存上一次遍历的末尾连乘串的运算结果，用于本次遍历的乘法运算
     * 时间复杂度O(n*3^n)，空间复杂度O(n) (sb拷贝到结果集合需要O(1)，共3^(n-1)种情况)
     * <p>
     * 例如：num="1234"
     * t=0，得到的运算结果为1，preMul=1，curResult=1
     * t=1，如果运算符为减法，得到的运算结果为1-2，preMul=-2，curResult=1-2=-1
     * t=2，如果运算符为乘法，得到的运算结果为1-2*3，preMul=-2*3=-6，curResult=-1-(-2)+(-2)*3=-5
     * t=3，如果运算符为乘法，则得到的运算结果为1-2*3*4，preMul=-6*4=-24，curResult=-5-(-6)+(-6)*4=-23
     *
     * @param num
     * @param target
     * @return
     */
    public List<String> addOperators(String num, int target) {
        List<String> list = new ArrayList<>();

        //pre：上一次遍历的末尾连乘串的运算结果
        //result：当前遍历的运算结果(即num[0]-num[t]运算结果)
        //使用long避免int溢出
        backtrack(0, 0, 0, num, target, new StringBuilder(), list);

        return list;
    }

    private void backtrack(int t, long preMul, long curResult, String num, int target, StringBuilder sb, List<String> list) {
        if (t == num.length()) {
            if (curResult == target) {
                list.add(sb.toString());
            }
            return;
        }

        //num[t]-num[i]
        //使用long避免int溢出
        long curNum = 0;

        for (int i = t; i < num.length(); i++) {
            //不能有前导0
            if (i > t && num.charAt(t) == '0') {
                return;
            }

            curNum = curNum * 10 + (num.charAt(t) - '0');
            int len = sb.length();

            //第一个数字，前面不需要添加加减乘号
            if (t == 0) {
                sb.append(curNum);
                backtrack(i + 1, curNum, curNum, num, target, sb, list);
                sb.delete(len, sb.length());
            } else {
                //加，末尾连乘串的运算结果preMul为curNum
                sb.append('+').append(curNum);
                backtrack(i + 1, curNum, curResult + curNum, num, target, sb, list);
                sb.delete(len, sb.length());

                //减，末尾连乘串的运算结果preMul为-curNum
                sb.append('-').append(curNum);
                backtrack(i + 1, -curNum, curResult - curNum, num, target, sb, list);
                sb.delete(len, sb.length());

                //乘，末尾连乘串的运算结果preMul为preMul*curNum
                sb.append('*').append(curNum);
                //考虑到运算符的优先级，先减去上一次末尾连乘串的运算结果，乘法运算符优先级高，再加上preMul*curNum
                //例如：num=123，当前遍历到3，pre为-2，sb为1-2，此时要*3，需要先计算2*3，再减去2*3的结果6
                backtrack(i + 1, preMul * curNum, curResult - preMul + preMul * curNum, num, target, sb, list);
                sb.delete(len, sb.length());
            }
        }
    }
}
