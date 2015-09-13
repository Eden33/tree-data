package com.eden33.tree.spring.dao;

import com.eden33.tree.spring.model.TreeNode;
import java.util.List;

/**
 * @author edi
 */
public interface TreeNodeDAO {
    
    /**
     * Generates tree data for this example.
     * 
     * @param level
     * @param fill
     */
    public void generateTreeData(int level, int fill);
    
    /**
     * @param id The node id.
     * @return The node. Null if no such node.
     */
    public TreeNode getTreeNode(int id);
    
    /**
     * @param id The node id of root the node.
     * @return The root node at idx 0 and all descendants (idx 1 to n) or null if no such node.
     */
    public List<TreeNode> getTreeNodes(int id);
}
