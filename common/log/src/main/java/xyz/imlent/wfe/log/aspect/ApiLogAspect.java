
package xyz.imlent.wfe.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xyz.imlent.wfe.core.util.WebUtil;
import xyz.imlent.wfe.log.model.ApiLog;
import xyz.imlent.wfe.log.publisher.ApiLogPublisher;

import java.time.Instant;

/**
 * @author wfee
 */
@Aspect
@Component
public class ApiLogAspect {
    @Around("@annotation(xyz.imlent.wfe.log.annotation.ApiLog)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = Instant.now().toEpochMilli();
        Object result = point.proceed();
        long end = Instant.now().toEpochMilli();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String url = WebUtil.getRequest().getRequestURL().toString();
        String ip = WebUtil.getIP();
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        ApiLog apiLog = ApiLog.builder().username(username).ip(ip).url(url).className(className).methodName(methodName).args(args).time(end - start).result(result).build();
        ApiLogPublisher.publishEvent(apiLog);
        return result;
    }
}
