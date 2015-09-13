package com.eden33.tree.spring.model;

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
        query = "SELECT hi.id, parent FROM (SELECT hierarchy_connect_by_parent_eq_prior_id(id) AS id, @level AS level FROM (SELECT  @start_with \\:= ?, @id \\:= @start_with, @level \\:= 0) vars, t_hierarchy WHERE @id IS NOT NULL) ho JOIN t_hierarchy hi ON hi.id = ho.id",
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
     * If this node is no leafe the list has at least one element.
     * Otherwise the list has length zero.
     * @return The list of direct children.
     */
    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
    
    
}
