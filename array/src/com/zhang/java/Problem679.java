package com.zhang.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date 2023/5/2 08:14
 * @Author zsy
 * @Description 24 点游戏 字节面试题 华为面试题 阿里面试题 微软面试题 网易面试题 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem89、Problem90、Problem97、Problem216、Problem301、Problem377、Problem491、Problem698、Offer17、Offer38
 * 给定一个长度为4的整数数组 cards 。你有 4 张卡片，每张卡片上都包含一个范围在 [1,9] 的数字。
 * 您应该使用运算符 ['+', '-', '*', '/'] 和括号 '(' 和 ')' 将这些卡片上的数字排列成数学表达式，以获得值24。
 * 你须遵守以下规则:
 * 除法运算符 '/' 表示实数除法，而不是整数除法。
 * 例如， 4 / (1 - 2 / 3)= 4 / (1 / 3)= 12 。
 * 每个运算都在两个数字之间。特别是，不能使用 “-” 作为一元运算符。
 * 例如，如果 cards =[1,1,1,1] ，则表达式 “-1 -1 -1 -1” 是 不允许 的。
 * 你不能把数字串在一起
 * 例如，如果 cards =[1,2,1,2] ，则表达式 “12 + 12” 无效。
 * 如果可以得到这样的表达式，其计算结果为 24 ，则返回 true ，否则返回 false 。
 * <p>
 * 输入: cards = [4, 1, 8, 7]
 * 输出: true
 * 解释: (8-4) * (7-1) = 24
 * <p>
 * 输入: cards = [1, 2, 1, 2]
 * 输出: false
 * <p>
 * cards.length == 4
 * 1 <= cards[i] <= 9
 */
public class Problem679 {
    public static void main(String[] args) {
        Problem679 problem679 = new Problem679();
        int[] cards = {3, 3, 8, 8};
        //8/(3-8/3)=24
        System.out.println(problem679.judgePoint24(cards));
        System.out.println(problem679.judgePoint24_2(cards));
    }

    /**
     * 回溯+剪枝
     * 从4个数字中有序的选2个数，作为1个数，剩余3个数，共4*3=12种选法，选1个操作符，共4种选法；
     * 从3个数字中有序的选2个数，作为1个数，剩余2个数，共3*2=6种选法，选1个操作符，共4种选法；
     * 从2个数字中有序的选2个数，作为1个数，剩余1个数，共2*1=2种选法，选1个操作符，共4种选法；
     * 时间复杂度O(12*4*6*4*2*4)=O(1)，空间复杂度O(1)
     *
     * @param cards
     * @return
     */
    public boolean judgePoint24(int[] cards) {
        //每次从list中取出2个数进行运算，得到的结果重新加入list
        List<Double> list = new ArrayList<>();

        for (int card : cards) {
            list.add((double) card);
        }

        return backtrack(list);
    }

    /**
     * 回溯+剪枝，并且输出每个和为24点的表达式
     * 从4个数字中有序的选2个数，作为1个数，剩余3个数，共4*3=12种选法，选1个操作符，共4种选法；
     * 从3个数字中有序的选2个数，作为1个数，剩余2个数，共3*2=6种选法，选1个操作符，共4种选法；
     * 从2个数字中有序的选2个数，作为1个数，剩余1个数，共2*1=2种选法，选1个操作符，共4种选法；
     * 时间复杂度O(12*4*6*4*2*4)=O(1)，空间复杂度O(1)
     *
     * @param cards
     * @return
     */
    public Set<String> judgePoint24_2(int[] cards) {
        //使用set是为了去重
        Set<String> result = new HashSet<>();
        //每次从list中取出2个数进行运算，得到的结果重新加入list
        List<Double> list = new ArrayList<>();
        //每次保存list中的表达式，最终得到和为24的表达式
        List<String> opsList = new ArrayList<>();

        for (int card : cards) {
            list.add((double) card);
            opsList.add(card + "");
        }

        backtrack2(list, opsList, result);

        return result;
    }

