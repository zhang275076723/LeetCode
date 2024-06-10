package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2023/5/22 08:40
 * @Author zsy
 * @Description 验证二叉树的前序序列化 入度出度类比类比Problem207、Problem210、Problem685、Problem1361 类比Problem271、Problem297、Problem449、Problem535、Problem625、Problem820、Problem1948、Offer37 栈类比Problem20、Problem71、Problem150、Problem224、Problem227、Problem341、Problem394、Problem678、Problem856、Problem946、Problem1003、Problem1047、Problem1096、Offer31、CharacterToInteger
 * 序列化二叉树的一种方法是使用 前序遍历 。
 * 当我们遇到一个非空节点时，我们可以记录下这个节点的值。
 * 如果它是一个空节点，我们可以使用一个标记值记录，例如 #。
 * 例如，上面的二叉树可以被序列化为字符串 "9,3,4,#,#,1,#,#,2,#,6,#,#"，其中 # 代表一个空节点。
 * 给定一串以逗号分隔的序列，验证它是否是正确的二叉树的前序序列化。编写一个在不重构树的条件下的可行算法。
 * 保证 每个以逗号分隔的字符或为一个整数或为一个表示 null 指针的 '#' 。
 * 你可以认为输入格式总是有效的
 * 例如它永远不会包含两个连续的逗号，比如 "1,,3" 。
 * 注意：不允许重建树。
 * <p>
 * 输入: preorder = "9,3,4,#,#,1,#,#,2,#,6,#,#"
 * 输出: true
 * <p>
 * 输入: preorder = "1,#"
 * 输出: false
 * <p>
 * 输入: preorder = "9,#,#,1"
 * 输出: false
 * <p>
 * 1 <= preorder.length <= 10^4
 * preorder 由以逗号 “，” 分隔的 [0,100] 范围内的整数和 “#” 组成
 */
public class Problem331 {
    public static void main(String[] args) {
        Problem331 problem331 = new Problem331();
//        String preorder = "9,3,4,#,#,1,#,#,2,#,6,#,#";
        String preorder = "9,#,92,#,#";
        System.out.println(problem331.isValidSerialization(preorder));
        System.out.println(problem331.isValidSerialization2(preorder));
    }

    /**
     * 栈
     * 栈顶存在两个连续的"#"，和一个非"#"元素，则栈顶三个元素出栈，再将一个"#"入栈，即用空节点作为子树
     * 遍历结束，栈中只有一个元素，并且为"#"，则是正确的二叉树的前序序列化
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param preorder
     * @return
     */
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) {
            return true;
        }

        Stack<String> stack = new Stack<>();
        String[] arr = preorder.split(",");

        for (int i = 0; i < arr.length; i++) {
            stack.push(arr[i]);

            //栈顶存在两个连续的"#"，和一个非"#"元素，则栈顶三个元素出栈，再将一个"#"入栈，即用空节点作为子树
            while (stack.size() >= 3 &&
                    "#".equals(stack.get(stack.size() - 1)) &&
                    "#".equals(stack.get(stack.size() - 2)) &&
                    !"#".equals(stack.get(stack.size() - 3))) {
                stack.pop();
                stack.pop();
                stack.pop();
                stack.push("#");
            }
        }

        //遍历结束，栈中只有一个元素，并且为"#"，则是正确的二叉树的前序序列化，返回true
        return stack.size() == 1 && "#".equals(stack.peek());
    }

    /**
     * 入度和出度
     * 核心思想：所有节点的入度之和等于所有节点的出度之和
     * 根节点的入度为0，出度为2；非根节点的入度为1，出度为2；空节点(即"#")的入度为1，出度为0
     * 前序遍历过程中，始终保持出度减入度之差大于0，如果不满足，则不是正确的二叉树的前序序列化，
     * 遍历结束时，如果出度减入度之差等于0，则是是正确的二叉树的前序序列化
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param preorder
     * @return
     */
    public boolean isValidSerialization2(String preorder) {
        if (preorder == null || preorder.length() == 0) {
            return true;
        }

        String[] arr = preorder.split(",");
        //前序遍历过程中出度减入度之差
        //初始化为1，因为根节点的入度为0，不像其他非根节点的入度为1，即当遍历到根节点时，degreeDiff++，
        //此时degreeDiff为2，仍然满足出度减入度之差
        int degreeDiff = 1;

        for (int i = 0; i < arr.length; i++) {
            //当前前序遍历过程中出度减入度之差为0，则不是正确的二叉树的前序序列化，返回false
            if (degreeDiff == 0) {
                return false;
            }

            //当前节点为空节点，入度为1，出度为0，即出度减入度之差减1
            if ("#".equals(arr[i])) {
                degreeDiff--;
            } else {
                //当前节点为非空节点，入度为1，出度为2(初始化degreeDiff为1已经考虑根节点入度为0)，即出度减入度之差加1
                degreeDiff++;
            }
        }

        //遍历结束时，如果出度减入度之差等于0，则是是正确的二叉树的前序序列化，返回true
        return degreeDiff == 0;
    }
}
