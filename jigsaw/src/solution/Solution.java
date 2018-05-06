package solution;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;


/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {


    private List<JigsawNode> solutionPath;  // 解路径：用以保存从起始状态到达目标状态的移动路径中的每一个状态节点
    private int searchedNodesNum;           // 已访问节点数：用以记录所有访问过的节点的数量
    /**
     * 拼图构造函数
     */
    public Solution() {
    }

    /**
     * 拼图构造函数
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
    }

    /**
     *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
     *
     * 算法思路：
     * 将起始节点放入一个open列表中。
     * 如果open列表为空，则搜索失败，问题无解；否则重复以下步骤：
     * a. 访问open列表中的第一个节点v，若v为目标节点，则搜索成功，退出。
     * b. 从open列表中删除节点v，放入close列表中。
     * c. 将所有与v邻接且未曾被访问的节点放入open列表中。
     * 
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     * @return 搜索成功时为true,失败为false
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
        Queue<JigsawNode> open = new LinkedList<JigsawNode>();
        Queue<JigsawNode> close = new LinkedList<JigsawNode>();

        this.beginJNode = new JigsawNode(bNode);
        this.endJNode = new JigsawNode(eNode);
        this.currentJNode = null;
        // 访问节点数大于29000个则认为搜索失败
        final int MAX_NODE_NUM = 29000;
        final int DIRS = 4;
        // 重置求解标记
        searchedNodesNum = 0;
        solutionPath = null;
        // (1)将起始节点放入open中
        open.offer(this.beginJNode);
        // (2) 如果open为空，或者访问节点数大于MAX_NODE_NUM个，则搜索失败，问题无解;否则循环直到求解成功
        while (searchedNodesNum < MAX_NODE_NUM && !open.isEmpty()) {
            searchedNodesNum++;
            // (2-1)取出open的第一个节点N，置为当前节点currentJNode
            //      若currentJNode为目标节点，则搜索成功，计算解路径，退出
            this.currentJNode = open.poll();
            if (this.currentJNode.equals(eNode)) {
                this.getPath();
                break;
            }
            // 放入close队列
            close.offer(currentJNode);
            // 初始化四个方向的位置，后面利用move函数来获得
            JigsawNode[] nextNodes = new JigsawNode[]{
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode),
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode)
            };
            // 加入close中，表示已发现
            // 探索所有与当前节点且未曾被访问的节点
            for (int i = 0; i < DIRS; i++) {
                if (nextNodes[i].move(i) && !close.contains(nextNodes[i])) {
                    open.offer(nextNodes[i]);
                }
            }
        }
        /*System.out.println("Jigsaw BFS Search Result:");
        System.out.println("Begin state:" + this.getBeginJNode().toString());
        System.out.println("End state:" + this.getEndJNode().toString());
        System.out.println("Solution Path: ");
        System.out.println(this.getSolutionPath());
        System.out.println("Total number of searched nodes:" + searchedNodesNum);
        System.out.println("Depth of the current node is:" + this.getCurrentJNode().getNodeDepth());*/
        return this.isCompleted();
    }
    /**
     *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
     * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * @param jNode - 要计算代价估计值的节点
     */
    public void estimateValue(JigsawNode jNode) {
        //估值方法一：确定后续节点不正确的数码个数
        int s = 0; 
        int dimension = JigsawNode.getDimension();
        for (int index = 1; index < dimension * dimension; index++) {
            if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
                s++;
            }
        }
        //估值方法二：所有放错位的数码与其正确位置的下标之和
        int distance_index = 0;
        //估值方法四：所有放错位的数码与其正确位置的几何距离之和
        int distance_geo = 0;
        //估值方法三：后续节点不正确的数码个数
        int wrong = 0;
        for (int index = 1; index < dimension * dimension; index++) {
            //找到位置不同的点，且不能为空格
            if (jNode.getNodesState()[index] != index && jNode.getNodesState()[index] != 0) {
                wrong++;
                int x1 = (index - 1)/dimension;
                int x2 = (jNode.getNodesState()[index]-1)/dimension;
                int y1 = (index - 1)%dimension;
                int y2 = (jNode.getNodesState()[index]-1)%dimension;
                int x = Math.abs(x1 - x2);
                int y = Math.abs(y1 - y2);
                distance_index += Math.abs(x + y);
                distance_geo += Math.sqrt(x*x + y*y);
            }
        }
        jNode.setEstimatedValue(s*7 + wrong*3 + distance_index*7 + distance_geo*14);
    }
   }
