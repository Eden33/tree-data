package com.eden33.tree.spring.model;

import com.eden33.tree.spring.dao.TreeNodeDAO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author edi
 */
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "generateTreeDataProcedure",
        query = "CALL prc_fill_hierarchy(:level, :fill)"
    ),
    @NamedNativeQuery(
        name = "selectTreeNodes", 
        query =   "SELECT hi.id, parent, ho.level "
                + "FROM "
                + "("
                + "     SELECT hierarchy_connect_by_parent_eq_prior_id(id) AS id, @level AS level "
                + "     FROM "
                + "     ("
                + "         SELECT  @start_with \\:= ?, @id \\:= @start_with, @level \\:= 0"
                + "     ) "
                + "vars, t_hierarchy WHERE @id IS NOT NULL) ho JOIN t_hierarchy hi ON hi.id = ho.id",
        resultClass = TreeNode.class 
    )
})
@Entity
@Table(name="t_hierarchy", indexes = {@Index(columnList = "id, parent")})
public class TreeNode {
    
    public TreeNode() {}
    
    public TreeNode(int id, int parent) {
        this.id = id;
        this.parent = parent;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(columnDefinition = "INT unsigned", nullable = false)
    private int id;
    
    @Column(columnDefinition = "INT unsigned", nullable = false)
    private int parent;
    
    private int level;
    
    @Transient
    private List<TreeNode> children = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * Use {@link TreeBuilder#registerTreeNodes(java.util.List)} 
     * after you have queried a list of nodes. E.g. with {@link TreeNodeDAO#getTreeNodes(int)}
     * After execution of {@link TreeBuilder#registerTreeNodes(java.util.List)}
     * you can build up the tree node child structure with {@link TreeBuilder#build()}
     * 
     * If this TreeNode is a leafe children length will be zero.
     * 
     * @return The list of direct children.
     */
    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

     /**
     * Level 1 means root node.
     * Level 2 means direct child of root node, and so on.
     * 
     * @return The level
     */
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
