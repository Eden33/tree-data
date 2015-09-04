package com.eden33.tree.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * @author edi
 */
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "generateTreeDataProcedure",
        query = "CALL prc_fill_hierarchy(:level, :fill)"
    )
})
@Entity
@Table(name="t_hierarchy", indexes = {@Index(columnList = "id, parent")})
public class TreeNode {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(columnDefinition = "INT unsigned", nullable = false)
    private int id;
    
    @Column(columnDefinition = "INT unsigned", nullable = false)
    private int parent;

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
    
    
}
