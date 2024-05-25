package com.zhang.java;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Date 2022/11/10 12:10
 * @Author zsy
 * @Description 简化路径 类比Problem588、Problem609、Problem1166、Problem1500 栈类比Problem20、Problem150、Problem224、Problem227、Problem331、Problem341、Problem394、Problem678、Problem856、Problem946、Problem1003、Problem1047、Problem1096、Offer31、CharacterToInteger
 * 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
 * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；
 * 此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；
 * 两者都可以是复杂相对路径的组成部分。
 * 任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。
 * 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
 * 请注意，返回的 规范路径 必须遵循下述格式：
 * 始终以斜杠 '/' 开头。
 * 两个目录名之间必须只有一个斜杠 '/' 。
 * 最后一个目录名（如果存在）不能 以 '/' 结尾。
 * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
 * 返回简化后得到的 规范路径 。
 * <p>
 * 输入：path = "/home/"
 * 输出："/home"
 * 解释：注意，最后一个目录名后面没有斜杠。
 * <p>
 * 输入：path = "/../"
 * 输出："/"
 * 解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
 * <p>
 * 输入：path = "/home//foo/"
 * 输出："/home/foo"
 * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
 * <p>
 * 输入：path = "/a/./b/../../c/"
 * 输出："/c"
 * <p>
 * 1 <= path.length <= 3000
 * path 由英文字母，数字，'.'，'/' 或 '_' 组成。
 * path 是一个有效的 Unix 风格绝对路径。
 */
public class Problem71 {
    public static void main(String[] args) {
        Problem71 problem71 = new Problem71();
        String path = "/home//foo/";
        System.out.println(problem71.simplifyPath(path));
    }

    /**
     * 栈
     * 遇到".."，出栈，即回到上级目录；遇到不是""或"."的情况，元素入栈，
     * 遍历结束之后，依次从队首出队，得到简化路径
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param path
     * @return
     */
    public String simplifyPath(String path) {
        String[] names = path.split("/");
        //保存路径的栈
        Deque<String> stack = new LinkedList<>();

        for (String name : names) {
            //当前文件名为".."，则需要回退到上一级
            if ("..".equals(name)) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else if ("".equals(name) || ".".equals(name)) {
                //当前文件名为""或"."，则不需要进行操作
            } else {
                //将当前文件名不为".."或""或"."，则将当前文件名加入栈中
                stack.offerLast(name);
            }
        }

        //栈为空，路径为空，直接返回"/"
        if (stack.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append("/").append(stack.pollFirst());
        }

        return sb.toString();
    }
}
