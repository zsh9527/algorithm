package utils;

import sort.Sort;

/**
 * ArrayUtils
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/03/07 12:05
 */
public class ArrayUtils {

    public static void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("长度").append(arr.length).append("---");
        for (int j : arr) {
            sb.append(j).append(" ");
        }
        System.out.println(sb);
    }

    /**
     * 交换数组中两处位置数据
     */
    public static void swap(int[] arr, int index1, int index2) {
        check(arr, index1);
        check(arr, index2);
        if (arr[index1] == arr[index2]) {
            return;
        }
        arr[index1] = arr[index1]^arr[index2];
        arr[index2] = arr[index1]^arr[index2];
        arr[index1] = arr[index1]^arr[index2];
    }

    /**
     * 检查索引
     */
    public static void check(int[] arr, int index) {
        if (index < 0 || index >= arr.length) {
            throw new RuntimeException("index out of range");
        }
    }

    /**
     * 检查排序class
     */
    public static void checkSortedClass(Sort sort) {

    }

    /**
     * 检查排序后数据
     */
    public static void checkSortedData(int[] arr, int[] exceptArr) {
        for (int i = 0; i < exceptArr.length; i++) {
            assert arr[i] == exceptArr[i];
        }
    }
}
