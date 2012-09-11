package com.kylin.jaxws.wsat;

import javax.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifier for specifying which RestaurantServiceAT implementation to use.
 * 
 * This Qualifier only supports the ClientStub implementation. The only other class to implement RestaurantServiceAT is the
 * RestaurantServiceATImpl class, which is not injected by CDI, so does not require Qualifier support.
 * 
 */
@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, FIELD })
public @interface ClientStub {
}
