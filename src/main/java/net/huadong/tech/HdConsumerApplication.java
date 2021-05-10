package net.huadong.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.huadong.tech.util.SpringUtils;
@SpringBootApplication
//@SpringBootApplication(exclude = DruidConfig.class)
//@EnableAutoConfiguration
//@EnableAutoConfiguration(exclude = DruidConfig.class)
@EnableTransactionManagement
//@ComponentScan // (basePackages="net.huadong.tech.privilege")
//@Configuration
@EnableCaching

// @EnableSwagger2
public class HdConsumerApplication {

	public static void main(String[] args) {
		SpringUtils.setApplicationContext(SpringApplication.run(HdConsumerApplication.class, args));
	}
}
