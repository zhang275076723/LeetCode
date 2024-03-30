package com.zhang.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2024/3/25 08:56
 * @Author zsy
 * @Description 统计字符串中的元音子字符串 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1371、Problem1456、Problem1704、Problem1839、Problem2063 滑动窗口类比
 * 子字符串 是字符串中的一个连续（非空）的字符序列。
 * 元音子字符串 是 仅 由元音（'a'、'e'、'i'、'o' 和 'u'）组成的一个子字符串，且必须包含 全部五种 元音。
 * 给你一个字符串 word ，统计并返回 word 中 元音子字符串的数目 。
 * <p>
 * 输入：word = "aeiouu"
 * 输出：2
 * 解释：下面列出 word 中的元音子字符串（斜体加粗部分）：
 * - "aeiouu"
 * - "aeiouu"
 * <p>
 * 输入：word = "unicornarihan"
 * 输出：0
 * 解释：word 中不含 5 种元音，所以也不会存在元音子字符串。
 * <p>
 * 输入：word = "cuaieuouac"
 * 输出：7
 * 解释：下面列出 word 中的元音子字符串（斜体加粗部分）：
 * - "cuaieuouac"
 * - "cuaieuouac"
 * - "cuaieuouac"
 * - "cuaieuouac"
 * - "cuaieuouac"
 * - "cuaieuouac"
 * - "cuaieuouac"
 * <p>
 * 输入：word = "bbaeixoubb"
 * 输出：0
 * 解释：所有包含全部五种元音的子字符串都含有辅音，所以不存在元音子字符串。
 * <p>
 * 1 <= word.length <= 100
 * word 仅由小写英文字母组成
 */
public class Problem2062 {
    public static void main(String[] args) {
        Problem2062 problem2062 = new Problem2062();
        String word = "cuaieuouac";
        System.out.println(problem2062.countVowelSubstrings(word));
        System.out.println(problem2062.countVowelSubstrings2(word));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param word
     * @return
     */
    public int countVowelSubstrings(String word) {
        //只包含元音字母，并且包含5种元音字母的字符串个数
        int count = 0;
        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        for (int i = 0; i < word.length(); i++) {
            //从i起始的字符串中包含的元音集合
            Set<Character> set = new HashSet<>();

            for (int j = i; j < word.length(); j++) {
                char c = word.charAt(j);

                //当前字符不是元音，直接跳出循环
                if (!vowelSet.contains(c)) {
                    break;
                }

                set.add(c);

                if (set.size() == 5) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 滑动窗口
     * 滑动窗口分割word得到只包含元音字母的字符串，滑动窗口找每个只包含元音字母的字符串中最小的只包含5种元音的字符串区间[l,r]，
     * 则[0,r]、[1,r]...[l-1,r]、[l,r]均为包含5种元音的字符串，即l+1个字符串满足要求
     * 时间复杂度O(mn)=O(n)，空间复杂度O(1) (n=word.length，m=word分割得到只包含元音字母的字符串的个数，m的个数应该远远小于n)
     *
     * @param word
     * @return
     */
    public int countVowelSubstrings2(String word) {
        //只包含元音字母，并且包含5种元音字母的字符串个数
        int count = 0;
        //滑动窗口左右下标索引，用于分割word得到只包含元音字母的字符串
        int left = 0;
        int right = 0;

        //5个小写元音字符的集合，不需要包含大写元音字母
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
        }};

        //滑动窗口分割word得到只包含元音字母的字符串
        while (right < word.length()) {
            char c = word.charAt(right);

            //当前字符c不是元音，更新left、right，进行下次循环
            if (!vowelSet.contains(c)) {
                right++;
                left = right;
                continue;
            }

            //[left,right]为只包含元音字母的字符串，滑动窗口找[left,right]中最小的只包含5种元音的字符串区间[l,r]，
            //则[left,r]、[left+1,r]...[l-1,r]、[l,r]均为包含5种元音的字符串，即l-left+1个字符串满足要求
            if (right == word.length() - 1 || !vowelSet.contains(word.charAt(right + 1))) {
                //[left,right]中最小的只包含5种元音的字符串区间[l,r]
                int l = left;
                int r = left;
                //key：元音字母，value：元音字母出现的次数
                Map<Character, Integer> map = new HashMap<>();

                //滑动窗口找[left,right]中最小的只包含5种元音的字符串区间[l,r]
                while (r <= right) {
                    map.put(word.charAt(r), map.getOrDefault(word.charAt(r), 0) + 1);

                    //[l,r]中字符word[l]出现次数大于1，则[l,r]不是最小的只包含5种元音的字符串区间，l右移
                    while (map.get(word.charAt(l)) > 1) {
                        map.put(word.charAt(l), map.get(word.charAt(l)) - 1);
                        l++;
                    }

                    //map中有5种元音字母，则[left,r]、[left+1,r]...[l-1,r]、[l,r]均为包含5种元音的字符串，即l-left+1个字符串满足要求
                    if (map.size() == 5) {
                        count = count + l - left + 1;
                    }

                    r++;
                }
            }

            right++;
        }

        return count;
    }
}
