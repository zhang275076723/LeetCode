package com.zhang.java;

/**
 * @Date 2023/12/1 08:56
 * @Author zsy
 * @Description 最大休假天数 Bellman-Ford类比Problem787、Problem1293、Problem1928 动态规划类比
 * 力扣想让一个最优秀的员工在 N 个城市间旅行来收集算法问题。
 * 但只工作不玩耍，聪明的孩子也会变傻，所以您可以在某些特定的城市和星期休假。
 * 您的工作就是安排旅行使得最大化你可以休假的天数，但是您需要遵守一些规则和限制。
 * 规则和限制：
 * 1、您只能在 N 个城市之间旅行，用 0 到 n-1 的索引表示。一开始，您在索引为 0 的城市，并且那天是星期一。
 * 2、这些城市通过航班相连。这些航班用 n x n 矩阵 flights（不一定是对称的）表示，flights[i][j] 代表城市 i 到城市 j 的航空状态。
 * 如果没有城市 i 到城市 j 的航班，flights[i][j] = 0 ；否则，flights[i][j] = 1 。同时，对于所有的 i ，flights[i][i] = 0 。
 * 3、您总共有 k 周（每周7天）的时间旅行。您每天最多只能乘坐一次航班，并且只能在每周的星期一上午乘坐航班。
 * 由于飞行时间很短，我们不考虑飞行时间的影响。
 * 4、对于每个城市，不同的星期您休假天数是不同的，给定一个 N*K 矩阵 days 代表这种限制，
 * days[i][j] 代表您在第j个星期在城市i能休假的最长天数。
 * 5、如果您从 A 市飞往 B 市，并在当天休假，扣除的假期天数将计入 B 市当周的休假天数。
 * 6、我们不考虑飞行时数对休假天数计算的影响。
 * 给定 flights 矩阵和 days 矩阵，您需要输出 k 周内可以休假的最长天数。
 * <p>
 * 输入:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[1,3,1],[6,0,3],[3,3,3]]
 * 输出: 12
 * 解释:
 * 最好的策略之一：
 * 第一个星期 : 星期一从城市 0 飞到城市 1，玩 6 天，工作 1 天。
 * （虽然你是从城市 0 开始，但因为是星期一，我们也可以飞到其他城市。）
 * 第二个星期 : 星期一从城市 1 飞到城市 2，玩 3 天，工作 4 天。
 * 第三个星期 : 呆在城市 2，玩 3 天，工作 4 天。
 * Ans = 6 + 3 + 3 = 12.
 * <p>
 * 输入:flights = [[0,0,0],[0,0,0],[0,0,0]], days = [[1,1,1],[7,7,7],[7,7,7]]
 * 输出: 3
 * 解释:
 * 由于没有航班可以让您飞到其他城市，你必须在城市 0 呆整整 3 个星期。
 * 对于每一个星期，你只有一天时间玩，剩下六天都要工作。
 * 所以最大休假天数为 3.
 * Ans = 1 + 1 + 1 = 3.
 * <p>
 * 输入:flights = [[0,1,1],[1,0,1],[1,1,0]], days = [[7,0,0],[0,7,0],[0,0,7]]
 * 输出: 21
 * 解释:
 * 最好的策略之一是：
 * 第一个星期 : 呆在城市 0，玩 7 天。
 * 第二个星期 : 星期一从城市 0 飞到城市 1，玩 7 天。
 * 第三个星期 : 星期一从城市 1 飞到城市 2，玩 7 天。
 * Ans = 7 + 7 + 7 = 21
 * <p>
 * n == flights.length
 * n == flights[i].length
 * n == days.length
 * k == days[i].length
 * 1 <= n, k <= 100
 * flights[i][j] 不是 0 就是 1
 * 0 <= days[i] <= 7
 */
public class Problem568 {
    public static void main(String[] args) {
        Problem568 problem568 = new Problem568();
        int[][] flights = {{0, 1, 1}, {1, 0, 1}, {1, 1, 0}};
        int[][] days = {{1, 3, 1}, {6, 0, 3}, {3, 3, 3}};
        System.out.println(problem568.maxVacationDays(flights, days));
    }

    /**
     * 动态规划(Bellman-Ford)
     * dp[i][j]：节点i在第j周休假的最长天数
     * dp[i][j] = max(dp[k][j-1]+days[i][j-1]) (flights[k][i]为1，即存在节点k到节点i的边)
     * (节点i在第j周休假的天数为days[i][j-1]，而不是days[i][j]，因为days第1周休假的天数是从下标索引0开始)
     * 时间复杂度O(k*n^2)，空间复杂度O(nk)
     *
     * @param flights
     * @param days
     * @return
     */
    public int maxVacationDays(int[][] flights, int[][] days) {
        //节点的个数
        int n = days.length;
        //旅行的周数
        int k = days[0].length;
        //节点i在第j周休假的最长天数
        int[][] dp = new int[n][k + 1];

        //dp初始化，第j周无法到达节点i
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = Integer.MIN_VALUE;
            }
        }

        //初始化，节点0在第0周休假的最长天数为0
        dp[0][0] = 0;

        //由节点j在第l-1周休假的最长天数求节点i在第l周休假的最长天数
        for (int l = 0; l <= k; l++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //存在节点j到节点i的边
                    if (flights[i][j] == 1 && l - 1 >= 0 && dp[j][l - 1] != Integer.MAX_VALUE) {
                        //注意：节点i在第l周休假的天数为days[i][l-1]，而不是days[i][l]，因为days第1周休假的天数是从下标索引0开始
                        dp[i][l] = Math.max(dp[i][l], dp[j][l - 1] + days[i][l - 1]);
                    }
                }
            }
        }

        //第k周休假的最长天数
        int result = 0;

        for (int i = 0; i < n; i++) {
            result = Math.max(result, dp[i][k]);
        }

        return result;
    }
}
