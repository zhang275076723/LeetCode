package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/9/24 08:30
 * @Author zsy
 * @Description 设计食物评分系统 延迟删除类比Problem480、Problem855、Problem2034、Problem2349 有序集合类比Problem220、Problem352、Problem363、Problem855、Problem981、Problem1146、Problem1348、Problem1912、Problem2034、Problem2071、Problem2349、Problem2502、Problem2590
 * 设计一个支持下述操作的食物评分系统：
 * 修改 系统中列出的某种食物的评分。
 * 返回系统中某一类烹饪方式下评分最高的食物。
 * 实现 FoodRatings 类：
 * FoodRatings(String[] foods, String[] cuisines, int[] ratings) 初始化系统。
 * 食物由 foods、cuisines 和 ratings 描述，长度均为 n 。
 * foods[i] 是第 i 种食物的名字。
 * cuisines[i] 是第 i 种食物的烹饪方式。
 * ratings[i] 是第 i 种食物的最初评分。
 * void changeRating(String food, int newRating) 修改名字为 food 的食物的评分。
 * String highestRated(String cuisine) 返回指定烹饪方式 cuisine 下评分最高的食物的名字。
 * 如果存在并列，返回 字典序较小 的名字。
 * 注意，字符串 x 的字典序比字符串 y 更小的前提是：x 在字典中出现的位置在 y 之前，也就是说，要么 x 是 y 的前缀，
 * 或者在满足 x[i] != y[i] 的第一个位置 i 处，x[i] 在字母表中出现的位置在 y[i] 之前。
 * <p>
 * 输入
 * ["FoodRatings", "highestRated", "highestRated", "changeRating", "highestRated", "changeRating", "highestRated"]
 * [[["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"], ["korean", "japanese", "japanese", "greek", "japanese", "korean"], [9, 12, 8, 15, 14, 7]], ["korean"], ["japanese"], ["sushi", 16], ["japanese"], ["ramen", 16], ["japanese"]]
 * 输出
 * [null, "kimchi", "ramen", null, "sushi", null, "ramen"]
 * 解释
 * FoodRatings foodRatings = new FoodRatings(["kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"], ["korean", "japanese", "japanese", "greek", "japanese", "korean"], [9, 12, 8, 15, 14, 7]);
 * foodRatings.highestRated("korean"); // 返回 "kimchi"
 * <                                   // "kimchi" 是分数最高的韩式料理，评分为 9 。
 * foodRatings.highestRated("japanese"); // 返回 "ramen"
 * <                                     // "ramen" 是分数最高的日式料理，评分为 14 。
 * foodRatings.changeRating("sushi", 16); // "sushi" 现在评分变更为 16 。
 * foodRatings.highestRated("japanese"); // 返回 "sushi"
 * <                                     // "sushi" 是分数最高的日式料理，评分为 16 。
 * foodRatings.changeRating("ramen", 16); // "ramen" 现在评分变更为 16 。
 * foodRatings.highestRated("japanese"); // 返回 "ramen"
 * <                                     // "sushi" 和 "ramen" 的评分都是 16 。
 * <                                     // 但是，"ramen" 的字典序比 "sushi" 更小。
 * <p>
 * 1 <= n <= 2 * 10^4
 * n == foods.length == cuisines.length == ratings.length
 * 1 <= foods[i].length, cuisines[i].length <= 10
 * foods[i]、cuisines[i] 由小写英文字母组成
 * 1 <= ratings[i] <= 10^8
 * foods 中的所有字符串 互不相同
 * 在对 changeRating 的所有调用中，food 是系统中食物的名字。
 * 在对 highestRated 的所有调用中，cuisine 是系统中 至少一种 食物的烹饪方式。
 * 最多调用 changeRating 和 highestRated 总计 2 * 10^4 次
 */
