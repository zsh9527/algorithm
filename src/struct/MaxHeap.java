package struct;

/**
 *  最大堆 -- 内部为二叉树
 *
 * parent > child
 */
public class MaxHeap {

    private int length = 0;
    private final int[] datas;

    public MaxHeap(int maxSize) {
        int capacity = findTableSizeof2(maxSize);
        // 简化操作, 初始数据为0, 所以不能传负数
        this.datas = new int[capacity];
    }

    /**
     * 将数扩充为2的n次方
     */
    int findTableSizeof2(int target){
        int temp = target -1;
        temp |= temp >> 1;
        temp |= temp >> 2;
        temp |= temp >> 4;
        temp |= temp >> 8;
        temp |= temp >> 16;
        return temp + 1;
    }

    /**
     * 效率 nlog2^n
     */
    public void buildMinHeap(int[] arrs) {
        for (int obj : arrs) {
            push(obj);
        }
    }

    /**
     * 效率 n
     */
    public void buildMinHeap2(int[] arrs) {
        this.length = arrs.length;
        for (int i = 0; i< arrs.length; i++) {
            datas[i + 1] = arrs[i];
        }
        // 从结尾开始， 构建245， 367的最大堆, 继续递归构建123的最大堆
        for (int i = length / 2; i >= 1; i--) {
            buildSubTree(i);
        }
    }

    /**
     * 从结尾开始， 构建245， 367的最大堆, 继续递归构建123的最大堆
     *
     * @param parentIndex 子树的父节点索引
     */
    public void buildSubTree(int parentIndex) {
        int temp = datas[parentIndex];
        while (parentIndex < length) {
            int bigIndex = datas[parentIndex<<1] > datas[(parentIndex<<1)+1] ? parentIndex<<1 : (parentIndex<<1)+1;
            if (datas[bigIndex] > temp) {
                datas[parentIndex] = datas[bigIndex];
                parentIndex = bigIndex;
            } else {
                datas[parentIndex] = temp;
                break;
            }
        }
        System.out.println(this);
    }

    /**
     * 结尾增加数据， 然后移动数据维持堆特性
     */
    public void push(int obj) {
        // length+1, 因为index为0的数据项为空的
        int insertIndex = length + 1;
        for (int parentIndex = insertIndex>>1; parentIndex > 0 && datas[parentIndex] < obj;
             insertIndex = parentIndex, parentIndex = parentIndex>>1) {
            // 当前数据大于父节点数据, 交换数据
            datas[insertIndex] = datas[parentIndex];
        }
        datas[insertIndex] = obj;
        length++;
        System.out.println(this);
    }

    /**
     * 移除头节点数据， 将最后一个元素移动到头节点， 然后移动数据维持堆特性
     */
    public int remove() {
        int result = datas[1];
        int lastData = datas[length];
        for (int i = 1; i < length; ) {
            int bigIndex = datas[i<<1] > datas[(i<<1)+1] ? i<<1 : (i<<1)+1;
            if (datas[bigIndex] > lastData) {
                datas[i] = datas[bigIndex];
                i = bigIndex;
            } else {
                datas[i] = lastData;
                break;
            }
        }
        // 清除旧的数据
        datas[length] = 0;
        length--;
        System.out.println(this);
        return result;
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("长度").append(this.length).append("\n");
        for (int i = 1; i < this.length + 1; i++) {
            sb.append(datas[i] + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //MaxHeap list = new MaxHeap(50);
        //list.bulidMinHeap(new int[]{5,3,8,9,11,24,1,9});
        //System.out.println();
        //for (int i = 0; i < 8; i++) {
        //    list.remove();
        //}

        MaxHeap list2 = new MaxHeap(50);
        list2.buildMinHeap2(new int[]{5,3,8,9,11,24,1,9});
        System.out.println(list2);
    }
}