package com.mycompany.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.idempotent.jpa.JpaMessageIdRepository;

public class CamelRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("jpa:com.mycompany.model.Client?persistenceUnit=persistenceUnit&consumeDelete=false")
		.split().simple("${body}")
			.idempotentConsumer(simple("${body.id}/${body.name}"), JpaMessageIdRepository.jpaMessageIdRepository("persistenceUnit", "ClientRepository"))
			.log("${body.name} processed");
	}

}
