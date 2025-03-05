package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/2/29 08:55
 * @Author zsy
 * @Description 设计文件系统 类比Problem71、Problem588、Problem609、Problem642、Problem1233、Problem1268、Problem1500、Problem1948 前缀树类比Problem14、Problem208、Problem211、Problem212、Problem336、Problem421、Problem676、Problem677、Problem720、Problem745、Problem820、Problem1804、Problem3043
 * 你需要设计一个文件系统，你可以创建新的路径并将它们与不同的值关联。
 * 路径的格式是一个或多个连接在一起的字符串，形式为： / ，后面跟着一个或多个小写英文字母。
 * 例如， " /leetcode" 和 "/leetcode/problems" 是有效路径，而空字符串 "" 和 "/" 不是。
 * 实现 FileSystem 类:
 * bool createPath(string path, int value) 创建一个新的 path ，并在可能的情况下关联一个 value ，然后返回 true 。
 * 如果路径已经存在或其父路径不存在，则返回 false 。
 * int get(string path) 返回与 path 关联的值，如果路径不存在则返回 -1 。
 * <p>
 * 输入：
 * ["FileSystem","create","get"]
 * [[],["/a",1],["/a"]]
 * 输出：
 * [null,true,1]
 * 解释：
 * FileSystem fileSystem = new FileSystem();
 * fileSystem.create("/a", 1); // 返回 true
 * fileSystem.get("/a"); // 返回 1
 * <p>
 * 输入：
 * ["FileSystem","createPath","createPath","get","createPath","get"]
 * [[],["/leet",1],["/leet/code",2],["/leet/code"],["/c/d",1],["/c"]]
 * 输出：
 * [null,true,true,2,false,-1]
 * 解释：
 * FileSystem fileSystem = new FileSystem();
 * fileSystem.createPath("/leet", 1); // 返回 true
 * fileSystem.createPath("/leet/code", 2); // 返回 true
 * fileSystem.get("/leet/code"); // 返回 2
 * fileSystem.createPath("/c/d", 1); // 返回 false 因为父路径 "/c" 不存在。
 * fileSystem.get("/c"); // 返回 -1 因为该路径不存在。
 * <p>
 * 对两个函数的调用次数加起来小于等于 10^4
 * 2 <= path.length <= 100
 * 1 <= value <= 10^9
 */
public class Problem1166 {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        // 返回 true
        System.out.println(fileSystem.createPath("/leet", 1));
        // 返回 true
        System.out.println(fileSystem.createPath("/leet/code", 2));
        // 返回 2
        System.out.println(fileSystem.get("/leet/code"));
        // 返回 false 因为父路径 "/c" 不存在。
        System.out.println(fileSystem.createPath("/c/d", 1));
        // 返回 -1 因为该路径不存在。
        System.out.println(fileSystem.get("/c"));
    }

    /**
     * 前缀树
     */
    static class FileSystem {
        //前缀树
        private final Trie trie;

        public FileSystem() {
            trie = new Trie();
        }

        /**
         * 根据"/"拆分path，将每个路径插入前缀树中，在末尾节点设置值为value，如果路径已经存在或者父路径不存在，返回false；否则，返回true
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param path
         * @param value
         * @return
         */
        public boolean createPath(String path, int value) {
            return trie.insert(path, value);
        }

        /**
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param path
         * @return
         */
        public int get(String path) {
            return trie.search(path);
        }

        /**
         * 前缀树
         */
        private static class Trie {
            private final TrieNode root;

            public Trie() {
                root = new TrieNode();
            }

            public boolean insert(String path, int value) {
                TrieNode node = root;
                //路径数组
                String[] arr = path.split("/");

                //注意：i是从1开始到arr.length-2，arr[0]为空不考虑，arr[arr.length-1]结尾路径需要特殊考虑
                for (int i = 1; i < arr.length - 1; i++) {
                    //父路径不存在，返回false
                    if (!node.children.containsKey(arr[i])) {
                        return false;
                    }

                    node = node.children.get(arr[i]);
                }

                //结尾路径已经存在，返回false
                if (node.children.containsKey(arr[arr.length - 1])) {
                    return false;
                }

                //创建结尾路径
                node.children.put(arr[arr.length - 1], new TrieNode());
                node = node.children.get(arr[arr.length - 1]);
                //结尾路径节点value赋值
                node.value = value;
                node.isEnd = true;

                return true;
            }

            private int search(String path) {
                TrieNode node = root;
                //路径数组
                String[] arr = path.split("/");

                //注意：i是从1开始遍历，arr[0]为空不考虑
                for (int i = 1; i < arr.length; i++) {
                    if (!node.children.containsKey(arr[i])) {
                        return -1;
                    }

                    node = node.children.get(arr[i]);
                }

                return node.value;
            }

            /**
             * 前缀树节点
             */
            private static class TrieNode {
                //注意：key为String类型，而不是Character类型
                private final Map<String, TrieNode> children;
                //根节点到当前节点的路径关联的值
                private int value;
                private boolean isEnd;

                public TrieNode() {
                    children = new HashMap<>();
                    //赋值为-1表示不存在根节点到当前节点的路径
                    value = -1;
                    isEnd = false;
                }
            }
        }
    }
}
