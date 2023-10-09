package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/6/18 11:50
 * @Author zsy
 * @Description 格雷编码 模拟类比Problem38 类比Problem753 回溯+剪枝类比Problem17、Problem22、Problem39、Problem40、Problem46、Problem47、Problem77、Problem78、Problem90、Problem97、Problem216、Problem301、Problem377、Problem491、Problem679、Problem698、Offer17、Offer38
 * n 位格雷码序列 是一个由 2^n 个整数组成的序列，其中：
 * 每个整数都在范围 [0, 2^n - 1] 内（含 0 和 2^n - 1）
 * 第一个整数是 0
 * 一个整数在序列中出现 不超过一次
 * 每对 相邻 整数的二进制表示 恰好一位不同 ，且
 * 第一个 和 最后一个 整数的二进制表示 恰好一位不同
 * 给你一个整数 n ，返回任一有效的 n 位格雷码序列 。
 * <p>
 * 输入：n = 2
 * 输出：[0,1,3,2]
 * 解释：
 * [0,1,3,2] 的二进制表示是 [00,01,11,10] 。
 * - 00 和 01 有一位不同
 * - 01 和 11 有一位不同
 * - 11 和 10 有一位不同
 * - 10 和 00 有一位不同
 * [0,2,3,1] 也是一个有效的格雷码序列，其二进制表示是 [00,10,11,01] 。
 * - 00 和 10 有一位不同
 * - 10 和 11 有一位不同
 * - 11 和 01 有一位不同
 * - 01 和 00 有一位不同
 * <p>
 * 输入：n = 1
 * 输出：[0,1]
 * <p>
 * 1 <= n <= 16
 */
public class Problem89 {
    public static void main(String[] args) {
        Problem89 problem89 = new Problem89();
        int n = 3;
        System.out.println(problem89.grayCode(n));
        System.out.println(problem89.grayCode2(n));
        System.out.println(problem89.grayCode3(n));
    }

    /**
     * 模拟
     * 从n=1开始，根据n位格雷码得到n+1位格雷码，
     * n+1位格雷码中，前2^n位格雷码是正序n位格雷码最高位补0，后2^n位格雷码是逆序n位格雷码最高位补1
     * 例如：n=2，4位格雷码为：00、01、11、10
     * n=3，前4位格雷码为：0(00)，0(01)，0(11)，0(10)，后4位格雷码为：1(10)，1(11)，1(01)，1(00)
     * 时间复杂度O(2^n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {
        List<Integer> list = new ArrayList<>();
        //list初始化，n=0时，格雷码只有一个0
        list.add(0);

        for (int i = 1; i <= n; i++) {
            //i位格雷码的后2^(i-1)位是逆序i-1位格雷码最高位补1
            for (int j = list.size() - 1; j >= 0; j--) {
                list.add((1 << (i - 1)) | list.get(j));
            }
        }

        return list;
    }

    /**
     * 公式
     * 由n位二进制码得到n位格雷码，
     * 保留二进制码的最高位作为格雷码的最高位，二进制码的最高位和次高位异或，作为格雷码的次高位，以此类推，得到格雷码
     * 例如；n=4，二进制码1001，对应的格雷码为1101
     * < 1  0  0  1 (二进制码)
     * < 0  1  0  0 (二进制码无符号右移一位)
     * < ^          (异或)
     * < 1  1  0  1 (格雷码)
     * 时间复杂度O(2^n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public List<Integer> grayCode2(int n) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < (1 << n); i++) {
            //i和i无符号右移1位进行异或，得到当前二进制码对应的格雷码
            list.add(i ^ (i >>> 1));
        }

        return list;
    }

    /**
     * 回溯+剪枝
     * 例如：n=3
     * <                 根节点
     * <              /        \
     * <            /           \
     * <          /              \
     * <        0                 1
     * <      /   \             /   \
     * <     0     1           1     0
     * <   /  \   / \        /  \   / \
     * <  0   1  1   0      0   1  1   0
     * < 000 001 011 010   110 111 101 100
     * 时间复杂度O(2^n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public List<Integer> grayCode3(int n) {
        List<Integer> list = new ArrayList<>();

        //flag标志位表示当前节点是左子树还是右子树，0：当前节点是左子树，1：当前节点是右子树
        //左子树的子节点添加顺序为0、1；右子树的子节点添加顺序为1、0
        backtrack(0, n, 0, new StringBuilder(), list);

        return list;
    }

    private void backtrack(int t, int n, int flag, StringBuilder sb, List<Integer> list) {
        if (t == n) {
            int num = 0;

            //二进制sb转换为十进制数字保存到list中
            for (int i = 0; i < n; i++) {
                num = (num << 1) + (sb.charAt(i) - '0');
            }

            list.add(num);
            return;
        }

        //当前节点是左子树，子节点添加顺序为0、1
        if (flag == 0) {
            sb.append(0);
            //flag为0，表示左子树
            backtrack(t + 1, n, 0, sb, list);
            sb.delete(sb.length() - 1, sb.length());

            sb.append(1);
            //flag为1，表示右子树
            backtrack(t + 1, n, 1, sb, list);
            sb.delete(sb.length() - 1, sb.length());
        } else {
            //当前节点是右子树，子节点添加顺序为1、0
            sb.append(1);
            //flag为0，表示左子树
            backtrack(t + 1, n, 0, sb, list);
            sb.delete(sb.length() - 1, sb.length());

            sb.append(0);
            //flag为1，表示右子树
            backtrack(t + 1, n, 1, sb, list);
            sb.delete(sb.length() - 1, sb.length());
        }
    }
}
