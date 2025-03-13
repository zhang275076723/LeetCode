package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/19 09:04
 * @Author zsy
 * @Description 删除子文件夹 微软面试题 类比Problem71、Problem588、Problem609、Problem642、Problem1166、Problem1268、Problem1500、Problem1948 前缀树类比
 * 你是一位系统管理员，手里有一份文件夹列表 folder，你的任务是要删除该列表中的所有 子文件夹，并以 任意顺序 返回剩下的文件夹。
 * 如果文件夹 folder[i] 位于另一个文件夹 folder[j] 下，那么 folder[i] 就是 folder[j] 的 子文件夹 。
 * 文件夹的「路径」是由一个或多个按以下格式串联形成的字符串：'/' 后跟一个或者多个小写英文字母。
 * 例如，"/leetcode" 和 "/leetcode/problems" 都是有效的路径，而空字符串和 "/" 不是。
 * <p>
 * 输入：folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
 * 输出：["/a","/c/d","/c/f"]
 * 解释："/a/b" 是 "/a" 的子文件夹，而 "/c/d/e" 是 "/c/d" 的子文件夹。
 * <p>
 * 输入：folder = ["/a","/a/b/c","/a/b/d"]
 * 输出：["/a"]
 * 解释：文件夹 "/a/b/c" 和 "/a/b/d" 都会被删除，因为它们都是 "/a" 的子文件夹。
 * <p>
 * 输入: folder = ["/a/b/c","/a/b/ca","/a/b/d"]
 * 输出: ["/a/b/c","/a/b/ca","/a/b/d"]
 * <p>
 * 1 <= folder.length <= 4 * 10^4
 * 2 <= folder[i].length <= 100
 * folder[i] 只包含小写字母和 '/'
 * folder[i] 总是以字符 '/' 起始
 * folder 每个元素都是 唯一 的
 */
public class Problem1233 {
    public static void main(String[] args) {
        Problem1233 problem1233 = new Problem1233();
        String[] folder = {"/a", "/a/b", "/c/d", "/c/d/e", "/c/f"};
        System.out.println(problem1233.removeSubfolders(folder));
        System.out.println(problem1233.removeSubfolders2(folder));
    }

    /**
     * 排序
     * folder[i]按照字典序排序，如果结果集合的末尾文件夹加上"/"是当前文件夹folder[i]的前缀，
     * 则当前文件夹folder[i]是结果集合的末尾文件夹的子文件夹
     * 时间复杂度O(mn*logn+mn)，空间复杂度O(logn+m) (n=folder.length，m=max(folder[i].length()))
     * (排序的空间复杂度O(logn)，compareTo的空间复杂度O(m))
     *
     * @param folder
     * @return
     */
    public List<String> removeSubfolders(String[] folder) {
        //folder[i]按照字典序排序
        Arrays.sort(folder, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });

        List<String> list = new ArrayList<>();
        //按照字典序排序后第一个文件夹不是子文件夹，直接加入list
        list.add(folder[0]);

        for (int i = 1; i < folder.length; i++) {
            //list的末尾文件夹
            String lastFolder = list.get(list.size() - 1);

            //lastFolder加上"/"是当前文件夹folder[i]的前缀，则当前文件夹folder[i]是lastFolder的子文件夹；否则，不是子文件夹
            if (!(lastFolder.length() < folder[i].length() &&
                    (lastFolder + "/").equals(folder[i].substring(0, lastFolder.length() + 1)))) {
                list.add(folder[i]);
            }
        }

        return list;
    }

    /**
     * 前缀树+dfs
     * 时间复杂度O(mn)，空间复杂度O(mn) (n=folder.length，m=max(folder[i].length()))
     *
     * @param folder
     * @return
     */
    public List<String> removeSubfolders2(String[] folder) {
        Trie trie = new Trie();

        for (int i = 0; i < folder.length; i++) {
            trie.insert(folder[i], i);
        }

        return trie.search(folder);
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        /**
         * 文件夹路径加入前缀树中，并设置末尾节点在folder中的下标索引为index
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param str
         * @param index 文件夹路径str在folder中的下标索引
         */
        public void insert(String str, int index) {
            TrieNode node = root;
            //路径数组
            String[] arr = str.split("/");

            //注意：i是从1开始遍历，arr[0]为空不考虑
            for (int i = 1; i < arr.length; i++) {
                if (!node.children.containsKey(arr[i])) {
                    node.children.put(arr[i], new TrieNode());
                }

                node = node.children.get(arr[i]);
            }

            node.index = index;
            node.isEnd = true;
        }

        /**
         * 前缀树中查询不是子文件夹的文件夹在folder中的下标索引集合
         * 时间复杂度O(mn)，空间复杂度O(mn) (n=folder.length，m=max(folder[i].length()))
         *
         * @param folder
         * @return
         */
        public List<String> search(String[] folder) {
            List<String> list = new ArrayList<>();

            dfs(root, list, folder);

            return list;
        }

        private void dfs(TrieNode node, List<String> list, String[] folder) {
            //找到第一个根节点到当前节点的文件夹不是子文件夹，则对应的文件夹路径加入list中，
            //从当前节点往子节点的路径就不需要遍历，直接返回
            if (node.index != -1) {
                list.add(folder[node.index]);
                return;
            }

            //继续往子节点dfs
            for (TrieNode childNode : node.children.values()) {
                dfs(childNode, list, folder);
            }
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            //注意：key为String类型，而不是Character类型
            private final Map<String, TrieNode> children;
            //根节点到当前节点的文件夹路径在folder中的下标索引
            private int index;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                index = -1;
                isEnd = false;
            }
        }
    }
}
