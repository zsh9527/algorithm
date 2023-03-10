package struct;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static utils.TreeNodeUtils.*;

/**
 * 二叉树, 内部链表实现
 */
public class TreeNode {

    public TreeNode parent;
    public TreeNode left;
    public TreeNode right;
    public int data;

    public TreeNode(int data, TreeNode parent) {
        this.data = data;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.valueOf(this.data);
    }

    public static void main(String[] args) {
        int[] arr = {-1, 1, 3, 3, 7, 5, 5, 7};
        TreeNode root = generateTree(arr);
        System.out.println("层次遍历");
        levelIterator(root);
        System.out.println();
        System.out.println("前序遍历");
        firstRootIterator(root);
        System.out.println();
        System.out.println("中序遍历");
        centerRootIterator(root);
        System.out.println();
        System.out.println("后序遍历");
        lastRootIterator(root);
        System.out.println();
        System.out.println("是否平衡树");
        System.out.println(isBalanceTree(root));
        int[] arr2 = {-1, 1, 3, 3, 7, 5, 7, 7};
        TreeNode root2 = generateTree(arr2);
        System.out.println(isBalanceTree(root2));
        System.out.println("生成哈夫曼树--层次遍历");
        int[] arr3 = {-1, 1, 5, 9, 25, 3, 6, 70, 22};
        TreeNode root3 = generateHaermanTree(arr3);
        levelIterator(root3);
        System.out.println("节点71座旋转");
        leftRotation(root3.right);
        levelIterator(root3);
    }

    /**
     * 从数组生成二叉树
     * 数组数据从1开始
     *
     * @return 二叉树根节点
     */
    public static TreeNode generateTree(int[] arr) {
        TreeNode[] nodes = new TreeNode[arr.length];
        nodes[1] = new TreeNode(arr[1], null);
        for (int i = 2; i < arr.length; i++) {
            TreeNode parent = nodes[i / 2];
            nodes[i] = new TreeNode(arr[i], parent);
            if (i % 2 == 0) {
                parent.left = nodes[i];
            } else {
                parent.right = nodes[i];
            }
        }
        return nodes[1];
    }

    /**
     * 从数组生成哈尔曼树
     * 递归每次取权重最小的数生成一颗新的树
     *
     * @return 二叉树根节点
     */
    public static TreeNode generateHaermanTree(int[] arr) {
        List<TreeNode> nodes = new LinkedList<>();
        for (int i = 1; i < arr.length; i++) {
            TreeNode node = new TreeNode(arr[i], null);
            nodes.add(node);
        }
        // 每次取权重最小的数生成一颗新的树
        while (nodes.size() != 1) {
            AtomicReference<TreeNode> min1 = new AtomicReference<>(nodes.get(0));
            AtomicReference<TreeNode> min2 = new AtomicReference<>(nodes.get(1));
            nodes.stream().skip(2).forEach(obj -> {
                if (obj.data < min1.get().data || obj.data < min2.get().data) {
                    if (min1.get().data > min2.get().data) {
                        min1.set(obj);
                    } else {
                        min2.set(obj);
                    }
                }
            });
            // 生成一颗新的树
            TreeNode node = new TreeNode(min1.get().data + min2.get().data, null);
            node.left = min1.get();
            node.right = min2.get();
            min1.get().parent = node;
            min2.get().parent = node;
            nodes.remove(min1.get());
            nodes.remove(min2.get());
            nodes.add(node);
        }
        return nodes.get(0);
    }

    /**
     * 检查是否为平衡二叉树, 即根节点左子树和右子树完全相等
     */
    public static boolean isBalanceTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        return compareTwoTree(root.left, root.right);
    }

    /**
     * 比较两个子树的对称性
     */
    public static boolean compareTwoTree(TreeNode tree1, TreeNode tree2) {
        if (tree1 == null && tree2 == null) {
            return true;
        }
        if (tree1 == null || tree2 == null) {
            return false;
        }
        if (tree1.data == tree2.data) {
            // 递归检查左子树和右子树
            if (compareTwoTree(tree1.left, tree2.right)) {
                return compareTwoTree(tree1.right, tree2.left);
            }
            return false;
        }
        return false;
    }
}
