package com.eden33.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Populate `t_hierarchy` table with tree data on each web application startup.
 * 
 * @author edi
 */
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("Initialize web application, generate tree data - START");
        
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            Query query = session.getNamedQuery("generateTreeDataProcedure"); 
            query.setInteger("level", 6);
            query.setInteger("fill", 5);
            query.executeUpdate();

            session.getTransaction().commit();
            session.close();
            sessionFactory.close();      
        } catch(Exception e) {
            logger.error("Expetion caught: ", e);
        }
        
        logger.debug("Initialize web application, generate tree data - END");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
