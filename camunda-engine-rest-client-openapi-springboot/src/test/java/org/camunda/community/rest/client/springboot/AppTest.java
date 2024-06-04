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
    assertThat(properties.getEnabled()).isEqualTo(true);
    assertThat(properties.getBpmnResources()).isEqualTo("classpath*:**/*.bpmn");
    assertThat(properties.getDmnResources()).isEqualTo("classpath*:**/*.dmn");
    assertThat(properties.getFormResources()).isEqualTo("classpath*:**/*.form");
    assertThat(properties.getFailStartupOnError()).isEqualTo(true);
  }

  @Test
  void shouldNotSetBaseUrl() {
    assertThat(starter.getBasePath()).isNull();
  }
}
