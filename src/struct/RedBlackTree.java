package struct;

/**
 * 红黑树
 *
 * 1. 根节点为黑色
 * 2. 节点为红色或者黑色
 * 3. 叶子节点为黑色 （null节点为叶子节点）
 * 4. 不能有两个连续的红色节点
 * 5. 从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点。
 *
 * 特性：
 * 4+5要求保证了--从根到叶子的最长的可能路径不多于最短的可能路径的两倍长
 * @reference https://zh.wikipedia.org/zh-hans/%E7%BA%A2%E9%BB%91%E6%A0%91
 */
public class RedBlackTree {

    // 有序树, 左数 < root < 右树, 中序遍历则输出为从小到大排序
    public RedBlackNode root;
    private static final int BLACK = 0;
    private static final int RED = 1;

    /**
     * 中序遍历  左子树 ---> 根结点 ---> 右子树
     *
     * 压平的数据
     */
    public static void centerRootIterator(RedBlackNode node) {
        if (node != null) {
            centerRootIterator(node.left);
            System.out.print(node + "\t");
            centerRootIterator(node.right);
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
    public static void leftRotation(RedBlackNode node) {
        RedBlackNode right = node.right;
        RedBlackNode parent = node.parent;
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
    public static void rightRotation(RedBlackNode node) {
        RedBlackNode left = node.left;
        RedBlackNode parent = node.parent;
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

    /**
     * 新插入节点标记为红色(如果是黑色， 并且父节点为红色则破坏了5的特性)
     * 时间复杂度为logN(基本只是查询插入顺序) -- 和最多两次旋转
     */
    public void insert(int data) {
        RedBlackNode node = new RedBlackNode(data, null);
        node.color = RED;
        // 获取插入位置
        if (root == null) {
            // 差别位置为root
        } else {
            RedBlackNode parentNode = root;
            // 定义插入节点位置
            while (true) {
                if (data < parentNode.data) {
                    if (parentNode.left == null) {
                        parentNode.left = node;
                        node.parent = parentNode;
                        break;
                    } else {
                        parentNode = parentNode.left;
                    }
                } else {
                    if (parentNode.right == null) {
                        parentNode.right = node;
                        node.parent = parentNode;
                        break;
                    } else {
                        parentNode = parentNode.right;
                    }
                }
            }
        }
        insertCase1(node);
    }

    /**
     * 删除节点
     * 时间复杂度为logN(基本只是查询插入顺序) -- 和最多两次旋转
     * 删除节点有两个儿子 -- 取左子树最大值或取右子树最小值交换 -- 即变成了删除交换节点（必定最多只存在一个子节点）
     */
    public void delete(RedBlackNode node) {
        if (node.left != null) {
            // 取左子树最大值或取右子树最小值交换
            RedBlackNode newNode = node.left;
            while (newNode.right != null) {
                newNode = newNode.right;
            }
            // 交换值, 变成删除交换节点
            node.data = newNode.data;
            deleteChildOne(newNode);
            return;
        } else if (node.right != null) {
            // 取左子树最大值或取右子树最小值交换
            RedBlackNode newNode = node.right;
            while (newNode.left != null) {
                newNode = newNode.left;
            }
            // 交换值, 变成删除交换节点
            node.data = newNode.data;
            deleteChildOne(newNode);
            return;
        }
        deleteChildOne(node);
    }

    /**
     * 删除节点最多只有一个儿子
     */
    public void deleteChildOne(RedBlackNode node) {
        RedBlackNode child = node.left != null ? node.left : node.right;
        if (node.color == RED) {
            // 删除一个红色节点, 一定非root, 父亲和儿子一定是黑色的, 将儿子替换到此位置
            replaceNode(node, child);
        } else {
            if (child != null && child.color == RED) {
                // 删除一个黑色节点, 但是其儿子为红色节点, 将儿子替换到此位置, 染为黑色即可
                replaceNode(node, child);
                child.color = BLACK;
            } else {
                // 删除的节点和它的儿子二者都是黑色的时候(儿子为空也是黑色) -- 此路径节点黑色数量少1
                // 替换删除的节点和它的儿子
                deleteCase1(node);
                // 去除删除节点链接索引
                if (node.parent == null) {
                    root = null;
                } else if (node.parent.left == node) {
                    node.parent.left = null;
                } else {
                    node.parent.right = null;
                }
            }
        }
    }

    /**
     * 替换两个节点
     *
     * @param node 原父节点
     * @param child 子节点
     */
    private void replaceNode(RedBlackNode node, RedBlackNode child) {
        if (node.parent != null) {
            if (node.parent.left == node) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }
        if (child != null) {
            child.parent = node.parent;
        }
    }

    /**
     * （前提）节点和它的儿子二者都是黑色的
     *
     * 情况一， 删除节点为root
     */
    private void deleteCase1(RedBlackNode node) {
        if (node.parent == null) {
            return;
        } else {
            deleteCase2(node);
        }
    }

    /**
     * （前提）节点和它的儿子二者都是黑色的 -- 兄弟节点不可能为null, 不然黑色节点数量不同意
     *
     * 情况二， 兄弟节点为红色, 在N的父亲上左旋转， 并替换N的父亲和祖父颜色
     * 操作完成后， N的父亲由黑色变为红色， N的祖父（原兄弟节点）由红色变为黑色, 节点路径上黑色节点未变化
     * 为第四部做准备，次步骤后兄弟节点一定都为黑色
     */
    private void deleteCase2(RedBlackNode node) {
        RedBlackNode brother = getBrother(node);
        assert brother != null;
        if (brother.color == RED) {
            if (node.parent.left == node) {
                leftRotation(node.parent);
                if (root.parent != null) {
                    root = root.parent;
                }
            } else {
                rightRotation(node.parent);
                if (root.parent != null) {
                    root = root.parent;
                }
            }
            node.parent.color = RED;
            node.parent.parent.color = BLACK;
        }
        deleteCase3(node);
    }

    /**
     * 情况三， 兄弟节点和父亲和兄弟节点的所有儿子都为黑色， 将兄弟节点绘制为红色 将父节点视为删除节点重新执行步骤1
     * 操作完成后， 通过N父亲的所有节点的黑色节点数量一致， 比原来减少了1
     */
    private void deleteCase3(RedBlackNode node) {
        RedBlackNode brother = getBrother(node);
        assert brother != null;
        if (node.parent.color == BLACK) {
            if ((brother.left == null || brother.left.color == BLACK) &&
                (brother.right == null || brother.right.color == BLACK)) {
                // 将兄弟节点绘制为红色
                brother.color = RED;
                deleteCase1(node.parent);
                return;
            }
        }
        deleteCase4(node);
    }

    /**
     * 情况四， 兄弟节点和兄弟节点的所有儿子都为黑色， 父亲为红色节点； 交换父亲和兄弟节点颜色
     * 操作完成后， 节点路径上黑色节点+1, 达到平衡
     */
    private void deleteCase4(RedBlackNode node) {
        RedBlackNode brother = getBrother(node);
        assert brother != null;
        if (node.parent.color == RED && (brother.left == null || brother.left.color == BLACK)
            && (brother.right == null || brother.right.color == BLACK)) {
            // 交换父亲和兄弟节点颜色
            brother.color = RED;
            node.parent.color = BLACK;
        } else {
            deleteCase5(node);
        }
    }

    /**
     * 情况五， 兄弟节点为黑色，节点为父亲左儿子， 兄弟节点右儿子为黑色,左儿子为红色
     * 在兄弟节点上做右旋转， 变换兄弟节点和兄弟节点新父亲颜色
     * 操作完成后， 兄弟节点路径上黑色节点不变, 但是节点有了一个黑色的兄弟节点， 并且该兄弟节点的右儿子为红色
     * 为步骤六做准备
     */
    private void deleteCase5(RedBlackNode node) {
        RedBlackNode brother = getBrother(node);
        assert brother != null;
        if (node.parent.left == node) {
            if ((brother.right == null || brother.right.color == BLACK)
                && (brother.left != null || brother.left.color == RED)) {
                rightRotation(brother);
                if (root.parent != null) {
                    root = root.parent;
                }
                brother.color = RED;
                brother.parent.color = BLACK;
            }
        } else {
            if ((brother.left == null || brother.left.color == BLACK)
                && (brother.right != null || brother.right.color == RED)) {
                leftRotation(brother);
                if (root.parent != null) {
                    root = root.parent;
                }
                brother.color = RED;
                brother.parent.color = BLACK;
            }
        }
        deleteCase6(node);
    }

    /**
     * 情况六， 兄弟节点为黑色，节点为父亲左儿子， 兄弟节点右儿子为红色
     * 在父亲节点上做左旋转， 变换父亲节点和兄弟节点的颜色， 兄弟节点右儿子变为黑色
     * 操作完成后， 兄弟节点路径上黑色节点不变, 节点路径上黑色节点数+1， 维持了平衡
     */
    private void deleteCase6(RedBlackNode node) {
        RedBlackNode brother = getBrother(node);
        assert brother != null;
        brother.color = node.parent.color;
        node.parent.color = BLACK;
        if (node.parent.left == node) {
            brother.right.color = BLACK;
            leftRotation(node.parent);
            if (root.parent != null) {
                root = root.parent;
            }
        } else {
            brother.left.color = BLACK;
            rightRotation(node.parent);
            if (root.parent != null) {
                root = root.parent;
            }
        }
    }

    /**
     * 情况一， 插入树为空树
     */
    private void insertCase1(RedBlackNode node) {
        if (node.parent == null) {
            // 第一条数据, 渲染为黑色
            node.color = BLACK;
            root = node;
        } else {
            insertCase2(node);
        }
    }

    /**
     * 情况二， 父节点为黑色
     * 符合红黑树特性, 不需要调整
     */
    private void insertCase2(RedBlackNode node) {
        if (node.parent.color == BLACK) {
            return;
        } else {
            insertCase3(node);
        }
    }

    /**
     * 情况三， 父节点和叔父节点二者都是红色
     *
     * 将父节点和叔父节点修改为黑色, 将祖父节点绘制为红色（维持特性5）
     * 将祖父节点视为新加入节点从头检查
     */
    private void insertCase3(RedBlackNode node) {
        // 走到此处, 父节点为红色
        RedBlackNode uncle = getUncle(node);
        if (uncle != null && uncle.color == RED) {
            // 父节点和叔父节点都为红色 -- 此处因为父级节点为红色, 所以必定存在祖父节点
            node.parent.parent.left.color = BLACK;
            node.parent.parent.right.color = BLACK;
            node.parent.parent.color = RED;
            // 将祖父节点视为新加入节点从头检查
            insertCase1(node.parent.parent);
        } else {
            insertCase4(node);
        }
    }

    /**
     * 情况四， 父节点是红色而叔父节点是黑色或缺少
     * 新节点N是其父节点P的右子节点而父节点P又是其父节点的左子节点
     * 进行一次左旋转调换新节点和其父节点的角色
     *
     * (通过旋转维持平衡， 将父节点视为新插入节点， 继续下一步)
     */
    private void insertCase4(RedBlackNode node) {
        if (node.parent.right == node && node.parent.parent.left == node.parent) {
            // 新节点N是其父节点P的右子节点而父节点P又是其父节点的左子节点
            // 进行一次左旋转调换新节点和其父节点的角色
            leftRotation(node.parent);
            insertCase5(node.left);
        } else if (node.parent.left == node && node.parent.parent.right == node.parent) {
            // 新节点N是其父节点P的左子节点而父节点P又是其父节点的右子节点
            // 进行一次右旋转调换新节点和其父节点的角色
            rightRotation(node.parent);
            insertCase5(node.right);
        } else {
            insertCase5(node);
        }
    }

    /**
     * 情况五， 父节点是红色而叔父节点是黑色或缺少
     * 新节点N是其父节点的左子节点，而父节点P又是其父节点G的左子节点
     * 针对祖父节点G的一次右旋转
     * 切换以前的父节点P和祖父节点G的颜色，结果的树满足性质4。性质5也仍然保持满足
     * (通过旋转维持平衡并符合性质)
     * 旋转可能改变root节点
     */
    private void insertCase5(RedBlackNode node) {
        // 切换以前的父节点P和祖父节点G的颜色
        node.parent.color = BLACK;
        node.parent.parent.color = RED;
        if (node.parent.left == node) {
            rightRotation(node.parent.parent);
        } else {
            // 不可能一个左一个右的情况， 因为情况4会旋转统一变换为5
           leftRotation(node.parent.parent);
        }
        if (root.color == RED) {
            // 旋转改变了root节点
            root = node.parent;
        }
    }

    private RedBlackNode getUncle(RedBlackNode node) {
        if (node.parent == null) {
            return null;
        }
        if (node.parent.parent == null) {
            return null;
        }
        if (node.parent.parent.left == node.parent) {
            return node.parent.parent.right;
        }
        return node.parent.parent.left;
    }

    /**
     * 获取兄弟节点
     */
    private RedBlackNode getBrother(RedBlackNode node) {
        if (node.parent == null) {
            return null;
        }
        if (node.parent.left == node) {
            return node.parent.right;
        }
        return node.parent.left;
    }

    // 实现多态需要写重写get方法， 太懒了不想改
    static class RedBlackNode {

        public RedBlackNode parent;
        public RedBlackNode left;
        public RedBlackNode right;
        public int data;
        // 0表示黑色, 1表示红色
        public int color;

        public RedBlackNode(int data, RedBlackNode parent) {
            this.data = data;
            this.parent = parent;
        }

        @Override
        public String toString() {
            String color = this.color == BLACK ? "黑":"红";
            return color + this.data;
        }
    }

    public static void main(String[] args) {
        int[] arr = {13, 1, 5, 9, 25, 3, 6, 70, 22};
        RedBlackTree redBlackTree = new RedBlackTree();
        for (int data : arr) {
            redBlackTree.insert(data);
            System.out.println("中序遍历");
            centerRootIterator(redBlackTree.root);
            System.out.println();
        }
        System.out.println("删除根节点5");
        redBlackTree.delete(redBlackTree.root);
        centerRootIterator(redBlackTree.root);
        System.out.println();
        System.out.println("删除节点1");
        redBlackTree.delete(redBlackTree.root.left);
        centerRootIterator(redBlackTree.root);
        System.out.println();
        System.out.println("删除节点25");
        redBlackTree.delete(redBlackTree.root.right);
        centerRootIterator(redBlackTree.root);
        System.out.println();
        while (redBlackTree.root != null) {
            System.out.println("删除节点根节点");
            redBlackTree.delete(redBlackTree.root);
            centerRootIterator(redBlackTree.root);
            System.out.println();
        }
    }
}
