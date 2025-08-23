package testers;

import jbsae.func.prim.*;
import jbsae.struct.prim.*;

import java.util.*;

import static jbsae.util.Mathf.*;

public class Runner{
    public static String in;
    public static ObjfMap<String> variables = new ObjfMap<>();
    public static FloatQueue values = new FloatQueue();
    public static IntQueue operations = new IntQueue();

    public static void main(String[] args) throws Exception{
//        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
//        while((in = f.readLine()).length() != 0){
//            CharSeq real = new CharSeq(in).removeAll(" ");
//            if(Character.isLetter(real.get(0))){
//                int i = real.indexOf("=");
//                if(i < 0) System.out.println(variables.get(real.toString()));
//                else variables.add(real.substring(0, i).toString(), eval(real.substring(i + 1, real.size)));
//            }
//        }

//        InputStreamReader reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("shader.frag"));
//        BufferedReader f = new BufferedReader(reader);
//        System.out.println(f.readLine());

        System.out.println(new Runner().countSubIslands(new int[][] {new int[] {1,0,1,0,1}, new int[] {1,1,1,1,1}, new int[] {0,0,0,0, 0},new int[] {1,1,1,1,1},new int[]{1,0,1,0,1}},
        new int[][] {new int[] {0,0,0,0,0}, new int[]{1,1,1,1,1}, new int[] {0,1,0,1,0},new int[] {0,1,0,1,0},new int[]{1,0,0,0,1}}));
//        KthLargest K = new KthLargest(3, new int[] {4,5,8,2});
//
//        System.out.println(K.add(3));
//        System.out.println(K.add(5));
//        System.out.println(K.add(10));
//        System.out.println(K.add(9));
//        System.out.println(K.add(4));
//        int[] letters = new int[26];
//        for(int i = 0;i < 26;i++){
//            letters[i] = 1 << i;
//            System.out.println(letters[i]);
//        }
//
//        int[] solution = new int[100];
//        byte[] amounts = new byte[26];
//        for(String word : words){
//            Arrays.fill(amounts, (byte)0);
//            for(int j = 0;j < word.length();j++){
//                int value = word.charAt(j) - 'a';
//                int code = letters[value];
//                solution[amounts[value]++] |= value;
//            }
//        }
    }


    public static int opp(int in){
        for(Operation o : Operation.all) if(o.value == in) return o.ordinal();
        return -1;
    }

    public static float eval(CharSeq expression){
        FloatQueue values = new FloatQueue().addLast(0);
        IntQueue operations = new IntQueue();
        CharSeq stack = new CharSeq();
        for(int i = 0;i < expression.size;i++){
            int opp = opp(expression.get(i));
            if(Character.isDigit(expression.get(i)) || expression.get(i) == '.') stack.add(expression.get(i));
            else if(opp >= 0){
                if(stack.size > 0){
                    if(Character.isDigit(stack.get(0))) values.addLast(Float.parseFloat(stack.toString()));
                    else values.addLast(variables.get(stack.toString()));
                    stack.clear();
                }
                boolean parentheses = opp == Operation.parenthesesClose.ordinal();
                while(operations.size > 0 && (opp <= operations.last() || parentheses) && operations.last() != Operation.parentheses.ordinal()) values.addLast(Operation.all[operations.popLast()].result.get(values.popLast(), values.popLast()));
                if(parentheses) operations.popLast();
            }else stack.add(expression.get(i));
        }
        if(stack.size > 0){
            if(Character.isDigit(stack.get(0))) values.addLast(Float.parseFloat(stack.toString()));
            else values.addLast(variables.get(stack.toString()));
        }
        while(operations.size > 0) values.addLast(Operation.all[operations.popLast()].result.get(values.popLast(), values.popLast()));
        return values.last();
    }

