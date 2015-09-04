package com.eden33.tree.config;

import com.eden33.tree.dao.TreeNodeDAO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Configure Spring on web application startup 
 * and generate fresh tree data in database.
 * 
 * @author edi
 */
public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);
    
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        logger.debug("onStartup ------------------------------ START");

        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebAppConfig.class);
        
        ServletRegistration.Dynamic dispatcher = sc.addServlet(
                "SpringDispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1); 
        
        appContext.refresh();
        
        TreeNodeDAO treeNodeDAO = (TreeNodeDAO) appContext.getBean("treeNodeDAO");
        if(null == treeNodeDAO) {
            throw new RuntimeException("Failed to get managed bean TreeNodeDAO!");
        }
        
        //generate fresh tree data on each startup
        treeNodeDAO.generateTreeData(3, 4);
        
        logger.debug("onStartup ------------------------------ END");
    }
    
}
