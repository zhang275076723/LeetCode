package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/8/6 08:53
 * @Author zsy
 * @Description 汉诺塔问题 类比Problem365
 * 在经典汉诺塔问题中，有 3 根柱子及 N 个不同大小的穿孔圆盘，盘子可以滑入任意一根柱子。
 * 一开始，所有盘子自上而下按升序依次套在第一根柱子上(即每一个盘子只能放在更大的盘子上面)。移动圆盘时受到以下限制:
 * (1) 每次只能移动一个盘子;
 * (2) 盘子只能从柱子顶端滑出移到下一根柱子;
 * (3) 盘子只能叠在比它大的盘子上。
 * 请编写程序，用栈将所有盘子从第一根柱子移到最后一根柱子。
 * 你需要原地修改栈。
 * <p>
 * 输入：A = [2, 1, 0], B = [], C = []
 * 输出：C = [2, 1, 0]
 * <p>
 * 输入：A = [1, 0], B = [], C = []
 * 输出：C = [1, 0]
 * <p>
 * A中盘子的数目不大于14个。
 */
public class Interview_08_06 {
    public static void main(String[] args) {
        Interview_08_06 interview_08_06 = new Interview_08_06();
        List<Integer> A = new ArrayList<Integer>() {{
            add(2);
            add(1);
            add(0);
        }};
        List<Integer> B = new ArrayList<>();
        List<Integer> C = new ArrayList<>();
        interview_08_06.hanota(A, B, C);
    }

    /**
     * 递归
     * 将A中盘子看成两部分，最下面1个盘子作为一个整体，A中除了最下面的n-1个盘子作为一个整体，则将A中盘子按照规则移动到C，需要3步：
     * 1、将A中除了最下面的n-1个盘子通过C，移动到B
     * 2、将A中最下面1个盘子通过B，移动到C
     * 3、将B中n-1个盘子通过A，移动到C
     * 时间复杂度O(2^n)，空间复杂度O(n)
     *
     * @param A
     * @param B
     * @param C
     */
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        if (A.size() == 0) {
            return;
        }

        dfs(A.size(), A, B, C);
    }

    /**
     * 将A中n个盘子通过B，移动到C
     *
     * @param n A中盘子的数量
     * @param A
     * @param B
     * @param C
     */
    private void dfs(int n, List<Integer> A, List<Integer> B, List<Integer> C) {
        //A中只有1个盘子需要移动，直接将这1个盘子移动到C中
        if (n == 1) {
            //注意：不能写成A.remove(0)，因为n为1时，A中元素个数不一定为1，所以要移除最上面元素只能通过A.remove(A.size()-1)
            C.add(A.remove(A.size() - 1));
//            System.out.println(A);
//            System.out.println(B);
//            System.out.println(C);
//            System.out.println();
            return;
        }

        //将A中除了最下面的n-1个盘子通过C，移动到B
        dfs(n - 1, A, C, B);
        //将A中最下面1个盘子通过B，移动到C
        dfs(1, A, B, C);
        //将B中n-1个盘子通过A，移动到C
        dfs(n - 1, B, A, C);
    }
}
