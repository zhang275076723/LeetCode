package com.zhang.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Date 2022/10/10 10:34
 * @Author zsy
 * @Description 中文数字转int 类比Problem8、Problem12、Problem13、Problem168、Problem171、Problem273、Offer67 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem331、Problem341、Problem394、Problem678、Problem726、Problem856、Problem946、Problem1003、Problem1047、Problem1096、Offer31
 * 将输入的中文数字转化为int数字输出
 * <p>
 * 输入：n = 一亿一千二百五十八万三千二百四十
 * 输出：112,583,240
 * <p>
 * 所输入的数据都在int范围之内
 */
public class ChineseToInteger {
    public static void main(String[] args) {
        ChineseToInteger chineseToInteger = new ChineseToInteger();
        String str = "一亿一千二百五十八万三千二百四十五";
        //112583245
        System.out.println(chineseToInteger.convert(str));
    }

    /**
     * 单调栈
     * 单调递减栈中保存当前遍历到的数字，当单调递减栈栈顶元素小于当前元素时，栈中小于当前元素的值依次出栈相加，
     * 相加结果乘以当前元素再重新入栈，遍历结束，栈中元素依次出栈并相加，即为结果
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param str
     * @return
     */
    public int convert(String str) {
        //单调递减栈，保存当前数字
        Stack<Integer> stack = new Stack<>();
        //中文字符和对应数字的映射map
        Map<Character, Integer> map = new HashMap<Character, Integer>() {{
            put('一', 1);
            put('二', 2);
            put('三', 3);
            put('四', 4);
            put('五', 5);
            put('六', 6);
            put('七', 7);
            put('八', 8);
            put('九', 9);
            put('十', 10);
            put('百', 100);
            put('千', 1_000);
            put('万', 10_000);
            put('亿', 100_000_000);
        }};

        for (char c :str.toCharArray()) {
            //当前字符对应的数字
            int num = map.get(c);
            //单调栈中小于num的元素之和
            int sum = 0;

            while (!stack.isEmpty() && stack.peek() < num) {
                sum = sum + stack.pop();
            }

            //num满足单调递减栈，即sum没有变，仍为0，则num直接入栈
            if (sum == 0) {
                stack.push(num);
            } else {
                //num不满足单调递减栈，则栈中小于当前元素的值依次出栈相加，相加结果乘以当前元素再重新入栈
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
