package com.zhang.java;

/**
 * @Date 2023/5/16 10:04
 * @Author zsy
 * @Description IP地址与int整数的转换 腾讯面试题 类比Problem93、Problem468、Problem751 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65
 * 将ip地址转换成10进制整数。
 * <p>
 * 输入：ip = "10.0.3.193"
 * 输出：167773121
 * 解释：p地址为10.0.3.193，把ip每段拆分成一个二进制形式组合起来为00001010 00000000 00000011 11000001，
 * 然后把这个二进制数转变成十进制整数就是167773121。
 */
public class IPToInt {
    public static void main(String[] args) {
        IPToInt ipToInt = new IPToInt();
        String ip = "10.0.3.193";
        int num = ipToInt.ipToInt(ip);
        String numIp = ipToInt.intToIp(num);
        System.out.println(num);
        System.out.println(numIp);
    }

    /**
     * 位运算
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param ip
     * @return
     */
    public int ipToInt(String ip) {
        int result = 0;
        int index = 0;

        //ip地址只有4段
        for (int i = 0; i < 4; i++) {
            int j = index;

            while (j < ip.length() && ip.charAt(j) != '.') {
                j++;
            }

            int ipSegment = Integer.parseInt(ip.substring(index, j));
            //每次左移8位，表示一个ip段
            result = (result << 8) + ipSegment;
            //跳过当前'.'
            index = j + 1;
        }

        return result;
    }

    /**
     * 位运算
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public String intToIp(int num) {
        //ipv4只有4个ip段
        int[] arr = new int[4];

        for (int i = 3; i >= 0; i--) {
            arr[i] = num & 0xff;
            //每次无符号右移8位，得到一个ip段
            num = num >>> 8;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append(arr[i]).append('.');
        }

        //去除末尾'.'
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }
}
