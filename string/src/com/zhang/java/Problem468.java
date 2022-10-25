package com.zhang.java;

/**
 * @Date 2022/7/7 11:33
 * @Author zsy
 * @Description 验证IP地址 类比Problem93、Offer46
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
//        String queryIP = "2001:db8:85a3:0:0:8A2E:0370:7334";
        String queryIP = "12.12.12";
        System.out.println(problem468.validIPAddress(queryIP));
    }

    /**
     * 模拟，判断每一个ip段是否合法
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param queryIP
     * @return
     */
    public String validIPAddress(String queryIP) {
        if (queryIP == null || queryIP.length() < 7 || queryIP.length() > 39 ||
                (queryIP.contains(".") && queryIP.contains(":"))) {
            return "Neither";
        }

        //ipv4
        if (queryIP.contains(".")) {
            //ipv4长度超过15，即不是合法ipv4
            if (queryIP.length() > 15) {
                return "Neither";
            }

            //当前ipv4段起始索引
            int left = 0;
            //当前ipv4段末尾索引
            int right = 0;
            //当前遍历的ipv4段个数
            int count = 1;

            while (left < queryIP.length()) {
                //遍历到最后一个ipv4段
                if (count == 4) {
                    if (isValidIPv4Segment(queryIP, left, queryIP.length() - 1)) {
                        return "IPv4";
                    } else {
                        return "Neither";
                    }
                }

                while (right < queryIP.length() && queryIP.charAt(right) != '.') {
                    right++;
                }

                if (isValidIPv4Segment(queryIP, left, right - 1)) {
                    left = right + 1;
                    right = right + 1;
                    count++;
                } else {
                    return "Neither";
                }
            }

            //ipv4段个数小于4个，即不是合法ipv4
            return "Neither";
        } else {
            //ipv6

            //ipv6长度小于15，即不是合法ipv6
            if (queryIP.length() < 15) {
                return "Neither";
            }

            //当前ipv6段起始索引
            int left = 0;
            //当前ipv6段末尾索引
            int right = 0;
            //当前遍历的ipv6段个数
            int count = 1;

            while (left < queryIP.length()) {
                //遍历到最后一个ipv6段
                if (count == 8) {
                    if (isValidIPv6Segment(queryIP, left, queryIP.length() - 1)) {
                        return "IPv6";
                    } else {
                        return "Neither";
                    }
                }

                while (right < queryIP.length() && queryIP.charAt(right) != ':') {
                    right++;
                }

                if (isValidIPv6Segment(queryIP, left, right - 1)) {
                    left = right + 1;
                    right = right + 1;
                    count++;
                } else {
                    return "Neither";
                }
            }

            //ipv6段个数小于8个，即不是合法ipv6
            return "Neither";
        }
    }

    /**
     * 判断当前ipv4段是否合法
     *
     * @param queryIP
     * @param left
     * @param right
     * @return
     */
    private boolean isValidIPv4Segment(String queryIP, int left, int right) {
        //ip段长度超过3，即不是合法ipv4段
        if (right - left >= 3) {
            return false;
        }

        //当前ip段为空，即不是合法ipv4段
        if (left > right) {
            return false;
        }

        if (left == right && queryIP.charAt(left) >= '0' && queryIP.charAt(right) <= '9') {
            return true;
        }

        //有前导0
        if (queryIP.charAt(left) == '0') {
            return false;
        }

        int result = 0;

        for (int i = left; i <= right; i++) {
            //非数字的情况
            if (queryIP.charAt(i) > '9' || queryIP.charAt(i) < '0') {
                return false;
            }

            result = result * 10 + queryIP.charAt(i) - '0';
        }

        return result <= 255;
    }

    /**
     * 判断当前ipv6段是否合法
     *
     * @param queryIP
     * @param left
     * @param right
     * @return
     */
    private boolean isValidIPv6Segment(String queryIP, int left, int right) {
        //ip段长度超过4，即不是合法ipv6段
        if (right - left >= 4) {
            return false;
        }

        //当前ip段为空，即不是合法ipv6段
        if (left > right) {
            return false;
        }

        for (int i = left; i <= right; i++) {
            //非法字母，或者'.'，都说明不是合法ipv6段
            if ((queryIP.charAt(i) >= 'G' && queryIP.charAt(i) <= 'Z') ||
                    (queryIP.charAt(i) >= 'g' && queryIP.charAt(i) <= 'z') ||
                    queryIP.charAt(i) == '.') {
                return false;
            }
        }

        return true;
    }
}
