package xyz.imlent.wfe.core.handler;

import com.alibaba.fastjson.JSON;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.imlent.wfe.core.model.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static feign.Util.RETRY_AFTER;
import static feign.Util.checkNotNull;
import static java.util.Locale.US;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * feign异常处理：
 * 设置：feign.hystrix.enabled: false 才生效，feign调用服务报错后会进入该类
 * 目的：将所有报错信息抛出
 * 解决每个client需要写fallback类及错误信息被吞
 * 解决feign hystrix fallback自动处理responseStatus为200导致seata分布式事务不生效
 * 解决FeignException的message错误信息提示不是需要的内容
 *
 * @author wfee
 */
@Slf4j
@Configuration
public class FeignExceptionHandler {
    private final RetryAfterDecoder retryAfterDecoder = new RetryAfterDecoder();

    @Bean
    public ErrorDecoder errorDecoder() {
        return (method, response) -> {
            String msg = "";
            if (response.body() != null) {
                try {
                    // 服务之间调用所有异常处理
                    R r = JSON.parseObject(response.body().asInputStream(), R.class);
                    msg = r.getMsg();
                } catch (Exception e) {
                    log.error("系统异常", e);
                }
            }
            FeignException exception = errorStatus(response.status(), msg, null);
            Date retryAfter = retryAfterDecoder.apply(firstOrNull(response.headers(), RETRY_AFTER));
            if (retryAfter != null) {
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        exception,
                        retryAfter);
            }
            return exception;
        };
    }


    private <T> T firstOrNull(Map<String, Collection<T>> map, String key) {
        if (map.containsKey(key) && !map.get(key).isEmpty()) {
            return map.get(key).iterator().next();
        }
        return null;
    }

    public Exception decode(String methodKey, Response response) {
        Date retryAfter = retryAfterDecoder.apply(firstOrNull(response.headers(), RETRY_AFTER));
        return null;
    }

    /**
     * Decodes a {@link feign.Util#RETRY_AFTER} header into an absolute date, if possible. <br>
     * See <a href="https://tools.ietf.org/html/rfc2616#section-14.37">Retry-After format</a>
     */
    static class RetryAfterDecoder {

        static final DateFormat RFC822_FORMAT =
                new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", US);
        private final DateFormat rfc822Format;

        RetryAfterDecoder() {
            this(RFC822_FORMAT);
        }

        RetryAfterDecoder(DateFormat rfc822Format) {
            this.rfc822Format = checkNotNull(rfc822Format, "rfc822Format");
        }

        protected long currentTimeMillis() {
            return System.currentTimeMillis();
        }

        /**
         * returns a date that corresponds to the first time a request can be retried.
         *
         * @param retryAfter String in
         *                   <a href="https://tools.ietf.org/html/rfc2616#section-14.37" >Retry-After format</a>
         */
        public Date apply(String retryAfter) {
            if (retryAfter == null) {
                return null;
            }
            if (retryAfter.matches("^[0-9]+$")) {
                long deltaMillis = SECONDS.toMillis(Long.parseLong(retryAfter));
                return new Date(currentTimeMillis() + deltaMillis);
            }
            synchronized (rfc822Format) {
                try {
                    return rfc822Format.parse(retryAfter);
                } catch (ParseException ignored) {
                    return null;
                }
            }
        }
    }

    /**
     * 异常处理： 修改FeignException
     *
     * @param status
     * @param message
     * @param body
     * @return
     */
    private static FeignException errorStatus(int status, String message, byte[] body) {
        switch (status) {
            case 400:
                return new FeignException.BadRequest(message, body);
            case 401:
                return new FeignException.Unauthorized(message, body);
            case 403:
                return new FeignException.Forbidden(message, body);
            case 404:
                return new FeignException.NotFound(message, body);
            case 405:
                return new FeignException.MethodNotAllowed(message, body);
            case 406:
                return new FeignException.NotAcceptable(message, body);
            case 409:
                return new FeignException.Conflict(message, body);
            case 410:
                return new FeignException.Gone(message, body);
            case 415:
                return new FeignException.UnsupportedMediaType(message, body);
            case 429:
                return new FeignException.TooManyRequests(message, body);
            case 422:
                return new FeignException.UnprocessableEntity(message, body);
            case 500:
                return new FeignException.InternalServerError(message, body);
            case 501:
                return new FeignException.NotImplemented(message, body);
            case 502:
                return new FeignException.BadGateway(message, body);
            case 503:
                return new FeignException.ServiceUnavailable(message, body);
            case 504:
                return new FeignException.GatewayTimeout(message, body);
            default:
                return new FeignException.InternalServerError(message, body);
        }
    }
}
