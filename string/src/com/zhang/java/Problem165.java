package com.zhang.java;


/**
 * @Date 2022/3/15 20:13
 * @Author zsy
 * @Description 比较版本号 阿里面试题
 * 给你两个版本号 version1 和 version2 ，请你比较它们。
 * 版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由 多位数字 组成，可能包含 前导零 。
 * 每个版本号至少包含一个字符。
 * 修订号从左到右编号，下标从 0 开始，最左边的修订号下标为 0 ，下一个修订号下标为 1 ，以此类推。
 * 例如，2.5.33 和 0.1 都是有效的版本号。
 * <p>
 * 比较版本号时，请按从左到右的顺序依次比较它们的修订号。
 * 比较修订号时，只需比较 忽略任何前导零后的整数值 。也就是说，修订号 1 和修订号 001 相等 。
 * 如果版本号没有指定某个下标处的修订号，则该修订号视为 0 。
 * 例如，版本 1.0 小于版本 1.1 ，因为它们下标为 0 的修订号相同，而下标为 1 的修订号分别为 0 和 1 ，0 < 1 。
 * <p>
 * 返回规则如下：
 * 如果 version1 > version2 返回 1，
 * 如果 version1 < version2 返回 -1，
 * 除此之外返回 0。
 * <p>
 * 输入：version1 = "1.01", version2 = "1.001"
 * 输出：0
 * 解释：忽略前导零，"01" 和 "001" 都表示相同的整数 "1"
 * <p>
 * 输入：version1 = "1.0", version2 = "1.0.0"
 * 输出：0
 * 解释：version1 没有指定下标为 2 的修订号，即视为 "0"
 * <p>
 * 输入：version1 = "0.1", version2 = "1.1"
 * 输出：-1
 * 解释：version1 中下标为 0 的修订号是 "0"，version2 中下标为 0 的修订号是 "1" 。0 < 1，所以 version1 < version2
 */
public class Problem165 {
    public static void main(String[] args) {
        Problem165 problem165 = new Problem165();
        String version1 = "1.01";
        String version2 = "1.01.22.1";
        System.out.println(problem165.compareVersion(version1, version2));
        System.out.println(problem165.compareVersion2(version1, version2));
    }

    /**
     * 字符串分割，时间复杂度为O(max(m,n))，空间复杂的为O(m+n)
     *
     * @param version1
     * @param version2
     * @return
     */
    public int compareVersion(String version1, String version2) {
        String[] split1 = version1.split("\\.");
        String[] split2 = version2.split("\\.");

        for (int i = 0; i < Math.max(split1.length, split2.length); i++) {
            int value1 = 0;
            int value2 = 0;

            if (i < split1.length) {
                value1 = Integer.parseInt(split1[i]);
            }

            if (i < split2.length) {
                value2 = Integer.parseInt(split2[i]);
            }

            if (value1 > value2) {
                return 1;
            } else if (value1 < value2) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * 双指针，在分割版本号的同时解析出修订号进行比较，时间复杂度为O(max(m,n))，空间复杂的为O(1)
     *
     * @param version1
     * @param version2
     * @return
     */
    public int compareVersion2(String version1, String version2) {
        int i = 0;
        int j = 0;

        while (i < version1.length() || j < version2.length()) {
            int value1 = 0;
            int value2 = 0;

            while (i < version1.length() && version1.charAt(i) != '.') {
                value1 = value1 * 10 + version1.charAt(i) - '0';
                i++;
            }
            //跳过'.'
            i++;

            while (j < version2.length() && version2.charAt(j) != '.') {
                value2 = value2 * 10 + version2.charAt(j) - '0';
                j++;
            }
            //跳过'.'
            j++;

            //比较
            if (value1 > value2) {
                return 1;
            } else if (value1 < value2) {
                return -1;
            }
        }

        return 0;
    }
}
