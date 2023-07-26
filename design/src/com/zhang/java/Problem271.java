package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/5/6 08:20
 * @Author zsy
 * @Description 字符串的编码与解码 移位运算类比IPToInt 类比Problem394、Problem443、Problem604 序列化类比Problem297、Problem331、Problem449、Offer37
 * 请你设计一个算法，可以将一个 字符串列表 编码成为一个 字符串。
 * 这个编码后的字符串是可以通过网络进行高效传送的，并且可以在接收端被解码回原来的字符串列表。
 * 1 号机（发送方）有如下函数：
 * string encode(vector<string> strs) {
 * // ... your code
 * return encoded_string;
 * }
 * 2 号机（接收方）有如下函数：
 * vector<string> decode(string s) {
 * //... your code
 * return strs;
 * }
 * 1 号机（发送方）执行：
 * string encoded_string = encode(strs);
 * 2 号机（接收方）执行：
 * vector<string> strs2 = decode(encoded_string);
 * 此时，2 号机（接收方）的 strs2 需要和 1 号机（发送方）的 strs 相同。
 * 请你来实现这个 encode 和 decode 方法。
 * <p>
 * 输入：dummy_input = ["Hello","World"]
 * 输出：["Hello","World"]
 * 解释：
 * 1 号机：
 * Codec encoder = new Codec();
 * String msg = encoder.encode(strs);
 * 1 号机 ---msg---> 2 号机
 * 2 号机：
 * Codec decoder = new Codec();
 * String[] strs = decoder.decode(msg);
 * <p>
 * 输入：dummy_input = [""]
 * 输出：[""]
 * <p>
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * 因为字符串可能会包含 256 个合法 ascii 字符中的任何字符，所以您的算法必须要能够处理任何可能会出现的字符。
 * 请勿使用 “类成员”、“全局变量” 或 “静态变量” 来存储这些状态，您的编码和解码算法应该是非状态依赖的。
 * 请不要依赖任何方法库，例如 eval 又或者是 serialize 之类的方法。本题的宗旨是需要您自己实现 “编码” 和 “解码” 算法。
 */
public class Problem271 {
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        strs.add("abcfe");
        strs.add("uv");
        strs.add("hij");
//        Codec encoder = new Codec();
//        Codec decoder = new Codec();
        Codec2 encoder = new Codec2();
        Codec2 decoder = new Codec2();
        String msg = encoder.encode(strs);
        List<String> result = decoder.decode(msg);
        System.out.println(msg);
        System.out.println(result);
    }

    /**
     * 使用分隔符
     */
    static class Codec {
        //分隔符，每个字符串可能包含256个有效的ASCII码字符，所以用非ASCII字符作为分隔符
        private final char DELIMITER = '#';

        /**
         * 先拼接当前字符串，再拼接分隔符
         * 例如：["abcfe","uv","hij"]编码为abcfe#uv#hij
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param strs
         * @return
         */
        public String encode(List<String> strs) {
            if (strs == null || strs.size() == 0) {
                return "";
            }

            StringBuilder sb = new StringBuilder();

            for (String str : strs) {
                sb.append(str).append(DELIMITER);
            }

            //去除末尾分隔符'#'
            return sb.delete(sb.length() - 1, sb.length()).toString();
        }

        /**
         * 遍历编码后的字符串，根据分隔符得到每个字符串
         * 例如：abcfe#uv#hij解码为["abcfe","uv","hij"]
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param s
         * @return
         */
        public List<String> decode(String s) {
            if (s == null || s.length() == 0) {
                return new ArrayList<>();
            }

            List<String> list = new ArrayList<>();
            int left = 0;
            int right = 0;

            while (left < s.length()) {
                while (right < s.length() && s.charAt(right) != DELIMITER) {
                    right++;
                }

                list.add(s.substring(left, right));
                left = right + 1;
                right = left;
            }

            return list;
        }
    }

    /**
     * 分块编码，使用4字节表示每个字符串的长度，即最大能够表示长度为2^32-1的字符串 (HTTP1.1采用就是分块编码)
     */
    static class Codec2 {
        /**
         * 拼接4字节的当前字符串长度(16进制)，再拼接当前字符串
         * 例如：["abcfe","uv","hij"]编码为0x00000005abcfe0x00000002uv0x00000003hij，其中0x00000005是16进制表示的4字节数
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param strs
         * @return
         */
        public String encode(List<String> strs) {
            if (strs == null || strs.size() == 0) {
                return "";
            }

            StringBuilder sb = new StringBuilder();

            for (String str : strs) {
                //当前字符串的长度
                int len = str.length();
                //4字节的字符串长度数组
                byte[] arr = new byte[4];

                for (int i = 3; i >= 0; i--) {
                    arr[i] = (byte) (len & 0xff);
                    //每次往右移8位，即移动一个字节的长度
                    len = len >>> 8;
                }

                //先拼接4字节的当前字符串长度，再拼接当前字符串
                sb.append(new String(arr)).append(str);
            }

            return sb.toString();
        }

        /**
         * 遍历编码后的字符串，每次读取4字节的字符串长度，再根据长度往后读取字符串
         * 例如：0x00000005abcfe0x00000002uv0x00000003hij解码为["abcfe","uv","hij"]，其中0x00000005是16进制表示的4字节数
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param s
         * @return
         */
        public List<String> decode(String s) {
            if (s == null || s.length() == 0) {
                return new ArrayList<>();
            }

            List<String> list = new ArrayList<>();
            int index = 0;

            while (index < s.length()) {
                //4字节的字符串长度数组
                byte[] arr = s.substring(index, index + 4).getBytes();
                //当前字符串的长度
                int len = 0;
                index = index + 4;

                for (int i = 0; i < 4; i++) {
                    //每次往左移8位，即移动一个字节的长度
                    len = (len << 8) + arr[i];
                }

                list.add(s.substring(index, index + len));
                index = index + len;
            }

            return list;
        }
    }
}
