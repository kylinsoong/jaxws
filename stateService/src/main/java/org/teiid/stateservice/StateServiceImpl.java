package org.teiid.stateservice;

import org.teiid.stateservice.StateService;
import org.teiid.stateservice.jaxb.StateInfo;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService(serviceName = "stateService", endpointInterface = "org.teiid.stateservice.StateService", targetNamespace = "http://www.teiid.org/stateService/")
public class StateServiceImpl implements StateService {
	
	StateData stateData = new StateData();
	
	public java.util.List<org.teiid.stateservice.jaxb.StateInfo> getAllStateInfo() {
		return stateData.getAll();
	}

	public org.teiid.stateservice.jaxb.StateInfo getStateInfo(java.lang.String stateCode) throws GetStateInfoFault_Exception {
		StateInfo info = stateData.getData(stateCode);
		if(null == info) {
			throw new GetStateInfoFault_Exception(stateCode + " is not a valid state abbreviation");
		}
		return info;
	}
}