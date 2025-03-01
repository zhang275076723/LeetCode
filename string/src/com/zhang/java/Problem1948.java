package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/30 08:51
 * @Author zsy
 * @Description 删除系统中的重复文件夹 类比Problem536、Problem606、Problem652 类比Problem71、Problem588、Problem609、Problem642、Problem1166、Problem1233、Problem1268、Problem1500 类比Problem271、Problem297、Problem331、Problem449、Problem535、Problem625、Problem820、Offer37 前缀树类比
 * 由于一个漏洞，文件系统中存在许多重复文件夹。
 * 给你一个二维数组 paths，其中 paths[i] 是一个表示文件系统中第 i 个文件夹的绝对路径的数组。
 * 例如，["one", "two", "three"] 表示路径 "/one/two/three" 。
 * 如果两个文件夹（不需要在同一层级）包含 非空且相同的 子文件夹 集合 并具有相同的子文件夹结构，则认为这两个文件夹是相同文件夹。
 * 相同文件夹的根层级 不 需要相同。
 * 如果存在两个（或两个以上）相同 文件夹，则需要将这些文件夹和所有它们的子文件夹 标记 为待删除。
 * 例如，下面文件结构中的文件夹 "/a" 和 "/b" 相同。
 * 它们（以及它们的子文件夹）应该被 全部 标记为待删除：
 * /a
 * /a/x
 * /a/x/y
 * /a/z
 * /b
 * /b/x
 * /b/x/y
 * /b/z
 * 然而，如果文件结构中还包含路径 "/b/w" ，那么文件夹 "/a" 和 "/b" 就不相同。
 * 注意，即便添加了新的文件夹 "/b/w" ，仍然认为 "/a/x" 和 "/b/x" 相同。
 * 一旦所有的相同文件夹和它们的子文件夹都被标记为待删除，文件系统将会 删除 所有上述文件夹。
 * 文件系统只会执行一次删除操作。
 * 执行完这一次删除操作后，不会删除新出现的相同文件夹。
 * 返回二维数组 ans ，该数组包含删除所有标记文件夹之后剩余文件夹的路径。
 * 路径可以按 任意顺序 返回。
 * <p>
 * 输入：paths = [["a"],["c"],["d"],["a","b"],["c","b"],["d","a"]]
 * 输出：[["d"],["d","a"]]
 * 解释：文件结构如上所示。
 * 文件夹 "/a" 和 "/c"（以及它们的子文件夹）都会被标记为待删除，因为它们都包含名为 "b" 的空文件夹。
 * <p>
 * 输入：paths = [["a"],["c"],["a","b"],["c","b"],["a","b","x"],["a","b","x","y"],["w"],["w","y"]]
 * 输出：[["c"],["c","b"],["a"],["a","b"]]
 * 解释：文件结构如上所示。
 * 文件夹 "/a/b/x" 和 "/w"（以及它们的子文件夹）都会被标记为待删除，因为它们都包含名为 "y" 的空文件夹。
 * 注意，文件夹 "/a" 和 "/c" 在删除后变为相同文件夹，但这两个文件夹不会被删除，因为删除只会进行一次，且它们没有在删除前被标记。
 * <p>
 * 输入：paths = [["a","b"],["c","d"],["c"],["a"]]
 * 输出：[["c"],["c","d"],["a"],["a","b"]]
 * 解释：文件系统中所有文件夹互不相同。
 * 注意，返回的数组可以按不同顺序返回文件夹路径，因为题目对顺序没有要求。
 * <p>
 * 输入：paths = [["a"],["a","x"],["a","x","y"],["a","z"],["b"],["b","x"],["b","x","y"],["b","z"]]
 * 输出：[]
 * 解释：文件结构如上所示。
 * 文件夹 "/a/x" 和 "/b/x"（以及它们的子文件夹）都会被标记为待删除，因为它们都包含名为 "y" 的空文件夹。
 * 文件夹 "/a" 和 "/b"（以及它们的子文件夹）都会被标记为待删除，因为它们都包含一个名为 "z" 的空文件夹以及上面提到的文件夹 "x" 。
 * <p>
 * 输入：paths = [["a"],["a","x"],["a","x","y"],["a","z"],["b"],["b","x"],["b","x","y"],["b","z"],["b","w"]]
 * 输出：[["b"],["b","w"],["b","z"],["a"],["a","z"]]
 * 解释：本例与上例的结构基本相同，除了新增 "/b/w" 文件夹。
 * 文件夹 "/a/x" 和 "/b/x" 仍然会被标记，但 "/a" 和 "/b" 不再被标记，因为 "/b" 中有名为 "w" 的空文件夹而 "/a" 没有。
 * 注意，"/a/z" 和 "/b/z" 不会被标记，因为相同子文件夹的集合必须是非空集合，但这两个文件夹都是空的。
 * <p>
 * 1 <= paths.length <= 2 * 10^4
 * 1 <= paths[i].length <= 500
 * 1 <= paths[i][j].length <= 10
 * 1 <= sum(paths[i][j].length) <= 2 * 10^5
 * path[i][j] 由小写英文字母组成
 * 不会存在两个路径都指向同一个文件夹的情况
 * 对于不在根层级的任意文件夹，其父文件夹也会包含在输入中
 */
