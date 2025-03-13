package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2023/10/1 08:25
 * @Author zsy
 * @Description 重复的DNA序列 类比Problem1461、Problem1684、Problem2506 字符串哈希类比Problem1044、Problem1316、Problem1392、Problem1698、Problem3029、Problem3031、Problem3042、Problem3045 状态压缩类比Problem294、Problem464、Problem473、Problem526、Problem638、Problem698、Problem847、Problem1723、Problem1908、Problem2305 哈希表类比Problem1、Problem128、Problem166、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem609、Problem763、Problem1500、Problem1640、Problem2657、Offer50
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
        System.out.println(problem187.findRepeatedDnaSequences3(s));
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
     * 哈希表+二进制状态压缩
     * 通过s.substring()获取字符串需要O(10)，将长度为10的字符串用二进制形式表示需要O(1)，
     * DNA中的字符只有'A'、'C'、'G'、'T'4种情况，每个字符需要2bit表示，则长度为10的字符串需要20bit来表示，即int就能表示长度为10的序列
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
        //key：长度为10的序列的二进制表示，value：当前长度为10的序列出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        //key：DNA中的字符，value：DNA中的字符共4种情况，每个字符需要2bit表示
        Map<Character, Integer> character2BitMap = new HashMap<Character, Integer>() {{
            put('A', 0);
            put('C', 1);
            put('G', 2);
            put('T', 3);
        }};

        //当前长度为10的字符串二进制压缩表示
        int key = 0;

        //初始化前9个字符，之后每次将新的1个字符加入key，低20位作为当前长度为10的字符串二进制压缩表示
        for (int i = 0; i < 9; i++) {
            char c = s.charAt(i);
            key = (key << 2) + character2BitMap.get(c);
        }

        for (int i = 0; i <= s.length() - 10; i++) {
            char c = s.charAt(i + 9);
            //每次将新的1个字符加入key，低20位作为当前长度为10的字符串二进制压缩表示
            key = ((key << 2) + character2BitMap.get(c)) & ((1 << 20) - 1);
            map.put(key, map.getOrDefault(key, 0) + 1);

            //当前字符串第一次重复出现，加入list集合中，避免重复添加
            if (map.get(key) == 2) {
                list.add(s.substring(i, i + 10));
            }
        }

        return list;
    }

    /**
     * 字符串哈希
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public List<String> findRepeatedDnaSequences3(String s) {
        //大质数，p进制
        int p = 131;
        long[] hash = new long[s.length() + 1];
        long[] prime = new long[s.length() + 1];

        //p^0初始化
        prime[0] = 1;

        for (int i = 1; i <= s.length(); i++) {
            char c = s.charAt(i - 1);
            //注意：不需要进行取模运算，产生溢出相当于自动对MOD取模
            hash[i] = hash[i - 1] * p + c;
            prime[i] = prime[i - 1] * p;
        }

        List<String> list = new ArrayList<>();
        //key：s中长度为10的字符串的哈希值，value：当前长度为10的字符串的哈希值出现的次数
        Map<Long, Integer> map = new HashMap<>();

        for (int i = 0; i <= s.length() - 10; i++) {
            //s[i]-s[i+9]的哈希值
            //hash[i]乘以prime[10]相当于hash[i]在p进制情况下左移10位
            long h = hash[i + 10] - hash[i] * prime[10];
            map.put(h, map.getOrDefault(h, 0) + 1);

            //当前字符串第一次重复出现，加入list集合中，避免重复添加
            if (map.get(h) == 2) {
                list.add(s.substring(i, i + 10));
            }
        }

        return list;
    }
}