    private boolean backtrack(List<Double> list) {
        //当list中只剩1个数时，则得到了4个数的运算结果
        if (list.size() == 1) {
            //因为是实数运算，所以得到的结果和24之间的误差小于10^(-6)，则认为得到的结果就是24
            return Math.abs(list.get(0) - 24) < 1e-6;
        }

        for (int i = 0; i < list.size(); i++) {
            //第1个要进行运算的数
            double num1 = list.remove(i);

            for (int j = 0; j < list.size(); j++) {
                //第2个要进行运算的数
                double num2 = list.remove(j);

                //选1个运算符
                for (int k = 0; k < 4; k++) {
                    //加法
                    if (k == 1) {
                        list.add(num1 + num2);
                    } else if (k == 2) {
                        //减法
                        list.add(num1 - num2);
                    } else if (k == 3) {
                        //乘法
                        list.add(num1 * num2);
                    } else {
                        //除法
                        //实数运算和0比较必须通过差值比较，num2和0之间的误差小于10^(-6)，则认为num2为0，0不能做除数
                        if (Math.abs(num2) < 1e-6) {
                            continue;
                        }
                        list.add(num1 / num2);
                    }

                    //继续往后寻找，当找到和为24的组合，直接返回true
                    if (backtrack(list)) {
                        return true;
                    }

                    //删除之前添加的运算结果
                    list.remove(list.size() - 1);
                }

                //添加之前删除的第2个要进行运算的数
                list.add(j, num2);
            }

            //添加之前删除的第1个要进行运算的数
            list.add(i, num1);
        }

        //遍历结束还没有找到和为24的表达式，返回false
        return false;
    }

    private void backtrack2(List<Double> list, List<String> opsList, Set<String> result) {
        //当list中只剩1个数时，则得到了4个数的运算结果
        if (list.size() == 1) {
            //因为是实数运算，所以得到的结果和24之间的误差小于10^(-6)，则认为得到的结果就是24
            if (Math.abs(list.get(0) - 24) < 1e-6) {
                result.add(opsList.get(0));
            }
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            //第1个要进行运算的数
            double num1 = list.remove(i);
            //第1个要进行运算的表达式
            String ops1 = opsList.remove(i);

            for (int j = 0; j < list.size(); j++) {
                //第2个要进行运算的数
                double num2 = list.remove(j);
                //第2个要进行运算的表达式
                String ops2 = opsList.remove(j);

                //选1个运算符
                for (int k = 0; k < 4; k++) {
                    //加法
                    if (k == 0) {
                        list.add(num1 + num2);
                        opsList.add("(" + ops1 + "+" + ops2 + ")");
                    } else if (k == 1) {
                        //减法
                        list.add(num1 - num2);
                        opsList.add("(" + ops1 + "-" + ops2 + ")");
                    } else if (k == 2) {
                        //乘法
                        list.add(num1 * num2);
                        opsList.add("(" + ops1 + "*" + ops2 + ")");
                    } else {
                        //除法
                        //实数运算和0比较必须通过差值比较，num2和0之间的误差小于10^(-6)，则认为num2为0，0不能做除数
                        if (Math.abs(num2) < 1e-6) {
                            continue;
                        }
                        list.add(num1 / num2);
                        opsList.add("(" + ops1 + "/" + ops2 + ")");
                    }

                    backtrack2(list, opsList, result);

                    //删除之前添加的运算表达式
                    opsList.remove(opsList.size() - 1);
                    //删除之前添加的运算结果
                    list.remove(list.size() - 1);
                }

                //添加之前删除的第2个要进行运算的表达式
                opsList.add(j, ops2);
                //添加之前删除的第1个要进行运算的数
                list.add(j, num2);
            }

            //添加之前删除的第1个要进行运算的表达式
            opsList.add(i, ops1);
            //添加之前删除的第1个要进行运算的数
            list.add(i, num1);
        }
    }
}
