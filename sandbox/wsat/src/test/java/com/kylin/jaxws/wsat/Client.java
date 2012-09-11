package com.kylin.jaxws.wsat;

import com.arjuna.mw.wst11.client.JaxWSHeaderContextProcessor;
import com.kylin.jaxws.wsat.RestaurantException;
import com.kylin.jaxws.wsat.ws.RestaurantServiceAT;


import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A Client stub to the restaurant service.
 * 
 */
@ClientStub
public class Client implements RestaurantServiceAT {
    private RestaurantServiceAT restaurant;

    /**
     * Default constructor with hard-coded values for the RestaurantServiceAT endpoint details (wsdl url, service name & port
     * name)
     * 
     * @throws MalformedURLException if the WSDL url is malformed.
     */
    public Client() throws MalformedURLException {
        URL wsdlLocation = new URL("http://localhost:8080/wsat-simple/RestaurantServiceAT?wsdl");
        QName serviceName = new QName("https://github.com/kylinsoong/jaxws", "RestaurantServiceATService");
        QName portName = new QName("https://github.com/kylinsoong/jaxws", "RestaurantServiceAT");

        Service service = Service.create(wsdlLocation, serviceName);
        restaurant = service.getPort(portName, RestaurantServiceAT.class);

        /*
         * Add client handler chain
         */
        BindingProvider bindingProvider = (BindingProvider) restaurant;
        List<Handler> handlers = new ArrayList<Handler>(1);
        handlers.add(new JaxWSHeaderContextProcessor());
        bindingProvider.getBinding().setHandlerChain(handlers);
    }

    /**
     * Create a new booking
     */
    @Override
    public void makeBooking() throws RestaurantException {
        restaurant.makeBooking();
    }

    /**
     * obtain the number of existing bookings
     * 
     * @return the number of current bookings
     */
    @Override
    public int getBookingCount() {
        return restaurant.getBookingCount();
    }

    /**
     * Reset the booking count to zero
     */
    @Override
    public void reset() {
        restaurant.reset();
    }
}
