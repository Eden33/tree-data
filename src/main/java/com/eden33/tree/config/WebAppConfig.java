package com.eden33.tree.config;

import com.eden33.tree.dao.TreeNodeDAO;
import com.eden33.tree.dao.TreeNodeDAOImpl;
import com.eden33.tree.model.TreeNode;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class is used to configure spring framework AnnotationConfigWebApplicationContext
 * and therefore DispatcherServlet programmatically.
 * @see WebAppInitializer
 * 
 * @author edi
 */
@Configuration
@ComponentScan("com.eden33.tree")
@EnableTransactionManagement
public class WebAppConfig {
        
    private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);
    
    @Bean( name = "data-source" )
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/tree_test");
        dataSource.setUsername("tree_test");
        dataSource.setPassword("tree_test");
        
        //max one connection in the pool for testing
        dataSource.setMaxTotal(1);
        dataSource.setMaxIdle(1);
        
        return dataSource;
    }
        
    @Autowired
    @Bean(name = "sessionFactory" )
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(getHibernateProperties());
        sessionBuilder.addAnnotatedClass(TreeNode.class);
        return sessionBuilder.buildSessionFactory();
    }
    
    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }
    
    @Autowired
    @Bean(name = "treeNodeDAO")
    public TreeNodeDAO getTreeNodeDAO(SessionFactory sessionFactory) {
        logger.debug("get treeNodeDAO");
        return new TreeNodeDAOImpl(sessionFactory);
    }
    
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
    	properties.put("hibernate.show_sql", "false");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        
        //create the schema and destroy previous data
        properties.put("hibernate.hbm2ddl.auto", "create");
        
        return properties;
    }
}
