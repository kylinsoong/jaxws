package org.jboss.jaxws.helloworld;

import javax.ejb.Stateless;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@Stateless
@WebService(
	endpointInterface = "org.jboss.jaxws.helloworld.HelloWorld",
	targetNamespace = "http://localhost:8080/jaxws-helloworld", 
	serviceName = "HelloWorldService"
)
public class HelloWorldService implements HelloWorld {
	
	private static final Logger logger = Logger.getLogger(HelloWorldService.class);
	
	
	public String helloworld() {
		
		logger.info("HelloWorld Service be invoked");

		return "HelloWorld, JAX-WS!";
	}
}