    public int numberOfSubarrays(int[] nums, int k){
        if(nums.length < Short.MAX_VALUE){
            short odds = 0;
            for(short i = 0;i < nums.length;i++) if((nums[i] & 1) == 1) odds++;

            short run = 0, index = 0;
            short[] runs = new short[odds + 1];
            for(short i = 0;i < nums.length;i++){
                if((nums[i] & 1) == 1){
                    runs[index++] = run;
                    run = 0;
                }else run++;
            }
            runs[odds] = run;

            short total = 0;
            for(short i = 0;i < odds + 1 - k;i++){
                total += (runs[i] + 1) * (runs[i + k] + 1);
            }

            return total;
        }else{
            int odds = 0;
            for(int i = 0;i < nums.length;i++) if((nums[i] & 1) == 1) odds++;

            int run = 0, index = 0;
            int[] runs = new int[odds + 1];
            for(int i = 0;i < nums.length;i++){
                if((nums[i] & 1) == 1){
                    runs[index++] = run;
                    run = 0;
                }else run++;
            }
            runs[odds] = run;

            int total = 0;
            for(int i = 0;i < odds + 1 - k;i++){
                total += (runs[i] + 1) * (runs[i + k] + 1);
            }

            return total;
        }
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2){
        int[] smaller, larger;
        if(nums1.length < nums2.length){
            smaller = nums1;
            larger = nums2;
        }else{
            smaller = nums2;
            larger = nums1;
        }

        if(larger.length == 1) return (larger[0] + smaller[0]) / 2.0;

        int total = nums1.length + nums2.length;
        boolean even = (total & 1) == 0;

        int min = 0, max = smaller.length - 1;
        int index = (max - min + 1) / 2;
        int corrosponding = (total / 2) - (index + 1);

        double result = -1;
        while(true){
            if(smaller[index] == larger[corrosponding]){
                result = smaller[index];
                break;
            }

            if(smaller[index] > larger[corrosponding]) max = Math.max(index - 1, min);
            else if(smaller[index] < larger[corrosponding]) min = Math.min(index + 1, max);

            corrosponding -= (max - min + 1) / 2 - index;
            index = (max - min + 1) / 2;

            if(max == min){
                //Do the boogie
                if(max == 0){
                    if(even){
                        result = Math.min(larger[corrosponding] + smaller[0], larger[corrosponding] + larger[corrosponding + 1]) / 2.0;
                        if(smaller.length > 1) result = Math.min(result, (smaller[0] + smaller[1]) / 2.0);
                    }else result = Math.min(larger[corrosponding + 1], smaller[0]);
                }else if(min == smaller.length - 1){

                }else{
                    //Do the boogie pt 2 electric boogaloo
                }
                break;
            }

            //These theoretically should never happen
//            if(corrosponding < 0){
//                System.out.println("Corrosponding is < 0");
//                break;
//            }
//
//            if(corrosponding >= larger.length){
//                System.out.println("Corrosponding is out of bounds");
//                break;
//            }
//
//            if(max < min){
//                System.out.println("max < min????");
//                break;
//            }
        }

        return result;
    }

    public class ListNode{
        int val;
        ListNode next;

        ListNode(){
        }

        ListNode(int val){
            this.val = val;
        }

        ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        ListNode base = new ListNode(l1.val + l2.val);
        ListNode next = base;

        l1 = l1.next;
        l2 = l2.next;
        while(l1 != null || l2 != null){
            ListNode old = next;
            next.next = new ListNode(0);
            next = next.next;

            if(old.val >= 10){
                old.val -= 10;
                next.val++;
            }

            if(l1 != null){
                l1 = l1.next;
                if(l1 != null) next.val += l1.val;
            }
            if(l2 != null){
                l2 = l2.next;
                if(l2 != null) next.val += l1.val;
            }
        }

        if(next.val >= 10){
            next.val -= 10;
            next.next = new ListNode(1);
        }
        return base;
    }

    public int lengthOfLongestSubstring(String s){
        if(s.length() < 1) return 0;

        int end = 0, max = 1;
        int[] lastSeen = new int['~' - ' ' + 1];
        for(int i = 0;i < s.length();i++){
            int value = s.charAt(i) - ' ';
            if(end < lastSeen[value]) end = lastSeen[value];
            lastSeen[value] = i + 1;

            if(i - end + 1 > max) max = i - end + 1;
        }
        return max;
    }

    public int longestSubarray(int[] nums, int limit){
        int max = 0;
        int end = 0, maxIndex = 0, minIndex = 0;
        for(int i = 0;i < nums.length;i++){
            if(nums[i] < nums[minIndex]) minIndex = i;
            else if(nums[i] > nums[maxIndex]) maxIndex = i;
            if(nums[maxIndex] - nums[minIndex] > limit){
                end = Math.min(maxIndex, minIndex) + 1;
                minIndex = maxIndex = i;
                for(int j = end;j < i;j++){
                    if(nums[j] < nums[minIndex]) minIndex = j;
                    else if(nums[j] > nums[maxIndex]) maxIndex = j;
                }
            }
            max = Math.max(max, i - end + 1);
        }
        return max;
    }

    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(){
        }

