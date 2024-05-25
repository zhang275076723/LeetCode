package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/18 08:23
 * @Author zsy
 * @Description 在系统中查找重复文件 华为面试题 类比Problem71、Problem588、Problem1166、Problem1233、Problem1500 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem763、Problem1500、Problem1640、Offer50
 * 给你一个目录信息列表 paths ，包括目录路径，以及该目录中的所有文件及其内容，请你按路径返回文件系统中的所有重复文件。
 * 答案可按 任意顺序 返回。
 * 一组重复的文件至少包括 两个 具有完全相同内容的文件。
 * 输入 列表中的单个目录信息字符串的格式如下：
 * "root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"
 * 这意味着，在目录 root/d1/d2/.../dm 下，
 * 有 n 个文件 ( f1.txt, f2.txt ... fn.txt ) 的内容分别是 ( f1_content, f2_content ... fn_content ) 。
 * 注意：n >= 1 且 m >= 0 。如果 m = 0 ，则表示该目录是根目录。
 * 输出 是由 重复文件路径组 构成的列表。
 * 其中每个组由所有具有相同内容文件的文件路径组成。
 * 文件路径是具有下列格式的字符串：
 * "directory_path/file_name.txt"
 * 进阶：
 * 假设您有一个真正的文件系统，您将如何搜索文件？广度搜索还是宽度搜索？
 * 如果文件内容非常大（GB级别），您将如何修改您的解决方案？
 * 如果每次只能读取 1 kb 的文件，您将如何修改解决方案？
 * 修改后的解决方案的时间复杂度是多少？其中最耗时的部分和消耗内存的部分是什么？如何优化？
 * 如何确保您发现的重复文件不是误报？
 * <p>
 * 输入：paths = ["root/a 1.txt(abcd) 2.txt(efgh)","root/c 3.txt(abcd)","root/c/d 4.txt(efgh)","root 4.txt(efgh)"]
 * 输出：[["root/a/2.txt","root/c/d/4.txt","root/4.txt"],["root/a/1.txt","root/c/3.txt"]]
 * <p>
 * 输入：paths = ["root/a 1.txt(abcd) 2.txt(efgh)","root/c 3.txt(abcd)","root/c/d 4.txt(efgh)"]
 * 输出：[["root/a/2.txt","root/c/d/4.txt"],["root/a/1.txt","root/c/3.txt"]]
 * <p>
 * 1 <= paths.length <= 2 * 10^4
 * 1 <= paths[i].length <= 3000
 * 1 <= sum(paths[i].length) <= 5 * 10^5
 * paths[i] 由英文字母、数字、字符 '/'、'.'、'('、')' 和 ' ' 组成
 * 你可以假设在同一目录中没有任何文件或目录共享相同的名称。
 * 你可以假设每个给定的目录信息代表一个唯一的目录。目录路径和文件信息用单个空格分隔。
 */
public class Problem609 {
    public static void main(String[] args) {
        Problem609 problem609 = new Problem609();
        String[] paths = {
                "root/a 1.txt(abcd) 2.txt(efgh)",
                "root/c 3.txt(abcd)",
                "root/c/d 4.txt(efgh)",
                "root 4.txt(efgh)"
        };
        System.out.println(problem609.findDuplicate(paths));
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(n) (n：paths中文件的个数)
     * <p>
     * 进阶问题：
     * 1、假设您有一个真正的文件系统，您将如何搜索文件？广度搜索还是宽度搜索？
     * dfs，dfs和bfs的时间复杂度相同，但bfs的空间复杂度通常来说大于dfs的空间复杂度
     * 2、如果文件内容非常大（GB级别），您将如何修改您的解决方案？
     * 分块存储，并存储元数据，元数据包括文件名、文件大小、分块的偏移量、分块的哈希值等
     * 3、如果每次只能读取 1 kb 的文件，您将如何修改解决方案？
     * 分块存储，每块大小为1kb
     * 4、修改后的解决方案的时间复杂度是多少？其中最耗时的部分和消耗内存的部分是什么？如何优化？
     * 假设分块后文件块的个数为n，每块文件的大小为k，则时间复杂度为O(kn^2)。计算每块哈希值的时间复杂度为O(k)，
     * 则共需要O(kn)计算元数据，每两个文件之间都需要进行比较，比较次数为n(n-1)/2，先比较文件大小，再比较每块的哈希值，
     * 即每次比较的时间复杂度为O(k)，总时间复杂度为O(kn^2)。
     * 最耗时的部分和消耗内存的部分是每块哈希值的计算O(k)。
     * 优化每块哈希值的计算，即增大每块的大小k，则减少分块的个数n，使得O(kn^2)变小。
     * 5、如何确保您发现的重复文件不是误报？
     * 先比较文件大小，再比较每块的哈希值，如果都相同，则需要比较文件内容，如果都相同，才是重复文件。
     *
     * @param paths
     * @return
     */
    public List<List<String>> findDuplicate(String[] paths) {
        //key：文件内容，value：相同文件内容的文件路径集合
        Map<String, List<String>> map = new HashMap<>();

        for (String path : paths) {
            String[] arr = path.split(" ");

            //注意：i是从1开始遍历，arr[0]为相同文件路径
            for (int i = 1; i < arr.length; i++) {
                //arr[i]中的文件名
                String filename = arr[i].substring(0, arr[i].indexOf('('));
                //arr[i]中的文件内容
                String content = arr[i].substring(arr[i].indexOf('(') + 1, arr[i].length() - 1);

                if (!map.containsKey(content)) {
                    map.put(content, new ArrayList<>());
                }

                List<String> list = map.get(content);
                list.add(arr[0] + "/" + filename);
            }
        }

        List<List<String>> result = new ArrayList<>();

        for (List<String> list : map.values()) {
            //相同文件内容的文件个数大于1，则存在重复的文件
            if (list.size() > 1) {
                result.add(list);
            }
        }

        return result;
    }
}
