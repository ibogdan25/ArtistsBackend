package service;

import model.ArtistSubcategory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import utils.Properties;
import utils.ServerContext;
import xmlparsers.Subcategory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static utils.ConstantsUtils.*;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "repository")
@ComponentScan(basePackages = {"controller", "repository", "service"})
@EntityScan(basePackages = "model")
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = false)
public class Application implements EnvironmentAware {
    private static SessionFactory factory;

    private static Environment env;

    private static ArtistCategoriesServiceImpl artistCategoriesService;

    @Autowired
    public Application(ArtistCategoriesServiceImpl artistCategoriesService) {
        this.artistCategoriesService = artistCategoriesService;
    }

    @Override
    public void setEnvironment(Environment env) {
        Application.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        loadProperties();
        start();
    }

    public static void startHibernate() {
        try {
            factory = new Configuration().
                    configure("hibernate.cfg.xml").
                    addPackage("model").
                    buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    private static void loadProperties() {
        Properties properties = new Properties();
        final String loadCategoriesFromFile = env.getProperty(PROP_LOAD_CATEGORIES_FROM_FILE, EMPTY_STRING);
        if (!EMPTY_STRING.equals(loadCategoriesFromFile)) {
            properties.add(PROP_LOAD_CATEGORIES_FROM_FILE, loadCategoriesFromFile);
        }

        final String resetDatabase = env.getProperty(PROP_RESET_DATABASE, FALSE);
        properties.add(PROP_RESET_DATABASE, resetDatabase);

        final String startHibernate = env.getProperty(PROP_START_HIBERNATE, TRUE);
        properties.add(PROP_START_HIBERNATE, startHibernate);

        final String imgPath = env.getProperty(PROP_PATH_TO_IMAGES, EMPTY_STRING);
        properties.add(PROP_PATH_TO_IMAGES, imgPath);

        final String emailUsername = env.getProperty(PROP_EMAIL_USERNAME, EMPTY_STRING);
        properties.add(PROP_EMAIL_USERNAME, emailUsername);

        final String emailPassword = env.getProperty(PROP_EMAIL_PASSWORD, EMPTY_STRING);
        properties.add(PROP_EMAIL_PASSWORD, emailPassword);

        final String smtpServer = env.getProperty(PROP_EMAIL_SMTP_SERVER, EMPTY_STRING);
        properties.add(PROP_EMAIL_SMTP_SERVER, smtpServer);

        ServerContext.setProperties(properties);
    }

    private static void start() {
        Properties properties = ServerContext.getProperties();
        if (properties.contains(PROP_START_HIBERNATE) && TRUE.equals(properties.getValue(PROP_START_HIBERNATE))) {
            startHibernate();
        }
        final String pathCategories = properties.getValue(PROP_LOAD_CATEGORIES_FROM_FILE);
        if (!EMPTY_STRING.equals(pathCategories)) {
            loadCategories(pathCategories);
        }
    }

    private static void loadCategories(final String pathCategories) {
        try {
            File file = new File(pathCategories);
            JAXBContext jaxbContext = JAXBContext.newInstance(xmlparsers.Categories.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            xmlparsers.Categories categories = (xmlparsers.Categories) jaxbUnmarshaller.unmarshal(file);
            for (xmlparsers.Category category : categories.getCategories()) {
                model.ArtistCategory artistCategory = new model.ArtistCategory(category.getName());
                Set<ArtistSubcategory> artistSubcategories = new HashSet<>();
                for (Subcategory subCategory : category.getSubcategories()){
                    artistSubcategories.add(new ArtistSubcategory(subCategory.getName(),subCategory.getPhotoUrl()));
                }
                artistCategory.setPhotoUrl(category.getPhotoUrl());
                artistCategory.setArtistSubcategorySet(artistSubcategories);
                artistCategoriesService.addCategoryIfNotExists(artistCategory);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}