package struct;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树, 内部链表实现
 */
public class TreeNode<E> {

    public TreeNode<E> parent;
    public TreeNode<E> left;
    public TreeNode<E> right;
    public E data;

    public TreeNode(E data, TreeNode<E> parent) {
        this.data = data;
        this.parent = parent;
    }

    public static void main(String[] args) {
        int[] arr = {-1, 1, 3, 3, 7, 5, 5, 7};
        TreeNode<Integer> root = generateTree(arr);
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
        TreeNode<Integer> root2 = generateTree(arr2);
        System.out.println(isBalanceTree(root2));
    }

    /**
     * 前序遍历 根结点 ---> 左子树 ---> 右子树
     */
    public static void firstRootIterator(TreeNode<Integer> node) {
        if (node != null) {
            System.out.print(node.data + "\t");
            firstRootIterator(node.left);
            firstRootIterator(node.right);
        }
    }

    /**
     * 中序遍历  左子树 ---> 根结点 ---> 右子树
     *
     * 压平的数据
     */
    public static void centerRootIterator(TreeNode<Integer> node) {
        if (node != null) {
            centerRootIterator(node.left);
            System.out.print(node.data + "\t");
            centerRootIterator(node.right);
        }
    }

    /**
     * 后序遍历  左子树 ---> 右子树 ---> 根结点
     */
    public static void lastRootIterator(TreeNode<Integer> node) {
        if (node != null) {
            lastRootIterator(node.left);
            lastRootIterator(node.right);
            System.out.print(node.data + "\t");
        }
    }

    /**
     * 层次遍历 -- 逐层遍历， 利用队列
     *
     * @param root 根节点 非null
     */
    public static void levelIterator(TreeNode<Integer> root) {
        Queue<TreeNode<Integer>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<Integer> node = queue.remove();
            System.out.print(node.data + "\t");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /**
     * 从数组生成二叉树
     * 数组数据从1开始
     *
     * @return 二叉树根节点
     */
    public static TreeNode<Integer> generateTree(int[] arr) {
        TreeNode<Integer>[] nodes = new TreeNode[arr.length];
        nodes[1] = new TreeNode<>(arr[1], null);
        for (int i = 2; i < arr.length; i++) {
            TreeNode<Integer> parent = nodes[i / 2];
            nodes[i] = new TreeNode<>(arr[i], parent);
            if (i % 2 == 0) {
                parent.left = nodes[i];
            } else {
                parent.right = nodes[i];
            }
        }
        return nodes[1];
    }

    /**
     * 检查是否为平衡二叉树, 即根节点左子树和右子树完全相等
     */
    public static boolean isBalanceTree(TreeNode<Integer> root) {
        if (root == null) {
            return true;
        }
        return compareTwoTree(root.left, root.right);
    }

    /**
     * 比较两个子树的对称性
     */
    public static boolean compareTwoTree(TreeNode<Integer> tree1, TreeNode<Integer> tree2) {
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
