package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/10/2 08:39
 * @Author zsy
 * @Description 设计一个待办事项清单 有序集合类比
 * 设计一个待办事项清单，用户可以添加 任务 ，标记任务为 完成状态 ，或获取待办任务列表。
 * 用户还可以为任务添加 标签 ，并可以按照特定标签筛选任务。
 * 实现 TodoList 类：
 * TodoList() 初始化对象。
 * int addTask(int userId, String taskDescription, int dueDate, List<String> tags) 为用户 ID 为 userId 的用户添加一个任务，
 * 该任务的到期日期为 dueDate ，附带了一个标签列表 tags 。
 * 返回值为任务的 ID 。该 ID 从 1 开始，依次 递增。即，第一个任务的ID应为 1 ，第二个任务的 ID 应为 2 ，以此类推。
 * List<String> getAllTasks(int userId) 返回未标记为完成状态的 ID 为 userId 的用户的所有任务列表，按照到期日期排序。
 * 如果用户没有未完成的任务，则应返回一个空列表。
 * List<String> getTasksForTag(int userId, String tag) 返回 ID 为 userId 的用户未标记为完成状态且具有 tag 标签之一的所有任务列表，
 * 按照到期日期排序。如果不存在此类任务，则返回一个空列表。
 * void completeTask(int userId, int taskId) 仅在任务存在且 ID 为 userId 的用户拥有此任务且它是未完成状态时，
 * 将 ID 为 taskId 的任务标记为已完成状态。
 * <p>
 * 输入
 * ["TodoList", "addTask", "addTask", "getAllTasks", "getAllTasks", "addTask", "getTasksForTag", "completeTask", "completeTask", "getTasksForTag", "getAllTasks"]
 * [[], [1, "Task1", 50, []], [1, "Task2", 100, ["P1"]], [1], [5], [1, "Task3", 30, ["P1"]], [1, "P1"], [5, 1], [1, 2], [1, "P1"], [1]]
 * 输出
 * [null, 1, 2, ["Task1", "Task2"], [], 3, ["Task3", "Task2"], null, null, ["Task3"], ["Task3", "Task1"]]
 * 解释
 * TodoList todoList = new TodoList();
 * todoList.addTask(1, "Task1", 50, []); // 返回1。为ID为1的用户添加一个新任务。
 * todoList.addTask(1, "Task2", 100, ["P1"]); // 返回2。为ID为1的用户添加另一个任务，并给它添加标签“P1”。
 * todoList.getAllTasks(1); // 返回["Task1", "Task2"]。用户1目前有两个未完成的任务。
 * todoList.getAllTasks(5); // 返回[]。用户5目前没有任务。
 * todoList.addTask(1, "Task3", 30, ["P1"]); // 返回3。为ID为1的用户添加另一个任务，并给它添加标签“P1”。
 * todoList.getTasksForTag(1, "P1"); // 返回["Task3", "Task2"]。返回ID为1的用户未完成的带有“P1”标签的任务。
 * todoList.completeTask(5, 1); // 不做任何操作，因为任务1不属于用户5。
 * todoList.completeTask(1, 2); // 将任务2标记为已完成。
 * todoList.getTasksForTag(1, "P1"); // 返回["Task3"]。返回ID为1的用户未完成的带有“P1”标签的任务。
 * // 注意，现在不包括 “Task2” ，因为它已经被标记为已完成。
 * todoList.getAllTasks(1); // 返回["Task3", "Task1"]。用户1现在有两个未完成的任务。
 * <p>
 * 1 <= userId, taskId, dueDate <= 100
 * 0 <= tags.length <= 100
 * 1 <= taskDescription.length <= 50
 * 1 <= tags[i].length, tag.length <= 20
 * 所有的 dueDate 值都是唯一的。
 * 所有字符串都由小写和大写英文字母和数字组成。
 * 每个方法最多被调用 100 次。
 */
