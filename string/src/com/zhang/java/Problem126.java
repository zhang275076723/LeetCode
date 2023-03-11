package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/13 08:28
 * @Author zsy
 * @Description 单词接龙 II Amazon面试题 双向bfs类比Problem127、Problem1345
 * 按字典 wordList 完成从单词 beginWord 到单词 endWord 转化，
 * 一个表示此过程的 转换序列 是形式上像 beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
 * 每对相邻的单词之间仅有单个字母不同。
 * 转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单词。
 * sk == endWord
 * 给你两个单词 beginWord 和 endWord ，以及一个字典 wordList 。
 * 请你找出并返回所有从 beginWord 到 endWord 的 最短转换序列 ，如果不存在这样的转换序列，返回一个空列表。
 * 每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回。
 * <p>
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * 输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * 解释：存在 2 种最短的转换序列：
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 * <p>
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * 输出：[]
 * 解释：endWord "cog" 不在字典 wordList 中，所以不存在符合要求的转换序列。
 * <p>
 * 1 <= beginWord.length <= 5
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 500
 * wordList[i].length == beginWord.length
 * beginWord、endWord 和 wordList[i] 由小写英文字母组成
 * beginWord != endWord
 * wordList 中的所有单词 互不相同
 */
public class Problem126 {
    public static void main(String[] args) {
        Problem126 problem126 = new Problem126();
//        //双向bfs超时，但正确
//        String beginWord = "aaaaa";
//        String endWord = "ggggg";
//        List<String> wordList = new ArrayList<>(Arrays.asList("aaaaa", "caaaa", "cbaaa", "daaaa", "dbaaa", "eaaaa", "ebaaa", "faaaa", "fbaaa", "gaaaa", "gbaaa", "haaaa", "hbaaa", "iaaaa", "ibaaa", "jaaaa", "jbaaa", "kaaaa", "kbaaa", "laaaa", "lbaaa", "maaaa", "mbaaa", "naaaa", "nbaaa", "oaaaa", "obaaa", "paaaa", "pbaaa", "bbaaa", "bbcaa", "bbcba", "bbdaa", "bbdba", "bbeaa", "bbeba", "bbfaa", "bbfba", "bbgaa", "bbgba", "bbhaa", "bbhba", "bbiaa", "bbiba", "bbjaa", "bbjba", "bbkaa", "bbkba", "bblaa", "bblba", "bbmaa", "bbmba", "bbnaa", "bbnba", "bboaa", "bboba", "bbpaa", "bbpba", "bbbba", "abbba", "acbba", "dbbba", "dcbba", "ebbba", "ecbba", "fbbba", "fcbba", "gbbba", "gcbba", "hbbba", "hcbba", "ibbba", "icbba", "jbbba", "jcbba", "kbbba", "kcbba", "lbbba", "lcbba", "mbbba", "mcbba", "nbbba", "ncbba", "obbba", "ocbba", "pbbba", "pcbba", "ccbba", "ccaba", "ccaca", "ccdba", "ccdca", "cceba", "cceca", "ccfba", "ccfca", "ccgba", "ccgca", "cchba", "cchca", "cciba", "ccica", "ccjba", "ccjca", "cckba", "cckca", "cclba", "cclca", "ccmba", "ccmca", "ccnba", "ccnca", "ccoba", "ccoca", "ccpba", "ccpca", "cccca", "accca", "adcca", "bccca", "bdcca", "eccca", "edcca", "fccca", "fdcca", "gccca", "gdcca", "hccca", "hdcca", "iccca", "idcca", "jccca", "jdcca", "kccca", "kdcca", "lccca", "ldcca", "mccca", "mdcca", "nccca", "ndcca", "occca", "odcca", "pccca", "pdcca", "ddcca", "ddaca", "ddada", "ddbca", "ddbda", "ddeca", "ddeda", "ddfca", "ddfda", "ddgca", "ddgda", "ddhca", "ddhda", "ddica", "ddida", "ddjca", "ddjda", "ddkca", "ddkda", "ddlca", "ddlda", "ddmca", "ddmda", "ddnca", "ddnda", "ddoca", "ddoda", "ddpca", "ddpda", "dddda", "addda", "aedda", "bddda", "bedda", "cddda", "cedda", "fddda", "fedda", "gddda", "gedda", "hddda", "hedda", "iddda", "iedda", "jddda", "jedda", "kddda", "kedda", "lddda", "ledda", "mddda", "medda", "nddda", "nedda", "oddda", "oedda", "pddda", "pedda", "eedda", "eeada", "eeaea", "eebda", "eebea", "eecda", "eecea", "eefda", "eefea", "eegda", "eegea", "eehda", "eehea", "eeida", "eeiea", "eejda", "eejea", "eekda", "eekea", "eelda", "eelea", "eemda", "eemea", "eenda", "eenea", "eeoda", "eeoea", "eepda", "eepea", "eeeea", "ggggg", "agggg", "ahggg", "bgggg", "bhggg", "cgggg", "chggg", "dgggg", "dhggg", "egggg", "ehggg", "fgggg", "fhggg", "igggg", "ihggg", "jgggg", "jhggg", "kgggg", "khggg", "lgggg", "lhggg", "mgggg", "mhggg", "ngggg", "nhggg", "ogggg", "ohggg", "pgggg", "phggg", "hhggg", "hhagg", "hhahg", "hhbgg", "hhbhg", "hhcgg", "hhchg", "hhdgg", "hhdhg", "hhegg", "hhehg", "hhfgg", "hhfhg", "hhigg", "hhihg", "hhjgg", "hhjhg", "hhkgg", "hhkhg", "hhlgg", "hhlhg", "hhmgg", "hhmhg", "hhngg", "hhnhg", "hhogg", "hhohg", "hhpgg", "hhphg", "hhhhg", "ahhhg", "aihhg", "bhhhg", "bihhg", "chhhg", "cihhg", "dhhhg", "dihhg", "ehhhg", "eihhg", "fhhhg", "fihhg", "ghhhg", "gihhg", "jhhhg", "jihhg", "khhhg", "kihhg", "lhhhg", "lihhg", "mhhhg", "mihhg", "nhhhg", "nihhg", "ohhhg", "oihhg", "phhhg", "pihhg", "iihhg", "iiahg", "iiaig", "iibhg", "iibig", "iichg", "iicig", "iidhg", "iidig", "iiehg", "iieig", "iifhg", "iifig", "iighg", "iigig", "iijhg", "iijig", "iikhg", "iikig", "iilhg", "iilig", "iimhg", "iimig", "iinhg", "iinig", "iiohg", "iioig", "iiphg", "iipig", "iiiig", "aiiig", "ajiig", "biiig", "bjiig", "ciiig", "cjiig", "diiig", "djiig", "eiiig", "ejiig", "fiiig", "fjiig", "giiig", "gjiig", "hiiig", "hjiig", "kiiig", "kjiig", "liiig", "ljiig", "miiig", "mjiig", "niiig", "njiig", "oiiig", "ojiig", "piiig", "pjiig", "jjiig", "jjaig", "jjajg", "jjbig", "jjbjg", "jjcig", "jjcjg", "jjdig", "jjdjg", "jjeig", "jjejg", "jjfig", "jjfjg", "jjgig", "jjgjg", "jjhig", "jjhjg", "jjkig", "jjkjg", "jjlig", "jjljg", "jjmig", "jjmjg", "jjnig", "jjnjg", "jjoig", "jjojg", "jjpig", "jjpjg", "jjjjg", "ajjjg", "akjjg", "bjjjg", "bkjjg", "cjjjg", "ckjjg", "djjjg", "dkjjg", "ejjjg", "ekjjg", "fjjjg", "fkjjg", "gjjjg", "gkjjg", "hjjjg", "hkjjg", "ijjjg", "ikjjg", "ljjjg", "lkjjg", "mjjjg", "mkjjg", "njjjg", "nkjjg", "ojjjg", "okjjg", "pjjjg", "pkjjg", "kkjjg", "kkajg", "kkakg", "kkbjg", "kkbkg", "kkcjg", "kkckg", "kkdjg", "kkdkg", "kkejg", "kkekg", "kkfjg", "kkfkg", "kkgjg", "kkgkg", "kkhjg", "kkhkg", "kkijg", "kkikg", "kkljg", "kklkg", "kkmjg", "kkmkg", "kknjg", "kknkg", "kkojg", "kkokg", "kkpjg", "kkpkg", "kkkkg", "ggggx", "gggxx", "ggxxx", "gxxxx", "xxxxx", "xxxxy", "xxxyy", "xxyyy", "xyyyy", "yyyyy", "yyyyw", "yyyww", "yywww", "ywwww", "wwwww", "wwvww", "wvvww", "vvvww", "vvvwz", "avvwz", "aavwz", "aaawz", "aaaaz"));
//        String beginWord = "hot";
//        String endWord = "dog";
//        List<String> wordList = new ArrayList<>(Arrays.asList("hot", "cog", "dog", "tot", "hog", "hop", "pot", "dot"));
        String beginWord = "red";
        String endWord = "tax";
        List<String> wordList = new ArrayList<>(Arrays.asList("ted", "tex", "red", "tax", "tad", "den", "rex", "pee"));
        //转换序列：red -> rex -> tex -> tax、red -> ted -> tex -> tax、red -> ted -> tad -> tax
        System.out.println(problem126.findLadders(beginWord, endWord, wordList));
        System.out.println(problem126.findLadders2(beginWord, endWord, wordList));
    }

