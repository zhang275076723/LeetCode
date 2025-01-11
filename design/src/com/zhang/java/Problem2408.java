package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/9/29 08:52
 * @Author zsy
 * @Description 设计 SQL
 * 给定 n 个表，用两个数组 names 和 columns 表示，其中 names[i] 是第 i 个表的名称，columns[i] 是第 i 个表的列数。
 * 您能够执行以下 操作:
 * 在特定的表中 插入 一行。插入的每一行都有一个 id。id 是使用自动递增方法分配的，其中第一个插入行的 id 为 1，
 * 插入到同一个表中的其他行的 id 为最后一个插入行的id (即使它已被删除) 加1。
 * 从指定表中 删除 一行。注意，删除一行不会影响下一个插入行的 id。
 * 从任何表中 查询 一个特定的单元格并返回其值。
 * 实现 SQL 类:
 * SQL(String[] names, int[] columns) 创造 n 个表。
 * void insertRow(String name, String[] row) 向表 name 中添加一行。保证 表存在，并且数组 row 的大小等于表中的列数。
 * void deleteRow(String name, int rowId) 从表 name 中移除行 rowId 。保证 表和行都 存在。
 * String selectCell(String name, int rowId, int columnId) 返回表 name 中 rowId 行和 columnId 列中的单元格值。
 * <p>
 * 输入
 * ["SQL", "insertRow", "selectCell", "insertRow", "deleteRow", "selectCell"]
 * [[["one", "two", "three"], [2, 3, 1]], ["two", ["first", "second", "third"]], ["two", 1, 3], ["two", ["fourth", "fifth", "sixth"]], ["two", 1], ["two", 2, 2]]
 * 输出
 * [null, null, "third", null, null, "fifth"]
 * 解释
 * SQL sql = new SQL(["one", "two", "three"], [2, 3, 1]); // 创建三个表。
 * sql.insertRow("two", ["first", "second", "third"]); // 向表 "2" 添加一行。id 是 1。
 * sql.selectCell("two", 1, 3); // 返回 "third"，查找表 "two" 中 id 为 1 的行中第三列的值。
 * sql.insertRow("two", ["fourth", "fifth", "sixth"]); // 将另一行添加到表 "2" 中。它的 id 是 2。
 * sql.deleteRow("two", 1); // 删除表 "two" 的第一行。注意，第二行仍然有 id 2。
 * sql.selectCell("two", 2, 2); // 返回 "fifth"，查找表 "two" 中 id 为 2 的行中第二列的值。
 * <p>
 * n == names.length == columns.length
 * 1 <= n <= 10^4
 * 1 <= names[i].length, row[i].length, name.length <= 20
 * names[i], row[i], name 由小写英文字母组成。
 * 1 <= columns[i] <= 100
 * 所有的 names 字符串都是 不同 的。
 * name 存在于 names.
 * row.length 等于所选表中的列数。
 * rowId 和 columnId 是有效的值。
 * 最多 250 次调用 insertRow 和 deleteRow 。
 * 最多 10^4 次调用 selectCell。
 */
public class Problem2408 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>() {{
            add("one");
            add("two");
            add("three");
        }};
        List<Integer> columns = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(1);
        }};
        // 创建三个表。
        SQL sql = new SQL(names, columns);
        // 向表 "2" 添加一行。id 是 1。
        sql.insertRow("two", new ArrayList<String>() {{
            add("first");
            add("second");
            add("third");
        }});
        // 返回 "third"，查找表 "two" 中 id 为 1 的行中第三列的值。
        System.out.println(sql.selectCell("two", 1, 3));
        // 将另一行添加到表 "2" 中。它的 id 是 2。
        sql.insertRow("two", new ArrayList<String>() {{
            add("fourth");
            add("fifth");
            add("sixth");
        }});
        // 删除表 "two" 的第一行。注意，第二行仍然有 id 2。
        sql.deleteRow("two", 1);
        // 返回 "fifth"，查找表 "two" 中 id 为 2 的行中第二列的值。
        System.out.println(sql.selectCell("two", 2, 2));
    }

    /**
     * 哈希表
     */
    static class SQL {
        //key：数据库表名，value：当前表中数据
        //表的第一行只有一个元素，表示当前表的列数
        //表除了第一行的每一行第一个元素为当前行是否被删除标志位，1：当前行存在，0：当前行被删除
        private final Map<String, List<List<String>>> map;

        public SQL(List<String> names, List<Integer> columns) {
            map = new HashMap<>();

            for (int i = 0; i < names.size(); i++) {
                //表名
                String tableName = names.get(i);
                //表的列数
                int column = columns.get(i);
                //当前表的数据
                List<List<String>> result = new ArrayList<>();
                //当前表第一行数据
                List<String> list = new ArrayList<>();
                //第一行只有一个元素，表示当前表的列数
                list.add(column + "");
                result.add(list);

                map.put(tableName, result);
            }
        }

        public void insertRow(String name, List<String> row) {
            //数据库中不存在name表，或者row的大小不等于表的列数，直接返回
            if (!map.containsKey(name) || row.size() != Integer.parseInt(map.get(name).get(0).get(0))) {
                return;
            }

            List<String> list = new ArrayList<>();
            //当前行数据第一个元素为1，表示当前行未被删除
            list.add("1");
            list.addAll(row);
            map.get(name).add(list);
        }

        public void deleteRow(String name, int rowId) {
            //数据库中不存在name表，或者rowId不合法，直接返回
            if (!map.containsKey(name) || rowId <= 0 || rowId > map.get(name).size()) {
                return;
            }

            //当前行数据第一个元素置为0，表示当前行被删除
            map.get(name).get(rowId).set(0, "0");
        }

        public String selectCell(String name, int rowId, int columnId) {
            //数据库中不存在name表，或者rowId、columnId不合法，或者当前行被删除，直接返回""
            if (!map.containsKey(name) || rowId <= 0 || rowId > map.get(name).size() ||
                    columnId < 0 || columnId > Integer.parseInt(map.get(name).get(0).get(0)) ||
                    "0".equals(map.get(name).get(rowId).get(0))) {
                return "";
            }

            return map.get(name).get(rowId).get(columnId);
        }
    }
}
