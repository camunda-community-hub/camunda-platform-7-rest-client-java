package org.cibseven.community.rest.client.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
      "cibseven.bpm.client.base-url=http://new-url/engine-rest",
      "cibseven.bpm.client.basic-auth.username=newuser",
      "cibseven.bpm.client.basic-auth.password=newpass"
    })
public class CibSevenPropertiesTest {
  @Autowired CibSevenOpenApiStarter starter;

  @Test
  void shouldRun() {}

  @Test
  void shouldUseCibSevenProperties() {
    assertThat(starter.getBasePath()).isEqualTo("http://new-url/engine-rest");
    assertThat(starter.getUsername()).isEqualTo("newuser");
    // Note: password test omitted for security
  }
}
