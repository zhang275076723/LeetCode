package com.zhang.java;


import java.util.*;

/**
 * @Date 2024/5/16 08:38
 * @Author zsy
 * @Description 设计内存文件系统 类比Problem71、Problem1166、Problem1500 前缀树类比
 * 设计一个内存文件系统，模拟以下功能：
 * 实现文件系统类:
 * FileSystem() 初始化系统对象
 * List<String> ls(String path)
 * 如果 path 是一个文件路径，则返回一个仅包含该文件名称的列表。
 * 如果 path 是一个目录路径，则返回该目录中文件和 目录名 的列表。
 * 答案应该 按字典顺序 排列。
 * void mkdir(String path) 根据给定的路径创建一个新目录。给定的目录路径不存在。如果路径中的中间目录不存在，您也应该创建它们。
 * void addContentToFile(String filePath, String content)
 * 如果 filePath 不存在，则创建包含给定内容 content的文件。
 * 如果 filePath 已经存在，将给定的内容 content附加到原始内容。
 * String readContentFromFile(String filePath) 返回 filePath下的文件内容。
 * <p>
 * ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
 * [[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
 * 输出:
 * [null,[],null,null,["a"],"hello"]
 * 解释:
 * FileSystem fileSystem = new FileSystem();
 * fileSystem.ls("/");                         // 返回 []
 * fileSystem.mkdir("/a/b/c");
 * fileSystem.addContentToFile("/a/b/c/d", "hello");
 * fileSystem.ls("/");                         // 返回 ["a"]
 * fileSystem.readContentFromFile("/a/b/c/d"); // 返回 "hello"
 * <p>
 * 1 <= path.length, filePath.length <= 100
 * path 和 filePath 都是绝对路径，除非是根目录 ‘/’ 自身，其他路径都是以 ‘/’ 开头且 不 以 ‘/’ 结束。
 * 你可以假定所有操作的参数都是有效的，即用户不会获取不存在文件的内容，或者获取不存在文件夹和文件的列表。
 * 你可以假定所有文件夹名字和文件名字都只包含小写字母，且同一文件夹下不会有相同名字的文件夹或文件。
 * 1 <= content.length <= 50
 * ls, mkdir, addContentToFile, and readContentFromFile 最多被调用 300 次
 */
public class Problem588 {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        // 返回 []
        System.out.println(fileSystem.ls("/"));
        // 创建路径"/a/b/c"
        fileSystem.mkdir("/a/b/c");
        // 在路径"/a/b/c"下创建文件名为"d"内容为"hello"的文件
        fileSystem.addContentToFile("/a/b/c/d", "hello");
        // 返回 ["a"]
        System.out.println(fileSystem.ls("/"));
        // 返回 "hello"
        System.out.println(fileSystem.readContentFromFile("/a/b/c/d"));
    }

    static class FileSystem {
        //前缀树
        private final Trie trie;

        public FileSystem() {
            trie = new Trie();
        }

        /**
         * 按字典顺序返回当前路径中的文件名和目录集合
         * 不存在路径path，返回空集合；当前路径为文件路径，返回文件名；当前路径为目录路径，返回当前路径中的文件名和目录集合
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param path
         * @return
         */
        public List<String> ls(String path) {
            Trie.TrieNode node = trie.search(path);

            //不存在路径path，返回空集合
            if (node == null) {
                return new ArrayList<>();
            }

            //当前路径为文件路径，返回文件名
            if (node.isFile) {
                return new ArrayList<String>() {{
                    add(node.filename);
                }};
            }

            List<String> list = new ArrayList<>();

            //当前路径为目录路径，返回当前路径中的文件名和目录集合
            for (String str : node.children.keySet()) {
                list.add(str);
            }

            //按照字典顺序排序，即由小到大排序
            list.sort(new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    return str1.compareTo(str2);
                }
            });

            return list;
        }

        /**
         * 创建path路径
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param path
         */
        public void mkdir(String path) {
            trie.insert(path);
        }

        /**
         * 创建content内容的filePath文件路径，如果filePath文件路径存在，则content内容附加到末尾
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param filePath
         * @param content
         */
        public void addContentToFile(String filePath, String content) {
            trie.insert(filePath, content);
        }

        /**
         * 返回filePath下的文件内容
         * 时间复杂度O(n)，空间复杂度O(n)
         *
         * @param filePath
         * @return
         */
        public String readContentFromFile(String filePath) {
            Trie.TrieNode node = trie.search(filePath);

            //不存在路径path，返回""
            if (node == null) {
                return "";
            }

            return node.content.toString();
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
             * 创建path路径
             * 时间复杂度O(n)，空间复杂度O(n)
             *
             * @param path
             */
            public void insert(String path) {
                TrieNode node = root;
                //路径数组
                String[] arr = path.split("/");

                //注意：i是从1开始遍历，arr[0]为空不考虑
                for (int i = 1; i < arr.length; i++) {
                    if (!node.children.containsKey(arr[i])) {
                        node.children.put(arr[i], new TrieNode());
                    }

                    node = node.children.get(arr[i]);
                }

                node.isEnd = true;
            }

            /**
             * 创建content内容的filePath文件路径，如果filePath文件路径存在，则content内容附加到末尾
             * 时间复杂度O(n)，空间复杂度O(n)
             *
             * @param filePath
             * @param content
             */
            public void insert(String filePath, String content) {
                TrieNode node = root;
                //路径数组
                String[] arr = filePath.split("/");

                //注意：i是从1开始遍历，arr[0]为空不考虑
                for (int i = 1; i < arr.length; i++) {
                    if (!node.children.containsKey(arr[i])) {
                        node.children.put(arr[i], new TrieNode());
                    }

                    node = node.children.get(arr[i]);
                }

                node.filename = arr[arr.length - 1];
                node.content.append(content);
                node.isFile = true;
                node.isEnd = true;
            }

            /**
             * 返回path路径中的末尾前缀树节点，如果不存在，则返回null
             * 时间复杂度O(n)，空间复杂度O(n)
             *
             * @param path
             * @return
             */
            public TrieNode search(String path) {
                TrieNode node = root;
                //路径数组
                String[] arr = path.split("/");

                //注意：i是从1开始遍历，arr[0]为空不考虑
                for (int i = 1; i < arr.length; i++) {
                    node = node.children.get(arr[i]);

                    if (node == null) {
                        return null;
                    }
                }

                return node;
            }

            /**
             * 前缀树节点
             */
            private static class TrieNode {
                //注意：key为String类型，而不是Character类型
                private final Map<String, TrieNode> children;
                //当前节点的文件名
                private String filename;
                //当前节点的文件内容
                private final StringBuilder content;
                //当前节点是文件节点还是目录节点
                private boolean isFile;
                private boolean isEnd;

                public TrieNode() {
                    children = new HashMap<>();
                    filename = "";
                    content = new StringBuilder();
                    isFile = false;
                    isEnd = false;
                }
            }
        }
    }
}
