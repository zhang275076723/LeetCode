package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2023/10/1 08:25
 * @Author zsy
 * @Description 重复的DNA序列 哈希表类比Problem242、Problem383、Problem387、Problem389、Problem554、Problem763、Problem1640、Offer50 位运算类比
 * DNA序列 由一系列核苷酸组成，缩写为 'A', 'C', 'G' 和 'T'.。
 * 例如，"ACGAATTCCG" 是一个 DNA序列 。
 * 在研究 DNA 时，识别 DNA 中的重复序列非常有用。
 * 给定一个表示 DNA序列 的字符串 s ，返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)。
 * 你可以按 任意顺序 返回答案。
 * <p>
 * 输入：s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * 输出：["AAAAACCCCC","CCCCCAAAAA"]
 * <p>
 * 输入：s = "AAAAAAAAAAAAA"
 * 输出：["AAAAAAAAAA"]
 * <p>
 * 0 <= s.length <= 10^5
 * s[i]=='A'、'C'、'G' or 'T'
 */
public class Problem187 {
    public static void main(String[] args) {
        Problem187 problem187 = new Problem187();
        String s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT";
        System.out.println(problem187.findRepeatedDnaSequences(s));
        System.out.println(problem187.findRepeatedDnaSequences2(s));
    }

    /**
     * 哈希表
     * 得到s中长度为10的所有字符串，加入哈希表中，如果哈希表中当前字符串出现次数为2，则是第一次重复出现，加入结果集合中
     * 时间复杂度O(Cn)，空间复杂度O(Cn) (C：字符串的长度，C=10)
     *
     * @param s
     * @return
     */
    public List<String> findRepeatedDnaSequences(String s) {
        if (s.length() < 10) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();
        //key：长度为10的字符串，value：当前字符串出现的次数
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i <= s.length() - 10; i++) {
            String str = s.substring(i, i + 10);
            map.put(str, map.getOrDefault(str, 0) + 1);

            //当前字符串第一次重复出现，加入list集合中，避免重复添加
            if (map.get(str) == 2) {
                list.add(str);
            }
        }

        return list;
    }

    /**
     * 哈希表+位运算
     * 通过s.substring()获取字符串需要O(10)，将长度为10的字符串用二进制形式表示需要O(1)，
     * 字符共4种情况，每个字符需要2bit表示，则长度为10的字符串需要20bit来表示
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public List<String> findRepeatedDnaSequences2(String s) {
        if (s.length() < 10) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();
        //key：长度为10的字符串的二进制表示，value：当前字符串出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        //key：字符串中的字符，value：字符共4种情况，每个字符需要2bit表示
        Map<Character, Integer> character2BitMap = new HashMap<Character, Integer>() {{
            put('A', 0);
            put('C', 1);
            put('G', 2);
            put('T', 3);
        }};

        //当前字符串的二进制表示
        int key = 0;

        for (int i = 0; i < 9; i++) {
            key = (key << 2) + character2BitMap.get(s.charAt(i));
        }

        for (int i = 0; i <= s.length() - 10; i++) {
            //低20位作为当前字符串的二进制表示
            key = ((key << 2) + character2BitMap.get(s.charAt(i + 9))) & ((1 << 20) - 1);
            map.put(key, map.getOrDefault(key, 0) + 1);

            //当前字符串第一次重复出现，加入list集合中，避免重复添加
            if (map.get(key) == 2) {
                list.add(s.substring(i, i + 10));
            }
        }

        return list;
    }
}