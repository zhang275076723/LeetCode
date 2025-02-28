package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/10/1 08:58
 * @Author zsy
 * @Description 设计电影租借系统 有序集合类比Problem220、Problem352、Problem363、Problem855、Problem981、Problem1146、Problem1348、Problem2034、Problem2071、Problem2349、Problem2353、Problem2502、Problem2590
 * 你有一个电影租借公司和 n 个电影商店。你想要实现一个电影租借系统，它支持查询、预订和返还电影的操作。
 * 同时系统还能生成一份当前被借出电影的报告。
 * 所有电影用二维整数数组 entries 表示，其中 entries[i] = [shopi, moviei, pricei] 表示商店 shopi 有一份电影 moviei 的拷贝，
 * 租借价格为 pricei 。每个商店有 至多一份 编号为 moviei 的电影拷贝。
 * 系统需要支持以下操作：
 * Search：找到拥有指定电影且 未借出 的商店中 最便宜的 5 个 。
 * 商店需要按照 价格 升序排序，如果价格相同，则 shopi 较小 的商店排在前面。
 * 如果查询结果少于 5 个商店，则将它们全部返回。
 * 如果查询结果没有任何商店，则返回空列表。
 * Rent：从指定商店借出指定电影，题目保证指定电影在指定商店 未借出 。
 * Drop：在指定商店返还 之前已借出 的指定电影。
 * Report：返回 最便宜的 5 部已借出电影 （可能有重复的电影 ID），将结果用二维列表 res 返回，
 * 其中 res[j] = [shopj, moviej] 表示第 j 便宜的已借出电影是从商店 shopj 借出的电影 moviej 。
 * res 中的电影需要按 价格 升序排序；如果价格相同，则 shopj 较小 的排在前面；如果仍然相同，则 moviej 较小 的排在前面。
 * 如果当前借出的电影小于 5 部，则将它们全部返回。如果当前没有借出电影，则返回一个空的列表。
 * 请你实现 MovieRentingSystem 类：
 * MovieRentingSystem(int n, int[][] entries) 将 MovieRentingSystem 对象用 n 个商店和 entries 表示的电影列表初始化。
 * List<Integer> search(int movie) 如上所述，返回 未借出 指定 movie 的商店列表。
 * void rent(int shop, int movie) 从指定商店 shop 借出指定电影 movie 。
 * void drop(int shop, int movie) 在指定商店 shop 返还之前借出的电影 movie 。
 * List<List<Integer>> report() 如上所述，返回最便宜的 已借出 电影列表。
 * 注意：测试数据保证 rent 操作中指定商店拥有 未借出 的指定电影，且 drop 操作指定的商店 之前已借出 指定电影。
 * <p>
 * 输入：
 * ["MovieRentingSystem", "search", "rent", "rent", "report", "drop", "search"]
 * [[3, [[0, 1, 5], [0, 2, 6], [0, 3, 7], [1, 1, 4], [1, 2, 7], [2, 1, 5]]], [1], [0, 1], [1, 2], [], [1, 2], [2]]
 * 输出：
 * [null, [1, 0, 2], null, null, [[0, 1], [1, 2]], null, [0, 1]]
 * 解释：
 * MovieRentingSystem movieRentingSystem = new MovieRentingSystem(3, [[0, 1, 5], [0, 2, 6], [0, 3, 7], [1, 1, 4], [1, 2, 7], [2, 1, 5]]);
 * movieRentingSystem.search(1);  // 返回 [1, 0, 2] ，商店 1，0 和 2 有未借出的 ID 为 1 的电影。商店 1 最便宜，商店 0 和 2 价格相同，所以按商店编号排序。
 * movieRentingSystem.rent(0, 1); // 从商店 0 借出电影 1 。现在商店 0 未借出电影编号为 [2,3] 。
 * movieRentingSystem.rent(1, 2); // 从商店 1 借出电影 2 。现在商店 1 未借出的电影编号为 [1] 。
 * movieRentingSystem.report();   // 返回 [[0, 1], [1, 2]] 。商店 0 借出的电影 1 最便宜，然后是商店 1 借出的电影 2 。
 * movieRentingSystem.drop(1, 2); // 在商店 1 返还电影 2 。现在商店 1 未借出的电影编号为 [1,2] 。
 * movieRentingSystem.search(2);  // 返回 [0, 1] 。商店 0 和 1 有未借出的 ID 为 2 的电影。商店 0 最便宜，然后是商店 1 。
 * <p>
 * 1 <= n <= 3 * 10^5
 * 1 <= entries.length <= 10^5
 * 0 <= shopi < n
 * 1 <= moviei, pricei <= 10^4
 * 每个商店 至多 有一份电影 moviei 的拷贝。
 * search，rent，drop 和 report 的调用 总共 不超过 10^5 次。
 */
