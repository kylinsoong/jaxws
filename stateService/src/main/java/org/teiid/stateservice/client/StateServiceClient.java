package org.teiid.stateservice.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.teiid.stateservice.GetStateInfoFault_Exception;
import org.teiid.stateservice.StateService;
import org.teiid.stateservice.jaxb.StateInfo;

public class StateServiceClient {
	
	

	public static void main(String[] args) throws MalformedURLException, GetStateInfoFault_Exception {
		
		final QName serviceName = new QName("http://www.teiid.org/stateService/", "stateService");
		final URL wsdlURL = new URL("http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL");
		
		final QName stateServiceSOAP = new QName("http://www.teiid.org/stateService/", "StateServiceImplPort");

		final Service service = Service.create(wsdlURL, serviceName);
		StateService client = service.getPort(stateServiceSOAP, StateService.class);
		
		for (StateInfo info : client.getAllStateInfo()) {
			System.out.println(info.getName() + ", " + info.getCapital() + ", " + info.getAbbreviation() + ", " + info.getYearOfStatehood());
		}
		
		System.out.println();
		
		StateInfo info = client.getStateInfo("CA");
		System.out.println(info.getName() + ", " + info.getCapital() + ", " + info.getAbbreviation() + ", " + info.getYearOfStatehood());	
	}

}
