/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package additional;

import json.utility.ResponseConstants;

/**
 *
 * @author TrungHTH
 */
public class DMSException extends Exception {
    private Integer errorCode;

    /**
     * Creates a new instance of
     * <code>BetException</code> without detail message.
     */
    public DMSException() {
    }

    /**
     * Constructs an instance of
     * <code>BetException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DMSException(String msg) {
        super(msg);
        this.errorCode = ResponseConstants.NORMAL_ERROR_CODE;
    }

    public DMSException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
