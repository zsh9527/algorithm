package struct;

/**
 * 自定义简易版本ArrayList
 */
public class MyArrayList<T> {

    private int length = 0;
    private int capacity;
    // 无法初始化泛型数组
    private Object[] datas;

    public MyArrayList(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
        this.datas = new Object[capacity];
    }

    public void add(T obj) {
        this.length++;
        checkAndDouble();
        this.datas[length - 1] = obj;
    }

    public T get(int index) {
        if (index >= this.length) {
            return null;
        }
        return (T) this.datas[index];
    }

    public boolean contain(T obj) {
        for (int i = 0; i < length; i++) {
            if (obj == datas[i] || obj.equals(datas[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(int index) {
        if (index >= length) {
            return false;
        }
        for (int i = index; i < length-1; i++) {
            datas[i] = datas[i+1];
        }
        length--;
        return true;
    }

    public boolean remove(T obj) {
        for (int i = 0; i < length; i++) {
            if (obj == datas[i] || obj.equals(datas[i])) {
               return remove(i);
            }
        }
        return false;
    }

    public int size() {
        return this.length;
    }

    /**
     * 检查是否超出容量， 是则两倍扩容
     */
    private void checkAndDouble() {
        if (length > capacity) {
            Object[] newData = new Object[capacity  * 2];
            System.arraycopy(datas, 0, newData, 0, capacity);
            capacity = capacity * 2;
            datas = newData;
        }
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("长度").append(this.length).append("\n");
        for (int i = 0; i < this.length; i++) {
            sb.append(datas[i] + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>(5);
        for (int i = 0; i < 8; i++) {
            list.add("obj" + i);
            System.out.println(list);
        }
        System.out.println(list.get(3));
        System.out.println(list.contain("obj5"));
        System.out.println(list.contain("obj11"));
        System.out.println(list.remove("obj3"));
        System.out.println(list);
        System.out.println(list.remove(4));
        System.out.println(list);
    }
}