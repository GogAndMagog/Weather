package org.fizz_buzz;


import org.fizz_buzz.config.DataLayerConfig;
import org.fizz_buzz.config.LiquibaseConfig;
import org.fizz_buzz.config.RootConfig;
import org.fizz_buzz.config.ServiceConfig;
import org.fizz_buzz.model.User;
import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.test.TestBean;
import org.fizz_buzz.test.TestPrototypeBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpringApplicationRunner {

    private static final String testUser = "Ivan";
    private static final String testPassword = "pass";

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
//                TestBean.class,
//                ServletConfig.class
//                ComponentScanConfig.class
                DataLayerConfig.class,
                RootConfig.class,
                LiquibaseConfig.class,
                ServiceConfig.class
        ); // ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");

        TestBean bean = (TestBean) applicationContext.getBean("testBean");
        System.out.println("Test bean ID: " + bean.getId());
//        var controller = applicationContext.getBean("testController", TestController.class);
//        System.out.println("Test bean Controller: " + controller);
//        System.out.println("Test bean Controller test bean ID: " + controller.getTestBean().getId());

        var prototypeFirst = applicationContext.getBean("testPrototypeBean", TestPrototypeBean.class);
        var prototypeSecond = applicationContext.getBean("testPrototypeBean", TestPrototypeBean.class);

        System.out.println(prototypeFirst);
        System.out.println(prototypeSecond);

        var datasource = applicationContext.getBean(DataSource.class);

        String query = """
                SELECT * FROM "Main"."Users";
                """;

        try (Connection connection = datasource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            var rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + ", " + rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
//        userRepository.deleteAll();
        var user = new User();
//        user.setLogin("JPA Test Login2");
//        user.setPassword("JPA Test Password");
//        userRepository.save(user);
//        var users = userRepository.findAll();
//        System.out.println(users);


        LocalContainerEntityManagerFactoryBean fm = applicationContext.getBean(LocalContainerEntityManagerFactoryBean.class);
        System.out.println(fm.getJpaDialect().toString());
    }
}
