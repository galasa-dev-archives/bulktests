/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package bulktest;

public interface IBulkTestEnvironment {

    long getDelay();

    boolean shouldFail();

}
