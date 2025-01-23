package org.fizz_buzz;


import org.fizz_buzz.config.ComponentScanConfig;
import org.fizz_buzz.config.ServletConfig;
import org.fizz_buzz.controller.TestController;
import org.fizz_buzz.test.TestBean;
import org.fizz_buzz.test.TestPrototypeBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringApplicationRunner{

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
//                TestBean.class,
//                ServletConfig.class
                ComponentScanConfig.class
        ); // ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");

        TestBean bean = (TestBean) applicationContext.getBean("testBean");
        System.out.println("Test bean ID: " + bean.getId());
        var controller = applicationContext.getBean("testController", TestController.class);
        System.out.println("Test bean Controller: " + controller);
        System.out.println("Test bean Controller test bean ID: " + controller.getTestBean().getId());

        var prototypeFirst = applicationContext.getBean("testPrototypeBean", TestPrototypeBean.class);
        var prototypeSecond = applicationContext.getBean("testPrototypeBean", TestPrototypeBean.class);

        System.out.println(prototypeFirst);
        System.out.println(prototypeSecond);

    }
}
