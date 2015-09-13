package com.eden33.tree.jsf.controller;

import com.eden33.tree.jsf.util.TreeNodeUtil;
import com.eden33.tree.spring.model.TreeBuilder;
import com.eden33.tree.spring.service.TreeService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author edi
 */
@ManagedBean
@ViewScoped
public class TreeController {
    
    private static final Logger logger = LoggerFactory.getLogger(TreeController.class);
    
    private TreeNode root;
    
    @ManagedProperty(value="#{treeService}")
    private TreeService treeService;

    public TreeService getTreeService() {
        return treeService;
    }

    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }
    
    @PostConstruct
    public void init() {
        
// Primefaces example --------- START    
//        root = new DefaultTreeNode("Root", null);
//        TreeNode node0 = new DefaultTreeNode("Node 0", root);
//        TreeNode node1 = new DefaultTreeNode("Node 1", root);
//         
//        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
//        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);
//         
//        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);
//         
//        node1.getChildren().add(new DefaultTreeNode("Node 1.1"));
//        node00.getChildren().add(new DefaultTreeNode("Node 0.0.0"));
//        node00.getChildren().add(new DefaultTreeNode("Node 0.0.1"));
//        node01.getChildren().add(new DefaultTreeNode("Node 0.1.0"));
//        node10.getChildren().add(new DefaultTreeNode("Node 1.0.0"));
//        root.getChildren().add(new DefaultTreeNode("Node 2"));
// Primefaces example --------- END   

// my first try ------ START
//        List<com.eden33.tree.spring.model.TreeNode> nodes = new ArrayList<>();
//        nodes.add(new com.eden33.tree.spring.model.TreeNode(1, 0));
//        nodes.add(new com.eden33.tree.spring.model.TreeNode(2, 1));
//        nodes.add(new com.eden33.tree.spring.model.TreeNode(6, 2));
//        nodes.add(new com.eden33.tree.spring.model.TreeNode(37, 6));
//        nodes.add(new com.eden33.tree.spring.model.TreeNode(53, 6));
//        nodes.add(new com.eden33.tree.spring.model.TreeNode(3, 1));
// my first try ------ END     
        
        List<com.eden33.tree.spring.model.TreeNode> nodes = treeService.getTreeNodes(1);  
        
        if(null != nodes) {
            TreeBuilder treeBuilder = new TreeBuilder();
            treeBuilder.registerTreeNodes(nodes);
            treeBuilder.setTreeNodeRootId(nodes.get(0).getId());
            com.eden33.tree.spring.model.TreeNode modelNode = treeBuilder.build();
            root = TreeNodeUtil.prepareTreeNodesForClientUI(modelNode);            
        }
    }
    
    public TreeNode getRoot() {
        return root;
    }
}
