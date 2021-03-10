/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package bulktest;

import dev.galasa.ManagerException;

public class BulkTestManagerException extends ManagerException {
    private static final long serialVersionUID = 1L;

    public BulkTestManagerException() {
    }

    public BulkTestManagerException(String message) {
        super(message);
    }

    public BulkTestManagerException(Throwable cause) {
        super(cause);
    }

    public BulkTestManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public BulkTestManagerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
