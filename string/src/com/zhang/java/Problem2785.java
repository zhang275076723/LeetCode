package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/3/29 08:31
 * @Author zsy
 * @Description 将字符串中的元音字母排序 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1704、Problem1839、Problem2062、Problem2063、Problem2559、Problem2586
 * 给你一个下标从 0 开始的字符串 s ，将 s 中的元素重新 排列 得到新的字符串 t ，它满足：
 * 所有辅音字母都在原来的位置上。
 * 更正式的，如果满足 0 <= i < s.length 的下标 i 处的 s[i] 是个辅音字母，那么 t[i] = s[i] 。
 * 元音字母都必须以他们的 ASCII 值按 非递减 顺序排列。
 * 更正式的，对于满足 0 <= i < j < s.length 的下标 i 和 j  ，
 * 如果 s[i] 和 s[j] 都是元音字母，那么 t[i] 的 ASCII 值不能大于 t[j] 的 ASCII 值。
 * 请你返回结果字母串。
 * 元音字母为 'a' ，'e' ，'i' ，'o' 和 'u' ，它们可能是小写字母也可能是大写字母，辅音字母是除了这 5 个字母以外的所有字母。
 * <p>
 * 输入：s = "lEetcOde"
 * 输出："lEOtcede"
 * 解释：'E' ，'O' 和 'e' 是 s 中的元音字母，'l' ，'t' ，'c' 和 'd' 是所有的辅音。将元音字母按照 ASCII 值排序，辅音字母留在原地。
 * <p>
 * 输入：s = "lYmpH"
 * 输出："lYmpH"
 * 解释：s 中没有元音字母（s 中都为辅音字母），所以我们返回 "lYmpH" 。
 * <p>
 * 1 <= s.length <= 10^5
 * s 只包含英语字母表中的 大写 和 小写 字母。
 */
public class Problem2785 {
    public static void main(String[] args) {
        Problem2785 problem2785 = new Problem2785();
        String s = "lEetcOde";
        System.out.println(problem2785.sortVowels(s));
    }

    /**
     * 模拟
     * 遍历s保存s中的元音字母，对s中的元音字母由小到大的顺序，再次遍历s，
     * 如果当前字符是元音字母，则顺序插入之前排好序的元音字母；如果当前字符不是元音字母，则插入当前字符
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String sortVowels(String s) {
        //大写和小写共10个元音字符的集合
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
            add('A');
            add('E');
            add('I');
            add('O');
            add('U');
        }};

        //存储s中元音字母集合
        List<Character> list = new ArrayList<>();

        for (char c : s.toCharArray()) {
            if (vowelSet.contains(c)) {
                list.add(c);
            }
        }

        //元音字母由小到大的顺序
        list.sort(new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                return c1 - c2;
            }
        });

        //结果数组
        char[] arr = new char[s.length()];
        //list当前下标索引
        int index = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //当前字符c是元音字母，则顺序插入之前排好序的元音字母
            if (vowelSet.contains(c)) {
                arr[i] = list.get(index);
                index++;
            } else {
                //当前字符c不是元音字母，则插入当前字符
                arr[i] = c;
            }
        }

        return new String(arr);
    }
}
