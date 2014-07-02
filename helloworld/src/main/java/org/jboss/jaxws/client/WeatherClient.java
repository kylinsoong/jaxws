package org.jboss.jaxws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

public class WeatherClient {

	public static void main(String[] args) throws MalformedURLException {

		final QName serviceName = new QName("http://ws.cdyne.com/WeatherWS/", "Weather");
		final URL wsdlURL = new URL("http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL");

		final Service service = Service.create(wsdlURL, serviceName);
		
		for(Iterator<QName> iterator = service.getPorts(); iterator.hasNext();) {
			System.out.println(iterator.next());
		} 
		
		Dispatch<Source> disp = service.createDispatch(new QName("http://ws.cdyne.com/WeatherWS/", "WeatherSoap"), Source.class, Service.Mode.PAYLOAD);
		
		System.out.println(disp);
	}

}
