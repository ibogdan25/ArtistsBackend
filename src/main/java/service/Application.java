package service;

import com.fasterxml.classmate.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.hibernate.cfg.Configuration;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "repository")
@ComponentScan(basePackages = { "controller", "repository", "service"} )
@EntityScan(basePackages = "model")
public class Application {
    private static SessionFactory factory;

    public static void main(String[] args) {
        startHibernate();

        SpringApplication.run(Application.class, args);
    }
    public static void startHibernate() {
        try {
            factory = new Configuration().
                    configure().
                    addPackage("model").
                            buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
