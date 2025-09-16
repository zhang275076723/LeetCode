package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/1/8 08:46
 * @Author zsy
 * @Description 哈夫曼编码
 * 对字符串按照Huffman编码方式编码为01序列，输出编码后的01序列，并比较其相对于定长编码的压缩率。
 * 例如对于字符串"AABBBEEEEGZ"，如果使用定长编码，'A'、'B'、'E'、'G'、'Z'字符各需要3位01串编码，编码后的字符长度为3*11=33位，
 * 如果使用Huffman编码，编码后的字符长度为2*3+3*2+4*1+4+4=24，压缩率为24/33=72.73%。
 * <p>
 * 输入："AABBBEEEEGZ"
 * 输出：Huffman编码后的01序列 = 000010101011111111010011(建立哈夫曼树的方式不同，得到的01序列也不同), 压缩率 = 72.73%
 */
public class HuffmanEncode {
    public static void main(String[] args) {
        String str = "AABBBEEEEGZ";
        HuffmanTree huffmanTree = new HuffmanTree(str);
        System.out.println("哈夫曼编码之前：" + str);
        System.out.println("哈夫曼编码之后：" + huffmanTree.huffmanEncodeStr);
        System.out.println("哈夫曼编码压缩率：" + huffmanTree.getZipRate());
    }

    private static class HuffmanTree {
        private HuffmanNode root;
        //每一个字符对应的哈夫曼编码的01序列map
        private final Map<Character, String> encodeMap;
        //定长编码长度
        private int fixedEncodeLength;
        //哈夫曼编码之后的01序列
        private String huffmanEncodeStr;

        HuffmanTree(String str) {
            encodeMap = new HashMap<>();
            buildHuffmanTree(str);
            //dfs建立encodeMap
            buildEncodeMap(root);
            //bfs建立encodeMap
//            buildEncodeMap();
            encode(str);
        }

        private void buildHuffmanTree(String str) {
            //str中字符和出现的次数
            Map<Character, Integer> map = new HashMap<>();
            //存储str中每个字符节点
            List<HuffmanNode> list = new ArrayList<>();

            //统计str中每个字符出现的次数
            for (char c : str.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            //计算定长编码的长度，因为没有2为底的log，所以使用到了换底公式
            fixedEncodeLength = str.length() * (int) Math.ceil(Math.log(map.size()) / Math.log(2));

            //根据map集合创建哈夫曼节点，加入到list集合中
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                HuffmanNode huffmanNode = new HuffmanNode(entry.getKey(), entry.getValue());
                list.add(huffmanNode);
            }

            //每次从list中选择权值最小的两个节点，合并为一个节点
            while (list.size() > 1) {
                //list集合按照weight由小到大排序
                list.sort(new Comparator<HuffmanNode>() {
                    @Override
                    public int compare(HuffmanNode node1, HuffmanNode node2) {
                        return node1.weight - node2.weight;
                    }
                });

                //list集合中权值较小的前2个节点从list中移除，合并为一个huffman节点，再重新放入list中
                HuffmanNode node1 = list.remove(0);
                HuffmanNode node2 = list.remove(0);
                HuffmanNode node = new HuffmanNode(node1.weight + node2.weight);
                node.left = node1;
                node.right = node2;
                //新节点加入list集合
                list.add(node);
            }

            root = list.get(0);
        }

        /**
         * dfs建立encodeMap，对节点的code进行哈夫曼编码
         * 左子树为0，右子树为1
         */
        private void buildEncodeMap(HuffmanNode node) {
            if (node.left == null && node.right == null) {
                encodeMap.put(node.value, node.code);
                return;
            }

            if (node.left != null) {
                node.left.code = node.code + "0";
                buildEncodeMap(node.left);
            }

            if (node.right != null) {
                node.right.code = node.code + "1";
                buildEncodeMap(node.right);
            }
        }

        /**
         * bfs建立encodeMap，对节点的code进行哈夫曼编码
         * 左子树为0，右子树为1
         */
        private void buildEncodeMap() {
            Queue<HuffmanNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                HuffmanNode node = queue.poll();

                //当前节点为叶节点，即需要编码的哈夫曼节点，加入到encodeMap，继续下次循环
                if (node.left == null && node.right == null) {
                    encodeMap.put(node.value, node.code);
                    continue;
                }

                if (node.left != null) {
                    node.left.code = node.code + "0";
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    node.right.code = node.code + "1";
                    queue.offer(node.right);
                }
            }
        }

        private void encode(String str) {
            StringBuilder sb = new StringBuilder();

            for (char c : str.toCharArray()) {
                sb.append(encodeMap.get(c));
            }

            huffmanEncodeStr = sb.toString();
        }

        private double getZipRate() {
            return (double) huffmanEncodeStr.length() / fixedEncodeLength;
        }

        private static class HuffmanNode {
            //当前节点表示的字符
            private char value;
            //当前节点的权值，即为当前字符出现的次数
            private int weight;
            //当前节点的Huffman编码
            private String code;
            private HuffmanNode left;
            private HuffmanNode right;

            public HuffmanNode(char value, int weight) {
                this.value = value;
                this.weight = weight;
                //注意：必须设置当前节点编码为""，不能为null，否则和null拼接之后会输出字符串"null"
                this.code = "";
            }

            public HuffmanNode(int weight) {
                this.weight = weight;
                //注意：必须设置当前节点编码为""，不能为null，否则和null拼接之后会输出字符串"null"
                this.code = "";
            }
        }
    }
}
