package com.zhang.java;

/**
 * @Date 2022/6/24 8:14
 * @Author zsy
 * @Description 字符串相乘 类比Problem415
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 * 注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。
 * <p>
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 * <p>
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 * <p>
 * 1 <= num1.length, num2.length <= 200
 * num1 和 num2 只能由数字组成。
 * num1 和 num2 都不包含任何前导零，除了数字0本身。
 */
public class Problem43 {
    public static void main(String[] args) {
        Problem43 problem43 = new Problem43();
        String num1 = "123";
        String num2 = "456";
        System.out.println(problem43.multiply(num1, num2));
        System.out.println(problem43.multiply2(num1, num2));
    }

    /**
     * 手动乘法模拟
     * num1与num2的每一位相乘并累加
     * 时间复杂度O(mn+n^2)，空间复杂度O(m+n) (m=num1.length, n=num2.length) (num1和num2乘积最大长度为m+n)
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0) {
            return null;
        }

        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }

        String result = "0";
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (j >= 0) {
            //num1和num2[j]相乘的结果
            StringBuilder sb = new StringBuilder();

            //补0
            for (int k = 0; k < num2.length() - j - 1; k++) {
                sb.append(0);
            }

            //当前位的进位
            int carry = 0;
            int n2 = num2.charAt(j) - '0';

            //num1和num2[j]相乘
            while (i >= 0) {
                int n1 = num1.charAt(i) - '0';
                int temp = n1 * n2 + carry;
                sb.append(temp % 10);
                carry = temp / 10;
                i--;
            }

            //i重新指向num1末尾元素
            i = num1.length() - 1;

            //如果最高位进位不为0，则需要添加最高位的进位
            if (carry != 0) {
                sb.append(carry);
            }

            //num1和num2[j]相乘的结果进行累加，因为sb是尾添加，所以需要反转
            result = addString(result, sb.reverse().toString());

            j--;
        }

        return result;
    }

    /**
     * 手动乘法模拟优化
     * num1和num2乘积最大长度为m+n，num1每一位分别于num2每一位相乘，分别放到结果数组中
     * 例如：num1=123，num2=45，结果数组长度为5
     * num1的3与num2的5相乘为15，结果数组为  [0,0,0,1,5]
     * num1的2与num2的5相乘为10，结果数组为  [0,0,1,1,5]
     * num1的1与num2的5相乘为5，结果数组为   [0,0,6,1,5]
     * num1的3与num2的4相乘为12，结果数组为  [0,0,7,3,5]
     * num1的2与num2的4相乘为8，结果数组为   [0,1,5,3,5]
     * num1的1与num2的4相乘为4，结果数组为   [0,5,5,3,5]
     * 时间复杂度O(mn)，空间复杂度O(m+n) (m=num1.length, n=num2.length) (num1和num2乘积最大长度为m+n)
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply2(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0) {
            return null;
        }

        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }

        int[] result = new int[num1.length() + num2.length()];

        for (int j = num2.length() - 1; j >= 0; j--) {
            //num2[j]的值
            int n2 = num2.charAt(j) - '0';

            for (int i = num1.length() - 1; i >= 0; i--) {
                //num2[i]的值
                int n1 = num1.charAt(i) - '0';
                //num2[j]与num1[i]乘积
                int temp = n1 * n2 + result[j + i + 1];
                //当前位
                result[j + i + 1] = temp;
            }
        }

        //有可能存在当前位进位还有进位的情况，即结果数组中每个元素有可能大于9
        for (int i = result.length - 1; i > 0; i--) {
            if (result[i] > 9) {
                //进位
                result[i - 1] = result[i - 1] + result[i] / 10;
                //当前位
                result[i] = result[i] % 10;
            }
        }

        StringBuilder sb = new StringBuilder();

        if (result[0] != 0) {
            sb.append(result[0]);
        }

        for (int i = 1; i < result.length; i++) {
            sb.append(result[i]);
        }

        return sb.toString();
    }

    /**
     * 两个字符串相加
     * 时间复杂度O(max(m,n))，空间复杂度O(max(m,n))
     *
     * @param num1
     * @param num2
     * @return
     */
    private String addString(String num1, String num2) {
        StringBuilder sb = new StringBuilder();

        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int carry = 0;

        while (i >= 0 && j >= 0) {
            int cur = (num1.charAt(i) - '0') + (num2.charAt(j) - '0') + carry;
            sb.append(cur % 10);
            carry = cur / 10;

            i--;
            j--;
        }

        while (i >= 0) {
            int cur = num1.charAt(i) - '0' + carry;
            sb.append(cur % 10);
            carry = cur / 10;
            i--;
        }

        while (j >= 0) {
            int cur = num2.charAt(j) - '0' + carry;
            sb.append(cur % 10);
            carry = cur / 10;
            j--;
        }

        //如果最高位进位为1，则需要添加最高位为1
        if (carry == 1) {
            sb.append(1);
        }

        //因为是尾添加，所以需要反转
        return sb.reverse().toString();
    }
}
