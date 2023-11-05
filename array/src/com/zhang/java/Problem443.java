package com.zhang.java;

/**
 * @Date 2023/5/6 09:07
 * @Author zsy
 * @Description 压缩字符串 类比Problem271、Problem394、Problem604、Problem900
 * 给你一个字符数组 chars ，请使用下述算法压缩：
 * 从一个空字符串 s 开始。对于 chars 中的每组 连续重复字符 ：
 * 如果这一组长度为 1 ，则将字符追加到 s 中。
 * 否则，需要向 s 追加字符，后跟这一组的长度。
 * 压缩后得到的字符串 s 不应该直接返回 ，需要转储到字符数组 chars 中。
 * 需要注意的是，如果组长度为 10 或 10 以上，则在 chars 数组中会被拆分为多个字符。
 * 请在 修改完输入数组后 ，返回该数组的新长度。
 * 你必须设计并实现一个只使用常量额外空间的算法来解决此问题。
 * <p>
 * 输入：chars = ["a","a","b","b","c","c","c"]
 * 输出：返回 6 ，输入数组的前 6 个字符应该是：["a","2","b","2","c","3"]
 * 解释："aa" 被 "a2" 替代。"bb" 被 "b2" 替代。"ccc" 被 "c3" 替代。
 * <p>
 * 输入：chars = ["a"]
 * 输出：返回 1 ，输入数组的前 1 个字符应该是：["a"]
 * 解释：唯一的组是“a”，它保持未压缩，因为它是一个字符。
 * <p>
 * 输入：chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
 * 输出：返回 4 ，输入数组的前 4 个字符应该是：["a","b","1","2"]。
 * 解释：由于字符 "a" 不重复，所以不会被压缩。"bbbbbbbbbbbb" 被 “b12” 替代。
 * <p>
 * 1 <= chars.length <= 2000
 * chars[i] 可以是小写英文字母、大写英文字母、数字或符号
 */
public class Problem443 {
    public static void main(String[] args) {
        Problem443 problem443 = new Problem443();
//        char[] chars = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        char[] chars = {'a', 'a', 'a', 'b', 'b', 'a', 'a'};
        System.out.println(problem443.compress(chars));
    }

    /**
     * 双指针
     * 一个指针指向写入位置，另一个指针指向读取位置
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param chars
     * @return
     */
    public int compress(char[] chars) {
        if (chars.length == 1) {
            return 1;
        }

        //指向读取位置
        int index = 0;
        //指向写入位置
        int len = 0;

        while (index < chars.length) {
            //当前字符c
            char c = chars[index];
            //字符c出现次数
            int count = 1;
            index++;

            while (index < chars.length && chars[index] == c) {
                count++;
                index++;
            }

            //当前字符c写入chars，len后移
            chars[len] = c;
            len++;

            //字符c出现次数大于1才进行压缩
            if (count != 1) {
                //字符c出现次数字符串
                String str = count + "";

                for (int i = 0; i < str.length(); i++) {
                    chars[len] = str.charAt(i);
                    len++;
                }
            }
        }

        return len;
    }
}