public class Problem1912 {
    public static void main(String[] args) {
        int n = 3;
        int[][] entries = {{0, 1, 5}, {0, 2, 6}, {0, 3, 7}, {1, 1, 4}, {1, 2, 7}, {2, 1, 5}};
        MovieRentingSystem movieRentingSystem = new MovieRentingSystem(n, entries);
        // 返回 [1, 0, 2] ，商店 1，0 和 2 有未借出的 ID 为 1 的电影。商店 1 最便宜，商店 0 和 2 价格相同，所以按商店编号排序。
        System.out.println(movieRentingSystem.search(1));
        // 从商店 0 借出电影 1 。现在商店 0 未借出电影编号为 [2,3] 。
        movieRentingSystem.rent(0, 1);
        // 从商店 1 借出电影 2 。现在商店 1 未借出的电影编号为 [1] 。
        movieRentingSystem.rent(1, 2);
        // 返回 [[0, 1], [1, 2]] 。商店 0 借出的电影 1 最便宜，然后是商店 1 借出的电影 2 。
        System.out.println(movieRentingSystem.report());
        // 在商店 1 返还电影 2 。现在商店 1 未借出的电影编号为 [1,2] 。
        movieRentingSystem.drop(1, 2);
        // 返回 [0, 1] 。商店 0 和 1 有未借出的 ID 为 2 的电影。商店 0 最便宜，然后是商店 1 。
        System.out.println(movieRentingSystem.search(2));
    }

    /**
     * 哈希表+有序集合
     */
    static class MovieRentingSystem {
        //key：商店id，value：商店节点
        private final Map<Integer, Shop> shopMap;
        //key：电影id，value：按照价格由小到大，商店id由小到大排序的未出租电影的有序集合
        private final Map<Integer, TreeSet<Movie>> unrentedMovieMap;
        //按照价格由小到大，商店id由小到大，电影id由小到大排序的已出租电影的有序集合
        private final TreeSet<Movie> rentedMovieTreeSet;

        /**
         * 时间复杂度O(mlogm)，空间复杂度O(m) (m=entries.length)
         *
         * @param n
         * @param entries
         */
        public MovieRentingSystem(int n, int[][] entries) {
            shopMap = new HashMap<>();
            unrentedMovieMap = new HashMap<>();
            rentedMovieTreeSet = new TreeSet<>(new Comparator<Movie>() {
                @Override
                public int compare(Movie movie1, Movie movie2) {
                    //按照价格由小到大，商店id由小到大，电影id由小到大排序
                    if (movie1.price != movie2.price) {
                        return movie1.price - movie2.price;
                    } else {
                        if (movie1.shopId != movie2.shopId) {
                            return movie1.shopId - movie2.shopId;
                        } else {
                            return movie1.movieId - movie2.movieId;
                        }
                    }
                }
            });

            for (int i = 0; i < entries.length; i++) {
                int shopId = entries[i][0];
                int movieId = entries[i][1];
                int price = entries[i][2];

                if (!shopMap.containsKey(shopId)) {
                    shopMap.put(shopId, new Shop(shopId));
                }

                if (!unrentedMovieMap.containsKey(movieId)) {
                    unrentedMovieMap.put(movieId, new TreeSet<>(new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie1, Movie movie2) {
                            //按照价格由小到大，商店id由小到大排序
                            if (movie1.price != movie2.price) {
                                return movie1.price - movie2.price;
                            } else {
                                return movie1.shopId - movie2.shopId;
                            }
                        }
                    }));
                }

