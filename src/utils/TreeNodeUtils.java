package utils;

import struct.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 二叉树工具类
 */
public class TreeNodeUtils {

    /**
     * 前序遍历 根结点 ---> 左子树 ---> 右子树
     */
    public static void firstRootIterator(TreeNode node) {
        if (node != null) {
            System.out.print(node + "\t");
            firstRootIterator(node.left);
            firstRootIterator(node.right);
        }
    }

    /**
     * 中序遍历  左子树 ---> 根结点 ---> 右子树
     *
     * 压平的数据
     */
    public static void centerRootIterator(TreeNode node) {
        if (node != null) {
            centerRootIterator(node.left);
            System.out.print(node + "\t");
            centerRootIterator(node.right);
        }
    }

    /**
     * 后序遍历  左子树 ---> 右子树 ---> 根结点
     */
    public static void lastRootIterator(TreeNode node) {
        if (node != null) {
            lastRootIterator(node.left);
            lastRootIterator(node.right);
            System.out.print(node + "\t");
        }
    }

    /**
     * 层次遍历 -- 逐层遍历， 利用队列
     *
     * @param root 根节点 非null
     */
    public static void levelIterator(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            System.out.print(node + "\t");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /**
     * 树P左旋转 -- 旋转前后 中序遍历不变
     *
     * 设 Q 为 P 的右子树。
     * 将 P 的右子树设为 Q 的左子树。
     * [将 Q 的左子树的父节点设为 P]
     * 将 Q 的左子树设为 P。
     * [将 P 的父节点设为 Q]
     * @ref https://zh.wikipedia.org/zh-hans/%E6%A0%91%E6%97%8B%E8%BD%AC
     */
    public static void leftRotation(TreeNode node) {
        TreeNode right = node.right;
        TreeNode parent = node.parent;
        if (right == null) {
            return;
        }
        // 将 P 的右子树设为 Q 的左子树。
        node.right = right.left;
        if (node.right  != null) {
            node.right.parent = node;
        }
        // 将 Q 的左子树设为 P
        right.left = node;
        // 将 P 的父节点设为 Q
        node.parent = right;
        right.parent = parent;
        if (parent != null) {
            // 修改P的父节点
            if (parent.left == node) {
                parent.left = right;
            } else {
                parent.right = right;
            }
        }
    }

    /**
     * 树P右旋转 -- 旋转前后 中序遍历不变
     *
     * 设 P 为 Q 的左子树。
     * 将 Q 的左子树设为 P 的右子树。
     * [将 P 的右子树的父节点设为 Q]
     * 将 P 的右子树设为 Q。
     * [将 Q 的父节点设为 P]
     * @ref https://zh.wikipedia.org/zh-hans/%E6%A0%91%E6%97%8B%E8%BD%AC
     */
    public static void rightRotation(TreeNode node) {
        TreeNode left = node.left;
        TreeNode parent = node.parent;
        if (left == null) {
            return;
        }
        // 将 Q 的左子树设为 P 的右子树。
        node.left = left.right;
        if (node.left != null) {
            node.left.parent = node;
        }
        // 将 P 的右子树设为 Q
        left.right = node;
        // 将 P 的父节点设为 Q
        node.parent = left;
        left.parent = parent;
        if (parent != null) {
            // 修改P的父节点
            if (parent.left == node) {
                parent.left = left;
            } else {
                parent.right = left;
            }
        }
    }
}
