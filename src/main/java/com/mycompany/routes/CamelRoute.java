package com.mycompany.routes;


import javax.sql.DataSource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

	@Autowired private DataSource dataSource;
	
	@Override
	public void configure() throws Exception {
		
		// TODO Auto-generated method stub
		from("jpa:com.mycompany.model.Client?persistenceUnit=persistenceUnit&consumeDelete=false&consumer.delay=10000")
		.split().simple("${body}")
			.idempotentConsumer(simple("${body.id}/${body.name}"), new JdbcMessageIdRepository(dataSource, "ClientConsumer"))
			.log("${body.name} processed");
	}

}
