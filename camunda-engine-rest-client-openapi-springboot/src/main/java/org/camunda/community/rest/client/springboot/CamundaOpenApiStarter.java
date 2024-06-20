package org.camunda.community.rest.client.springboot;

import org.camunda.community.rest.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CamundaOpenApiStarter {

  private final String basePath;
  private final String username;
  private final String password;

  public CamundaOpenApiStarter(
      @Value("${camunda.bpm.client.base-url:#{null}}") String basePath,
      @Value("${camunda.bpm.client.basic-auth.username:#{null}}") String username,
      @Value("${camunda.bpm.client.basic-auth.password:#{null}}") String password) {
    this.basePath = basePath;
    this.username = username;
    this.password = password;
  }

  public String getBasePath() {
    return basePath;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  @Bean
  public ApiClient createApiClient() {
    ApiClient client = new ApiClient();
    if (basePath != null) {
      client.setBasePath(basePath);
    }
    if (username != null) {
      client.setUsername(username);
    }
    if (password != null) {
      client.setPassword(password);
    }
    return client;
  }
}
