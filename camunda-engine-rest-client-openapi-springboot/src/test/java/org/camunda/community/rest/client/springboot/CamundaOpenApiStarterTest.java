package org.camunda.community.rest.client.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
      "camunda.bpm.client.basic-auth.username=username",
      "camunda.bpm.client.basic-auth.password=password",
      "camunda.bpm.client.base-url=http://base-url/engine-rest"
    })
public class CamundaOpenApiStarterTest {
  @Autowired CamundaOpenApiStarter starter;

  @Test
  void shouldSetBaseUrl() {
    assertThat(starter.getBasePath()).isEqualTo("http://base-url/engine-rest");
  }

  @Test
  void shouldSetCredentials() {
    assertThat(starter.getUsername()).isEqualTo("username");
    assertThat(starter.getPassword()).isEqualTo("password");
  }
}
