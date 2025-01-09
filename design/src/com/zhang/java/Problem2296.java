package com.zhang.java;

/**
 * @Date 2024/9/5 08:38
 * @Author zsy
 * @Description 设计一个文本编辑器 类比Problem622、Problem641、Problem1472
 * 请你设计一个带光标的文本编辑器，它可以实现以下功能：
 * 添加：在光标所在处添加文本。
 * 删除：在光标所在处删除文本（模拟键盘的删除键）。
 * 移动：将光标往左或者往右移动。
 * 当删除文本时，只有光标左边的字符会被删除。
 * 光标会留在文本内，也就是说任意时候 0 <= cursor.position <= currentText.length 都成立。
 * 请你实现 TextEditor 类：
 * TextEditor() 用空文本初始化对象。
 * void addText(string text) 将 text 添加到光标所在位置。添加完后光标在 text 的右边。
 * int deleteText(int k) 删除光标左边 k 个字符。返回实际删除的字符数目。
 * string cursorLeft(int k) 将光标向左移动 k 次。返回移动后光标左边 min(10, len) 个字符，其中 len 是光标左边的字符数目。
 * string cursorRight(int k) 将光标向右移动 k 次。返回移动后光标左边 min(10, len) 个字符，其中 len 是光标左边的字符数目。
 * <p>
 * 输入：
 * ["TextEditor", "addText", "deleteText", "addText", "cursorRight", "cursorLeft", "deleteText", "cursorLeft", "cursorRight"]
 * [[], ["leetcode"], [4], ["practice"], [3], [8], [10], [2], [6]]
 * 输出：
 * [null, null, 4, null, "etpractice", "leet", 4, "", "practi"]
 * 解释：
 * TextEditor textEditor = new TextEditor(); // 当前 text 为 "|" 。（'|' 字符表示光标）
 * textEditor.addText("leetcode"); // 当前文本为 "leetcode|" 。
 * textEditor.deleteText(4); // 返回 4
 * <                         // 当前文本为 "leet|" 。
 * <                         // 删除了 4 个字符。
 * textEditor.addText("practice"); // 当前文本为 "leetpractice|" 。
 * textEditor.cursorRight(3); // 返回 "etpractice"
 * <                          // 当前文本为 "leetpractice|".
 * <                          // 光标无法移动到文本以外，所以无法移动。
 * <                          // "etpractice" 是光标左边的 10 个字符。
 * textEditor.cursorLeft(8); // 返回 "leet"
 * <                         // 当前文本为 "leet|practice" 。
 * <                         // "leet" 是光标左边的 min(10, 4) = 4 个字符。
 * textEditor.deleteText(10); // 返回 4
 * <                          // 当前文本为 "|practice" 。
 * <                          // 只有 4 个字符被删除了。
 * textEditor.cursorLeft(2); // 返回 ""
 * <                         // 当前文本为 "|practice" 。
 * <                         // 光标无法移动到文本以外，所以无法移动。
 * <                         // "" 是光标左边的 min(10, 0) = 0 个字符。
 * textEditor.cursorRight(6); // 返回 "practi"
 * <                          // 当前文本为 "practi|ce" 。
 * <                          // "practi" 是光标左边的 min(10, 6) = 6 个字符。
 * <p>
 * 1 <= text.length, k <= 40
 * text 只含有小写英文字母。
 * 调用 addText ，deleteText ，cursorLeft 和 cursorRight 的 总 次数不超过 2 * 10^4 次。
 */
public class Problem2296 {
    public static void main(String[] args) {
        // 当前 text 为 "|" 。（'|' 字符表示光标）
//        TextEditor textEditor = new TextEditor();
        TextEditor2 textEditor = new TextEditor2();
        // 当前文本为 "leetcode|" 。
        textEditor.addText("leetcode");
        // 返回 4
        // 当前文本为 "leet|" 。
        // 删除了 4 个字符。
        System.out.println(textEditor.deleteText(4));
        // 当前文本为 "leetpractice|" 。
        textEditor.addText("practice");
        // 返回 "etpractice"
        // 当前文本为 "leetpractice|".
        // 光标无法移动到文本以外，所以无法移动。
        // "etpractice" 是光标左边的 10 个字符。
        System.out.println(textEditor.cursorRight(3));
        // 返回 "leet"
        // 当前文本为 "leet|practice" 。
        // "leet" 是光标左边的 min(10, 4) = 4 个字符。
        System.out.println(textEditor.cursorLeft(8));
        // 返回 4
        // 当前文本为 "|practice" 。
        // 只有 4 个字符被删除了。
        System.out.println(textEditor.deleteText(10));
        // 返回 ""
        // 当前文本为 "|practice" 。
        // 光标无法移动到文本以外，所以无法移动。
        // "" 是光标左边的 min(10, 0) = 0 个字符。
        System.out.println(textEditor.cursorLeft(2));
        // 返回 "practi"
        // 当前文本为 "practi|ce" 。
        // "practi" 是光标左边的 min(10, 6) = 6 个字符。
        System.out.println(textEditor.cursorRight(6));
    }

