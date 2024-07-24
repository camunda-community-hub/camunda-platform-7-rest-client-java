package org.camunda.community.rest.client.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
      "camunda.autoDeploy.bpmnResources=custom",
      "camunda.autoDeploy.dmnResources=custom",
      "camunda.autoDeploy.formResources=custom",
      "camunda.autoDeploy.enabled=false",
      "camunda.autoDeploy.failStartupOnError=false"
    })
public class LegacyPropertiesTest {
  @Autowired CamundaAutodeploymentProperties properties;

  @Test
  void shouldRun() {}

  @Test
  void shouldSetLegacyProperties() {
    assertThat(properties.isEnabled()).isFalse();
    assertThat(properties.getBpmnResources()).isEqualTo("custom");
    assertThat(properties.getDmnResources()).isEqualTo("custom");
    assertThat(properties.getFormResources()).isEqualTo("custom");
    assertThat(properties.isFailStartupOnError()).isFalse();
  }
}
