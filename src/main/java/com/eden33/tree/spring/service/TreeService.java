package com.eden33.tree.spring.service;

import com.eden33.tree.spring.dao.TreeNodeDAO;
import com.eden33.tree.spring.model.TreeNode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author edi
 */
@Service("treeService")
@Transactional
public class TreeService {
    
    @Autowired
    private TreeNodeDAO treeNodeDAO;
    
    public TreeService() {}

    public TreeNodeDAO getTreeNodeDAO() {
        return treeNodeDAO;
    }

    public void setTreeNodeDAO(TreeNodeDAO treeNodeDAO) {
        this.treeNodeDAO = treeNodeDAO;
    }
    
    /**
     * @param id The node id of root the node.
     * @return The root node at idx 0 and all descendants (idx 1 to n) or null if no such node
     */
    public List<TreeNode> getTreeNodes(int id) {
        return treeNodeDAO.getTreeNodes(id);
    }
    
}
