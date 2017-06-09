package com.mycompany.routes;


import javax.sql.DataSource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.model.Client;
import com.mycompany.service.MyService;

@Component
public class CamelRoute extends RouteBuilder {

	@Autowired private DataSource dataSource;
	
	@Override
	public void configure() throws Exception {
		
		// TODO Auto-generated method stub
//		from("jpa:com.mycompany.model.Client?persistenceUnit=persistenceUnit&consumeDelete=false&consumer.delay=15000&sharedEntityManager=true&joinTransaction=false").transacted()
		from("file:input?noop=true&delay=60000").transacted()
		.unmarshal(new BindyCsvDataFormat(Client.class))
		.split().simple("${body}")
			.log("Processing Client [${body.name}]")
//			.idempotentConsumer(simple("${body.id}/${body.name}"), new JdbcMessageIdRepository(dataSource, "ClientConsumer"))
//			.process(new Processor() {
//				
//				@Override
//				public void process(Exchange exchange) throws Exception {
//					long time = System.currentTimeMillis();
//					
//					if (time%2==0)
//						throw new RuntimeException("erro");
//					Client c = ((Client)exchange.getIn().getBody()); 
////					c.setName(c.getName() + "[processed]");
//				}
//			})
			.bean(MyService.class, "recordLog")
			.log("${body.name} processed");
	}

}
