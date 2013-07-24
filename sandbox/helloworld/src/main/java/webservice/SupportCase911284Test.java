package webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class SupportCase911284Test {

	public static void main(String[] args) throws MalformedURLException {

		// Use API
		final URL wsdlURL = new URL("http://localhost:8080/WebServiceTest/ServiceBean?wsdl");
		final QName qname = new QName("http://webservice/","ServiceBeanService");
		final Service service = Service.create(wsdlURL, qname);
		ServiceBean test = service.getPort(ServiceBean.class);
		System.out.println(test.sayHello("Hello"));
		
		// Use ServiceBeanService
		ServiceBean serviceBean = new ServiceBeanService().getServiceBeanPort();
		System.out.println(serviceBean.sayHello("Hello Again"));
	}

}
