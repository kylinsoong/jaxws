package com.kylin.jaxws.wsat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.handler.Handler;

import org.apache.log4j.Logger;


import com.arjuna.mw.wst11.UserTransaction;
import com.arjuna.mw.wst11.UserTransactionFactory;
import com.arjuna.mw.wst11.client.JaxWSHeaderContextProcessor;
import com.kylin.jaxws.wsat.ws.RestaurantServiceAT;
import com.kylin.jaxws.wsat.ws.RestaurantServiceATService;

/**
 * <p>
 * A simple servlet 3 that begins a WS-AtomicTransaction and invokes a Web service. If the call is successful, the transaction is committed.
 * </p>
 * <p/>
 * <p/>
 * The servlet is registered and mapped to /WSATSimpleServletClient using the {@linkplain javax.servlet.annotation.WebServlet}
 *
 * @HttpServlet}. </p>
 */
@WebServlet("/WSATSimpleServletClient")
public class WSATSimpleServletClient extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(WSATSimpleServletClient.class);

    private static final long serialVersionUID = -8314035702649252239L;

    @WebServiceRef(value=RestaurantServiceATService.class)
    private RestaurantServiceAT client;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	logger.info("doGet");
    	
//    	handle(req, resp);
    }
    	
    	protected void handle(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
    	
    	logger.info("WSAT simple Servlet Client");
        
        /*
         * Add client handler chain
         */
        BindingProvider bindingProvider = (BindingProvider) client;
        List<Handler> handlers = new ArrayList<Handler>(1);
        handlers.add(new JaxWSHeaderContextProcessor());
        bindingProvider.getBinding().setHandlerChain(handlers);

        /*
         * Lookup the DNS name of the server from the environment and set the endpoint address on the client.
         */
        String openshift = System.getenv("OPENSHIFT_APP_DNS");
        if (openshift != null) {
            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://"+openshift+"/RestaurantServiceAT");
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.write("<h1>This example demonstrates the deployment of a WS-AT (WS-AtomicTransaction) enabled JAX-WS Web service bundled in a war archive for deployment to *JBoss AS 7*.</h1>");

        System.out.println("[CLIENT] Creating a new WS-AT User Transaction");
        UserTransaction ut = UserTransactionFactory.userTransaction();
        try {
            System.out.println("[CLIENT] Beginning Atomic Transaction (All calls to Web services that support WS-AT wil be included in this transaction)");
            ut.begin();
            System.out.println("[CLIENT] invoking makeBooking() on WS");
            client.makeBooking();
            System.out.println("[CLIENT] committing Atomic Transaction (This will cause the AT to complete successfully)");
            ut.commit();

            out.write("<p><i>Go to your JBoss Application Server console or Server log to see the result of the transaction</i></p>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rollbackIfActive(ut);
            client.reset();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }


    /**
     * Utility method for rolling back a transaction if it is currently active.
     *
     * @param ut The User Business Activity to cancel.
     */
    private void rollbackIfActive(UserTransaction ut) {
        try {
            ut.rollback();
        } catch (Throwable th2) {
            // do nothing, not active
        }
    }

}
