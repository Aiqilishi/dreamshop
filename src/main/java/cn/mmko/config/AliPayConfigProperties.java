package cn.mmko.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "alipay", ignoreUnknownFields = true)
public class AliPayConfigProperties {
    private String app_id;
    private String merchant_private_key;
    private String alipay_public_key;
    private String notify_url;
    private String gatewayUrl;
    private String sign_type="RSA2";
    private String charset="UTF-8";
    private String format="json";

}