public class Problem2353 {
    public static void main(String[] args) {
        String[] foods = {"kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi"};
        String[] cuisines = {"korean", "japanese", "japanese", "greek", "japanese", "korean"};
        int[] ratings = {9, 12, 8, 15, 14, 7};
//        FoodRatings foodRatings = new FoodRatings(foods, cuisines, ratings);
        FoodRatings2 foodRatings = new FoodRatings2(foods, cuisines, ratings);
        // 返回 "kimchi"
        // "kimchi" 是分数最高的韩式料理，评分为 9 。
        System.out.println(foodRatings.highestRated("korean"));
        // 返回 "ramen"
        // "ramen" 是分数最高的日式料理，评分为 14 。
        System.out.println(foodRatings.highestRated("japanese"));
        // "sushi" 现在评分变更为 16 。
        foodRatings.changeRating("sushi", 16);
        // 返回 "sushi"
        // "sushi" 是分数最高的日式料理，评分为 16 。
        System.out.println(foodRatings.highestRated("japanese"));
        // "ramen" 现在评分变更为 16 。
        foodRatings.changeRating("ramen", 16);
        // 返回 "ramen"
        // "sushi" 和 "ramen" 的评分都是 16 。
        // 但是，"ramen" 的字典序比 "sushi" 更小。
        System.out.println(foodRatings.highestRated("japanese"));
    }

    /**
     * 哈希表+有序集合
     */
    static class FoodRatings {
        //key：食物名，value：食物节点
        private final Map<String, Food> foodMap;
        //key：食物的烹饪方式，value：相同烹饪方式食物按照评分由高到低，食物名字典序由小到大排序的有序集合
        //注意：有序集合中key唯一并且不能修改，如果要修改，则需要先删除，再添加
        private final Map<String, TreeSet<Food>> treeSetMap;

        public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
            foodMap = new HashMap<>();
            treeSetMap = new HashMap<>();

            for (int i = 0; i < foods.length; i++) {
                Food food = new Food(foods[i], cuisines[i], ratings[i]);
                foodMap.put(foods[i], food);

                if (!treeSetMap.containsKey(cuisines[i])) {
                    treeSetMap.put(cuisines[i], new TreeSet<>(new Comparator<Food>() {
                        @Override
                        public int compare(Food food1, Food food2) {
                            //相同烹饪方式食物按照评分由高到低，食物名字典序由小到大排序
                            if (food1.rating != food2.rating) {
                                return food2.rating - food1.rating;
                            } else {
                                return food1.foodName.compareTo(food2.foodName);
                            }
                        }
                    }));
                }

                //cuisines[i]所在的有序集合中加入food
                TreeSet<Food> treeSet = treeSetMap.get(cuisines[i]);
                treeSet.add(food);
            }
        }

        public void changeRating(String foodName, int newRating) {
            //foodName不是系统中食物的名字，则直接返回
            if (!foodMap.containsKey(foodName)) {
                return;
            }

            Food food = foodMap.get(foodName);
            TreeSet<Food> treeSet = treeSetMap.get(food.cuisine);
            //注意：有序集合中修改需要先删除，再添加
            treeSet.remove(food);
            food.rating = newRating;
            treeSet.add(food);
        }

        public String highestRated(String cuisine) {
            //cuisine不是系统中食物的烹饪方式，则返回""
            if (!treeSetMap.containsKey(cuisine)) {
                return "";
            }

            //返回cuisine所在的有序集合中第一个食物名，即为评分最高字典序最小的食物名
            return treeSetMap.get(cuisine).first().foodName;
        }

        /**
         * 食物节点
         */
        private static class Food {
            //食物名
            private String foodName;
            //烹饪方式
            private String cuisine;
            //评分
            private int rating;

            public Food(String foodName, String cuisine, int rating) {
                this.foodName = foodName;
                this.cuisine = cuisine;
                this.rating = rating;
            }

