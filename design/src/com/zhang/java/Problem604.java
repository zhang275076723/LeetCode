package com.zhang.java;

/**
 * @Date 2023/5/6 08:30
 * @Author zsy
 * @Description 迭代压缩字符串 类比Problem271、Problem394、Problem443
 * 对于一个压缩字符串，设计一个数据结构，它支持如下两种操作： next 和 hasNext。
 * 给定的压缩字符串格式为：每个字母后面紧跟一个正整数，这个整数表示该字母在解压后的字符串里连续出现的次数。
 * next() - 如果压缩字符串仍然有字母未被解压，则返回下一个字母，否则返回一个空格。
 * hasNext() - 判断是否还有字母仍然没被解压。
 * 注意：
 * 请记得将你的类在 StringIterator 中 初始化 ，因为静态变量或类变量在多组测试数据中不会被自动清空。
 * <p>
 * StringIterator iterator = new StringIterator("L1e2t1C1o1d1e1");
 * iterator.next(); // 返回 'L'
 * iterator.next(); // 返回 'e'
 * iterator.next(); // 返回 'e'
 * iterator.next(); // 返回 't'
 * iterator.next(); // 返回 'C'
 * iterator.next(); // 返回 'o'
 * iterator.next(); // 返回 'd'
 * iterator.hasNext(); // 返回 true
 * iterator.next(); // 返回 'e'
 * iterator.hasNext(); // 返回 false
 * iterator.next(); // 返回 ' '
 */
public class Problem604 {
    public static void main(String[] args) {
        String s = "L1e2t1C1o1d1e1";
        StringIterator iterator = new StringIterator(s);
        // 返回 'L'
        System.out.println(iterator.next());
        // 返回 'e'
        System.out.println(iterator.next());
        // 返回 'e'
        System.out.println(iterator.next());
        // 返回 't'
        System.out.println(iterator.next());
        // 返回 'C'
        System.out.println(iterator.next());
        // 返回 'o'
        System.out.println(iterator.next());
        // 返回 'd'
        System.out.println(iterator.next());
        // 返回 true
        System.out.println(iterator.hasNext());
        // 返回 'e'
        System.out.println(iterator.next());
        // 返回 false
        System.out.println(iterator.hasNext());
        // 返回 ' '
        System.out.println(iterator.next());
    }

    static class StringIterator {
        //压缩字符串
        private final String str;
        //当前遍历到的字符
        private char c;
        //当前遍历到的str下标索引
        private int index;
        //字符c剩余出现的次数
        private int count;

        public StringIterator(String s) {
            str = s;
            index = 0;
            count = 0;
        }

        public char next() {
            if (!hasNext()) {
                return ' ';
            }

            if (count > 0) {
                count--;
                return c;
            }

            //往后找一个字符
            c = str.charAt(index);
            index++;
            //c的次数初始化为0
            count = 0;

            //往后找c剩余出现的次数
            while (index < str.length() && str.charAt(index) >= '0' && str.charAt(index) <= '9') {
                count = count * 10 + str.charAt(index) - '0';
                index++;
            }

            //移除一个c，count减1
            count--;

            return c;
        }

        public boolean hasNext() {
            //当前未遍历到str的末尾，或者当前字符c剩余出现次数大于0，则还有字符未被解压
            return index != str.length() || count > 0;
        }

    }
}
