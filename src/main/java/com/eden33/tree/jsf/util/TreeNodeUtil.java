package com.eden33.tree.jsf.util;

import com.eden33.tree.spring.model.TreeNode;
import java.util.List;

/**
 * Use this utility class to prepare {@link com.eden33.tree.spring.model.TreeNode}
 * to be displayed in client UI.
 * 
 * @author edi
 */
public class TreeNodeUtil {
    
    /**
     * Wraps {@link com.eden33.tree.spring.model.TreeNode} into JSF TreeNodes to be displayed in Client UI.
     * 
     * @param rootNode
     * @return 
     */
    public static org.primefaces.model.TreeNode prepareTreeNodesForClientUI(TreeNode rootNode) {
        org.primefaces.model.TreeNode hiddenRoot = new org.primefaces.model.DefaultTreeNode("Root", null);
        org.primefaces.model.TreeNode visibleRoot = new org.primefaces.model.DefaultTreeNode(rootNode.getId());
        hiddenRoot.getChildren().add(visibleRoot);
        prepareChildren(visibleRoot, rootNode.getChildren());
        return hiddenRoot;
    }
    
    private static void prepareChildren(org.primefaces.model.TreeNode node, List<TreeNode> children) {
        for (int i = 0; i < children.size(); i++) {
            TreeNode childNode = children.get(i);
            org.primefaces.model.TreeNode childNodeUI = new org.primefaces.model.DefaultTreeNode(childNode.getId());
            node.getChildren().add(childNodeUI);
            if(childNode.getChildren().size() > 0) {
                prepareChildren(childNodeUI, childNode.getChildren());
            }
        }
    }    
}