    /**
     * bfs+dfs
     * 从beginWord开始bfs，bfs每次往外扩一层，将本次word能够转换的单词全部加入队列中，
     * convertMap存储相邻单词的最短转换方案，用于最短转换序列的复原；stepMap存储遍历到单词的路径长度，避免遗漏其他最短转换序列，
     * 直至遍历到了endWord，则存在最短转换序列，通过convertMap得到所有的最短转换序列。
     * 优化：替换word中的某个字符，如果替换后的单词是wordList中的单词，则加入队列；而不是遍历wordList，找能转换的单词
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        //wordList中不包含endWord，beginWord无法转换为endWord，直接返回空集合
        if (!wordList.contains(endWord)) {
            return new ArrayList<>();
        }

        Queue<String> queue = new LinkedList<>();
        //存储queue遍历到单词，作为访问标志
        Set<String> set = new HashSet<>();
        //存储wordList中单词，用于判断当前单词某个字符进行替换之后是否在wordSet中，是否能够进行转换
        Set<String> wordSet = new HashSet<>(wordList);
        queue.offer(beginWord);
        set.add(beginWord);

        //记录当前单词由哪些单词转换而来的，用于dfs恢复最短转换路径，key：当前单词，value：可以转换为单词key的List集合
        Map<String, List<String>> convertMap = new HashMap<>();
        //记录当前单词是bfs哪次访问到，即bfs遍历的次数，避免遗漏其他最短转换序列
        //例如：从前往后bfs中，party能够转换为parry，此时parry标记已访问，但marry也能转换为parry，
        //但此时parry已被访问，会跳过marry到parry的这条最短路径，通过stepMap，判断到parry路径中，
        //parry是否在stepMap已存在，并且从begin到parry的路径和当前bfs扩展的层数count1相同，
        //则marry到parry的这条路径也是最短转换路径，也要加入convertMap中(从后往前bfs同理)
        Map<String, Integer> stepMap = new HashMap<>();
        //bfs遍历到beginWord的次数为0次
        stepMap.put(beginWord, 0);

        //bfs向外扩展的层数，得到beginWord转换为endWord的最短转换序列中的单词个数
        int count = 0;
        //是否存在beginWord转换为endWord的最短转换序列标志位
        boolean flag = false;

        while (!queue.isEmpty()) {
            //count加1，表示bfs每次往外扩一层
            count++;
            int size = queue.size();

            //每次往外扩一层，将本次所有能够转换的word加入队列中
            for (int i = 0; i < size; i++) {
                //当前单词
                String word = queue.poll();
                //当前单词word转换为char数组，便于字符替换
                char[] wordArr = word.toCharArray();

                //替换wordArr中某个字符，如果替换后的单词在wordSet中，则加入队列
                for (int j = 0; j < wordArr.length; j++) {
                    //保存之前的wordArr[j]，用于字符复原，便于下一位字符的替换
                    char originChar = wordArr[j];

                    //wordArr[j]替换为字符k，判断替换之后的单词是否是wordList中单词
                    for (char k = 'a'; k <= 'z'; k++) {
                        wordArr[j] = k;
                        //替换之后的单词
                        String newWord = new String(wordArr);

                        //newWord不是wordSet中单词，直接进行下次循环
                        if (!wordSet.contains(newWord)) {
                            continue;
                        }

                        //newWord已经遍历过，应该继续下次循环，但此时count和遍历到newWord的路径长度一样，
                        //则word转换为newWord也是一条最短转换路径，也要加入convertMap，避免遗漏其他最短转换序列
                        if (stepMap.containsKey(newWord) && count == stepMap.get(newWord)) {
                            List<String> list = convertMap.get(newWord);
                            list.add(word);
                        }

                        //newWord已经在set中，则newWord已被遍历，直接进行下次循环
                        if (set.contains(newWord)) {
                            continue;
                        }

                        //newWord已经在set2中，则已经找到beginWord转换为endWord的最短转换序列
                        if (newWord.equals(endWord)) {
                            //已经找到beginWord转换为endWord的最短转换序列，标志位置为true，
                            //不能break，要把当前队列中节点全部遍历完，充分得到不同的最短路径
                            flag = true;
                        }

                        queue.offer(newWord);
                        set.add(newWord);

                        if (!convertMap.containsKey(newWord)) {
                            convertMap.put(newWord, new ArrayList<>());
                        }

                        //word转换为newWord是相邻的最短转换路径，加入convertMap
                        List<String> list = convertMap.get(newWord);
                        list.add(word);

                        //beginWord到newWord的最短转换路径长度，加入stepMap
                        stepMap.put(newWord, count);
                    }

                    //wordArr[j]恢复为之前的字符，用于下个位置的字符替换
                    wordArr[j] = originChar;
                }
            }

            //存在beginWord转换为endWord的最短转换序列，已经充分得到convertMap，
            //直接跳出bfs，根据convertMap通过dfs找最短转换序列
            if (flag) {
                break;
            }
        }

        List<List<String>> result = new ArrayList<>();

        //存在beginWord转换到endWord的最短转换序列，通过dfs找所有的最短转换序列
        if (flag) {
            //convertMap中存储的单词key，是由value集合中的单词转换得到的，所以通过LinkedList首添加，得到最短转换序列
            LinkedList<String> path = new LinkedList<>();
            //反向bfs，需要将末尾单词加入path中
            path.addFirst(endWord);
            backtrack(beginWord, endWord, convertMap, path, result);
        }

        return result;
    }

    /**
     * 双向bfs+dfs (有用例超时，但正确)
     * 从beginWord和endWord同时开始bfs，bfs每次往外扩一层，将本次word能够转换的单词全部加入队列中，
     * convertMap存储相邻单词的最短转换方案，用于最短转换序列的复原；stepMap存储遍历到单词的路径长度，避免遗漏其他最短转换序列，
     * 直至其中一个队列遍历到了另一个队列已经遍历过的单词，则存在最短转换序列，通过convertMap得到所有的最短转换序列。
     * 优化一：优先遍历两个队列中存储单词较少的队列，能够加快查询速度
     * 优化二：替换word中的某个字符，如果替换后的单词是wordList中的单词，则加入队列；而不是遍历wordList，找能转换的单词
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
        //wordList中不包含endWord，beginWord无法转换为endWord，直接返回空集合
        if (!wordList.contains(endWord)) {
            return new ArrayList<>();
        }

        //从前往后存储queue1遍历到单词，作为访问标志，即先存储beginWord
        Set<String> set1 = new HashSet<>();
        //从后往前存储queue2遍历到单词，作为访问标志，即先存储endWord
        Set<String> set2 = new HashSet<>();
        //存储wordList中单词，用于判断当前单词某个字符进行替换之后是否在wordSet中，是否能够进行转换
        Set<String> wordSet = new HashSet<>(wordList);
        //注意：双向bfs，必须先将首尾节点在对应的set中设置为已访问，不能每次出队元素的时候再标记节点已访问
        set1.add(beginWord);
        set2.add(endWord);

        //从前往后遍历的队列，即从beginWord开始遍历
        Queue<String> queue1 = new LinkedList<>();
        //从后往前遍历的队列，即从endWord开始遍历
        Queue<String> queue2 = new LinkedList<>();
        queue1.offer(beginWord);
        queue2.offer(endWord);

        //记录当前单词由哪些单词转换而来的，用于dfs恢复最短转换路径，key：当前单词，value：可以转换为单词key的List集合
        Map<String, List<String>> convertMap = new HashMap<>();
        //记录当前单词是双向bfs哪次访问到，即正向或逆向bfs遍历的次数，避免遗漏其他最短转换序列
        //例如：从前往后bfs中，party能够转换为parry，此时parry标记已访问，但marry也能转换为parry，
        //但此时parry已被访问，会跳过marry到parry的这条最短路径，通过stepMap1，判断到parry路径中，
        //parry是否在stepMap1已存在，并且从begin到parry的路径和当前bfs扩展的层数count1相同，
        //则marry到parry的这条路径也是最短转换路径，也要加入convertMap中(从后往前bfs同理)
        Map<String, Integer> stepMap1 = new HashMap<>();
        Map<String, Integer> stepMap2 = new HashMap<>();
        //从前往后bfs，beginWord遍历次数为0次
        stepMap1.put(beginWord, 0);
        //从后往前bfs，endWord遍历次数为0次
        stepMap2.put(endWord, 0);

        //从前往后bfs扩展的层数
        int count1 = 0;
        //从后往前bfs扩展的层数
        int count2 = 0;

        //当前bfs的方向，1：从queue1正向bfs，-1：从queue2逆向bfs
        int direction = 1;
        //是否存在beginWord转换为endWord的最短转换序列标志位
        boolean flag = false;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //优化：优先遍历两个队列中存储单词较少的队列，能够加快查询速度
            //从前往后遍历的queue1中单词数量大于从后往前遍历的queue2中单词数量，两个队列和两个set交换，
            //queue1是两个队列中存储单词较少的队列，每次只遍历queue1
            if (queue1.size() > queue2.size()) {
                Queue<String> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                Set<String> tempSet = set1;
                set1 = set2;
                set2 = tempSet;

                //不能写成direction=-1，因为两个队列之前有可能交换过，写为-1不知道哪个是正向队列，哪个是逆向队列，
                //只能写为direction=-direction，表示两个队列之间交换，取较小的队列进行bfs
                direction = -direction;
            }

            //从前往后遍历，count1加1
            if (direction == 1) {
                count1++;
            } else {
                //从后往前遍历，count2加1
                count2++;
            }

            //记录存储单词较少的队列中单词的数量，进行bfs，表示每次往外扩一层
            int size = queue1.size();

            //queue1是两个队列中存储单词较少的队列，每次往外扩一层，将本次所有能够转换的word加入queue1
            for (int i = 0; i < size; i++) {
                //当前单词
                String word = queue1.poll();
                //当前单词word转换为char数组，便于字符替换
                char[] wordArr = word.toCharArray();

                //替换wordArr中某个字符，如果替换后的单词在wordSet中，则加入队列
                for (int j = 0; j < wordArr.length; j++) {
                    //保存之前的wordArr[j]，用于字符复原，便于下一位字符的替换
                    char originChar = wordArr[j];

                    //wordArr[j]替换为字符k，判断替换之后的单词是否是wordList中单词
                    for (char k = 'a'; k <= 'z'; k++) {
                        wordArr[j] = k;
                        //替换之后的单词
                        String newWord = new String(wordArr);

                        //newWord不是wordSet中单词，直接进行下次循环
                        if (!wordSet.contains(newWord)) {
                            continue;
                        }

                        //从前往后bfs，newWord已经遍历过，应该继续下次循环，但此时count1和遍历到newWord的路径长度一样，
                        //则word转换为newWord也是一条最短转换路径，也要加入convertMap，避免遗漏其他最短转换序列
                        if (direction == 1) {
                            if (stepMap1.containsKey(newWord) && stepMap1.get(newWord) == count1) {
                                List<String> list = convertMap.get(newWord);
                                list.add(word);
                            }
                        } else {
                            //从后往前bfs，newWord已经遍历过，应该继续下次循环，但此时count2和遍历到newWord的路径长度一样，
                            //则newWord转换为word也是一条最短转换路径，也要加入convertMap，避免遗漏其他最短转换序列
                            if (stepMap2.containsKey(newWord) && stepMap2.get(newWord) == count2) {
                                //从后往前bfs，convertMap中有可能没有word，则需要将word放入convertMap中
                                if (!convertMap.containsKey(word)) {
                                    convertMap.put(word, new ArrayList<>());
                                }

                                List<String> list = convertMap.get(word);
                                list.add(newWord);
                            }
                        }

                        //newWord已经在set1中，则newWord已被遍历，直接进行下次循环
                        if (set1.contains(newWord)) {
                            continue;
                        }

                        //newWord已经在set2中，双向dfs已经相交，则已经找到beginWord转换为endWord的最短转换序列
                        if (set2.contains(newWord)) {
                            //已经找到beginWord转换为endWord的最短转换序列，标志位置为true，
                            //不能break，要把当前队列中节点全部遍历完，充分得到不同的最短路径
                            flag = true;
                        }

                        queue1.offer(newWord);
                        set1.add(newWord);

                        //从前往后bfs，convertMap和stepMap1更新
                        if (direction == 1) {
                            if (!convertMap.containsKey(newWord)) {
                                convertMap.put(newWord, new ArrayList<>());
                            }

                            //word转换为newWord是相邻的最短转换路径，加入convertMap
                            List<String> list = convertMap.get(newWord);
                            list.add(word);

                            //beginWord到newWord的最短转换路径长度，加入stepMap1
                            stepMap1.put(newWord, count1);
                        } else {
                            //从后往前bfs，convertMap和stepMap2更新
                            if (!convertMap.containsKey(word)) {
                                convertMap.put(word, new ArrayList<>());
                            }

                            //newWord转换为word是相邻的最短转换路径，加入convertMap
                            List<String> list = convertMap.get(word);
                            list.add(newWord);

                            //newWord到endWord的最短转换路径长度，加入stepMap2
                            stepMap2.put(newWord, count2);
                        }
                    }

                    //wordArr[j]恢复为之前的字符，用于下个位置的字符替换
                    wordArr[j] = originChar;
                }
            }

            //存在beginWord转换为endWord的最短转换序列，已经充分得到convertMap，
            //直接跳出双向bfs，根据convertMap通过dfs找最短转换序列
            if (flag) {
                break;
            }
        }

        List<List<String>> result = new ArrayList<>();

        //存在beginWord转换到endWord的最短转换序列，通过dfs找所有的最短转换序列
        if (flag) {
            //convertMap中存储的单词key，是由value集合中的单词转换得到的，所以通过LinkedList首添加，得到最短转换序列
            LinkedList<String> path = new LinkedList<>();
            //反向bfs，需要将末尾单词加入path中
            path.addFirst(endWord);
            backtrack(beginWord, endWord, convertMap, path, result);
        }

        return result;
    }

    private void backtrack(String beginWord, String endWord, Map<String, List<String>> convertMap,
                           LinkedList<String> path, List<List<String>> result) {
        //beginWord和endWord相等，则找到了一条转换路径，加入result中
        if (beginWord.equals(endWord)) {
            result.add(new ArrayList<>(path));
            return;
        }

        //当前endWord不存在可以转换为beginWord的list集合，直接返回
        if (!convertMap.containsKey(endWord)) {
            return;
        }

        for (String newWord : convertMap.get(endWord)) {
            //convertMap中存储的单词key，是由value集合中的单词转换得到的，所以通过LinkedList首添加，得到最短转换序列
            path.addFirst(newWord);
            backtrack(beginWord, newWord, convertMap, path, result);
            path.removeFirst();
        }
    }
}
