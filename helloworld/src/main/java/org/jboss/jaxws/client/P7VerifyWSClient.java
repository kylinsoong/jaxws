package org.jboss.jaxws.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class P7VerifyWSClient {

	public static void main(String[] args) throws MalformedURLException {

		final QName serviceName = new QName("http://va.hipki.chttl.com", "P7VerifyWS");
		final URL wsdlURL = new URL("http://localhost:8080/TestWebService/P7VerifyWS?wsdl");
		
		final Service service = Service.create(wsdlURL, serviceName);
		
		System.out.println(service);
	}

}
