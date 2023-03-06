package sort;

import utils.ArrayUtils;

/**
 * 归并排序 -- 时间复杂度 nlog(n)
 * 缺点为需要额外空间
 *
 * 递归， 将n个元素分为n/2个元素的子序列; 合并所有的子序列
 */
public class MergeSort implements Sort {

    @Override
    public void sort(int[] arr) {
        sort(arr, 0 , arr.length - 1);
    }

    public void sort(int[] arr, int startIndex, int endIndex) {
        if (endIndex <= startIndex) {
            return;
        }
        int mid = (startIndex + endIndex) / 2;
        sort (arr, startIndex, mid);
        sort (arr, mid + 1, endIndex);
        merge(arr, startIndex, mid + 1, endIndex);
    }

    /**
     * 合并两堆已排序好的列表
     *
     * @param arr 包含所有元素的数组
     * @param arr1StartIndex 第一个排序好的列表在数组中的开始索引
     * @param arr2StartIndex 第一个排序好的列表在数组中的结束索引-1 第二个排序好的列表在数组中的开始索引
     * @param arr2EndIndex 第二个排序好的列表在数组中的结束索引
     */
    private void merge(int[] arr, int arr1StartIndex, int arr2StartIndex, int arr2EndIndex) {
        int[] result = new int[arr2EndIndex - arr1StartIndex + 1];
        // 两个数组索引位置
        int x = arr1StartIndex;
        int y = arr2StartIndex;
        for (int i = 0; i < result.length; i++) {
            if (x > arr2StartIndex - 1) {
                result[i] = arr[y++];
            } else if (y > arr2EndIndex) {
                result[i] = arr[x++];
            } else {
                if (arr[x] < arr[y]) {
                    result[i] = arr[x++];
                } else {
                    result[i] = arr[y++];
                }
            }
        }
        System.arraycopy(result, 0, arr, arr1StartIndex, result.length);
        ArrayUtils.printArray(arr);
    }

    public static void main(String[] args) {
        Sort sort = new MergeSort();
        sort.checkClass();
    }
}
