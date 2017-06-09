package com.mycompany.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mycompany.model.Client;
import com.mycompany.model.LogClient;

@Component
public class MyService {
	@PersistenceContext(unitName="persistenceUnit") private EntityManager em;
	
	private static final Logger LOG = LoggerFactory.getLogger(MyService.class);
	
	public void recordLog(@Body Client client, Exchange exchange) {
		LOG.info("Inserting client ["+client.getName()+"]");
		client = em.merge(client);
		LOG.info("Inserted client ["+client.getId()+" - "+client.getName()+"]");
		LOG.info("Inserting LogClient");
		LogClient log = new LogClient();
		log.setClient(client);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String strDate = sdf.format(new Date());
		log.setTxtLog("Persisted Client ["+client.getName()+"] at ["+strDate+"]");
		
//		exchange.getIn().getHeader("CamelEntityManager", EntityManager.class).merge(log);
		em.persist(log);
		LOG.info("Inserted LogClient ["+log.getId()+"]");
	}
}
