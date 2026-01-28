package examples;

import java.util.*;

public class Sum{
    public static int work = 0;

    public static int[] prefix_sum(int[] input){
        if(input.length <= 1) return new int[]{input[0]};
        if(input.length == 2) return new int[]{input[0], input[0] + input[1]};

        int[] halved = new int[(input.length + 1) / 2];
        for(int i = 0;i < input.length;i += 2){ // In parallel computing this would be O(1)
            halved[i / 2] = input[i] + (i + 1 < input.length ? input[i + 1] : 0);
            work++;
        }

        int[] sum = prefix_sum(halved);
        int[] result = new int[input.length];
        for(int i = 0;i < input.length;i += 2){
            result[i] = sum[i / 2] - (i + 1 < input.length ? input[i + 1] : 0);
            if(i + 1 < input.length) result[i + 1] = sum[i / 2];
            work++;
        }

        return result;
    }

    public static void main(String[] args){
        int n = 10000;
        int[] arr = new int[n];
        Arrays.fill(arr, 1);

        arr = prefix_sum(arr);
        System.out.println("Work Ratio: " + (double)((double)work / n));
        System.out.println(Arrays.toString(arr));
        for(int i = 0;i < n;i++){
            if(arr[i] != i + 1) throw new RuntimeException("Not equal at index: " + i);
        }
    }
}