public class Problem2590 {
    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        // 返回1。为ID为1的用户添加一个新任务。
        System.out.println(todoList.addTask(1, "Task1", 50, new ArrayList<>()));
        // 返回2。为ID为1的用户添加另一个任务，并给它添加标签“P1”。
        System.out.println(todoList.addTask(1, "Task2", 100, new ArrayList<String>() {{
            add("P1");
        }}));
        // 返回["Task1", "Task2"]。用户1目前有两个未完成的任务。
        System.out.println(todoList.getAllTasks(1));
        // 返回[]。用户5目前没有任务。
        System.out.println(todoList.getAllTasks(5));
        // 返回3。为ID为1的用户添加另一个任务，并给它添加标签“P1”。
        System.out.println(todoList.addTask(1, "Task3", 30, new ArrayList<String>() {{
            add("P1");
        }}));
        // 返回["Task3", "Task2"]。返回ID为1的用户未完成的带有“P1”标签的任务。
        System.out.println(todoList.getTasksForTag(1, "P1"));
        // 不做任何操作，因为任务1不属于用户5。
        todoList.completeTask(5, 1);
        // 将任务2标记为已完成。
        todoList.completeTask(1, 2);
        // 返回["Task3"]。返回ID为1的用户未完成的带有“P1”标签的任务。
        // 注意，现在不包括 “Task2” ，因为它已经被标记为已完成。
        System.out.println(todoList.getTasksForTag(1, "P1"));
        // 返回["Task3", "Task1"]。用户1现在有两个未完成的任务。
        System.out.println(todoList.getAllTasks(1));
    }

    /**
     * 哈希表+有序集合
     */
    static class TodoList {
        //下一个新建的任务id
        private int nextTaskId;
        //key：用户id，value：用户节点
        private final Map<Integer, User> userMap;

        public TodoList() {
            nextTaskId = 1;
            userMap = new HashMap<>();
        }

        public int addTask(int userId, String taskDescription, int dueDate, List<String> tags) {
            if (!userMap.containsKey(userId)) {
                userMap.put(userId, new User(userId));
            }

            User user = userMap.get(userId);
            Task task = new Task(nextTaskId, taskDescription, dueDate, tags);
            nextTaskId++;

            user.unfinishedTaskTreeSet.add(task);

            return task.taskId;
        }

        public List<String> getAllTasks(int userId) {
            if (!userMap.containsKey(userId)) {
                return new ArrayList<>();
            }

            List<String> list = new ArrayList<>();
            User user = userMap.get(userId);

            //task已经按照到期时间排序，则可以直接遍历
            for (Task task : user.unfinishedTaskTreeSet) {
                list.add(task.taskDescription);
            }

            return list;
        }

        public List<String> getTasksForTag(int userId, String tag) {
            if (!userMap.containsKey(userId)) {
                return new ArrayList<>();
            }

            List<String> list = new ArrayList<>();
            User user = userMap.get(userId);

            //task已经按照到期时间排序，则可以直接遍历
            for (Task task : user.unfinishedTaskTreeSet) {
                if (task.tags.contains(tag)) {
                    list.add(task.taskDescription);
                }
            }

            return list;
        }

        public void completeTask(int userId, int taskId) {
            if (!userMap.containsKey(userId)) {
                return;
            }

            User user = userMap.get(userId);

            //task已经按照到期时间排序，则可以直接遍历
            for (Task task : user.unfinishedTaskTreeSet) {
                if (task.taskId == taskId) {
                    user.unfinishedTaskTreeSet.remove(task);
                    user.finishedTaskSet.add(task);
                    break;
                }
            }
        }

        /**
         * 用户节点
         */
        private static class User {
            //用户id
            private int userId;
            //按照到期时间排序的未完成的任务有序集合
            private final TreeSet<Task> unfinishedTaskTreeSet;
            //已完成的任务集合
            private final Set<Task> finishedTaskSet;

            public User(int userId) {
                unfinishedTaskTreeSet = new TreeSet<>(new Comparator<Task>() {
                    @Override
                    public int compare(Task task1, Task task2) {
                        return task1.dueDate - task2.dueDate;
                    }
                });
                finishedTaskSet = new HashSet<>();
            }
        }

        /**
         * 任务节点
         */
        private static class Task {
            //任务id
            private int taskId;
            //任务名
            private String taskDescription;
            //任务到期时间
            private int dueDate;
            //任务标签集合
            //使用set，O(1)判断某个标签是否是tags中元素
            private Set<String> tags;

            public Task(int taskId, String taskDescription, int dueDate, List<String> tags) {
                this.taskId = taskId;
                this.taskDescription = taskDescription;
                this.dueDate = dueDate;
                this.tags = new HashSet<>(tags);
            }

            @Override
            public int hashCode() {
                return taskId + taskDescription.hashCode() + dueDate + tags.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Task) {
                    Task task = (Task) obj;

                    //因为taskId递增，所以只需要比较taskId就能判断两个任务是否相等
                    return taskId == task.taskId;
                }

                return false;
            }
        }
    }
}
