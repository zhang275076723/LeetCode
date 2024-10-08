package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/2/13 08:28
 * @Author zsy
 * @Description 单词接龙 II Amazon面试题 双向bfs类比Problem127、Problem433、Problem752、Problem815、Problem1345、Problem2059 保存父节点类比Problem113、Problem272、Problem310、Problem863、Offer34
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
//        //当前测试用例：双向bfs超时，但正确
//        String beginWord = "aaaaa";
//        String endWord = "ggggg";
//        //转换序列：aaaaa->aaaaz->aaawz->aavwz->avvwz->vvvwz->vvvww->wvvww->wwvww->wwwww->ywwww->yywww->yyyww->yyyyw->yyyyy->xyyyy->xxyyy->xxxyy->xxxxy->xxxxx->gxxxx->ggxxx->gggxx->ggggx->ggggg
//        List<String> wordList = new ArrayList<>(Arrays.asList("aaaaa", "caaaa", "cbaaa", "daaaa", "dbaaa", "eaaaa", "ebaaa", "faaaa", "fbaaa", "gaaaa", "gbaaa", "haaaa", "hbaaa", "iaaaa", "ibaaa", "jaaaa", "jbaaa", "kaaaa", "kbaaa", "laaaa", "lbaaa", "maaaa", "mbaaa", "naaaa", "nbaaa", "oaaaa", "obaaa", "paaaa", "pbaaa", "bbaaa", "bbcaa", "bbcba", "bbdaa", "bbdba", "bbeaa", "bbeba", "bbfaa", "bbfba", "bbgaa", "bbgba", "bbhaa", "bbhba", "bbiaa", "bbiba", "bbjaa", "bbjba", "bbkaa", "bbkba", "bblaa", "bblba", "bbmaa", "bbmba", "bbnaa", "bbnba", "bboaa", "bboba", "bbpaa", "bbpba", "bbbba", "abbba", "acbba", "dbbba", "dcbba", "ebbba", "ecbba", "fbbba", "fcbba", "gbbba", "gcbba", "hbbba", "hcbba", "ibbba", "icbba", "jbbba", "jcbba", "kbbba", "kcbba", "lbbba", "lcbba", "mbbba", "mcbba", "nbbba", "ncbba", "obbba", "ocbba", "pbbba", "pcbba", "ccbba", "ccaba", "ccaca", "ccdba", "ccdca", "cceba", "cceca", "ccfba", "ccfca", "ccgba", "ccgca", "cchba", "cchca", "cciba", "ccica", "ccjba", "ccjca", "cckba", "cckca", "cclba", "cclca", "ccmba", "ccmca", "ccnba", "ccnca", "ccoba", "ccoca", "ccpba", "ccpca", "cccca", "accca", "adcca", "bccca", "bdcca", "eccca", "edcca", "fccca", "fdcca", "gccca", "gdcca", "hccca", "hdcca", "iccca", "idcca", "jccca", "jdcca", "kccca", "kdcca", "lccca", "ldcca", "mccca", "mdcca", "nccca", "ndcca", "occca", "odcca", "pccca", "pdcca", "ddcca", "ddaca", "ddada", "ddbca", "ddbda", "ddeca", "ddeda", "ddfca", "ddfda", "ddgca", "ddgda", "ddhca", "ddhda", "ddica", "ddida", "ddjca", "ddjda", "ddkca", "ddkda", "ddlca", "ddlda", "ddmca", "ddmda", "ddnca", "ddnda", "ddoca", "ddoda", "ddpca", "ddpda", "dddda", "addda", "aedda", "bddda", "bedda", "cddda", "cedda", "fddda", "fedda", "gddda", "gedda", "hddda", "hedda", "iddda", "iedda", "jddda", "jedda", "kddda", "kedda", "lddda", "ledda", "mddda", "medda", "nddda", "nedda", "oddda", "oedda", "pddda", "pedda", "eedda", "eeada", "eeaea", "eebda", "eebea", "eecda", "eecea", "eefda", "eefea", "eegda", "eegea", "eehda", "eehea", "eeida", "eeiea", "eejda", "eejea", "eekda", "eekea", "eelda", "eelea", "eemda", "eemea", "eenda", "eenea", "eeoda", "eeoea", "eepda", "eepea", "eeeea", "ggggg", "agggg", "ahggg", "bgggg", "bhggg", "cgggg", "chggg", "dgggg", "dhggg", "egggg", "ehggg", "fgggg", "fhggg", "igggg", "ihggg", "jgggg", "jhggg", "kgggg", "khggg", "lgggg", "lhggg", "mgggg", "mhggg", "ngggg", "nhggg", "ogggg", "ohggg", "pgggg", "phggg", "hhggg", "hhagg", "hhahg", "hhbgg", "hhbhg", "hhcgg", "hhchg", "hhdgg", "hhdhg", "hhegg", "hhehg", "hhfgg", "hhfhg", "hhigg", "hhihg", "hhjgg", "hhjhg", "hhkgg", "hhkhg", "hhlgg", "hhlhg", "hhmgg", "hhmhg", "hhngg", "hhnhg", "hhogg", "hhohg", "hhpgg", "hhphg", "hhhhg", "ahhhg", "aihhg", "bhhhg", "bihhg", "chhhg", "cihhg", "dhhhg", "dihhg", "ehhhg", "eihhg", "fhhhg", "fihhg", "ghhhg", "gihhg", "jhhhg", "jihhg", "khhhg", "kihhg", "lhhhg", "lihhg", "mhhhg", "mihhg", "nhhhg", "nihhg", "ohhhg", "oihhg", "phhhg", "pihhg", "iihhg", "iiahg", "iiaig", "iibhg", "iibig", "iichg", "iicig", "iidhg", "iidig", "iiehg", "iieig", "iifhg", "iifig", "iighg", "iigig", "iijhg", "iijig", "iikhg", "iikig", "iilhg", "iilig", "iimhg", "iimig", "iinhg", "iinig", "iiohg", "iioig", "iiphg", "iipig", "iiiig", "aiiig", "ajiig", "biiig", "bjiig", "ciiig", "cjiig", "diiig", "djiig", "eiiig", "ejiig", "fiiig", "fjiig", "giiig", "gjiig", "hiiig", "hjiig", "kiiig", "kjiig", "liiig", "ljiig", "miiig", "mjiig", "niiig", "njiig", "oiiig", "ojiig", "piiig", "pjiig", "jjiig", "jjaig", "jjajg", "jjbig", "jjbjg", "jjcig", "jjcjg", "jjdig", "jjdjg", "jjeig", "jjejg", "jjfig", "jjfjg", "jjgig", "jjgjg", "jjhig", "jjhjg", "jjkig", "jjkjg", "jjlig", "jjljg", "jjmig", "jjmjg", "jjnig", "jjnjg", "jjoig", "jjojg", "jjpig", "jjpjg", "jjjjg", "ajjjg", "akjjg", "bjjjg", "bkjjg", "cjjjg", "ckjjg", "djjjg", "dkjjg", "ejjjg", "ekjjg", "fjjjg", "fkjjg", "gjjjg", "gkjjg", "hjjjg", "hkjjg", "ijjjg", "ikjjg", "ljjjg", "lkjjg", "mjjjg", "mkjjg", "njjjg", "nkjjg", "ojjjg", "okjjg", "pjjjg", "pkjjg", "kkjjg", "kkajg", "kkakg", "kkbjg", "kkbkg", "kkcjg", "kkckg", "kkdjg", "kkdkg", "kkejg", "kkekg", "kkfjg", "kkfkg", "kkgjg", "kkgkg", "kkhjg", "kkhkg", "kkijg", "kkikg", "kkljg", "kklkg", "kkmjg", "kkmkg", "kknjg", "kknkg", "kkojg", "kkokg", "kkpjg", "kkpkg", "kkkkg", "ggggx", "gggxx", "ggxxx", "gxxxx", "xxxxx", "xxxxy", "xxxyy", "xxyyy", "xyyyy", "yyyyy", "yyyyw", "yyyww", "yywww", "ywwww", "wwwww", "wwvww", "wvvww", "vvvww", "vvvwz", "avvwz", "aavwz", "aaawz", "aaaaz"));
//        String beginWord = "hot";
//        String endWord = "dog";
//        //转换序列：hot->dot->dog、hot->hog->dog
//        List<String> wordList = new ArrayList<>(Arrays.asList("hot", "cog", "dog", "tot", "hog", "hop", "pot", "dot"));
        String beginWord = "red";
        String endWord = "tax";
        //转换序列：red->rex->tex->tax、red->ted->tex->tax、red->ted->tad->tax
        List<String> wordList = new ArrayList<>(Arrays.asList("ted", "tex", "red", "tax", "tad", "den", "rex", "pee"));
        System.out.println(problem126.findLadders(beginWord, endWord, wordList));
        System.out.println(problem126.findLadders2(beginWord, endWord, wordList));
    }

    /**
     * bfs+dfs
     * bfs每次往外扩一层，将当前层中所有单词通过wordList变化能够得到的单词全部加入队列中，直至遍历到endWord，
     * 或全部遍历完都没有找到endWord，返回空集合
     * convertMap存储当前单词可由哪些单词转换而来，类似当前单词的父节点，用于dfs复原beginWord转换为endWord的最短转换路径；
     * stepMap存储beginWord转换为当前单词的最少转换次数，当存在多个最短转换路径，dfs复原最短转换路径时，避免遗漏其他最短转换路径
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        //有效单词集合，存储wordList中单词，O(1)判断当前单词是否是wordList中的单词
        Set<String> wordSet = new HashSet<>(wordList);

        //wordSet中不包含endWord，则beginWord无法转换为endWord，返回空集合
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }

        Queue<String> queue = new LinkedList<>();
        //访问集合，存储当前已经访问到的单词
        Set<String> visitedSet = new HashSet<>();
        queue.offer(beginWord);
        visitedSet.add(beginWord);

        //存储当前单词可由哪些单词转换而来，类似当前单词的父节点，用于dfs复原beginWord转换为endWord的最短转换路径
        //key：当前单词，value：可以转换为当前单词的单词集合，类似当前单词的父节点
        Map<String, List<String>> convertMap = new HashMap<>();
        //存储beginWord转换为当前单词的最少转换次数，每次只能将修改一个字母
        //当存在多个最短转换路径，dfs复原最短转换路径时，避免遗漏其他最短转换路径
        //例如：从前往后bfs中，party能够转换为parry，此时parry标记已访问，但marry也能转换为parry，
        //如果此时parry已被访问，则会跳过marry到parry的这条最短路径，通过stepMap，判断marry到parry路径中，
        //parry是否存在于stepMap中，并且从begin到parry的最少转换次数和当前bfs扩展的层数count+1是否相同，
        //如果都满足，则marry到parry的这条路径也是最短转换路径，也要加入convertMap中
        Map<String, Integer> stepMap = new HashMap<>();

        //bfs向外扩展的次数，beginWord转换为endWord的最少转换次数，每次只能将修改一个字母
        int count = 0;
        //是否存在beginWord转换为endWord的最短转换路径标志位，只有存在最短转换路径才dfs复原路径
        boolean flag = false;

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次往外扩一层，将本次所有能够转换的word加入队列中
            for (int i = 0; i < size; i++) {
                //当前单词
                String word = queue.poll();
                //word转换为char数组，便于某一位的变化
                char[] wordArr = word.toCharArray();

                //变化word中的第j位
                for (int j = 0; j < wordArr.length; j++) {
                    //word中的第j位
                    char cur = wordArr[j];

                    //变化word中的第j位为k
                    for (char k = 'a'; k <= 'z'; k++) {
                        wordArr[j] = k;
                        //变化word中的第j位为k得到的单词
                        String nextWord = new String(wordArr);

                        //nextWord已经转换为了endWord，则存在最短转换路径，flag置为true
                        //注意：此时不能break，如果break，则有可能会遗漏最短路径，需要bfs当前层中单词全部遍历完，得到所有的最短路径
                        if (nextWord.equals(endWord)) {
                            flag = true;
                        }

                        //nextWord不是wordSet中的单词，直接进行下次循环
                        if (!wordSet.contains(nextWord)) {
                            continue;
                        }

                        //nextWord已经遍历过，原本应该直接进行下次循环，但此时由word转换为nextWord也是一条最短路径，也要加入convertMap，避免遗漏其他最短转换路径
                        if (stepMap.containsKey(nextWord) && stepMap.get(nextWord) == count + 1) {
                            convertMap.get(nextWord).add(word);
                            continue;
                        }

                        //nextWord已经访问过，直接进行下次循环
                        if (visitedSet.contains(nextWord)) {
                            continue;
                        }

                        //beginWord转换为nextWord的最少转换次数为count+1，加入stepMap
                        stepMap.put(nextWord, count + 1);

                        if (!convertMap.containsKey(nextWord)) {
                            convertMap.put(nextWord, new ArrayList<>());
                        }

                        //word转换为nextWord是一条最短路径，加入convertMap
                        convertMap.get(nextWord).add(word);

                        //nextWord加入队列，并且设置nextWord已访问
                        queue.offer(nextWord);
                        visitedSet.add(nextWord);
                    }

                    //word中的第j位复原，用于第j+1位变化
                    wordArr[j] = cur;
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;

            //bfs当前层中单词全部遍历完，如果存在beginWord转换为endWord的最短转换路径，
            //则convertMap中已经保存所有beginWord转换为endWord的最短转换路径中的父节点，
            //跳出bfs，根据convertMap通过dfs复原最短转换路径
            if (flag) {
                break;
            }
        }

        //存在beginWord转换为endWord的最短转换路径，根据convertMap通过dfs复原最短转换路径
        if (flag) {
            List<List<String>> result = new ArrayList<>();

            //因为convertMap存储当前单词可由哪些单词转换而来，类似当前单词的父节点，需要首添加，所以使用LinkedList
            dfs(beginWord, endWord, convertMap, new LinkedList<>(), result);

            return result;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 双向bfs+dfs (有用例在交换队列取较小队列时超时，但正确)
     * 从beginWord和endWord同时开始bfs，bfs每次往外扩一层，将当前队列当前层中所有单词通过wordList变化能够得到的单词加入当前队列中，
     * 直至一个队列中包含了另一个队列中的单词，即双向bfs相交，或者全部遍历完都没有找到endWord，即不存在最短转换路径
     * convertMap存储当前单词可由哪些单词转换而来，类似当前单词的父节点，用于dfs复原beginWord转换为endWord的最短转换路径；
     * stepMap1存储beginWord转换为当前单词的最少转换次数，stepMap2存储当前单词转换为endWord的最少转换次数，
     * 当存在多个最短转换路径，dfs复原最短转换路径时，避免遗漏其他最短转换路径
     * 注意：双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders2(String beginWord, String endWord, List<String> wordList) {
        //有效单词集合，存储wordList中单词，O(1)判断当前单词是否是wordList中的单词
        Set<String> wordSet = new HashSet<>(wordList);

        //wordSet中不包含endWord，则beginWord无法转换为endWord，返回空集合
        if (!wordSet.contains(endWord)) {
            return new ArrayList<>();
        }

        //从前往后遍历的队列，即从beginWord开始遍历
        Queue<String> queue1 = new LinkedList<>();
        //从后往前遍历的队列，即从endWord开始遍历
        Queue<String> queue2 = new LinkedList<>();
        //从前往后遍历的访问集合，存储queue1已经访问到的单词
        Set<String> visitedSet1 = new HashSet<>();
        //从后往前遍历的访问集合，存储queue2已经访问到的单词
        Set<String> visitedSet2 = new HashSet<>();
        queue1.offer(beginWord);
        queue2.offer(endWord);
        //注意：双向bfs，必须先将首尾节点在对应的set中设置为已访问，不能每次出队元素的时候再标记节点已访问
        visitedSet1.add(beginWord);
        visitedSet2.add(endWord);

        //存储当前单词可由哪些单词转换而来，类似当前单词的父节点，用于dfs复原beginWord转换为endWord的最短转换路径
        //key：当前单词，value：可以转换为当前单词的单词集合，类似当前单词的父节点
        Map<String, List<String>> convertMap = new HashMap<>();
        //存储beginWord转换为当前单词的最少转换次数，每次只能将修改一个字母
        //当存在多个最短转换路径，dfs复原最短转换路径时，避免遗漏其他最短转换路径
        //例如：从前往后bfs中，party能够转换为parry，此时parry标记已访问，但marry也能转换为parry，
        //如果此时parry已被访问，则会跳过marry到parry的这条最短路径，通过stepMap，判断marry到parry路径中，
        //parry是否存在于stepMap中，并且从begin到parry的最少转换次数和当前bfs扩展的层数count1+1是否相同，
        //如果都满足，则marry到parry的这条路径也是最短转换路径，也要加入convertMap中(从后往前bfs同理)
        Map<String, Integer> stepMap1 = new HashMap<>();
        //存储当前单词转换为endWord的最少转换次数，每次只能将修改一个字母
        //当存在多个最短转换路径，dfs复原最短转换路径时，避免遗漏其他最短转换路径
        Map<String, Integer> stepMap2 = new HashMap<>();

        //从前往后bfs向外扩展的次数，beginWord转换为endWord的最少转换次数，每次只能将修改一个字母，
        //当双向bfs相交，count1+count2即为beginWord转换为endWord的最少转换次数
        int count1 = 0;
        //从后往前bfs向外扩展的次数，endWord转换为beginWord的最少转换次数，每次只能将修改一个字母
        //当双向bfs相交，count1+count2即为beginWord转换为endWord的最少转换次数
        int count2 = 0;
        //是否存在beginWord转换为endWord的最短转换路径标志位，只有存在最短转换路径才dfs复原路径
        boolean flag = false;
        //当前bfs的方向，1：queue1从前往后bfs，-1：queue2从后往前bfs
        int direction = 1;

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
            if (queue1.size() > queue2.size()) {
                Queue<String> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                Set<String> tempSet = visitedSet1;
                visitedSet1 = visitedSet2;
                visitedSet2 = tempSet;

                //当两个队列交换后，bfs方向也发生了变化
                direction = -direction;
            }

            int size = queue1.size();

            for (int i = 0; i < size; i++) {
                //当前单词
                String word = queue1.poll();
                //word转换为char数组，便于某一位的变化
                char[] wordArr = word.toCharArray();

                //变化word中的第j位
                for (int j = 0; j < wordArr.length; j++) {
                    //word中的第j位
                    char cur = wordArr[j];

                    //变化word中的第j位为k
                    for (char k = 'a'; k <= 'z'; k++) {
                        wordArr[j] = k;
                        //变化word中的第j位为k得到的单词
                        String nextWord = new String(wordArr);

                        //nextWord已经存在visitedSet2中，即双向bfs相交，则存在最短转换路径，flag置为true
                        //注意：此时不能break，如果break，则有可能会遗漏最短路径，需要bfs当前层中单词全部遍历完，得到所有的最短路径
                        if (visitedSet2.contains(nextWord)) {
                            flag = true;
                        }

                        //nextWord不是wordSet中的单词，直接进行下次循环
                        if (!wordSet.contains(nextWord)) {
                            continue;
                        }

                        //从前往后bfs，word转换为nextWord
                        if (direction == 1) {
                            //nextWord已经遍历过，原本应该直接进行下次循环，但此时由word转换为nextWord也是一条最短路径，也要加入convertMap，避免遗漏其他最短转换路径
                            if (stepMap1.containsKey(nextWord) && stepMap1.get(nextWord) == count1 + 1) {
                                convertMap.get(nextWord).add(word);
                            }
                        } else {
                            //从后往前bfs，nextWord转换为word

                            //nextWord已经遍历过，原本应该直接进行下次循环，但此时由nextWord转换为word也是一条最短路径，也要加入convertMap，避免遗漏其他最短转换路径
                            if (stepMap2.containsKey(nextWord) && stepMap2.get(nextWord) == count2 + 1) {
                                //从后往前bfs，convertMap中有可能没有word，则需要将word放入convertMap中
                                if (!convertMap.containsKey(word)) {
                                    convertMap.put(word, new ArrayList<>());
                                }

                                convertMap.get(word).add(nextWord);
                            }
                        }

                        //nextWord已经访问过，直接进行下次循环
                        if (visitedSet1.contains(nextWord)) {
                            continue;
                        }

                        //从前往后bfs，更新convertMap和stepMap1
                        if (direction == 1) {
                            if (!convertMap.containsKey(nextWord)) {
                                convertMap.put(nextWord, new ArrayList<>());
                            }

                            //word转换为nextWord是一条最短路径，加入convertMap
                            convertMap.get(nextWord).add(word);

                            //beginWord转换为nextWord的最少转换次数为count1+1，加入stepMap1
                            stepMap1.put(nextWord, count1 + 1);
                        } else {
                            //从后往前bfs，更新convertMap和stepMap2

                            if (!convertMap.containsKey(word)) {
                                convertMap.put(word, new ArrayList<>());
                            }

                            //nextWord转换为word是一条最短路径，加入convertMap
                            convertMap.get(word).add(nextWord);

                            //nextWord转换为endWord的最少转换次数为count2+1，加入stepMap2
                            stepMap2.put(nextWord, count2 + 1);
                        }

                        //nextWord加入队列queue1，并且设置nextWord已访问
                        queue1.offer(nextWord);
                        visitedSet1.add(nextWord);
                    }

                    //word中的第j位复原，用于第j+1位变化
                    wordArr[j] = cur;
                }
            }

            //当前是从前往后bfs，count1加1，表示从前往后bfs每次往外扩一层
            if (direction == 1) {
                count1++;
            } else {
                //当前是从后往前bfs，count2加1，表示从后往前bfs每次往外扩一层
                count2++;
            }

            //bfs当前层中单词全部遍历完，如果存在beginWord转换为endWord的最短转换路径，
            //则convertMap中已经保存所有beginWord转换为endWord的最短转换路径中的父节点，
            //跳出bfs，根据convertMap通过dfs复原最短转换路径
            if (flag) {
                break;
            }
        }

        //存在beginWord转换为endWord的最短转换路径，根据convertMap通过dfs复原最短转换路径
        if (flag) {
            List<List<String>> result = new ArrayList<>();

            //因为convertMap存储当前单词可由哪些单词转换而来，类似当前单词的父节点，需要首添加，所以使用LinkedList
            dfs(beginWord, endWord, convertMap, new LinkedList<>(), result);

            return result;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 根据convertMap通过dfs复原beginWord转换为endWord的最短转换路径
     *
     * @param beginWord
     * @param endWord
     * @param convertMap
     * @param path
     * @param result
     */
    private void dfs(String beginWord, String endWord, Map<String, List<String>> convertMap,
                     LinkedList<String> path, List<List<String>> result) {
        //beginWord和endWord相等，则找到了一条最短转换路径，加入result
        if (beginWord.equals(endWord)) {
            //单词首添加
            path.addFirst(endWord);
            result.add(new ArrayList<>(path));
            path.removeFirst();
            return;
        }

        //最短转换路径中转换为endWord的单词集合
        List<String> nextWordList = convertMap.get(endWord);

        //最短转换路径中不存在转换为endWord的单词集合，直接返回
        if (nextWordList == null) {
            return;
        }

        //因为convertMap存储当前单词可由哪些单词转换而来，类似当前单词的父节点，需要首添加
        path.addFirst(endWord);

        //最短转换路径中转换为endWord的单词nextWord
        for (String nextWord : convertMap.get(endWord)) {
            dfs(beginWord, nextWord, convertMap, path, result);
        }

        path.removeFirst();
    }
}
