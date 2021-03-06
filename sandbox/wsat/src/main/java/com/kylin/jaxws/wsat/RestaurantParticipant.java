package com.kylin.jaxws.wsat;

import com.arjuna.wst.*;

import java.io.Serializable;

/**
 * An adapter class that exposes the RestaurantManager as a WS-T Atomic Transaction participant.
 * 
 */
public class RestaurantParticipant implements Durable2PCParticipant, Serializable {
    
	private static final long serialVersionUID = 1L;

    // The back-end resource for managing bookings
    private MockRestaurantManager mockRestaurantManager = MockRestaurantManager.getSingletonInstance();

    // The transaction ID for this transaction
    private String txID;

    /**
     * Creates a new participant for this transaction. Participants and transaction instances have a one-to-one mapping.
     * 
     * @param txID the ID of the transaction tht this participant will be enlisted within.
     */
    public RestaurantParticipant(String txID) {
        this.txID = txID;
    }

    /**
     * Invokes the prepare step of the business logic, reporting activity and outcome.
     * 
     * @return Prepared where possible, Aborted where necessary.
     * @throws WrongStateException
     * @throws SystemException
     */
    public Vote prepare() throws WrongStateException, SystemException {
        // Log the event and invoke the prepare operation
        // on the back-end logic.
        System.out.println("[SERVICE] Prepare called on participant, about to prepare the back-end resource");
        boolean success = mockRestaurantManager.prepare(txID);

        // Map the return value from
        // the business logic to the appropriate Vote type.
        if (success) {
            System.out.println("[SERVICE] back-end resource prepared, participant votes prepared");
            return new Prepared();
        } else {
            System.out.println("[SERVICE] back-end resource failed to prepare, participant votes aborted");
            return new Aborted();
        }
    }

    /**
     * Invokes the commit step of the business logic.
     * 
     * @throws WrongStateException
     * @throws SystemException
     */
    public void commit() throws WrongStateException, SystemException {
        // Log the event and invoke the commit operation
        // on the backend business logic.
        System.out.println("[SERVICE] all participants voted 'prepared', so coordinator tells the participant to commit");
        mockRestaurantManager.commit(txID);
    }

    /**
     * Invokes the rollback operation on the business logic.
     * 
     * @throws WrongStateException
     * @throws SystemException
     */
    public void rollback() throws WrongStateException, SystemException {
        // Log the event and invoke the rollback operation
        // on the backend business logic.
        System.out
                .println("[SERVICE] one or more participants voted 'aborted' or a failure occurred, so coordinator tells the participant to rollback");
        mockRestaurantManager.rollback(txID);
    }

    public void unknown() throws SystemException {
        System.out.println("RestaurantParticipantAT.unknown");
    }

    public void error() throws SystemException {
        System.out.println("RestaurantParticipantAT.error");
    }

}
