package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2023/10/18 08:33
 * @Author zsy
 * @Description 破解保险箱 类比Problem89 类比Problem752 欧拉回路类比Problem332 图类比
 * 有一个需要密码才能打开的保险箱。
 * 密码是 n 位数, 密码的每一位都是范围 [0, k - 1] 中的一个数字。
 * 保险箱有一种特殊的密码校验方法，你可以随意输入密码序列，保险箱会自动记住 最后 n 位输入 ，如果匹配，则能够打开保险箱。
 * 例如，正确的密码是 "345" ，并且你输入的是 "012345" ：
 * 输入 0 之后，最后 3 位输入是 "0" ，不正确。
 * 输入 1 之后，最后 3 位输入是 "01" ，不正确。
 * 输入 2 之后，最后 3 位输入是 "012" ，不正确。
 * 输入 3 之后，最后 3 位输入是 "123" ，不正确。
 * 输入 4 之后，最后 3 位输入是 "234" ，不正确。
 * 输入 5 之后，最后 3 位输入是 "345" ，正确，打开保险箱。
 * 在只知道密码位数 n 和范围边界 k 的前提下，请你找出并返回确保在输入的 某个时刻 能够打开保险箱的任一 最短 密码序列 。
 * <p>
 * 输入：n = 1, k = 2
 * 输出："10"
 * 解释：密码只有 1 位，所以输入每一位就可以。"01" 也能够确保打开保险箱。
 * <p>
 * 输入：n = 2, k = 2
 * 输出："01100"
 * 解释：对于每种可能的密码：
 * - "00" 从第 4 位开始输入。
 * - "01" 从第 1 位开始输入。
 * - "10" 从第 3 位开始输入。
 * - "11" 从第 2 位开始输入。
 * 因此 "01100" 可以确保打开保险箱。"01100"、"10011" 和 "11001" 也可以确保打开保险箱。
 * <p>
 * 1 <= n <= 4
 * 1 <= k <= 10
 * 1 <= k^n <= 4096
 */
public class Problem753 {
    public static void main(String[] args) {
        Problem753 problem753 = new Problem753();
        int n = 3;
        int k = 2;
        //0001011100
        System.out.println(problem753.crackSafe(n, k));
    }

    /**
     * dfs欧拉回路
     * 核心思想：从n-1位的0节点出发，经过图中所有边恰好一次，即一笔画问题，得到欧拉回路
     * 欧拉回路：从一个节点发出，经过图中所有边恰好一次，并且能够遍历所有节点的回路(图中所有节点的入度和出度相等，则存在欧拉回路)
     * 长度为n-1的密码共k^(n-1)个，作为图中的节点，每个节点有k条边，分别为0、1、...、k-1，
     * 每个节点沿着一条边能够得到n位数字，即一个n位密码，每条边只能访问一次，标记当前边已访问，继续dfs，
     * 当前边遍历结束时，sb中添加当前边表示的数字，dfs结束，需要补n-1个0，表示从n个0起始，
     * 因为dfs添加边表示的数字时尾添加，所以最后反转sb得到结果
     * 时间复杂度O(k^n)，空间复杂度O(k^n) (k^(n-1)个节点，每个节点k条边，共k^n条边，每条边遍历一次需要O(k^n))
     * (sb长度为k^n+n-1，需要O(k^n)存储结果，图中共k^n条边，边访问集合需要O(k^n)存储边)
     * @param n
     * @param k
     * @return
     */
    public String crackSafe(int n, int k) {
        StringBuilder sb = new StringBuilder();
        //图中边访问集合，长度为n-1的密码共k^(n-1)个，作为图中的节点，每个节点有k条边，共k^n条边
        Set<Integer> visitedSet = new HashSet<>();
        //10^(n-1)，当前节点通过边到达下一个节点，需要模mod得到下一个节点
        //例如：n=3，k=2，当前节点为11，边为0，11*10+0=110，110%100=10，即当前节点11通过边0到达下一个节点10
        int mod = quickPow(10, n - 1);

        //t：当前n-1位数字(不考虑前导0)，以0作为起始节点
        dfs(0, k, mod, sb, visitedSet);

        //结果共k^n+n-1位，dfs得到k^n位，需要添加n-1个0，表示从n个0起始
        for (int i = 0; i < n - 1; i++) {
            //尾添加
            sb.append('0');
        }

        //因为dfs添加边表示的数字时尾添加，所以最后反转sb得到结果
        return sb.reverse().toString();
    }

    private void dfs(int t, int k, int mod, StringBuilder sb, Set<Integer> visitedSet) {
        for (int i = 0; i < k; i++) {
            //当前节点t沿着边得到的n位数字curNum，curNum的低n-1位即为下一个节点，curNum即为当前节点+当前边
            int curNum = t * 10 + i;

            //当前边已经遍历过，则不能重复遍历，直接进行下次循环
            if (visitedSet.contains(curNum)) {
                continue;
            }

            //当前边标记为已访问
            visitedSet.add(curNum);
            //curNum模mod，得到curNum的低n-1位即为下一个节点
            dfs(curNum % mod, k, mod, sb, visitedSet);
            //当前边遍历结束时尾添加，dfs结束之后反转sb，得到欧拉回路
            sb.append(i);
        }

//        //当前节点在邻接节点遍历结束时尾添加，dfs结束之后反转sb，得到欧拉回路
//        sb.append(t);
    }

    private int quickPow(int a, int n) {
        int result = 1;

        while (n != 0) {
            if ((n & 1) == 1) {
                result = result * a;
            }

            a = a * a;
            n = n >>> 1;
        }

        return result;
    }
}
