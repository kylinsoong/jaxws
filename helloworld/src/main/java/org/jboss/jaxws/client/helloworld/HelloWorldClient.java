package org.jboss.jaxws.client.helloworld;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.jboss.jaxws.helloworld.HelloWorld;

public class HelloWorldClient {
	public static void main(String[] args) throws Exception {

		final QName serviceName = new QName("http://localhost:8080/jaxws-helloworld", "HelloWorldService");
		final URL wsdlURL = new URL("http://localhost:8080/jaxws-helloworld/HelloWorldService/HelloWorldService?wsdl");

		final Service service = Service.create(wsdlURL, serviceName);
		
		// JAX-WS Proxy
		final HelloWorld helloworld = (HelloWorld) service.getPort(HelloWorld.class);
		System.out.println(helloworld.helloworld());
		
//		for(Iterator<QName> iterator = service.getPorts(); iterator.hasNext();) {
//			System.out.println(iterator.next());
//		} 
		
		// JAX-WS Dispatch APIs
		Dispatch<Source> disp = service.createDispatch(new QName("http://localhost:8080/jaxws-helloworld", "HelloWorldServicePort"), Source.class, Service.Mode.PAYLOAD);
				
		Source request = new StreamSource();
		Source response = disp.invoke(request);
		
	}
}