    /**
     * 字符串
     */
    static class TextEditor {
        private final StringBuilder sb;
        //sb中光标当前所在位置的下标索引
        private int curIndex;

        public TextEditor() {
            sb = new StringBuilder();
            curIndex = 0;
        }

        public void addText(String text) {
            sb.insert(curIndex, text);
            curIndex = curIndex + text.length();
        }

        public int deleteText(int k) {
            //删除k个字符后光标的下标索引
            int nextIndex = Math.max(curIndex - k, 0);
            //实际删除的字符个数
            int count = curIndex - nextIndex;
            sb.delete(nextIndex, curIndex);
            curIndex = nextIndex;

            return count;
        }

        public String cursorLeft(int k) {
            curIndex = Math.max(curIndex - k, 0);

            return sb.substring(Math.max(curIndex - 10, 0), curIndex);
        }

        public String cursorRight(int k) {
            curIndex = Math.min(curIndex + k, sb.length());

            return sb.substring(Math.max(curIndex - 10, 0), curIndex);
        }
    }

    /**
     * 双向链表
     */
    static class TextEditor2 {
        private final LinkedList linkedList;
        //链表中光标当前所在的节点，即每次插入当前节点下一个节点
        private Node curNode;

        public TextEditor2() {
            linkedList = new LinkedList();
            curNode = linkedList.head;
        }

        public void addText(String text) {
            for (char c : text.toCharArray()) {
                Node node = new Node(c);
                linkedList.addAfter(node, curNode);
                curNode = node;
            }
        }

        public int deleteText(int k) {
            //实际删除的字符个数
            int count = 0;

            //从光标位置最多往左删除k个字符
            while (k > 0 && curNode != linkedList.head) {
                Node preNode = curNode.pre;
                linkedList.remove(curNode);
                curNode = preNode;
                count++;
                k--;
            }

            return count;
        }

        public String cursorLeft(int k) {
            //从光标位置最多往左移动k位
            while (k > 0 && curNode != linkedList.head) {
                curNode = curNode.pre;
                k--;
            }

            StringBuilder sb = new StringBuilder();
            Node node = curNode;
            int count = 10;

            //从光标位置最多往左找10个字符
            while (count > 0 && node != linkedList.head) {
                sb.append(node.value);
                node = node.pre;
                count--;
            }

            //因为是从光标位置往左找，所以需要反转
            return sb.reverse().toString();
        }

        public String cursorRight(int k) {
            //从光标位置最多往右移动k位
            //注意：和左移不同，右移光标只能停留在尾节点的前驱节点
            while (k > 0 && curNode != linkedList.tail.pre) {
                curNode = curNode.next;
                k--;
            }

            StringBuilder sb = new StringBuilder();
            Node node = curNode;
            int count = 10;

            //从光标位置最多往左找10个字符
            while (count > 0 && node != linkedList.head) {
                sb.append(node.value);
                node = node.pre;
                count--;
            }

            //因为是从光标位置往左找，所以需要反转
            return sb.reverse().toString();
        }

        /**
         * 双向链表
         */
        private static class LinkedList {
            private final Node head;
            private final Node tail;

            public LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
            }

            public void addAfter(Node node, Node baseNode) {
                Node nextNode = baseNode.next;
                baseNode.next = node;
                node.pre = baseNode;
                node.next = nextNode;
                nextNode.pre = node;
            }

            public void remove(Node node) {
                Node preNode = node.pre;
                Node nextNode = node.next;
                preNode.next = nextNode;
                nextNode.pre = preNode;
                node.pre = null;
                node.next = null;
            }
        }

        /**
         * 双向链表节点
         */
        private static class Node {
            private char value;
            private Node pre;
            private Node next;

            public Node() {
            }

            public Node(char c) {
                this.value = c;
            }
        }
    }
}
