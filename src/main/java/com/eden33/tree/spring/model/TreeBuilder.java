package com.eden33.tree.spring.model;

import com.eden33.tree.spring.model.TreeNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to assemble tree data.
 * 
 * @author edi
 */
public class TreeBuilder {
    
    private static final Logger logger = LoggerFactory.getLogger(TreeBuilder.class);
    
    private HashMap<Integer, TreeNode> map = new HashMap<>();
    private int rootId = 1;
    
    public void registerTreeNode(TreeNode node) {
        if(null != node) {
            map.put(node.getId(), node);
        }
    }
    
    /**
     * Register tree nodes for next {@link #build() build}.
     * 
     * @param nodes 
     */
    public void registerTreeNodes(List<TreeNode> nodes) {
        if(null != nodes) {
            for (int i = 0; i < nodes.size(); i++) {
                TreeNode node = nodes.get(i);
                map.put(node.getId(), node);
            }    
        }
    }
    
    /**
     * Set the root node for next {@link #build() build}.
     * 
     * @param id 
     */
    public void setTreeNodeRootId(int id) {
        rootId = id;
    }
    
    /**
     * Build the tree with the data provided.
     * 
     * @see #registerTreeNodes(java.util.List) 
     * @see #registerTreeNode(com.eden33.tree.spring.model.TreeNode)
     * @return The root node or null if nothing to build.
     */
    public TreeNode build() {
        TreeNode value = null;
        int parentId = 0;
        for (Map.Entry<Integer, TreeNode> entrySet : map.entrySet()) {
            value = entrySet.getValue();
            parentId = value.getParent();
            if(0 != parentId) {
                TreeNode parent = map.get(parentId);
                parent.getChildren().add(value);
            }
        }
        TreeNode root = map.get(rootId);
        logger.debug("Tree built. Root has {} childrens.", root.getChildren().size());
        return root;
    }
}
