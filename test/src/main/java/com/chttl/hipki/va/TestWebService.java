package com.chttl.hipki.va;

import javax.jws.WebMethod;
import javax.jws.WebService;

//@WebService(targetNamespace = "http://localhost:8080/TestWebService", serviceName = "P7VerifyWS")
@WebService(serviceName = "P7VerifyWS")
public class TestWebService {

	@WebMethod
	public String verifyPKCS7Cert() {
		return "MyVerifyPKCS7CertMethod";
	}
}
