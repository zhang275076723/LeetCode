package com.zhang.java;

/**
 * @Date 2023/5/6 08:05
 * @Author zsy
 * @Description 外观数列 类比Problem271、Problem394、Problem443、Problem604
 * 给定一个正整数 n ，输出外观数列的第 n 项。
 * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
 * 你可以将其视作是由递归公式定义的数字字符串序列：
 * countAndSay(1) = "1"
 * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
 * 前五项如下：
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 第一项是数字 1
 * 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
 * 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
 * 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
 * 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
 * 要 描述 一个数字字符串，首先要将字符串分割为 最小 数量的组，每个组都由连续的最多 相同字符 组成。
 * 然后对于每个组，先描述字符的数量，然后描述字符，形成一个描述组。
 * 要将描述转换为数字字符串，先将每组中的字符数量用数字替换，再将所有描述组连接起来。
 * 例如，数字字符串 "3322251" 的描述如下图：
 * 2个3,3个2,1个5,1个1
 * 23+32+15+11=23321511
 * <p>
 * 输入：n = 1
 * 输出："1"
 * 解释：这是一个基本样例。
 * <p>
 * 输入：n = 4
 * 输出："1211"
 * 解释：
 * countAndSay(1) = "1"
 * countAndSay(2) = 读 "1" = 一 个 1 = "11"
 * countAndSay(3) = 读 "11" = 二 个 1 = "21"
 * countAndSay(4) = 读 "21" = 一 个 2 + 一 个 1 = "12" + "11" = "1211"
 * <p>
 * 1 <= n <= 30
 */
public class Problem38 {
    public static void main(String[] args) {
        Problem38 problem38 = new Problem38();
        int n = 6;
        //312211
        System.out.println(problem38.countAndSay(n));
    }

    /**
     * 模拟
     * 从n=2开始，根据前一项的外观数列得到当前项的外观数列
     * 时间复杂度O(n*L)，空间复杂度O(L) (L：生成每一项外观数列的最大长度)
     *
     * @param n
     * @return
     */
    public String countAndSay(int n) {
        if (n == 1) {
            return "1";
        }

        StringBuilder sb = new StringBuilder("1");

        //从第2项开始，根据前一项的外观数列得到当前项的外观数列
        for (int i = 2; i <= n; i++) {
            StringBuilder tempSb = new StringBuilder();
            int index = 0;

            while (index < sb.length()) {
                //当前字符c
                char c = sb.charAt(index);
                //字符c出现的次数
                int count = 1;
                index++;

                while (index < sb.length() && sb.charAt(index) == c) {
                    count++;
                    index++;
                }

                //拼接出现count次字符c
                tempSb.append(count).append(c);
            }

            //tempSb赋值给sb，进行下次循环
            sb = tempSb;
        }


        return sb.toString();
    }
}
