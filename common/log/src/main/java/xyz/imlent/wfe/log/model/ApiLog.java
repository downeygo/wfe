package xyz.imlent.wfe.log.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author wfee
 */
@Data
@Builder
public class ApiLog {
    private String username;
    private String url;
    private String ip;
    private String className;
    private String methodName;
    private Object[] args;
    private Long time;
    private Object result;
}
