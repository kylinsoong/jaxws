package com.kylin.jaxws.wsat.ws;

import com.kylin.jaxws.wsat.RestaurantException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Interface to a simple Restaurant. Provides simple methods to manipulate bookings.
 * 
 */
@WebService(name = "RestaurantServiceAT", targetNamespace = "https://github.com/kylinsoong/jaxws")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RestaurantServiceAT {

    /**
     * Create a new booking
     */
    @WebMethod
    public void makeBooking() throws RestaurantException;

    /**
     * obtain the number of existing bookings
     * 
     * @return the number of current bookings
     */
    @WebMethod
    public int getBookingCount();

    /**
     * Reset the booking count to zero
     */
    @WebMethod
    public void reset();

}
