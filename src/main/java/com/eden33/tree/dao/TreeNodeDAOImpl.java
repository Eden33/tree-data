package com.eden33.tree.dao;

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
     */
    @Override
    @Transactional
    public void generateTreeData(int level, int fill) {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("generateTreeDataProcedure"); 
            query.setInteger("level", level);
            query.setInteger("fill", fill);
            query.executeUpdate();
    } 
}
