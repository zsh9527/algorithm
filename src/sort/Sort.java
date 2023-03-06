package sort;

import utils.ArrayUtils;

/**
 * 排序
 */
public interface Sort {

    void sort(int[] arr);

    default void checkClass() {
        int[] exceptArr = new int[]{1, 3, 5, 7, 9};
        int[] arr = new int[]{3, 5, 1, 9, 7};
        this.sort(arr);
        ArrayUtils.checkSortedData(arr, exceptArr);
        System.out.println("\n\n\n");
        int[] arr2 = new int[]{9, 7, 5, 1, 3};
        this.sort(arr2);
        ArrayUtils.checkSortedData(arr2, exceptArr);
    }
}

