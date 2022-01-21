package xyz.imlent.wfe.gateway.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wfee
 */
@Data
@Component
@ConfigurationProperties(prefix = "gateway")
@ConditionalOnProperty(prefix = "gateway", name = "dynamic", havingValue = "true")
public class DynamicRoutesProperties {
    private boolean dynamic;
    private String routeDataId;
}
