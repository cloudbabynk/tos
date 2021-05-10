package net.huadong.tech;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
public class DruidConfig {
	@Bean
	@Primary
	public FilterRegistrationBean getFilterRegistrationBean() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setFilter(new WebStatFilter());
		filter.setName("druidWebStatFilter");
		filter.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		filter.addUrlPatterns("/*");
		return filter;
	}

	@Bean
	@Primary
	public ServletRegistrationBean getServletRegistrationBean() {
		ServletRegistrationBean servlet = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		servlet.setName("druidStatViewServlet");
		servlet.addInitParameter("resetEnable", "false");
		servlet.addInitParameter("loginUsername", "huadong"); // ++监控页面登录用户名
		servlet.addInitParameter("loginPassword", "huadong012"); // ++监控页面登录用户密码
		return servlet;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}
}

/*
 * Location:
 * F:\maven\net\huadong\tech\hd-springboot-idev\8.3.1-SNAPSHOT\hd-springboot-
 * idev-8.3.1-SNAPSHOT.jar Qualified Name: net.huadong.tech.DruidConfig Java
 * Class Version: 8 (52.0) JD-Core Version: 0.7.1
 */