            @Override
            public int hashCode() {
                return foodName.hashCode() + cuisine.hashCode() + rating;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Food) {
                    Food food = (Food) obj;

                    return foodName.equals(food.foodName) && cuisine.equals(food.cuisine) && rating == food.rating;
                }

                return false;
            }
        }
    }

    /**
     * 哈希表+优先队列，大根堆+延迟删除
     * 因为大根堆只能移除堆顶元素，所以对于非堆顶元素的删除使用延迟删除，在更新rating时，
     * cuisine所在大根堆中并不删除当前food，而是查找cuisine评分最高字典序最小的食物名时，
     * 如果堆顶元素食物名在foodMap中对应元素的评分不等于堆顶元素的评分，则堆顶元素评分发生了修改，删除堆顶元素
     */
    static class FoodRatings2 {
        //key：食物名，value：食物节点
        private final Map<String, Food> foodMap;
        //key：食物的烹饪方式，value：存储相同烹饪方式食物按照评分由高到低，食物名字典序由小到大排序的大根堆
        private final Map<String, PriorityQueue<Food>> priorityQueueMap;

        public FoodRatings2(String[] foods, String[] cuisines, int[] ratings) {
            foodMap = new HashMap<>();
            priorityQueueMap = new HashMap<>();

            for (int i = 0; i < foods.length; i++) {
                Food food = new Food(foods[i], cuisines[i], ratings[i]);
                foodMap.put(foods[i], food);

                if (!priorityQueueMap.containsKey(cuisines[i])) {
                    priorityQueueMap.put(cuisines[i], new PriorityQueue<>(new Comparator<Food>() {
                        @Override
                        public int compare(Food food1, Food food2) {
                            //相同烹饪方式食物按照评分由高到低，食物名字典序由小到大排序
                            if (food1.rating != food2.rating) {
                                return food2.rating - food1.rating;
                            } else {
                                return food1.foodName.compareTo(food2.foodName);
                            }
                        }
                    }));
                }

                //cuisines[i]所在的大根堆中加入food
                PriorityQueue<Food> priorityQueue = priorityQueueMap.get(cuisines[i]);
                priorityQueue.offer(food);
            }
        }

        public void changeRating(String foodName, int newRating) {
            //foodName不是系统中食物的名字，则直接返回
            if (!foodMap.containsKey(foodName)) {
                return;
            }

            //未修改评分之前的食物节点
            Food food = foodMap.get(foodName);
            //修改评分之后的食物节点
            Food newFood = new Food(food.foodName, food.cuisine, newRating);
            foodMap.put(foodName, newFood);
            //food.cuisine所在的大根堆中加入newFood
            //注意：food.cuisine所在的大根堆中并不删除food，而是在查找cuisine下评分最高食物名时延迟删除
            PriorityQueue<Food> priorityQueue = priorityQueueMap.get(food.cuisine);
            priorityQueue.offer(newFood);
        }

        public String highestRated(String cuisine) {
            //cuisine不是系统中食物的烹饪方式，则返回""
            if (!priorityQueueMap.containsKey(cuisine)) {
                return "";
            }

            PriorityQueue<Food> priorityQueue = priorityQueueMap.get(cuisine);

            //堆顶元素食物名在foodMap中对应元素的评分不等于堆顶元素的评分，则堆顶元素评分发生了修改，删除堆顶元素
            while (!priorityQueue.isEmpty() && foodMap.get(priorityQueue.peek().foodName).rating != priorityQueue.peek().rating) {
                priorityQueue.poll();
            }

            //大根堆堆顶元素，即为即为评分最高字典序最小的食物名
            return priorityQueue.peek().foodName;
        }

        /**
         * 食物节点
         */
        private static class Food {
            //食物名
            private String foodName;
            //烹饪方式
            private String cuisine;
            //评分
            private int rating;

            public Food(String foodName, String cuisine, int rating) {
                this.foodName = foodName;
                this.cuisine = cuisine;
                this.rating = rating;
            }
        }
    }
}
