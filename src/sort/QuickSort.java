package sort;

import utils.ArrayUtils;

/**
 * 快速排序 -- 时间复杂度 O(n2)。 平均时间复杂度为nlog2^n
 *
 * 递归， 以A（q）分界数组分为两部分， A1[p..q-1] A2[q+1.。r]; A[1]中元素都小于等于 A（q）,  A[2]中元素都大于 A（q）
 * q每次取第一个数据为分界点
 * 后续递归排序去掉q元素
 */
public class QuickSort implements Sort {

    @Override
    public void sort(int[] arr) {
        sort(arr, 0 , arr.length - 1);
    }

    public void sort(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        int bordIndex = splitByFirst(arr, startIndex, endIndex);
        sort(arr, startIndex, bordIndex - 1);
        sort(arr, bordIndex + 1, endIndex);
    }

    /**
     * 以arr[endIndex]界点排序， 小于等于arr[startIndex]放在左边， 大于arr[startIndex]放在右边
     *
     * 算法更简陋， 但是为稳定排序
     */
    private int splitByFirst(int[] arr, int startIndex, int endIndex) {
        // 取结尾数据作为分界点
        int bordData = arr[endIndex];
        // 分界索引
        int bordIndex = startIndex;
        // 遍历到结尾， 最后用于移动q
        for (int i = startIndex; i <= endIndex; i++) {
            if (arr[i] <= bordData) {
                // 存在一个小于等于数据， bordIndex移动
                // bordIndex和i不一致, 说明i前面存在bordIndex-i位大于bordData数据， 移动数据
                int temp = arr[i];
                int j = i;
                for (; j > bordIndex; j--) {
                    arr[j] = arr[j-1];
                }
                arr[j] = temp;
                bordIndex++;
            } else {
                // 存在一个大于数据, i递增, bordIndex不变
            }
        }
        ArrayUtils.printArray(arr);
        return bordIndex - 1;
    }

    /**
     * 以arr[startIndex]界点排序， 小于等于arr[startIndex]放在左边， 大于arr[startIndex]放在右边
     *
     * 从头遍历寻找不符合索引， 从尾遍历寻找不符合索引， 交换两个数据
     * 将arr[startIndex]插入合适位置
     * 此方法为不稳定排序，可能打乱数组顺序
     */
    private int splitByFirst2(int[] arr, int startIndex, int endIndex) {
        // 取开头数据作为分界点
        int bordData = arr[startIndex];
        // 跳过和自身比较
        int m = startIndex+1;
        int n = endIndex;
        // 从头遍历寻找不符合索引， 从尾遍历寻找不符合索引， 交换两个数据
        while (m < n) {
            while (arr[m] <= bordData && m < n) {
                m++;
            }
            while (arr[n] > bordData && m < n) {
                n--;
            }
            // 交换两个值
            ArrayUtils.swap(arr, m, n);
        }
        // 将arr[startIndex]插入合适位置； arr[startIndex]最小或第二小， m=n; arr[startIndex]最大， m=startIndex+1;
        int bordIndex;
        if (m == endIndex && arr[m] < bordData) {
            bordIndex = endIndex;
        } else {
            bordIndex = m -1;
        }
        arr[startIndex] = arr[bordIndex];
        arr[bordIndex] = bordData;
        ArrayUtils.printArray(arr);
        return bordIndex;
    }

    public static void main(String[] args) {
        QuickSort sort = new QuickSort();
        sort.checkClass();
    }
}
