package com.zhang.java;

/**
 * @Date 2023/5/6 08:30
 * @Author zsy
 * @Description 迭代压缩字符串 类比Problem271、Problem394、Problem443、Problem900 迭代器类比Problem173、Problem251、Problem281、Problem284、Problem341、Problem900、Problem1286、Problem1586
 * 设计并实现一个迭代压缩字符串的数据结构。
 * 给定的压缩字符串的形式是，每个字母后面紧跟一个正整数，表示该字母在原始未压缩字符串中出现的次数。
 * 设计一个数据结构，它支持如下两种操作： next 和 hasNext。
 * next() - 如果原始字符串中仍有未压缩字符，则返回下一个字符，否则返回空格。
 * hasNext() - 如果原始字符串中存在未压缩的的字母，则返回true，否则返回false。
 * <p>
 * 输入：
 * ["StringIterator", "next", "next", "next", "next", "next", "next", "hasNext", "next", "hasNext"]
 * [["L1e2t1C1o1d1e1"], [], [], [], [], [], [], [], [], []]
 * 输出：
 * [null, "L", "e", "e", "t", "C", "o", true, "d", true]
 * 解释：
 * StringIterator stringIterator = new StringIterator("L1e2t1C1o1d1e1");
 * stringIterator.next(); // 返回 "L"
 * stringIterator.next(); // 返回 "e"
 * stringIterator.next(); // 返回 "e"
 * stringIterator.next(); // 返回 "t"
 * stringIterator.next(); // 返回 "C"
 * stringIterator.next(); // 返回 "o"
 * stringIterator.hasNext(); // 返回 True
 * stringIterator.next(); // 返回 "d"
 * stringIterator.hasNext(); // 返回 True
 * <p>
 * 1 <= compressedString.length <= 1000
 * compressedString 由小写字母、大写字母和数字组成。
 * 在 compressedString 中，单个字符的重复次数在 [1,10 ^9] 范围内。
 * next 和 hasNext 的操作数最多为 100 。
 */
public class Problem604 {
    public static void main(String[] args) {
        String s = "L1e2t1C1o1d1e1";
        StringIterator stringIterator = new StringIterator(s);
        // 返回 'L'
        System.out.println(stringIterator.next());
        // 返回 'e'
        System.out.println(stringIterator.next());
        // 返回 'e'
        System.out.println(stringIterator.next());
        // 返回 't'
        System.out.println(stringIterator.next());
        // 返回 'C'
        System.out.println(stringIterator.next());
        // 返回 'o'
        System.out.println(stringIterator.next());
        // 返回 true
        System.out.println(stringIterator.hasNext());
        // 返回 'd'
        System.out.println(stringIterator.next());
        // 返回 true
        System.out.println(stringIterator.hasNext());
        // 返回 'e'
        System.out.println(stringIterator.next());
        // 返回 ' '
        System.out.println(stringIterator.next());
    }

    /**
     * 记录当前遍历到str的下标索引index、当前遍历到的字符c和当前遍历到的字符c剩余出现的次数count
     */
    static class StringIterator {
        //要遍历的字符串
        private final String str;
        //当前遍历到str的下标索引
        private int index;
        //当前遍历到的字符
        private char c;
        //字符c剩余出现的次数
        private int count;

        public StringIterator(String s) {
            str = s;
            index = 0;
            c = 0;
            count = 0;
        }

        public char next() {
            //str中不存在下一个字符，直接返回' '
            if (!hasNext()) {
                return ' ';
            }

            //当前字符c剩余出现的次数大于0，count减1，返回c
            if (count > 0) {
                count--;
                return c;
            }

            //往后找一个字符
            c = str.charAt(index);
            index++;
            //c的次数初始化为0
            count = 0;

            //index右移，确定c剩余出现的次数count
            while (index < str.length() && str.charAt(index) >= '0' && str.charAt(index) <= '9') {
                count = count * 10 + str.charAt(index) - '0';
                index++;
            }

            //移除一个c，count减1
            count--;

            return c;
        }

        public boolean hasNext() {
            //当前未遍历到str的末尾，或者当前字符c剩余出现次数大于0，则还有字符未被解压，返回true；否则，返回false
            return index != str.length() || count > 0;
        }
    }
}
