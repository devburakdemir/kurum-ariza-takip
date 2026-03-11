package org.example.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        try {
            // 1. Root Application Context (Services, DAOs)
            AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
            rootContext.register(HibernateConfig.class);
            servletContext.addListener(new ContextLoaderListener(rootContext));

            // 2. Web Application Context (Controllers, View Resolvers)
            AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
            webContext.register(WebAppConfig.class);

            // 3. Create and configure DispatcherServlet
            DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

            // 4. Register and configure DispatcherServlet
            ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                    "dispatcher",
                    dispatcherServlet
            );

            if (dispatcher == null) {
                throw new IllegalStateException("DispatcherServlet kaydı başarısız oldu. "
                        + "Olası nedenler:\n"
                        + "1. Servlet API versiyon uyumsuzluğu\n"
                        + "2. Tekrarlanan servlet kaydı\n"
                        + "3. Geçersiz ServletContext implementasyonu");
            }

            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");
            dispatcher.setAsyncSupported(true);

        } catch (Exception e) {
            servletContext.log("DispatcherServlet başlatma hatası", e);
            throw new ServletException("DispatcherServlet başlatılamadı", e);
        }
    }
}