# What's this

This for solving a axis2 client invoke webservice on EAP 6, throw the following error issue:

~~~
10:17:38,730 WARNING [org.apache.cxf.phase.PhaseInterceptorChain] (http-/127.0.0.1:8080-1) Interceptor for {http://va.hipki.chttl.com/}P7VerifyWS#{http://va.hipki.chttl.com/}verifyPKCS7Cert has thrown exception, unwinding now: org.apache.cxf.interceptor.Fault: The given SOAPAction http://va.hipki.chttl.com/TestWebService/verifyPKCS7Cert does not match an operation.
	at org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor$SoapActionInAttemptTwoInterceptor.handleMessage(SoapActionInInterceptor.java:188)
	at org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor$SoapActionInAttemptTwoInterceptor.handleMessage(SoapActionInInterceptor.java:162)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:272)
	at org.apache.cxf.transport.ChainInitiationObserver.onMessage(ChainInitiationObserver.java:121)
	at org.apache.cxf.transport.http.AbstractHTTPDestination.invoke(AbstractHTTPDestination.java:239)
	at org.jboss.wsf.stack.cxf.RequestHandlerImpl.handleHttpRequest(RequestHandlerImpl.java:92)
	at org.jboss.wsf.stack.cxf.transport.ServletHelper.callRequestHandler(ServletHelper.java:143)
	at org.jboss.wsf.stack.cxf.CXFServletExt.invoke(CXFServletExt.java:87)
	at org.apache.cxf.transport.servlet.AbstractHTTPServlet.handleRequest(AbstractHTTPServlet.java:286)

~~~

