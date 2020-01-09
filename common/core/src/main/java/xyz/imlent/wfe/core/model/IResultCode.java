package xyz.imlent.wfe.core.model;

import java.io.Serializable;

/**
 * @author wfee
 */
public interface IResultCode extends Serializable {
    /**
     * getMessage
     *
     * @return
     */
    String getMessage();

    /**
     * getCode
     *
     * @return
     */
    int getCode();
}
