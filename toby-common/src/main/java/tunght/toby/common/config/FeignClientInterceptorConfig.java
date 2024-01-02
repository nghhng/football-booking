package tunght.toby.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientInterceptorConfig {

    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                if(!shouldNotApplyInterceptor(requestTemplate)){
                    // Lấy giá trị Authorization từ đâu đó (có thể sử dụng ThreadLocal hoặc các cơ chế khác)
                    String authorizationHeader = AuthorizationContextHolder.getAuthorization();

                    // Thêm giá trị Authorization vào header của request
                    requestTemplate.header("Authorization", authorizationHeader);
                }
            }
        };
    }

    // Hàm kiểm tra điều kiện để xác định liệu interceptor có thực hiện hay không
    private boolean shouldNotApplyInterceptor(RequestTemplate requestTemplate) {
        // Thực hiện logic kiểm tra dựa trên requestTemplate
        return requestTemplate.url().contains("v1")||requestTemplate.url().contains("v2");
    }
}