package com.zhang.java;

/**
 * @Date 2024/4/5 08:43
 * @Author zsy
 * @Description 两个回文子序列长度的最大乘积 类比Problem1723、Problem2305 状态压缩类比 回文类比
 * 给你一个字符串 s ，请你找到 s 中两个 不相交回文子序列 ，使得它们长度的 乘积最大 。
 * 两个子序列在原字符串中如果没有任何相同下标的字符，则它们是 不相交 的。
 * 请你返回两个回文子序列长度可以达到的 最大乘积 。
 * 子序列 指的是从原字符串中删除若干个字符（可以一个也不删除）后，剩余字符不改变顺序而得到的结果。
 * 如果一个字符串从前往后读和从后往前读一模一样，那么这个字符串是一个 回文字符串 。
 * <p>
 * 输入：s = "leetcodecom"
 * 输出：9
 * 解释：最优方案是选择 "ete" 作为第一个子序列，"cdc" 作为第二个子序列。
 * 它们的乘积为 3 * 3 = 9 。
 * <p>
 * 输入：s = "bb"
 * 输出：1
 * 解释：最优方案为选择 "b" （第一个字符）作为第一个子序列，"b" （第二个字符）作为第二个子序列。
 * 它们的乘积为 1 * 1 = 1 。
 * <p>
 * 输入：s = "accbcaxxcxx"
 * 输出：25
 * 解释：最优方案为选择 "accca" 作为第一个子序列，"xxcxx" 作为第二个子序列。
 * 它们的乘积为 5 * 5 = 25 。
 * <p>
 * 2 <= s.length <= 12
 * s 只含有小写英文字母。
 */
public class Problem2002 {
    public static void main(String[] args) {
        Problem2002 problem2002 = new Problem2002();
        String s = "leetcodecom";
        System.out.println(problem2002.maxProduct(s));
    }

    /**
     * 二进制状态压缩
     * state[i]：二进制表示的数i对应s中子序列是否是回文串，如果是回文串，则为回文串的长度，0：当前字符没有出现，1：当前字符出现
     * 对每一个状态i，得到当前状态i的所有子状态j，判断子状态j和状态i^j是否都是回文串，如果都是回文串，则更新两个不相交回文子序列长度的最大乘积
     * 其中，状态i^j为子状态j在状态i中的补集，即子状态j选择状态i中的某些字符，状态i^j为子状态j未选择的剩余字符
     * 时间复杂度O(3^n)，空间复杂度O(2^n)
     * (k个1的二进制访问状态有C(n,k)种，每个k个1的二进制访问状态有2^k个子状态，共∑(C(n,k)*2^k)=∑(C(n,k)*2^k*1^(n-k))=3^n个子状态，二项式定理)
     * <p>
     * 例如：s="leetcodecom"
     * state[01010001000]=state[2^9+2^7+2^3]=state[648]=3，即s中子序列"ete"是回文串，长度为3
     * 状态i=01011011100，即对应s中"etcdec"，如果状态j=01010001000，即选择"ete"，则状态i^j=00001010100，即选择"cdc"，
     * 并且"ete"和"cdc"都是回文串，则"etcdec"可以得到两个不相交的回文串"ete"和"cdc"
     *
     * @param s
     * @return
     */
    public int maxProduct(String s) {
        int n = s.length();

        //二进制表示的数对应s中子序列是否是回文串数组，如果是回文串，则为回文串的长度
        int[] state = new int[1 << n];

        //遍历[0,2^n-1]所有的状态，判断当前状态i对应s中子序列是否是回文串
        for (int i = 0; i < (1 << n); i++) {
            if (isPalindrome(s, i)) {
                //二进制表示的状态i中位1的个数，即二进制表示的状态i对应s中回文串的长度
                int count = 0;
                //当前状态i
                int curState = i;

                //统计状态curState中位1的个数
                while (curState != 0) {
                    count = count + (curState & 1);
                    curState = curState >>> 1;
                }

                state[i] = count;
            }
        }

        //s中两个不相交回文子序列长度的最大乘积
        int max = 0;

        //对每一个状态i，得到当前状态i的所有子状态j，判断子状态j和状态i^j是否都是回文串
        //其中，状态i^j为子状态j在状态i中的补集，即子状态j选择状态i中的某些字符，状态i^j为子状态j未选择的剩余字符
        for (int i = 0; i < (1 << n); i++) {
            //j为二进制状态i的所有子状态
            //(j-1)&i：得到二进制状态i的所有子状态
            for (int j = i; j > 0; j = (j - 1) & i) {
                max = Math.max(max, state[j] * state[i ^ j]);
            }
        }

        return max;
    }

    /**
     * 二进制表示的数state对应s中子序列是否是回文串
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @param state
     * @return
     */
    private boolean isPalindrome(String s, int state) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            //从右往左找state最后一个二进制为1的位置
            //注意：二进制状态state从右往左找对应left
            while (left < right && ((state >>> left) & 1) != 1) {
                left++;
            }

            //从左往右找state第一个二进制为1的位置
            //注意：二进制状态state从左往右找对应right
            while (left < right && ((state >>> right) & 1) != 1) {
                right--;
            }

            //注意：二进制状态state中最低位对应s中右边字符，二进制状态state中最高位对应s中左边字符
            if (s.charAt(s.length() - 1 - left) != s.charAt(s.length() - 1 - right)) {
                return false;
            } else {
                left++;
                right--;
            }
        }

        return true;
    }
}
