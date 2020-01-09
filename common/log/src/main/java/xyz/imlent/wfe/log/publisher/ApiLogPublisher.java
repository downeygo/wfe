package xyz.imlent.wfe.log.publisher;

import xyz.imlent.wfe.core.util.SpringUtil;
import xyz.imlent.wfe.log.event.ApiLogEvent;
import xyz.imlent.wfe.log.model.ApiLog;

/**
 * @author wfee
 */
public class ApiLogPublisher {
    public static void publishEvent(ApiLog apiLog) {
        SpringUtil.publishEvent(new ApiLogEvent(apiLog));
    }
}
