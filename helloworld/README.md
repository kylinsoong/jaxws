# Run Hello World Example

* Start EAP 6

~~~
./standalone.sh
~~~

* Maven build generate `jaxws-helloworld.jar`

~~~
mvn clean install
~~~

* Deploy `jaxws-helloworld.jar` to EAP 6, make sure it deployed success, the following stated in server log:

~~~
21:14:36,139 INFO  [org.jboss.ws.cxf.metadata] (MSC service thread 1-4) JBWS024061: Adding service endpoint metadata: id=HelloWorldService
 address=http://localhost:8080/jaxws-helloworld/HelloWorldService/HelloWorldService
 implementor=org.jboss.jaxws.helloworld.HelloWorldService
 serviceName={org.jboss.jaxws.helloworld}HelloWorldService
 portName={org.jboss.jaxws.helloworld}HelloWorldServicePort
 annotationWsdlLocation=null
 wsdlLocationOverride=null
 mtomEnabled=false
~~~

### HelloWorld Client

* View wsdl via

    http://localhost:8080/jaxws-helloworld/HelloWorldService/HelloWorldService?wsdl

* Run [HelloWorldClient](src/main/java/org/jboss/jaxws/client/helloworld/HelloWorldClient.java) as java Application, the following output:

~~~
HelloWorld, JAX-WS!
~~~
