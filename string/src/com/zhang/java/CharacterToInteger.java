package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/10/10 10:34
 * @Author zsy
 * @Description 中文数字转int atoi类比Problem8 栈类比Problem150、Problem224、Problem227、Problem394
 * 将输入的中文数字转化为int数字输出
 * <p>
 * 输入：n = 一亿一千二百五十八万三千二百四十
 * 输出：112,583,240
 * <p>
 * 所输入的数据都在int范围之内
 */
public class CharacterToInteger {
    public static void main(String[] args) {
        CharacterToInteger characterToInteger = new CharacterToInteger();
        String str = "一亿一千二百五十八万三千二百四十";
        System.out.println(characterToInteger.myCharacterToInteger(str));
    }

    /**
     * 栈
     * 栈中保存当前数字，当遍历到数字'一'到'九'，则直接入栈；
     * 当遍历到'亿'、'万'、'千'、'百'、'十'，栈中小于当前单位的数字依次出栈并相加，乘上当前单位，再重新入栈，
     * 当遍历结束，栈中数字依次出栈并相加，即为结果
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param str
     * @return
     */
    public int myCharacterToInteger(String str) {
        //栈，保存当前数字
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            //当前字符
            char c = str.charAt(i);

            if (c == '一') {
                stack.push(1);
            } else if (c == '二') {
                stack.push(2);
            } else if (c == '三') {
                stack.push(3);
            } else if (c == '四') {
                stack.push(4);
            } else if (c == '五') {
                stack.push(5);
            } else if (c == '六') {
                stack.push(6);
            } else if (c == '七') {
                stack.push(7);
            } else if (c == '八') {
                stack.push(8);
            } else if (c == '九') {
                stack.push(9);
            } else if (c == '亿') {
                //栈中小于一亿的元素之和
                int sum = 0;
                //一亿
                int num = 100000000;

                //栈顶小于num的所有元素出栈，并相加
                while (!stack.isEmpty() && stack.peek() < num) {
                    sum = sum + stack.pop();
                }

                stack.push(sum * num);
            } else if (c == '万') {
                //栈中小于一万的元素之和
                int sum = 0;
                //一万
                int num = 10000;

                //栈顶小于num的所有元素出栈，并相加
                while (!stack.isEmpty() && stack.peek() < num) {
                    sum = sum + stack.pop();
                }

                stack.push(sum * num);
            } else if (c == '千') {
                //栈中小于一千的元素之和
                int sum = 0;
                //一千
                int num = 1000;

                //栈顶小于num的所有元素出栈，并相加
                while (!stack.isEmpty() && stack.peek() < num) {
                    sum = sum + stack.pop();
                }

                stack.push(sum * num);
            } else if (c == '百') {
                //栈中小于一百的元素之和
                int sum = 0;
                //一百
                int num = 100;

                //栈顶小于num的所有元素出栈，并相加
                while (!stack.isEmpty() && stack.peek() < num) {
                    sum = sum + stack.pop();
                }

                stack.push(sum * num);
            } else if (c == '十') {
                //栈中小于一十的元素之和
                int sum = 0;
                //一十
                int num = 10;

                //栈顶小于num的所有元素出栈，并相加
                while (!stack.isEmpty() && stack.peek() < num) {
                    sum = sum + stack.pop();
                }

                stack.push(sum * num);
            }
        }

        int result = 0;

        //栈中元素依次出栈并相加，得到最终结果
        while (!stack.isEmpty()) {
            result = result + stack.pop();
        }

        return result;
    }
}
