package com.zhang.java;

/**
 * @Date 2023/11/11 08:12
 * @Author zsy
 * @Description 展开二维向量 迭代器类比Problem173、Problem281、Problem284、Problem341、Problem1586
 * 请设计并实现一个能够展开二维向量的迭代器。
 * 该迭代器需要支持 next 和 hasNext 两种操作。
 * <p>
 * Vector2D iterator = new Vector2D([[1,2],[3],[4]]);
 * iterator.next(); // 返回 1
 * iterator.next(); // 返回 2
 * iterator.next(); // 返回 3
 * iterator.hasNext(); // 返回 true
 * iterator.hasNext(); // 返回 true
 * iterator.next(); // 返回 4
 * iterator.hasNext(); // 返回 false
 * <p>
 * 请记得 重置 在 Vector2D 中声明的类变量（静态变量），因为类变量会 在多个测试用例中保持不变，影响判题准确。请 查阅 这里。
 * 你可以假定 next() 的调用总是合法的，即当 next() 被调用时，二维向量总是存在至少一个后续元素。
 */
public class Problem251 {
    public static void main(String[] args) {
        int[][] vec = {{1, 2}, {3}, {}, {4, 5, 6}};
        Vector2D iterator = new Vector2D(vec);
        //1
        System.out.println(iterator.next());
        //2
        System.out.println(iterator.next());
        //3
        System.out.println(iterator.next());
        //true
        System.out.println(iterator.hasNext());
        //true
        System.out.println(iterator.hasNext());
        //4
        System.out.println(iterator.next());
        //5
        System.out.println(iterator.next());
        //6
        System.out.println(iterator.next());
        //false
        System.out.println(iterator.hasNext());
    }

    /**
     * 双指针
     * 双指针i、j指向当前遍历到的二维数组下标索引
     */
    static class Vector2D {
        private final int[][] vec;
        private int i;
        private int j;

        public Vector2D(int[][] vec) {
            this.vec = vec;
            i = 0;
            j = 0;
        }

        public int next() {
            int value = vec[i][j];
            j++;

            //当前行vec[i]中元素都遍历完，则i、j指针指向下一行的元素
            while (i < vec.length && j == vec[i].length) {
                i++;
                j = 0;
            }

            return value;
        }

        public boolean hasNext() {
            //i指向最后一行的下一行，j指向第0列，则所有元素都遍历完，不存在下一个元素
            return !(i == vec.length && j == 0);
        }
    }
}
