package com.zhang.java;

/**
 * @Date 2023/9/9 09:10
 * @Author zsy
 * @Description 累加数 各种数类比Problem202、Problem204、Problem263、Problem264、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1201、Problem1291、Offer10、Offer49 回溯+剪枝类比
 * 累加数 是一个字符串，组成它的数字可以形成累加序列。
 * 一个有效的 累加序列 必须 至少 包含 3 个数。
 * 除了最开始的两个数以外，序列中的每个后续数字必须是它之前两个数字之和。
 * 给你一个只包含数字 '0'-'9' 的字符串，编写一个算法来判断给定输入是否是 累加数 。
 * 如果是，返回 true ；否则，返回 false 。
 * 说明：累加序列里的数，除数字 0 之外，不会 以 0 开头，所以不会出现 1, 2, 03 或者 1, 02, 3 的情况。
 * <p>
 * 输入："112358"
 * 输出：true
 * 解释：累加序列为: 1, 1, 2, 3, 5, 8 。1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
 * <p>
 * 输入："199100199"
 * 输出：true
 * 解释：累加序列为: 1, 99, 100, 199。1 + 99 = 100, 99 + 100 = 199
 * <p>
 * 1 <= num.length <= 35
 * num 仅由数字（0 - 9）组成
 */
public class Problem306 {
    public static void main(String[] args) {
        Problem306 problem306 = new Problem306();
        String num = "199100199";
        System.out.println(problem306.isAdditiveNumber(num));
        System.out.println(problem306.isAdditiveNumber2(num));
    }

    /**
     * 模拟
     * 当前累加序列由前两个累加序列构成，确定第一个和第二个累加序列，根据前两个累加序列，得到下一个累加序列，判断能否构成num
     * 时间复杂度O(n^3)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public boolean isAdditiveNumber(String num) {
        if (num == null || num.length() < 3) {
            return false;
        }

        for (int i = 0; i < num.length() - 2; i++) {
            //第一个累加序列num1长度超过1的情况下不能出现前导0
            if (i > 0 && num.charAt(0) == '0') {
                return false;
            }

            //第一个累加序列num[0]-num[i]
            String num1 = num.substring(0, i + 1);

            for (int j = i + 1; j < num.length() - 1; j++) {
                //第二个累加序列num2长度超过1的情况下不能出现前导0
                if (j > i + 1 && num.charAt(i + 1) == '0') {
                    break;
                }

                //第二个累加序列num[i+1]-num[j]
                String num2 = num.substring(i + 1, j + 1);

                //判断num1和num2能否构成num
                if (isValid(num, num1, num2)) {
                    return true;
                }
            }
        }

        //遍历结束不存在num1和num2能构成num，返回false
        return false;
    }

    /**
     * 回溯+剪枝
     * 当前累加序列由前两个累加序列构成，确定第一个和第二个累加序列，根据前两个累加序列，得到下一个累加序列，判断能否构成num
     *
     * @param num
     * @return
     */
    public boolean isAdditiveNumber2(String num) {
        if (num == null || num.length() < 3) {
            return false;
        }

        return backtrack(0, 0, num, "", "");
    }

    /**
     * num1和num2能否构成num
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param num
     * @param num1
     * @param num2
     * @return
     */
    private boolean isValid(String num, String num1, String num2) {
        int index = num1.length() + num2.length();

        while (index < num.length()) {
            //num1和num2相加得到的下一个累加序列，使用long避免int溢出
            String nextNum1 = Long.parseLong(num1) + Long.parseLong(num2) + "";

            //num字符串中和nextNum1相同长度的下一个累加序列长度超过num范围，则num1和num2无法构成num，返回false
            if (index + nextNum1.length() > num.length()) {
                return false;
            }

            //num字符串中和nextNum1相同长度的下一个累加序列
            String nextNum2 = num.substring(index, index + nextNum1.length());

            //nextNum1和nextNum2不相等，则num1和num2无法构成num，返回false
            if (!nextNum1.equals(nextNum2)) {
                return false;
            }

            //指针右移
            index = index + nextNum1.length();
            //更新当前两个累加序列
            num1 = num2;
            num2 = nextNum1;
        }

        //遍历结束，则num1和num2能构成num，返回true
        return true;
    }

    /**
     * @param t
     * @param count 当前累加序列的个数，num遍历结束，至少有3个累加序列num才是累加数
     * @param num
     * @param num1  第一个累加序列，找下一个累加序列时，第二个累加序列作为第一个累加序列
     * @param num2  第二个累加序列，找下一个累加序列时，当前累加序列作为第二个累加序列
     * @return
     */
    private boolean backtrack(int t, int count, String num, String num1, String num2) {
        //num遍历结束，至少有3个累加序列num才是累加数
        if (t == num.length()) {
            return count >= 3;
        }

        for (int i = t; i < num.length(); i++) {
            //当前累加序列长度超过1的情况下不能出现前导0
            if (i > t && num.charAt(t) == '0') {
                return false;
            }

            //下一个累加序列num[t]-num[i]
            String nextNum = num.substring(t, i + 1);

            //第一个累加序列不存在
            if ("".equals(num1)) {
                if (backtrack(i + 1, count + 1, num, nextNum, num2)) {
                    return true;
                }
            } else if ("".equals(num2)) {
                //第二个累加序列不存在

                if (backtrack(i + 1, count + 1, num, num1, nextNum)) {
                    return true;
                }
            } else {
                //两个累加序列都存在

                //num1和num2相加得到的下一个累加序列，使用long避免int溢出
                String sum = Long.parseLong(num1) + Long.parseLong(num2) + "";

                if (nextNum.equals(sum) && backtrack(i + 1, count + 1, num, num2, nextNum)) {
                    return true;
                }
            }
        }

        //遍历结束不存在num1和num2能构成num，返回false
        return false;
    }
}
