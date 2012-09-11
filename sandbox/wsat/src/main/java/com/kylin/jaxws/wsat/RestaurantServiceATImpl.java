package com.kylin.jaxws.wsat;

import com.arjuna.mw.wst11.TransactionManager;
import com.arjuna.mw.wst11.TransactionManagerFactory;
import com.arjuna.mw.wst11.UserTransactionFactory;
import com.kylin.jaxws.wsat.ws.RestaurantServiceAT;


import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.annotation.WebServlet;
import java.util.UUID;

/**
 * An adapter class that exposes the RestaurantManager business API as a transactional Web Service.
 * 
 * 
 */
@WebService(serviceName = "RestaurantServiceATService", portName = "RestaurantServiceAT", name = "RestaurantServiceAT", targetNamespace = "https://github.com/kylinsoong/jaxws")
@HandlerChain(file = "/context-handlers.xml", name = "Context Handlers")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@WebServlet("/RestaurantServiceAT")
public class RestaurantServiceATImpl implements RestaurantServiceAT {

    private MockRestaurantManager mockRestaurantManager = MockRestaurantManager.getSingletonInstance();

    /**
     * Book a number of seats in the restaurant. Enrols a Participant, then passes the call through to the business logic.
     */
    @WebMethod
    public void makeBooking() throws RestaurantException {

        System.out.println("[SERVICE] Restaurant service invoked to make a booking");
        String transactionId;
        try {
            // get the transaction ID associated with this thread
            transactionId = UserTransactionFactory.userTransaction().toString();

            // enlist the Participant for this service:
            RestaurantParticipant restaurantParticipant = new RestaurantParticipant(transactionId);
            TransactionManager transactionManager = TransactionManagerFactory.transactionManager();
            System.out.println("[SERVICE] Enlisting a Durable2PC participant into the AT");
            transactionManager.enlistForDurableTwoPhase(restaurantParticipant, "restaurantServiceAT:" + UUID.randomUUID());
        } catch (Exception e) {
            throw new RestaurantException("Error when enlisting participant", e);
        }

        // invoke the backend business logic:
        System.out.println("[SERVICE] Invoking the back-end business logic");
        mockRestaurantManager.makeBooking(transactionId);
    }

    /**
     * obtain the number of existing bookings
     * 
     * @return the number of current bookings
     */
    @Override
    public int getBookingCount() {
        return mockRestaurantManager.getBookingCount();
    }

    /**
     * Reset the booking count to zero
     * 
     * Note: To simplify this example, this method is not part of the compensation logic, so will not be undone if the AT is
     * compensated. It can also be invoked outside of an active AT.
     */
    @Override
    public void reset() {
        mockRestaurantManager.reset();
    }
}
