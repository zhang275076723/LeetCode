package com.zhang.java;

/**
 * @Date 2024/7/2 08:58
 * @Author zsy
 * @Description 将二进制表示减到 1 的步骤数 类比Problem397、Problem453、Problem991、Problem1342、Problem2139、Problem2571
 * 给你一个以二进制形式表示的数字 s 。
 * 请你返回按下述规则将其减少到 1 所需要的步骤数：
 * 如果当前数字为偶数，则将其除以 2 。
 * 如果当前数字为奇数，则将其加上 1 。
 * 题目保证你总是可以按上述规则将测试用例变为 1 。
 * <p>
 * 输入：s = "1101"
 * 输出：6
 * 解释："1101" 表示十进制数 13 。
 * Step 1) 13 是奇数，加 1 得到 14
 * Step 2) 14 是偶数，除 2 得到 7
 * Step 3) 7  是奇数，加 1 得到 8
 * Step 4) 8  是偶数，除 2 得到 4
 * Step 5) 4  是偶数，除 2 得到 2
 * Step 6) 2  是偶数，除 2 得到 1
 * <p>
 * 输入：s = "10"
 * 输出：1
 * 解释："10" 表示十进制数 2 。
 * Step 1) 2 是偶数，除 2 得到 1
 * <p>
 * 输入：s = "1"
 * 输出：0
 * <p>
 * 1 <= s.length <= 500
 * s 由字符 '0' 或 '1' 组成。
 * s[0] == '1'
 */
public class Problem1404 {
    public static void main(String[] args) {
        Problem1404 problem1404 = new Problem1404();
        String s = "1101";
        System.out.println(problem1404.numSteps(s));
        System.out.println(problem1404.numSteps2(s));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int numSteps(String s) {
        char[] arr = s.toCharArray();
        int index = arr.length - 1;
        int count = 0;

        //s[0]为1，不需要遍历s[0]
        while (index > 0) {
            //当前位为0，则当前数字为偶数，除以2，相当于左移1位
            if (arr[index] == '0') {
                count++;
                index--;
            } else {
                //当前位为1，则当前数字为奇数，加上1，相当于从末尾开始往前的连续1变为0，连续1的前一个0变为1

                //从末尾开始往前的连续1的前一个0下标索引
                int i = index;

                while (i >= 0 && arr[i] == '1') {
                    arr[i] = '0';
                    i--;
                }

                //当前数字全部为1，则直接返回当前数字变为1的步骤数
                if (i < 0) {
                    return count + index + 2;
                }

                //arr[i]由0变为1
                arr[i] = '1';
                count++;
            }
        }

        return count;
    }

    /**
     * 模拟优化
     * 当前位为0，则当前数字为偶数，除以2，相当于左移1位，需要1次操作；
     * 当前位为1，则当前数字为奇数，加上1，相当于从末尾开始往前的连续1变为0，连续1的前一个0变为1，
     * 再将这些连续0移除，需要(j-i+2)次操作 (s[i]-s[j]为末尾连续1)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public int numSteps2(String s) {
        char[] arr = s.toCharArray();
        int index = arr.length - 1;
        int count = 0;

        //s[0]为1，不需要遍历s[0]
        while (index > 0) {
            //当前位为0，则当前数字为偶数，除以2，相当于左移1位
            if (arr[index] == '0') {
                count++;
                index--;
            } else {
                //当前位为1，则当前数字为奇数，加上1，相当于从末尾开始往前的连续1变为0，连续1的前一个0变为1，
                //再将这些连续0移除，需要(j-i+1)次操作 (s[i]为连续1的前一个0，s[j]为末尾的1)

                //从末尾开始往前的连续1的前一个0下标索引
                int i = index;

                while (i >= 0 && arr[i] == '1') {
                    i--;
                }

                //当前数字全部为1，则直接返回当前数字变为1的步骤数
                if (i < 0) {
                    return count + index + 2;
                }

                //arr[i]由0变为1
                arr[i] = '1';
                count = count + (index - i + 1);
                index = i;
            }
        }

        return count;
    }
}
