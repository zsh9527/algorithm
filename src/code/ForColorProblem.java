import java.util.HashSet;

/**
 * 四色问题 -- 任意地图能够只用4种颜色着色
 * 顶点使用一维数组表示（A, B, C, D, E, F)，边用二维数组表示（1, 表示两个顶点相邻）
 */
public class ForColorProblem {
    // 需要绘制节点
    public final PointObject[] points;
    // 颜色种类
    private final int colorNumber;
    // 顶点关联关系
    private final int[][] pointRelated = new int[][]{
        {0, 1 , 1, 1, 1, 1},
        {1, 0 , 1, 0, 1, 1},
        {1, 1 , 0, 1, 0, 0},
        {1, 0 , 1, 0, 1, 0},
        {1, 1 , 0, 1, 0, 1},
        {1, 1 , 0, 0, 1, 0}
    };

    /**
     * 指定绘制颜色数量
     */
    public ForColorProblem(int colorNumber) {
        this.points = new PointObject[6];
        this.colorNumber = colorNumber;
        for (int i = 0; i < 6; i++) {
            points[i] = new PointObject("point" + i);
        }
        paint();
    }

    /**
    * 给所有顶点着色
    * 1. 给第0个顶点着色， 用颜色0
    * 2. 给下一个顶点着色， 排除上面 已着色顶点中其相邻的顶点已用颜色
    * 3. 如何没有可用颜色了， 回溯上一个顶点， 用下一个可用颜色着色
    */
    private void paint() {
        HashSet<Integer> usedColorSet = new HashSet();
        // 逐步给各个点着色
        int pointIndex = 0;
        while (pointIndex < 6) {
            if (paintPoint(pointIndex, usedColorSet)) {
                // 绘制成功, 继续绘制下一个节点
                pointIndex++;
            } else {
                // 绘制失败， 回溯上一个顶点， 重新着色
                if (pointIndex > 1) {
                    pointIndex--;
                } else {
                    throw new RuntimeException("不可解问题");
                }
            }
        }
    }

    /**
     * 着色第n个节点，
     *
     * @param pointIndex 顶点index
     * @param usedColorSet 相邻的顶点已用颜色集合
     *
     * @return 绘制成功还是失败
     */
    private boolean paintPoint(int pointIndex, HashSet<Integer> usedColorSet) {
        usedColorSet.clear();
        // 可选用颜色索引, 如何已经着色过则为重新绘制, 必须取下一个颜色
        int colorIndex = 0;
        if (points[pointIndex].color != null) {
            System.out.println(points[pointIndex] + "-颜色重复-重新绘制");
            colorIndex = points[pointIndex].color + 1;
            points[pointIndex].color = null;
        }
        // 收集相邻的顶点已用颜色
        for (int index = pointIndex - 1; index >= 0; index--) {
            if (pointRelated[pointIndex][index] == 1) {
                usedColorSet.add(points[index].color);
            }
        }
        System.out.println(points[pointIndex].point + "-相邻节点已用颜色-" + usedColorSet);
        for (int index = colorIndex; index < colorNumber; index++) {
            if (!usedColorSet.contains(index)) {
                points[pointIndex].color = index;
                System.out.println(points[pointIndex]);
                break;
            }
        }
        return points[pointIndex].color != null;
    }

    /**
     * 节点对象， 包含节点名称和节点颜色
     */
    static class PointObject {
        // 省略set和get方法
        public String point;
        public Integer color;

        public PointObject(String point) {
            this.point = point;
        }

        @Override 
        public String toString() {
            return this.point + "-着色-" + this.color;
        }
    }

    public static void main(String[] args) {
        System.out.println("使用4种颜色绘制地图");
        ForColorProblem obj = new ForColorProblem(4);
        System.out.println("绘制结果");
        for (PointObject PointObject : obj.points) {
            System.out.print(PointObject + "\t");
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("使用3种颜色绘制地图");
        new ForColorProblem(3);
    }
}