package com.zhang.java;

/**
 * @Date 2024/2/21 08:44
 * @Author zsy
 * @Description 整数转换英文表示 微软面试题 类比Problem8、Problem12、Problem13、Problem168、Problem171、Problem273、Offer67、ChineseToInteger
 * 将非负整数 num 转换为其对应的英文表示。
 * <p>
 * 输入：num = 123
 * 输出："One Hundred Twenty Three"
 * <p>
 * 输入：num = 12345
 * 输出："Twelve Thousand Three Hundred Forty Five"
 * <p>
 * 输入：num = 1234567
 * 输出："One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 * <p>
 * 0 <= num <= 2^31 - 1
 */
public class Problem273 {
    public static void main(String[] args) {
        Problem273 problem273 = new Problem273();
        //"One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
        System.out.println(problem273.numberToWords(1234567));
        //"One Million One Hundred Twenty Three"
        System.out.println(problem273.numberToWords2(1000123));
    }

    /**
     * 模拟，迭代实现
     * 英文数字是每3位一组，计算每一组中的数字对应的英文，再拼接相应的单位Billion、Million、Thousand、""
     * 每3位一组的数x范围为[0,999]，根据x的范围，有4种情况：
     * 1、100<=x<=999，拼接xxx Hundred，x=x%100
     * 2、20<=x<100，拼接xxx-ty，x=x%10
     * 3、0<x<20，拼接One-Nineteen
     * 4、x=0，不拼接
     * 时间复杂度O(logn)=O(1)，空间复杂度O(logn)=O(1)
     * <p>
     * 例如：num=1,000,123
     * 第一组为1，即为One，拼接Million，得到One Million
     * 第二组为0，不拼接，得到One Million
     * 第二组为123，即为One Hundred Twenty Three，拼接""，得到One Million One Hundred Twenty Three
     *
     * @param num
     * @return
     */
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }

        //每3位一组后面需要拼接的英文单位数组
        String[] unitArr = {"Billion", "Million", "Thousand", ""};
        //大于等于20英文对应的数组
        String[] greaterEqualThan20Arr = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        //小于20英文对应的数组
        String[] lessThan20Arr = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

        StringBuilder sb = new StringBuilder();
        //unitArr的下标索引
        int index = 0;

        //int范围内数字最大的单位为Billion，即从1,000,000,000开始遍历，每次除以1,0000
        for (int i = (int) 1e9; i >= 1; i = i / 1000) {
            //当前3位一组的值
            int x = num / i;

            //x为0，不能拼接当前单位，直接进行下次循环
            if (x == 0) {
                index++;
                continue;
            }

            //100<=x<=999，拼接xxx Hundred
            if (x >= 100) {
                sb.append(lessThan20Arr[x / 100]).append(" ").append("Hundred").append(" ");
                x = x % 100;
            }

            //20<=x<100，拼接xxx-ty
            if (x >= 20) {
                sb.append(greaterEqualThan20Arr[x / 10]).append(" ");
                x = x % 10;
            }

            //0<x<20，拼接One-Nineteen
            if (x > 0) {
                sb.append(lessThan20Arr[x]).append(" ");
            }

            //拼接相应的单位
            sb.append(unitArr[index]).append(" ");

            //num取余数，找下一组3位一组的值
            num = num % i;
            index++;
        }

        int i = sb.length() - 1;

        //去除末尾空格
        while (i >= 0 && sb.charAt(i) == ' ') {
            i--;
        }

        //去除末尾空格
        return sb.delete(i + 1, sb.length()).toString();
    }

    /**
     * 模拟，递归实现
     * 英文数字是每3位一组，计算每一组中的数字对应的英文，再拼接相应的单位Billion、Million、Thousand、""
     * 每3位一组的数x范围为[0,999]，根据x的范围，有4种情况：
     * 1、100<=x<=999，拼接xxx Hundred，x=x%100
     * 2、20<=x<100，拼接xxx-ty，x=x%10
     * 3、0<x<20，拼接One-Nineteen
     * 4、x=0，不拼接
     * 时间复杂度O(logn)=O(1)，空间复杂度O(logn)=O(1)
     * <p>
     * 例如：num=1,000,123
     * 第一组为1，即为One，拼接Million，得到One Million
     * 第二组为0，不拼接，得到One Million
     * 第二组为123，即为One Hundred Twenty Three，拼接""，得到One Million One Hundred Twenty Three
     *
     * @param num
     * @return
     */
    public String numberToWords2(int num) {
        if (num == 0) {
            return "Zero";
        }

        //每3位一组后面需要拼接的英文单位数组
        String[] unitArr = {"Billion", "Million", "Thousand", ""};
        //大于等于20英文对应的数组
        String[] greaterEqualThan20Arr = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        //小于20英文对应的数组
        String[] lessThan20Arr = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

        StringBuilder sb = new StringBuilder();
        convert(num, sb, unitArr, greaterEqualThan20Arr, lessThan20Arr);

        //去除末尾空格
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }

    private void convert(int num, StringBuilder sb,
                         String[] unitArr, String[] greaterEqualThan20Arr, String[] lessThan20Arr) {
        //num<=0，不拼接，直接返回
        if (num <= 0) {
            return;
        }

        //0<num<20，拼接One-Nineteen
        if (num < 20) {
            sb.append(lessThan20Arr[num]).append(" ");
            return;
        }

        //20<=num<100，拼接xxx-ty
        if (num < 100) {
            sb.append(greaterEqualThan20Arr[num / 10]).append(" ");
            convert(num % 10, sb, unitArr, greaterEqualThan20Arr, lessThan20Arr);
            return;
        }

        //100<=num<=999，拼接xxx Hundred
        if (num < 1000) {
            sb.append(lessThan20Arr[num / 100]).append(" ").append("Hundred").append(" ");
            convert(num % 100, sb, unitArr, greaterEqualThan20Arr, lessThan20Arr);
            return;
        }

        //unitArr的下标索引
        int index = 0;

        //int范围内数字最大的单位为Billion，即从1,000,000,000开始遍历，每次除以1,0000
        for (int i = (int) 1e9; i >= 1; i = i / 1000) {
            //当前3位一组的值为0，不能拼接当前单位，直接进行下次循环
            if (num / i == 0) {
                index++;
                continue;
            }

            //拼接当前3位一组的值
            convert(num / i, sb, unitArr, greaterEqualThan20Arr, lessThan20Arr);
            //拼接相应的单位
            sb.append(unitArr[index]).append(" ");
            //拼接下一组3位一组的值
            convert(num % i, sb, unitArr, greaterEqualThan20Arr, lessThan20Arr);
            return;
        }
    }
}
