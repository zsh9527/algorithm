package sort;

import utils.ArrayUtils;

/**
 * 冒泡排序 -- 时间复杂度 n^2
 *
 * 重复的交换相邻的两个反序列的元素
 */
public class BubblingSort  implements Sort {

    @Override
    public void sort(int[] arr) {
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr.length - 1; y++) {
                if (arr[y] > arr[y+1]) {
                    ArrayUtils.swap(arr, y, y+1);
                }
            }
            ArrayUtils.printArray(arr);
        }
    }

    public static void main(String[] args) {
        Sort sort = new BubblingSort();
        sort.checkClass();
    }
}
