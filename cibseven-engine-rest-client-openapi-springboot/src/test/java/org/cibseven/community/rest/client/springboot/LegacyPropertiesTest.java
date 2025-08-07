package org.cibseven.community.rest.client.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
      "cibseven.autoDeploy.bpmnResources=custom",
      "cibseven.autoDeploy.dmnResources=custom",
      "cibseven.autoDeploy.formResources=custom",
      "cibseven.autoDeploy.enabled=false",
      "cibseven.autoDeploy.failStartupOnError=false"
    })
public class LegacyPropertiesTest {
  @Autowired CibSevenAutodeploymentProperties properties;

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
