package webservice;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class SupportCase911284Test2 {

	public static void main(String[] args) throws Exception {
		
		final URL wsdlURL = new URL("http://localhost:8080/WebServiceTest/ServiceBean?wsdl");
		final String namespace = "http://webservice/";
		final QName qname = new QName(namespace,"ServiceBeanService");

		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdlURL, qname);
		Object[] obj = client.invoke("sayHello", "Hello");
		for(Object o : obj) {
			System.out.println(o);
		}
	}

}
