package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/2/6 08:39
 * @Author zsy
 * @Description 设计 Excel 求和公式 类比Problem168、Problem171 拓扑排序类比
 * 请你设计 Excel 中的基本功能，并实现求和公式。
 * 实现 Excel 类：
 * Excel(int height, char width)：用高度 height 和宽度 width 初始化对象。
 * 该表格是一个大小为 height x width 的整数矩阵 mat，其中行下标范围是 [1, height] ，列下标范围是 ['A', width] 。
 * 初始情况下，所有的值都应该为 零 。
 * void set(int row, char column, int val)：将 mat[row][column] 的值更改为 val 。
 * int get(int row, char column)：返回 mat[row][column] 的值。
 * int sum(int row, char column, List<String> numbers)：将 mat[row][column] 的值设为由 numbers 表示的单元格的和，
 * 并返回 mat[row][column] 的值。此求和公式应该 长期作用于 该单元格，直到该单元格被另一个值或另一个求和公式覆盖。
 * 其中，numbers[i] 的格式可以为：
 * "ColRow"：表示某个单元格。
 * 例如，"F7" 表示单元格 mat[7]['F'] 。
 * "ColRow1:ColRow2"：表示一组单元格。
 * 该范围将始终为一个矩形，其中 "ColRow1" 表示左上角单元格的位置，"ColRow2" 表示右下角单元格的位置。
 * 例如，"B3:F7" 表示 3 <= i <= 7 和 'B' <= j <= 'F' 的单元格 mat[i][j] 。
 * 注意：可以假设不会出现循环求和引用。
 * 例如，mat[1]['A'] == sum(1, "B")，且 mat[1]['B'] == sum(1, "A") 。
 * <p>
 * 输入：
 * ["Excel", "set", "sum", "set", "get"]
 * [[3, "C"], [1, "A", 2], [3, "C", ["A1", "A1:B2"]], [2, "B", 2], [3, "C"]]
 * 输出：
 * [null, null, 4, null, 6]
 * <p>
 * 解释：
 * 执行以下操作：
 * Excel excel = new Excel(3, "C");
 * // 构造一个 3 * 3 的二维数组，所有值初始化为零。
 * //   A B C
 * // 1 0 0 0
 * // 2 0 0 0
 * // 3 0 0 0
 * excel.set(1, "A", 2);
 * // 将 mat[1]["A"] 设置为 2 。
 * //   A B C
 * // 1 2 0 0
 * // 2 0 0 0
 * // 3 0 0 0
 * excel.sum(3, "C", ["A1", "A1:B2"]); // 返回 4
 * // 将 mat[3]["C"] 设置为 mat[1]["A"] 的值与矩形范围的单元格和的和，该范围的左上角单元格位置为 mat[1]["A"] ，右下角单元格位置为 mat[2]["B"] 。
 * //   A B C
 * // 1 2 0 0
 * // 2 0 0 0
 * // 3 0 0 4
 * excel.set(2, "B", 2);
 * // 将 mat[2]["B"] 设置为 2 。注意 mat[3]["C"] 也应该更改。
 * //   A B C
 * // 1 2 0 0
 * // 2 0 2 0
 * // 3 0 0 6
 * excel.get(3, "C"); // 返回 6
 * <p>
 * 1 <= height <= 26
 * 'A' <= width <= 'Z'
 * 1 <= row <= height
 * 'A' <= column <= width
 * -100 <= val <= 100
 * 1 <= numbers.length <= 5
 * numbers[i] 的格式为 "ColRow" 或 "ColRow1:ColRow2" 。
 * 最多会对 set 、get 和 sum 进行 100 次调用。
 */
public class Problem631 {
    public static void main(String[] args) {
        Excel excel = new Excel(3, 'C');
        excel.set(1, 'A', 2);
        System.out.println(excel.sum(3, 'C', new ArrayList<String>() {{
            add("A1");
            add("A1:B2");
        }}));
        excel.set(2, 'B', 2);
        System.out.println(excel.get(3, 'C'));
    }

    /**
     * 拓扑排序
     * 节点之间存在求和关系，即节点之间存在有向边，在对某个节点更新时，要从当前节点开始dfs，更新邻接节点的值，
     * 同时当节点之间不存在依赖关系时，要及时清除其他节点到当前节点的有向边
     */
    public static class Excel {
        private final int m;
        private final int n;
        //存储元素的二维数组
        private final int[][] arr;
        //邻接矩阵，有向图
        //注意：共mn个节点，每个节点都可以其他节点存在边，即为稠密图，适用邻接矩阵，不适用邻接表
        //注意：两个节点之间可以存在多条边，即graph的值表示边的个数
        private final int[][] graph;

        public Excel(int height, char width) {
            m = height;
            n = width - 'A' + 1;
            arr = new int[m][n];
            graph = new int[m * n][m * n];
        }

