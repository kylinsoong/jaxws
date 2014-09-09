## What's this

The StateService implement JAX-WS, supply service for GetAllStateInfo and GetStateInfo via StateCode. The following have the steps for build, deploy and consume StateService.

## Build

Maven build commands:

~~~
mvn clean install
~~~

will generate deployment jar `StateService.jar`.

## Deploy

Deploy `StateService.jar` to a running JBoss server(Assume JBoss EAP 6 run on localhost), the following will output to server log:

~~~
18:16:07,231 INFO  [org.jboss.ws.cxf.metadata] (MSC service thread 1-3) JBWS024061: Adding service endpoint metadata: id=StateServiceImpl
 address=http://localhost:8080/StateService/stateService/StateServiceImpl
 implementor=org.teiid.stateservice.StateServiceImpl
 serviceName={http://www.teiid.org/stateService/}stateService
 portName={http://www.teiid.org/stateService/}StateServiceImplPort
 annotationWsdlLocation=null
 wsdlLocationOverride=null
 mtomEnabled=false
~~~

WSDL File can be viewed via http://localhost:8080/StateService/stateService/StateServiceImpl?WSDL

## Consume

### Java Client 

Run [StateServiceClient](src/main/java/org/teiid/stateservice/client/StateServiceClient.java) as Java Application will invoke GetAllStateInfo and GetStateInfo correspondingly, the following outputed:

~~~
Rhode Island, Providence, RI, 1790
Vermont, Montpelier, VT, 1791
Hawaii, Honolulu, HI, 1959
Maine, Augusta, ME, 1820
Virginia, Richmond, VA, 1788
Michigan, Lansing, MI, 1837
Delaware, Dover, DE, 1787
Idaho, Boise, ID, 1890
Iowa, Des Moines, IA, 1846
Maryland, Annapolis, MD, 1788
Massachusetts, Boston, MA, 1788
Arkansas, Little Rock, AR, 1836
Illinois, Springfield, IL, 1818
Utah, Salt Lake City, UT, 1896
Indiana, Indianapolis, IN, 1816
Minnesota, Saint Paul, MN, 1858
Arizona, Phoenix, AZ, 1912
Missouri, Jefferson City, MO, 1821
Montana, Helena, MT, 1889
Mississippi, Jackson, MS, 1817
New Hampshire, Concord, NH, 1788
New Jersey, Trenton, NJ, 1787
New Mexico, Santa Fe, NM, 1912
Alaska, Juneau, AK, 1959
Alabama, Montgomery, AL, 1819
Texas, Austin, TX, 1845
North Carolina, Raleigh, NC, 1789
North Dakota, Bismarck, ND, 1889
Nebraska, Lincoln, NE, 1867
New York, Albany, NY, 1788
Georgia, Atlanta, GA, 1788
Nevada, Carson City, NV, 1864
Tennessee, Nashville, TN, 1796
California, Sacramento, CA, 1850
Oklahoma, Oklahoma City, OK, 1907
Ohio, Columbus, OH, 1803
Wyoming, Cheyenne, WY, 1890
Florida, Tallahassee, FL, 1845
South Dakota, Pierre, SD, 1889
South Carolina, Columbia, SC, 1788
Connecticut, Hartford, CT, 1788
West Virginia, Charleston, WV, 1863
Wisconsin, Madison, WI, 1848
Kentucky, Frankfort, KY, 1792
Kansas, Topeka, KS, 1861
Oregon, Salem, OR, 1859
Louisiana, Baton Rouge, LA, 1812
Washington, Olympia, WA, 1889
Colorado, Denver, CO, 1876
Pennsylvania, Harrisburg, PA, 1787

California, Sacramento, CA, 1850
~~~

### soapUI
