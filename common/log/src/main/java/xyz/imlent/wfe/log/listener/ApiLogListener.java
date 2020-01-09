package xyz.imlent.wfe.log.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import xyz.imlent.wfe.log.event.ApiLogEvent;
import xyz.imlent.wfe.log.model.ApiLog;

/**
 * @author wfee
 */
@Slf4j
public class ApiLogListener {

    @Order
    @Async
    @EventListener(ApiLogEvent.class)
    public void showLog(ApiLogEvent event) {
        ApiLog source = (ApiLog) event.getSource();
        log.info(source.toString());
    }
}
