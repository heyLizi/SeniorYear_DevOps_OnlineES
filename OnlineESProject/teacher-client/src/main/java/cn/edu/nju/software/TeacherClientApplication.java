package cn.edu.nju.software;

import com.alibaba.druid.pool.DruidDataSource;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "cn.edu.nju.software")
@ServletComponentScan
public class TeacherClientApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TeacherClientApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TeacherClientApplication.class);
	}

	@Bean(name = "beetlSqlScannerConfigurer")
	public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
		BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
		conf.setBasePackage("cn.edu.nju.software");
		conf.setDaoSuffix("Dao");
		conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
		return conf;
	}

	@Bean(name = "sqlManagerFactoryBean")
	@Primary
	public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("datasource") DataSource datasource) {
		SqlManagerFactoryBean factory = new SqlManagerFactoryBean();

		BeetlSqlDataSource source = new BeetlSqlDataSource();
		source.setMasterSource(datasource);
		factory.setCs(source);
		factory.setDbStyle(new MySqlStyle());
		factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
		factory.setNc(new UnderlinedNameConversion());
		factory.setSqlLoader(new ClasspathLoader("/sql/beetlsql"));

		return factory;
	}


	@Bean(name="datasource")
	@ConfigurationProperties("spring.datasource")
	public DataSource getDataSource() {
		System.out.println("-------------------- primaryDataSource init ---------------------");
		return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}

	@Bean(name="txManager")
	public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("datasource") DataSource datasource) {
		DataSourceTransactionManager dsm = new DataSourceTransactionManager();
		dsm.setDataSource(datasource);
		return dsm;
	}

	@Bean
	public Converter<String, Date> addNewConvert() {
		return new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = null;
				try {
					date = sdf.parse((String) source);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return date;
			}
		};
	}
}
