package webservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

public class SupportCase911284Test {

	public static void main(String[] args) throws MalformedURLException {

		// Use API
		final URL wsdlURL = new URL("http://localhost:8080/WebServiceTest/ServiceBean?wsdl");
		final String namespace = "http://webservice/";
		final QName qname = new QName(namespace,"ServiceBeanService");
		final QName port = new QName(namespace, "ServiceBeanPort");
		final Service service = Service.create(wsdlURL, qname);
		
		ServiceBean test = service.getPort(ServiceBean.class);
		System.out.println(test.sayHello("Hello"));
		
//		Dispatch<Source> disp = service.createDispatch(port, Source.class, Service.Mode.MESSAGE);
//		Source request = new StreamSource("<sayHello/>");
//		Source response = disp.invoke(request);
		
		// Use ServiceBeanService
		ServiceBean serviceBean = new ServiceBeanService().getServiceBeanPort();
		System.out.println(serviceBean.sayHello("Hello Again"));
		
		
	}

}
