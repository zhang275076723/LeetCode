package com.zhang.java;

/**
 * @Date 2025/4/20 09:16
 * @Author zsy
 * @Description 将单词恢复初始状态所需的最短时间 II 同Problem3029 kmp类比Problem28、Problem214、Problem459、Problem471、Problem686、Problem796、Problem1392、Problem1408、Problem3029 字符串哈希类比Problem187、Problem1044、Problem1316、Problem1392、Problem1698、Problem3029、Problem3042、Problem3045、Problem3076
 * 给你一个下标从 0 开始的字符串 word 和一个整数 k 。
 * 在每一秒，你必须执行以下操作：
 * 移除 word 的前 k 个字符。
 * 在 word 的末尾添加 k 个任意字符。
 * 注意 添加的字符不必和移除的字符相同。但是，必须在每一秒钟都执行 两种 操作。
 * 返回将 word 恢复到其 初始 状态所需的 最短 时间（该时间必须大于零）。
 * <p>
 * 输入：word = "abacaba", k = 3
 * 输出：2
 * 解释：
 * 第 1 秒，移除 word 的前缀 "aba"，并在末尾添加 "bac" 。因此，word 变为 "cababac"。
 * 第 2 秒，移除 word 的前缀 "cab"，并在末尾添加 "aba" 。因此，word 变为 "abacaba" 并恢复到始状态。
 * 可以证明，2 秒是 word 恢复到其初始状态所需的最短时间。
 * <p>
 * 输入：word = "abacaba", k = 4
 * 输出：1
 * 解释：
 * 第 1 秒，移除 word 的前缀 "abac"，并在末尾添加 "caba" 。因此，word 变为 "abacaba" 并恢复到初始状态。
 * 可以证明，1 秒是 word 恢复到其初始状态所需的最短时间。
 * <p>
 * 输入：word = "abcbabcd", k = 2
 * 输出：4
 * 解释：
 * 每一秒，我们都移除 word 的前 2 个字符，并在 word 末尾添加相同的字符。
 * 4 秒后，word 变为 "abcbabcd" 并恢复到初始状态。
 * 可以证明，4 秒是 word 恢复到其初始状态所需的最短时间。
 * <p>
 * 1 <= word.length <= 10^6
 * 1 <= k <= word.length
 * word仅由小写英文字母组成。
 */
public class Problem3031 {
    public static void main(String[] args) {
        Problem3031 problem3031 = new Problem3031();
        String word = "abacaba";
        int k = 3;
        System.out.println(problem3031.minimumTimeToInitialState(word, k));
        System.out.println(problem3031.minimumTimeToInitialState2(word, k));
    }

    /**
     * kmp
     * 通过kmp得到next数组，next数组得到word匹配的公共前缀和后缀长度，word的公共前缀和后缀长度由大到小遍历，
     * 如果当前公共前缀和后缀长度通过每次移除k个字符得到，则未匹配的剩余字符串长度除以k即为最短时间
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param word
     * @param k
     * @return
     */
    public int minimumTimeToInitialState(String word, int k) {
        int[] next = new int[word.length()];
        int j = 0;

        //注意：i从1开始遍历，因为s[0]-s[0]不存在公共前缀和后缀
        for (int i = 1; i < word.length(); i++) {
            while (j > 0 && word.charAt(i) != word.charAt(j)) {
                j = next[j - 1];
            }

            if (word.charAt(i) == word.charAt(j)) {
                j++;
            }

            next[i] = j;
        }

        //word已经匹配的公共前缀和后缀长度
        int len = next[word.length() - 1];

        while (len > 0) {
            //word中未匹配的剩余长度，即每次移除k个字符的总移除长度
            int remainLen = word.length() - len;

            //remainLen整除k，即经过时间remainLen/k之后，剩余word[remainLen]-word[word.length()-1]和word[0]-word[len-1]匹配，
            //则返回时间remainLen/k
            if (remainLen % k == 0) {
                return remainLen / k;
            }

            //通过next数组，得到下一个word已经匹配的公共前缀和后缀长度
            len = next[len - 1];
        }

        //遍历结束，则返回时间上限(word.length()-1)/k+1
        return (word.length() - 1) / k + 1;
    }

    /**
     * 字符串哈希
     * hash[i]：s[0]-s[i-1]的哈希值
     * prime[i]：p^i的值
     * hash[j+1]-hash[i]*prime[j-i+1]：s[i]-s[j]的哈希值
     * 核心思想：将字符串看成P进制数，再对MOD取余，作为当前字符串的哈希值，只要两个字符串哈希值相等，则认为两个字符串相等
     * 一般取P为较大的质数，P=131或P=13331或P=131313，此时产生的哈希冲突低；
     * 一般取MOD=2^63(long类型最大值+1)，在计算时不处理溢出问题，产生溢出相当于自动对MOD取余；
     * 如果产生哈希冲突，则使用双哈希来减少冲突
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param word
     * @param k
     * @return
     */
    public int minimumTimeToInitialState2(String word, int k) {
        int p = 131;
        long[] hash = new long[word.length() + 1];
        long[] prime = new long[word.length() + 1];

        //p^0初始化
        prime[0] = 1;

        for (int i = 1; i <= word.length(); i++) {
            char c = word.charAt(i - 1);
            //注意：不需要进行取模运算，产生溢出相当于自动对MOD取模
            hash[i] = hash[i - 1] * p + c;
            prime[i] = prime[i - 1] * p;
        }

        //当前时间i得到的字符串是否为word，即word[i*k]-word[word.length()-1]和word[0]-word[word.length()-i*k-1]是否相等
        //注意：最多需要时间(word.length()-1)/k+1就可以遍历完整个word，则i要小于(word.length()-1)/k+1
        for (int i = 1; i < (word.length() - 1) / k + 1; i++) {
            //word[i*k]-word[word.length()-1]的哈希值
            long hash1 = hash[word.length()] - hash[i * k] * prime[word.length() - i * k];
            //word[0]-word[word.length()-i*k-1]的哈希值
            long hash2 = hash[word.length() - i * k] - hash[0] * prime[word.length() - i * k];

            if (hash1 == hash2) {
                return i;
            }
        }

        //遍历结束，则返回时间上限(word.length()-1)/k+1
        return (word.length() - 1) / k + 1;
    }
}