        /**
         * 1、删除指向当前节点的所有边，如果当前节点(row,column)是求和公式，则当前求和公式失效
         * 2、修改当前节点(row,column)值为val
         * 3、从当前节点开始dfs，更新路径上节点的值，如果存在当前节点到其他节点的边，则更新邻接节点的值
         * 时间复杂度O((mn)^2)，空间复杂度O(mn)
         *
         * @param row
         * @param column
         * @param val
         */
        public void set(int row, char column, int val) {
            //从当前节点开始dfs，路径上的节点需要加上的值
            int addValue = val - arr[row - 1][column - 'A'];
            //当前节点在邻接矩阵中的下标索引
            int index = (row - 1) * n + column - 'A';

            //1、删除指向当前节点的所有边，如果当前节点(row,column)是求和公式，则当前求和公式失效
            for (int i = 0; i < m * n; i++) {
                graph[i][index] = 0;
            }

            //2、修改当前节点(row,column)值为val
            arr[row - 1][column - 'A'] = val;

            //3、从当前节点开始dfs，更新路径上节点的值，如果存在当前节点到其他节点的边，则更新邻接节点的值
            dfs(index, addValue);
        }

        public int get(int row, char column) {
            return arr[row - 1][column - 'A'];
        }

        /**
         * 1、删除指向当前节点的所有边，如果当前节点(row,column)是求和公式，则当前求和公式失效
         * 2、遍历numbers，在图中添加指向当前节点的边，并计算numbers中元素之和result
         * 3、修改当前节点(row,column)值为result
         * 4、从当前节点开始dfs，更新路径上节点的值，如果存在当前节点到其他节点的边，则更新邻接节点的值
         * 时间复杂度O((mn)^2)，空间复杂度O(mn)
         *
         * @param row
         * @param column
         * @param numbers
         * @return
         */
        public int sum(int row, char column, List<String> numbers) {
            //当前节点(row,column)的值
            int result = 0;
            //当前节点在邻接矩阵中的下标索引
            int index = (row - 1) * n + column - 'A';

            //1、删除指向当前节点的所有边，如果当前节点(row,column)是求和公式，则当前求和公式失效
            for (int i = 0; i < m * n; i++) {
                graph[i][index] = 0;
            }

            //2、遍历numbers，在图中添加指向当前节点的边
            for (String number : numbers) {
                //number为某个单元格
                if (!number.contains(":")) {
                    //当前单元格的横坐标
                    int x = Integer.parseInt(number.substring(1)) - 1;
                    //当前单元格的纵坐标
                    int y = number.charAt(0) - 'A';

                    //节点(x,y)到当前节点(row,column)的边加1
                    graph[x * n + y][index]++;
                    result = result + arr[x][y];
                } else {
                    //number为一组单元格

                    //number中':'下标索引
                    int colonIndex = number.indexOf(':');
                    //矩阵左上角单元格的横坐标
                    int x1 = Integer.parseInt(number.substring(1, colonIndex)) - 1;
                    //矩阵左上角单元格的纵坐标
                    int y1 = number.charAt(0) - 'A';
                    //矩阵右下角单元格的横坐标
                    int x2 = Integer.parseInt(number.substring(colonIndex + 2)) - 1;
                    //矩阵右下角单元格的纵坐标
                    int y2 = number.charAt(colonIndex + 1) - 'A';

                    //矩阵(x1,y1)到(x2,y2)中的节点都要添加
                    for (int i = x1; i <= x2; i++) {
                        for (int j = y1; j <= y2; j++) {
                            //节点(i,j)到当前节点(row,column)的边加1
                            graph[i * n + j][index]++;
                            result = result + arr[i][j];
                        }
                    }
                }
            }

            //从当前节点开始dfs，路径上的节点需要加上的值
            int addValue = result - arr[row - 1][column - 'A'];
            //3、修改当前节点(row,column)值为result
            arr[row - 1][column - 'A'] = result;

            //4、从当前节点开始dfs，更新路径上节点的值，如果存在当前节点到其他节点的边，则更新邻接节点的值
            dfs(index, addValue);

            return result;
        }

        /**
         * 从节点u开始dfs，更新邻接节点i的值加上addValue
         * 时间复杂度O((mn)^2)，空间复杂度O(mn)
         *
         * @param u
         * @param addValue
         */
        private void dfs(int u, int addValue) {
            //从节点u找邻接节点i，更新节点i的值加上addValue
            for (int i = 0; i < m * n; i++) {
                if (graph[u][i] != 0) {
                    //节点i在arr中的横坐标
                    int x = i / n;
                    //节点i在arr中的纵坐标
                    int y = i % n;
                    //注意：节点u到节点i的边不止存在一条，所以是加上addValue乘以边数，而不是加上addValue
                    arr[x][y] = arr[x][y] + graph[u][i] * addValue;
                    //注意：节点i到节点i邻接节点的边，加上addValue乘以边数
                    dfs(i, graph[u][i] * addValue);
                }
            }
        }
    }
}
