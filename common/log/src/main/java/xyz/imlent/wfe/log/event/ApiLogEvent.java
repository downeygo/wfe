package xyz.imlent.wfe.log.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wfee
 */
public class ApiLogEvent extends ApplicationEvent {
    public ApiLogEvent(Object source) {
        super(source);
    }
}
