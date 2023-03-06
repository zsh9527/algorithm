package sort;

import utils.ArrayUtils;

/**
 * 插入排序 -- 时间复杂度 n^2
 * 最小时间复杂度 n
 *
 * 在排序好的子数组A[0..j-1]后， 将数据A[j]插入， 形成排序好的子数组A[0..j]
 */
public class InsertSort implements Sort {

    @Override
    public void sort(int[] arr) {
        // x索引为待排序的数据
        for (int x = 1; x < arr.length; x++) {
            // 此次循环排序的数据
            int temp = arr[x];
            // 对比已排序好的数据索引
            int y = x - 1;
            for (; y >= 0 && temp < arr[y]; y--) {
                // 后移一位
                arr[y + 1] = arr[y];
            }
            arr[y + 1] = temp;
            ArrayUtils.printArray(arr);
        }
    }

    public static void main(String[] args) {
        Sort sort = new InsertSort();
        sort.checkClass();
    }
}
