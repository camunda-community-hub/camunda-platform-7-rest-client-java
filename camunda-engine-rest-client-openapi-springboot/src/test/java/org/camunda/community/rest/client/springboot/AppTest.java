package org.camunda.community.rest.client.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppTest {
  @Autowired CamundaAutodeploymentProperties properties;
  @Autowired CamundaOpenApiStarter starter;

  @Test
  void shouldRun() {}

  @Test
  void shouldSetDefaultProperties() {
    assertThat(properties.isEnabled()).isTrue();
    assertThat(properties.getBpmnResources()).isEqualTo("classpath*:**/*.bpmn");
    assertThat(properties.getDmnResources()).isEqualTo("classpath*:**/*.dmn");
    assertThat(properties.getFormResources()).isEqualTo("classpath*:**/*.form");
    assertThat(properties.isFailStartupOnError()).isTrue();
  }

  @Test
  void shouldNotSetBaseUrl() {
    assertThat(starter.getBasePath()).isNull();
  }

  @Test
  void shouldNotSetCredentials() {
    assertThat(starter.getUsername()).isNull();
    assertThat(starter.getPassword()).isNull();
  }
}
