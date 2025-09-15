package com.zhang.java;

/**
 * @Date 2022/7/7 11:33
 * @Author zsy
 * @Description 验证IP地址 腾讯面试题 类比Problem93、Problem751、IPToInt
 * 给定一个字符串 queryIP。
 * 如果是有效的 IPv4 地址，返回 "IPv4" ；
 * 如果是有效的 IPv6 地址，返回 "IPv6" ；
 * 如果不是上述类型的 IP 地址，返回 "Neither" 。
 * 有效的IPv4地址 是 “x1.x2.x3.x4” 形式的IP地址。
 * 其中 0 <= xi <= 255 且 xi 不能包含 前导零。
 * 例如: “192.168.1.1” 、 “192.168.1.0” 为有效IPv4地址，
 * “192.168.01.1” 为无效IPv4地址;
 * “192.168.1.00” 、 “192.168@1.1” 为无效IPv4地址。
 * 一个有效的IPv6地址 是一个格式为“x1:x2:x3:x4:x5:x6:x7:x8” 的IP地址，其中:
 * 1 <= xi.length <= 4
 * xi 是一个 十六进制字符串 ，可以包含数字、小写英文字母( 'a' 到 'f' )和大写英文字母( 'A' 到 'F' )。
 * 在 xi 中允许前导零。
 * 例如 "2001:0db8:85a3:0000:0000:8a2e:0370:7334" 和 "2001:db8:85a3:0:0:8A2E:0370:7334" 是有效的 IPv6 地址，
 * 而 "2001:0db8:85a3::8A2E:037j:7334" 和 "02001:0db8:85a3:0000:0000:8a2e:0370:7334" 是无效的 IPv6 地址。
 * <p>
 * 输入：queryIP = "172.16.254.1"
 * 输出："IPv4"
 * 解释：有效的 IPv4 地址，返回 "IPv4"
 * <p>
 * 输入：queryIP = "2001:0db8:85a3:0:0:8A2E:0370:7334"
 * 输出："IPv6"
 * 解释：有效的 IPv6 地址，返回 "IPv6"
 * <p>
 * 输入：queryIP = "256.256.256.256"
 * 输出："Neither"
 * 解释：既不是 IPv4 地址，又不是 IPv6 地址
 * <p>
 * queryIP 仅由英文字母，数字，字符 '.' 和 ':' 组成。
 */
public class Problem468 {
    public static void main(String[] args) {
        Problem468 problem468 = new Problem468();
        String queryIP = "2001:0db8:85a3:0:0:8A2E:0370:7334:";
//        String queryIP = "12.12.12";
        System.out.println(problem468.validIPAddress(queryIP));
    }

    /**
     * 模拟
     * 遍历字符串，确定当前字符串是潜在的ipv4或ipv6，再判断每一个ip段是否合法
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param queryIP
     * @return
     */
    public String validIPAddress(String queryIP) {
        //当前字符串中既包含'.'，又包含':'，则不是合法ip
        if (queryIP == null || queryIP.length() < 7 || queryIP.length() > 39 ||
                (queryIP.contains(".") && queryIP.contains(":"))) {
            return "Neither";
        }

        //ipv4
        if (queryIP.contains(".")) {
            int index = 0;

            //ipv4只有4段
            for (int i = 0; i < 4; i++) {
                int j = index;

                while (j < queryIP.length() && queryIP.charAt(j) != '.') {
                    j++;
                }

                if (!isValidIpv4Segment(queryIP, index, j - 1)) {
                    return "Neither";
                }

                //跳过当前'.'
                index = j + 1;
            }

            //index多右移了1位，需要减1
            index--;

            return index == queryIP.length() ? "IPv4" : "Neither";
        } else {
            //ipv6

            int index = 0;

            //ipv6只有8段
            for (int i = 0; i < 8; i++) {
                int j = index;

                while (j < queryIP.length() && queryIP.charAt(j) != ':') {
                    j++;
                }

                if (!isValidIpv6Segment(queryIP, index, j - 1)) {
                    return "Neither";
                }

                //跳过当前':'
                index = j + 1;
            }

            //index多右移了1位，需要减1
            index--;

            return index == queryIP.length() ? "IPv6" : "Neither";
        }
    }

    /**
     * 判断s[left]-s[right]是否是合法的ipv4段
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private boolean isValidIpv4Segment(String s, int left, int right) {
        //当前ip段为空，或者ip段长度超过3，则不是合法ipv4段
        if (left > right || right - left >= 3) {
            return false;
        }

        //当前ip段长度为1，直接判断是否是0-9的数字
        if (left == right) {
            return s.charAt(left) >= '0' && s.charAt(left) <= '9';
        }

        //有前导0，且长度超过1，则不是合法ip段
        if (s.charAt(left) == '0') {
            return false;
        }

        int ipSegment = 0;

        for (int i = left; i <= right; i++) {
            char c = s.charAt(i);

            //当前字符不是0-9的数字，则不是合法ipv4段
            if (c < '0' || c > '9') {
                return false;
            }

            ipSegment = ipSegment * 10 + (c - '0');
        }

        return ipSegment <= 255;
    }

    /**
     * 判断s[left]-s[right]是否是合法的ipv6段
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private boolean isValidIpv6Segment(String s, int left, int right) {
        //当前ip段为空，或者ip段长度超过4，则不是合法ipv6段
        if (left > right || right - left >= 4) {
            return false;
        }

        for (int i = left; i <= right; i++) {
            char c = s.charAt(i);

            //当前字符不是0-9的数字，或者不是'A'-'G'、'a'-'g'之间字母，则不是合法ipv6段
            if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'))) {
                return false;
            }
        }

        return true;
    }
}
