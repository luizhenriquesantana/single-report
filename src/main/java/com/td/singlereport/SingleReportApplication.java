package com.td.singlereport;

import com.td.singlereport.config.Environment;
import com.td.singlereport.dispatcher.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class SingleReportApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(SingleReportApplication.class);

	private static ConfigurableApplicationContext applicationContext;
	private static SpringApplication application;

	@Autowired
	private Dispatcher dispatcher;

	public static void main(String[] args) {
//		SpringApplication.run(SingleReportApplication.class, args);

//		application = new SpringApplication(SingleReportApplication.class);
//		applicationContext = application.run(args);


		System.exit(SpringApplication.exit(SpringApplication.run(SingleReportApplication.class, args)));

		Environment.context().setApplication(application);
		Environment.context().setApplicationContext(applicationContext);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("{}", "Starting MoGeneratorApplication");
		dispatcher.run();
		LOG.info("{}", "Stopping MoGeneratorApplication");


	}

}