        TreeNode(int val){
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    public List<Integer> inorderTraversal(TreeNode root){
        LinkedList<TreeNode> next = new LinkedList<>();
        LinkedList<TreeNode> parents = new LinkedList<>();
        next.add(root);

        ArrayList<Integer> result = new ArrayList<>();
        while(!next.isEmpty()){
            TreeNode last = next.removeLast();

            if(last.right != null) next.addLast(last.right);
            if(last.left != null) next.addLast(last.left);

            if(last.left == null && last.right == null){
                result.add(last.val);
                if(parents.getLast().right == last || (parents.getLast().right == null && parents.getLast().left == last)) result.add(parents.removeLast().val);
            }else parents.addLast(last);
        }
        return result;
    }


    public int reverse(int x){
//        boolean negative = x < 0;
//        if(negative) x = -x;
//        boolean isMax = x == 2147483647 7483
//
//        int result = 0;
//        for(int i = 0;x != 0;i++){
//            result = result * 10 + x % 10;
//            x /= 10;
//        }
//
//        if(result == Integer.MAX_VALUE &&)
//
//            return (negative ? -1 : 1) * result;
        return -1;
    }


    public static enum Operation{
        add('+', (a, b) -> a + b), subtract('-', (a, b) -> a - b), multiply('*', (a, b) -> a * b), divide('/', (a, b) -> a / b), power('^', (a, b) -> pow(a, b)), parentheses('('), parenthesesClose(')');

        public static Operation[] all = values();

        public char value;
        public Floatfff result;

        Operation(char value){
            this(value, null);
        }

        Operation(char value, Floatfff result){
            this.value = value;
            this.result = result;
        }
    }

    public int[] sortJumbled(int[] mapping, int[] nums){
        int[] unscrambled = new int[nums.length];
        for(int i = 0;i < nums.length;i++){
            int initial = nums[i];
            if(initial == 0){
                unscrambled[i] = mapping[0];
                continue;
            }
            int place = 1;
            while(initial != 0){
                unscrambled[i] += place * mapping[initial % 10];
                initial /= 10;
                place *= 10;
            }
        }
        Integer[] pointers = new Integer[nums.length];
        for(int i = 0;i < nums.length;i++) pointers[i] = i;

        Arrays.sort(pointers, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2){
                return unscrambled[o1] == unscrambled[o2] ? o1 - o2 : unscrambled[o1] - unscrambled[o2];
            }
        });

        for(int i = 0;i < nums.length;i++) unscrambled[i] = nums[pointers[i]];
        return unscrambled;
    }