public class Problem1948 {
    public static void main(String[] args) {
        Problem1948 problem1948 = new Problem1948();
        List<List<String>> paths = new ArrayList<List<String>>() {{
            add(new ArrayList<String>() {{
                add("a");
            }});
            add(new ArrayList<String>() {{
                add("c");
            }});
            add(new ArrayList<String>() {{
                add("d");
            }});
            add(new ArrayList<String>() {{
                add("a");
                add("b");
            }});
            add(new ArrayList<String>() {{
                add("c");
                add("b");
            }});
            add(new ArrayList<String>() {{
                add("d");
                add("a");
            }});
        }};
        System.out.println(problem1948.deleteDuplicateFolder(paths));
    }

    /**
     * 哈希表+前缀树+dfs+排序序列化
     * 1、paths中每一条文件夹路径加入前缀树中
     * 2、dfs遍历，得到前缀树中每个节点排序后的序列化，并存储到map中，用于删除重复的文件夹
     * 3、dfs遍历，删除前缀树中的重复文件夹，即删除序列化出现次数大于1的节点
     *
     * @param paths
     * @return
     */
    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        Trie trie = new Trie();

        //1、paths中每一条文件夹路径加入前缀树中
        for (List<String> path : paths) {
            trie.insert(path);
        }

        //2、dfs遍历，得到前缀树中每个节点排序后的序列化，并存储到map中，用于删除重复的文件夹
        trie.serialize(trie.root);

        List<List<String>> result = new ArrayList<>();

        //3、dfs遍历，删除前缀树中的重复文件夹，即删除序列化出现次数大于1的节点
        trie.delete(trie.root, new ArrayList<>(), result);

        return result;
    }

    /**
     * 前缀树
     */
    private static class Trie {
        private final TrieNode root;
        //key：当前节点包含的所有子节点排序后的序列化字符串，value：当前排序后的序列化字符串出现的次数
        //用于删除重复的文件夹
        private final Map<String, Integer> map;

        public Trie() {
            root = new TrieNode();
            map = new HashMap<>();
            //初始化，前缀树根节点的序列化为""，出现次数为1
            map.put("", 1);
        }

        public void insert(List<String> list) {
            TrieNode node = root;

            for (String str : list) {
                if (!node.children.containsKey(str)) {
                    node.children.put(str, new TrieNode());
                }

                node = node.children.get(str);
            }

            node.isEnd = true;
        }

        /**
         * dfs遍历前缀树，得到前缀树中每个节点排序后的序列化，并存储到map中，用于删除重复的文件夹
         *
         * @param node
         */
        public void serialize(TrieNode node) {
            //当前节点为叶节点，则当前节点序列化为""
            if (node.children.isEmpty()) {
                node.serializeStr = "";
                return;
            }

            //当前节点子节点序列化的字符串集合
            List<String> list = new ArrayList<>();

            for (Map.Entry<String, TrieNode> entry : node.children.entrySet()) {
                StringBuilder sb = new StringBuilder();
                //当前节点子节点的文件夹名
                String childFolderName = entry.getKey();
                //当前节点子节点
                TrieNode childNode = entry.getValue();

                serialize(childNode);

                sb.append(childFolderName).append("(").append(childNode.serializeStr).append(")");
                list.add(sb.toString());
            }

            //按照字典序排序，避免存在2个节点包含的文件夹都相同但顺序不同的情况
            list.sort(new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    return str1.compareTo(str2);
                }
            });

            StringBuilder sb = new StringBuilder();

            //list按照字典序排序后进行拼接，得到当前节点序列化
            for (String str : list) {
                sb.append(str);
            }

            node.serializeStr = sb.toString();
            map.put(node.serializeStr, map.getOrDefault(node.serializeStr, 0) + 1);
        }

        /**
         * dfs遍历，删除当前节点中的重复文件夹，即删除序列化出现次数大于1的节点
         *
         * @param node
         * @param list   当前节点的文件夹路径
         * @param result
         */
        public void delete(TrieNode node, List<String> list, List<List<String>> result) {
            //当前节点序列化出现次数大于1，则当前节点为重复文件夹，直接返回
            if (map.get(node.serializeStr) > 1) {
                return;
            }

            //根节点到当前节点的文件夹路径加入result，空文件夹不加入result中
            if (!list.isEmpty()) {
                //不能写为result.add(list)，因为传入的是引用，当list修改之后，result中的结果也会修改
                result.add(new ArrayList<>(list));
            }

            for (Map.Entry<String, TrieNode> entry : node.children.entrySet()) {
                //当前节点子节点的文件夹名
                String childFolderName = entry.getKey();
                //当前节点子节点
                TrieNode childNode = entry.getValue();

                list.add(childFolderName);

                delete(childNode, list, result);

                list.remove(list.size() - 1);
            }
        }

        /**
         * 前缀树节点
         */
        private static class TrieNode {
            //注意：key为String类型，而不是Character类型
            private final Map<String, TrieNode> children;
            //当前节点包含的所有子节点排序后的序列化字符串
            //例如：示例4中节点a和b的serializeStr均为"x(y())z()"，如果当前节点为叶节点，则serializeStr为""
            private String serializeStr;
            private boolean isEnd;

            public TrieNode() {
                children = new HashMap<>();
                serializeStr = "";
                isEnd = false;
            }
        }
    }
}