                Movie movie = new Movie(movieId, shopId, price);
                shopMap.get(shopId).unrentedMap.put(movieId, movie);
                unrentedMovieMap.get(movieId).add(movie);
            }
        }

        /**
         * 返回未出租movieId的电影商店中按照价格由小到大排序、商店id由小到大排序的5个商店
         * 时间复杂度O(logn)，空间复杂度O(1) (n：系统中电影的总数)
         *
         * @param movieId
         * @return
         */
        public List<Integer> search(int movieId) {
            //movieId不存在，或者movieId的电影都已出租，则返回空列表
            if (!unrentedMovieMap.containsKey(movieId) || unrentedMovieMap.get(movieId).isEmpty()) {
                return new ArrayList<>();
            }

            List<Integer> list = new ArrayList<>();
            TreeSet<Movie> treeSet = unrentedMovieMap.get(movieId);
            Movie movie = treeSet.first();

            while (movie != null && list.size() < 5) {
                list.add(movie.shopId);
                //higher(x)：返回set中大于x的最小元素，如果不存在返回null
                movie = treeSet.higher(movie);
            }

            return list;
        }

        /**
         * 出租shopId商店的movieId电影
         * 时间复杂度O(logn)，空间复杂度O(1) (n：系统中电影的总数)
         *
         * @param shopId
         * @param movieId
         */
        public void rent(int shopId, int movieId) {
            //shopId不存在，或者shopId商店未出租的电影中没有movieId电影，则直接返回
            if (!shopMap.containsKey(shopId) || !shopMap.get(shopId).unrentedMap.containsKey(movieId)) {
                return;
            }

            Shop shop = shopMap.get(shopId);
            Movie movie = shop.unrentedMap.get(movieId);
            shop.unrentedMap.remove(movieId);
            shop.rentedMap.put(movieId, movie);
            //当前电影从unrentedMovieMap中移除
            unrentedMovieMap.get(movieId).remove(movie);
            //当前电影加入rentedMovieTreeSet中
            rentedMovieTreeSet.add(movie);
        }

        /**
         * 归还shopId商店的movieId电影
         * 时间复杂度O(logn)，空间复杂度O(1) (n：系统中电影的总数)
         *
         * @param shopId
         * @param movieId
         */
        public void drop(int shopId, int movieId) {
            //shopId不存在，或者shopId商店已出租的电影中没有movieId电影，则直接返回
            if (!shopMap.containsKey(shopId) || !shopMap.get(shopId).rentedMap.containsKey(movieId)) {
                return;
            }

            Shop shop = shopMap.get(shopId);
            Movie movie = shop.rentedMap.get(movieId);
            shop.unrentedMap.put(movieId, movie);
            shop.rentedMap.remove(movieId);
            //当前电影加入unrentedMovieMap中
            unrentedMovieMap.get(movieId).add(movie);
            //当前电影从rentedMovieTreeSet中移除
            rentedMovieTreeSet.remove(movie);
        }

        /**
         * 返回已出租电影中价格由小到大排序、商店id由小到大排序、电影id由小到大排序的5个电影
         * 时间复杂度O(logn)，空间复杂度O(1) (n：系统中电影的总数)
         *
         * @return
         */
        public List<List<Integer>> report() {
            //没有已出租的电影，则返回空列表
            if (rentedMovieTreeSet.isEmpty()) {
                return new ArrayList<>();
            }

            List<List<Integer>> result = new ArrayList<>();
            Movie movie = rentedMovieTreeSet.first();

            while (movie != null && result.size() < 5) {
                List<Integer> list = new ArrayList<>();
                list.add(movie.shopId);
                list.add(movie.movieId);
                result.add(list);

                //higher(x)：返回set中大于x的最小元素，如果不存在返回null
                movie = rentedMovieTreeSet.higher(movie);
            }

            return result;
        }

        /**
         * 商店节点
         */
        private static class Shop {
            //商店id
            private int shopId;
            //key：未出租电影id，value：电影节点
            private final Map<Integer, Movie> unrentedMap;
            //key：已出租电影id，value：电影节点
            private final Map<Integer, Movie> rentedMap;

            public Shop(int shopId) {
                this.shopId = shopId;
                unrentedMap = new HashMap<>();
                rentedMap = new HashMap<>();
            }
        }

        /**
         * 电影节点
         */
        private static class Movie {
            //电影id
            private int movieId;
            //电影所属商店id
            private int shopId;
            //电影价格
            private int price;

            public Movie(int movieId, int shopId, int price) {
                this.movieId = movieId;
                this.shopId = shopId;
                this.price = price;
            }

            @Override
            public int hashCode() {
                return movieId + shopId + price;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj instanceof Movie) {
                    Movie movie = (Movie) obj;

                    return movieId == movie.movieId && shopId == movie.shopId && price == movie.price;
                }

                return false;
            }
        }
    }
}