//    public void quickSort(int[] nums, int from, int to){
//        if(from + 1 >= to) return;
//
//        int pivot = nums[to - 1];
//        int lastSmaller = from;
//        for(int i = from;i < to;i++){
//            if(nums[i] <= pivot){
//                if(lastSmaller != i){
//                    int tmp = nums[i];
//                    nums[i] = nums[lastSmaller];
//                    nums[lastSmaller] = tmp;
//                }
//                lastSmaller++;
//            }
//        }
//
//        quickSort(nums, from, lastSmaller - 1);
//        quickSort(nums, lastSmaller - 1, to);
//    }


    public int partition(int[] arr, int low, int high){
        int pivot = arr[high];

        int i = (low - 1);

        for(int j = low;j <= high - 1;j++){
            if(arr[j] < pivot){
                i++;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }

        int tmp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmp;
        return (i + 1);
    }

    public void quickSort(int[] arr, int low, int high){
        if(low < high){
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public int[] sortArray(int[] nums){
//        quickSort(nums, 0, nums.length);
        return nums;
    }


    public int findTheCity(int n, int[][] edges, int distanceThreshold){
        short[][] paths = new short[n][n];

        for(int i = 0;i < n;i++){
            for(int j = 0;j < n;j++) paths[i][j] = i == j ? 0 : Short.MAX_VALUE;
        }

        for(int i = 0;i < edges.length;i++){
            paths[edges[i][0]][edges[i][1]] = (short)edges[i][2];
            paths[edges[i][1]][edges[i][0]] = (short)edges[i][2];
        }

        for(int k = 0;k < n;k++){
            for(int i = 0;i < n;i++){
                for(int j = 0;j < n;j++){
                    if(paths[i][k] == Short.MAX_VALUE || paths[k][j] == Short.MAX_VALUE) continue;
                    if(paths[i][j] > paths[i][k] + paths[k][j]) paths[i][j] = (short)(paths[i][k] + paths[k][j]);
                }
            }
        }

        int minCount = n, minIndex = -1;
        for(int i = 0;i < n;i++){
            int count = 0;
            for(int j = 0;j < n;j++) if(paths[i][j] <= distanceThreshold) count++;

            if(count <= minCount){
                minCount = count;
                minIndex = i;
            }
        }
        return minIndex;
    }

    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost){
        int[][] costs = new int[26][26];

        for(int i = 0;i < 26;i++) for(int j = 0;j < 26;j++) costs[i][j] = i == j ? 0 : 100000000;

        for(int i = 0;i < original.length;i++){
            int a = original[i] - 'a';
            int b = changed[i] - 'a';
            costs[a][b] = min(costs[a][b], cost[i]);
        }

        for(int k = 0;k < 26;k++){
            for(int i = 0;i < 26;i++){
                for(int j = 0;j < 26;j++){
                    costs[i][j] = min(costs[i][j], costs[i][k] + costs[k][j]);
                }
            }
        }

        long result = 0;
        for(int i = 0;i < source.length() && result != -1;i++){
            int a = source.charAt(i) - 'a';
            int b = target.charAt(i) - 'a';
            if(costs[a][b] < 0) result = -1;
            else result += costs[a][b];
        }
        return result;
    }

    public int numberOfSubstrings(String s){
        short[] sum0s = new short[s.length() + 1];
        short[] sum1s = new short[s.length() + 1];
        for(int i = 0;i < s.length();i++){
            if(s.charAt(i) == '0') sum0s[i + 1]++;
            else sum1s[i + 1]++;

            sum0s[i + 1] += sum0s[i];
            sum1s[i + 1] += sum1s[i];
        }

        int result = 0;
        for(int i = 0;i < s.length();i++){
            for(int j = 0;j <= i;j++){
                int ones = sum1s[i + 1] - sum1s[j];
                int zeroes = sum0s[i + 1] - sum0s[j];
                if(ones >= zeroes * zeroes) result++;
            }
        }
        return result;
    }

    public int secondMinimum(int n, int[][] edges, int time, int change){
        short[] min1 = new short[n];
        short[] min2 = new short[n];

        for(int i = 0;i < n;i++){
            min1[i] = Short.MAX_VALUE;
            min2[i] = Short.MAX_VALUE;
        }

        ArrayList<Short>[] edge = new ArrayList[n];
        for(int i = 0;i < edges.length;i++){
            short a = (short)(edges[i][0] - 1);
            short b = (short)(edges[i][1] - 1);

            if(edge[a] == null) edge[a] = new ArrayList<Short>();
            if(edge[b] == null) edge[b] = new ArrayList<Short>();

            edge[a].add(b);
            edge[b].add(a);
        }

        LinkedList<Short> node = new LinkedList<>();
        LinkedList<Short> dist = new LinkedList<>();

        node.add((short)0);
        dist.add((short)0);

        while(!node.isEmpty()){
            int current = node.pop();
            short curDist = dist.pop();

            if(curDist < min2[current]){
                if(curDist == min1[current]) continue;

                if(curDist < min1[current]){
                    min2[current] = min1[current];
                    min1[current] = curDist;
                }else min2[current] = curDist;

                if(edge[current] != null){
                    for(int i = 0;i < edge[current].size();i++){
                        short nextNode = edge[current].get(i);
                        if(curDist + 1 < min2[nextNode]){
                            node.add(nextNode);
                            dist.add((short)(curDist + 1));
                        }
                    }
                }
            }
        }

        int result = 0;
        for(int i = 0;i < min2[n - 1];i++) result = ((result / change) % 2 == 0 ? result : (result / change + 1) * change) + time;
        return result;
    }

    public String[] thousands = new String[]{"Thousand", "Million", "Billion"};

    public String hundred = "Hundred";

    public String[] tens = new String[]{"Twenty", "Thrity", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    public String[] elevens = new String[]{"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

    public String[] ones = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};

    public String numberToWords(int num){
        StringBuilder result = new StringBuilder(128);

        int place = 1000000000;
        for(int thousand = 0;thousand < 4;thousand++){
            int real = num / place % 1000;

            if(real != 0){
                int h = real / 100;
                if(h != 0) result.append(ones[h - 1]).append(' ').append(hundred).append(' ');

                int t = real % 100;

                if(t / 10 == 0) result.append(ones[t - 1]).append(' ');
                else if(t / 10 == 1) result.append(elevens[t - 10]).append(' ');
                else{
                    result.append(tens[t / 10 - 2]).append(' ');
                    if(t % 10 != 0) result.append(ones[t % 10 - 1]).append(' ');
                }

                if(thousand <= 2) result.append(thousands[thousand]).append(' ');
            }

            place /= 1000;
        }

        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    public int[] d4x = new int[]{1, 0, -1, 0}, d4y = new int[]{0, 1, 0, -1};

    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart){
        int res[][] = new int[rows * cols][2], index = 0;

        int x = cStart, y = rStart, d = 0, l = 0;
        while(index < res.length){
            if(d % 2 == 0) l++;

            for(int i = 0;i < l && index < res.length;i++){
                if(x >= 0 && x < cols && y >= 0 && y < rows){
                    res[index++][0] = y;
                    res[index++][1] = x;
                }
                x += d4x[d];
                y += d4y[d];
            }

            d = (d + 1) % 4;
        }

        return res;
    }


    public int numMagicSquaresInside(int[][] grid){
        int res = 0;
        for(int x = 0;x < grid.length - 2;x++){
            for(int y = 0;y < grid[x].length - 2;y++){
                boolean unique = true;
                for(int i = 0;i < 9 && unique;i++){
                    for(int j = 0;j < 9 && unique;j++) if((i != j && grid[x + i / 3][y + i % 3] == grid[x + j / 3][y + j % 3]) || grid[x + j / 3][y + j % 3] > 9) unique = false;
                }
                if(!unique) continue;

                int sum = grid[x][y] + grid[x + 1][y] + grid[x + 2][y];
                if(grid[x][y + 1] + grid[x + 1][y + 1] + grid[x + 2][y + 1] != sum ||
                grid[x][y + 2] + grid[x + 1][y + 2] + grid[x + 2][y + 2] != sum ||
                grid[x][y] + grid[x][y + 1] + grid[x][y + 2] != sum ||
                grid[x + 1][y] + grid[x + 1][y + 1] + grid[x + 1][y + 2] != sum ||
                grid[x + 2][y] + grid[x + 2][y + 1] + grid[x + 2][y + 2] != sum ||
                grid[x][y] + grid[x + 1][y + 1] + grid[x + 2][y + 2] != sum ||
                grid[x + 2][y] + grid[x + 1][y + 1] + grid[x][y + 2] != sum){
                    continue;
                }
                res++;
            }
        }

        return res;
    }

    public String minRemoveToMakeValid(String s){
        ArrayList<Integer> last = new ArrayList<>(), extra = new ArrayList<>();
        for(int i = 0;i < s.length();i++){
            if(s.charAt(i) == '(') last.add(i);
            else{
                if(last.size() > 0) last.remove(last.size() - 1);
                else extra.add(i);
            }
        }

        boolean[] remove = new boolean[s.length()];
        for(int i = 0;i < last.size();i++) remove[last.get(i)] = true;
        for(int i = 0;i < extra.size();i++) remove[extra.get(i)] = true;

        StringBuilder result = new StringBuilder(s.length());
        for(int i = 0;i < s.length();i++) if(!remove[i]) result.append(s.charAt(i));

        return result.toString();
    }

    public int d4x(int r){
        if(r == 0) return 1;
        if(r == 2) return -1;
        return 0;
    }

    public int d4y(int r){
        if(r == 1) return 1;
        if(r == 3) return -1;
        return 0;
    }


    public boolean valid(String[] grid, int x, int y){
        if(x < 0 || x >= grid.length * 3 || y < 0 || y >= grid.length * 3) return false;
        char c = grid[y / 3].charAt(x / 3);
        if(c == '/') return x % 3 == y % 3;
        if(c == '\\') return x % 3 == 2 - y % 3;
        return true;
    }


    public void fill(String[] grid, boolean[][] visited, int x, int y){
        visited[x][y] = true;
        for(int i = 0;i < 4;i++){
            int nx = x + d4x(i);
            int ny = y + d4y(i);
            if(valid(grid, nx, ny) && !visited[nx][ny]) fill(grid, visited, nx, ny);
        }
    }


    public int regionsBySlashes(String[] grid){
        int n = grid.length;

        int res = 0;
        boolean[][] visited = new boolean[n * 3][n * 3];
        for(int x = 0;x < visited.length;x++){
            for(int y = 0;y < visited[x].length;y++){
                if(valid(grid, x, y) && !visited[x][y]){
                    fill(grid, visited, x, y);
                    res++;
                }
            }
        }
        return res;
    }

    public static class KthLargest{
        public int[] lastK;

        public KthLargest(int k, int[] nums){
            lastK = new int[k];

            Arrays.sort(nums);
            for(int i = 0;i < k;i++) lastK[i] = nums[nums.length - k + i];
        }

        public int add(int val){
            boolean found = false;

            if(val > lastK[0]) lastK[0] = lastK[1];
            else return lastK[0];

            for(int i = 1;i < lastK.length - 1 && !found;i++){
                if(val <= lastK[i]){
                    lastK[i - 1] = val;
                    found = true;
                }else lastK[i] = lastK[i + 1];
            }
            if(!found){
                if(val < lastK[lastK.length - 1]) lastK[lastK.length - 2] = val;
                else lastK[lastK.length - 1] = val;
            }
            return lastK[0];
        }
    }

    public void findSolutions(int index, int value, int target, int[] max, List<Integer> used, List<List<Integer>> solutions){
        if(value > target) return;
        if(value == target){
            solutions.add(new ArrayList<Integer>(used));
            return;
        }

        if(index > max.length) return;
        for(int i = 0;i <= max[index];i++){
            findSolutions(index + 1, value + i * (index + 1), target, max, used, solutions);
            used.add(index + 1);
        }
        for(int i = 0;i <= max[index];i++) used.remove(used.size() - 1);
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        int[] max = new int[target];
        for(int i = 0;i < candidates.length;i++){
            if(candidates[i] > target) continue;
            max[candidates[i] - 1]++;
        }

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        findSolutions(0, 0, target, max, new ArrayList<Integer>(100), result);
        return result;
    }

    public int calc(String str, int[][] dp, int i, int j){
        if(i > j) return 0;
        if(dp[i][j] != -1) return dp[i][j];

        char ch = str.charAt(i);
        int ans = 1 + calc(str, dp, i + 1, j);
        for(int k = i + 1;k <= j;k++){
            if(str.charAt(k) == ch){
                ans = Math.min(ans, calc(str, dp, i, k - 1) + calc(str, dp, k + 1, j));
            }
        }
        return dp[i][j] = ans;
    }

    public int strangePrinter(String s){
        int n = s.length();
        int[][] dp = new int[n][n];
        for(int i = 0;i < n;i++) Arrays.fill(dp[i], -1);
        return calc(s, dp, 0, n - 1);
    }


    public void addAll(ArrayList<List<Integer>> result, ArrayList<Integer> current, int[] nums, int i){
        if(i > nums.length){
            result.add(current);
            return;
        }

        addAll(result, current, nums, i + 1);
        ArrayList<Integer> newarr = new ArrayList<>(current);
        current.add(nums[i]);
        addAll(result, newarr, nums, i + 1);
    }

    public List<List<Integer>> subsets(int[] nums){
        ArrayList<List<Integer>> result = new ArrayList<>();
        addAll(result, new ArrayList<Integer>(), nums, 0);
        return result;
    }

    //TODO: Use switch cases

    public void fill(int[][] grid, int value, int x, int y){
        if(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) return;
        if(grid[x][y] != 1) return;

        grid[x][y] = value;
        fill(grid, value, x + 1, y);
        fill(grid, value, x, y + 1);
        fill(grid, value, x - 1, y);
        fill(grid, value, x, y - 1);
    }

    public boolean fill(int[][] grid, int[][] back, int value, int x, int y){
        if(x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) return true;
        if(grid[x][y] != 1) return true;

        grid[x][y] = value;
        boolean res = back[x][y] != 0;
        res &= fill(grid, back, value, x + 1, y);
        res &= fill(grid, back, value, x, y + 1);
        res &= fill(grid, back, value, x - 1, y);
        res &= fill(grid, back, value, x, y - 1);
        return res;
    }

    public int countSubIslands(int[][] grid1, int[][] grid2){
        int count = 2, ans = 0;
        for(int x = 0;x < grid1.length;x++){
            for(int y = 0;y < grid1[0].length;y++){
                if(grid2[x][y] == 1) ans += fill(grid2, grid1, count++, x, y) ?1 : 0;
            }
        }

        return ans;
    }

}
