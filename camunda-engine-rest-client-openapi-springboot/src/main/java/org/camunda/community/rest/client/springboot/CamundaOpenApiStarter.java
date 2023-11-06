package org.camunda.community.rest.client.springboot;

import org.camunda.community.rest.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CamundaOpenApiStarter {

    @Value( "${camunda.bpm.client.base-url:null}" )
    private String basePath;

    @Bean
    public ApiClient createApiClient() {
        ApiClient client = new ApiClient();
        if (basePath!=null) {
            client.setBasePath(basePath);
        }
        return client;
    }

}
