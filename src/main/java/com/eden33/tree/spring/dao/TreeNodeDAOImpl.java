package com.eden33.tree.spring.dao;

import com.eden33.tree.spring.model.TreeNode;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author edi
 */
@Repository
public class TreeNodeDAOImpl implements TreeNodeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public TreeNodeDAOImpl() {}
    
    public TreeNodeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @param level
     * @param fill 
     */
    @Override
    @Transactional
    public void generateTreeData(int level, int fill) {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("generateTreeDataProcedure"); 
            query.setInteger("level", level);
            query.setInteger("fill", fill);
            query.executeUpdate();
    }

    /**
     * {@inheritDoc}
     * 
     * @param id
     * @return 
     */
    @Override
    public TreeNode getTreeNode(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc}
     * 
     * @param id
     * @return 
     */
    @Override
    public List<TreeNode> getTreeNodes(int id) {       
        Query query = sessionFactory.getCurrentSession().getNamedQuery("selectTreeNodes");
        query.setParameter(0, (id - 1));
        final List<TreeNode> list = query.list();
        return list;
    }
    
    
}
