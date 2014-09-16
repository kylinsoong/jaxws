package org.teiid.stateservice.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.teiid.stateservice.GetStateInfoFault_Exception;
import org.teiid.stateservice.StateService;
import org.teiid.stateservice.jaxb.GetStateInfo;
import org.teiid.stateservice.jaxb.ObjectFactory;
import org.teiid.stateservice.jaxb.StateInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StateServiceClient {	

	public static void main(String[] args) throws GetStateInfoFault_Exception, JAXBException, SOAPException, IOException, TransformerException, ParserConfigurationException, XMLStreamException, FactoryConfigurationError {
		
		final QName serviceName = new QName("http://www.teiid.org/stateService/", "stateService");
		final URL wsdlURL = new URL("http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL");
		
		final QName portName = new QName("http://www.teiid.org/stateService/", "StateServiceImplPort");

//		proxy_client(serviceName, wsdlURL, portName);
//		
//		dispatch_client_jaxb(serviceName, wsdlURL, portName);
//		
//		dispatch_client_soap(serviceName, wsdlURL, portName);
//		
//		dispatch_client_DOM(serviceName, wsdlURL, portName);
		
		dispatch_client_stax(serviceName, wsdlURL, portName);
		
	}

	protected static void dispatch_client_stax(QName serviceName, URL wsdlURL, QName portName) throws XMLStreamException, FactoryConfigurationError {
		
		Bus bus = BusFactory.getThreadDefaultBus();
		BusFactory.setThreadDefaultBus(null);
		Service service;
		try {
			service = Service.create(serviceName);
		} finally {
			BusFactory.setThreadDefaultBus(bus);
		}
		
		String bindingId = "http://schemas.xmlsoap.org/wsdl/soap/http";
		String endpointAddress = "http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL";
		service.addPort(portName, bindingId, endpointAddress);
		Dispatch<StAXSource> dispatch = service.createDispatch(portName, StAXSource.class, Mode.PAYLOAD);
		
		String xmlRequest = "<GetAllStateInfo xmlns=\"http://www.teiid.org/stateService/\"/>";
		XMLInputFactory factory = XMLInputFactory.newInstance();
		StAXSource source = new StAXSource(factory.createXMLStreamReader(new ByteArrayInputStream(xmlRequest.getBytes())));
		StAXSource returnValue = dispatch.invoke(source);
		
		printResult(returnValue.getXMLStreamReader());
	}
	
	private static void printResult(XMLStreamReader reader) throws XMLStreamException {
		while (true) {
			if (reader.getEventType() == XMLStreamConstants.END_DOCUMENT) {
				break;
			}
			if(reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
				String cursor = reader.getLocalName();
				if(cursor.equals("Name")){
					reader.next();
					String value = reader.getText();
					System.out.print(value + " ");
				}
				if(cursor.equals("Abbreviation")){
					reader.next();
					String value = reader.getText();
					System.out.print(value + " ");
				}
				if(cursor.equals("Capital")){
					reader.next();
					String value = reader.getText();
					System.out.print(value + " ");
				}
				if(cursor.equals("YearOfStatehood")){
					reader.next();
					String value = reader.getText();
					System.out.println(value + " ");
				}
			}
			
			reader.next();
		}
	}

	protected static void dispatch_client_DOM(QName serviceName, URL wsdlURL, QName portName) throws ParserConfigurationException, TransformerException {

		final Service service = Service.create(wsdlURL, serviceName);
		
		Dispatch<Source> dispatch = service.createDispatch(portName, Source.class, Mode.PAYLOAD);
		
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document requestDoc = db.newDocument();
		Element rootElement = requestDoc.createElementNS("http://www.teiid.org/stateService/", "GetAllStateInfo");
		requestDoc.appendChild(rootElement);
		DOMSource request = new DOMSource(requestDoc);
		System.out.println(getSourceAsXml(request));
		Source response = dispatch.invoke(request);
		System.out.println(getSourceAsXml(response));
		
		requestDoc = db.newDocument();
		rootElement = requestDoc.createElementNS("http://www.teiid.org/stateService/", "GetStateInfo");
		requestDoc.appendChild(rootElement);
		Element stateCode = requestDoc.createElement("stateCode"); 
		stateCode.appendChild(requestDoc.createTextNode("CA"));
		rootElement.appendChild(stateCode);
		request = new DOMSource(requestDoc);
		response = dispatch.invoke(request);
		System.out.println(getSourceAsXml(response));
	}

	protected static String getSourceAsXml(Source response) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    transformer.transform(response, result);
		return writer.toString();
	}

	protected static void dispatch_client_soap(QName serviceName, URL wsdlURL, QName portName) throws SOAPException, IOException, TransformerException {
		
		final Service service = Service.create(wsdlURL, serviceName);
		
		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Mode.MESSAGE);
		
		String getAll = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.teiid.org/stateService/\"><soapenv:Header/><soapenv:Body><stat:GetAllStateInfo></stat:GetAllStateInfo></soapenv:Body></soapenv:Envelope>";
		SOAPMessage response = dispatch.invoke(getRequest(getAll));
		Document doc = response.getSOAPBody().extractContentAsDocument();
		printxmldoc(doc);
		
		String getOne = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:stat=\"http://www.teiid.org/stateService/\"><soapenv:Header/><soapenv:Body><stat:GetStateInfo><stateCode>CA</stateCode></stat:GetStateInfo></soapenv:Body></soapenv:Envelope>" ;
		response = dispatch.invoke(getRequest(getOne));
		doc = response.getSOAPBody().extractContentAsDocument();
		printxmldoc(doc);
	}

	private static void printxmldoc(Document doc) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		System.out.println(writer.toString());
	}

	private static SOAPMessage getRequest(String xml) throws SOAPException, IOException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage(
						new MimeHeaders(),
						new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
		return message;
	}



	protected static void dispatch_client_jaxb(QName serviceName, URL wsdlURL, QName portName) throws JAXBException {

		final Service service = Service.create(wsdlURL, serviceName);
		
		JAXBContext ctx = JAXBContext.newInstance("org.teiid.stateservice.jaxb", StateServiceClient.class.getClassLoader());
		
		Dispatch<Object> dispatch = service.createDispatch(portName, ctx, Mode.PAYLOAD);
		
		Object response = dispatch.invoke(new ObjectFactory().createGetAllStateInfo());
		
		Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        StringWriter writer = new StringWriter();
        marshaller.marshal(response, writer);
        
		System.out.println(writer.toString());
		
		GetStateInfo info = new ObjectFactory().createGetStateInfo();
		info.setStateCode("CA");
		response = dispatch.invoke(info);
		
		writer = new StringWriter();
        marshaller.marshal(response, writer);
        
        System.out.println(writer.toString());
	}

	protected static void proxy_client(QName serviceName, URL wsdlURL, QName portName) throws GetStateInfoFault_Exception {
		
		final Service service = Service.create(wsdlURL, serviceName);
		StateService client = service.getPort(portName, StateService.class);
		
		for (StateInfo info : client.getAllStateInfo()) {
			System.out.println(info.getName() + ", " + info.getCapital() + ", " + info.getAbbreviation() + ", " + info.getYearOfStatehood());
		}
		
		System.out.println();
		
		StateInfo info = client.getStateInfo("CA");
		System.out.println(info.getName() + ", " + info.getCapital() + ", " + info.getAbbreviation() + ", " + info.getYearOfStatehood());
	}

}
