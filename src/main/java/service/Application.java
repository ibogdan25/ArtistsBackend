package service;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "controller"} )
public class Application {
    private static SessionFactory factory;

    public static void main(String[] args) {
        startHibernate();
        SpringApplication.run(Application.class, args);
    }
    public static void startHibernate() {
        try {
            factory = new AnnotationConfiguration().
                    configure().
                    addPackage("model").
                            buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
