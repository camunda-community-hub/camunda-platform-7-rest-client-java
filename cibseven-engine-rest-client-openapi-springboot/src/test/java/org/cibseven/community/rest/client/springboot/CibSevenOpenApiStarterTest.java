package org.cibseven.community.rest.client.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
      "cibseven.bpm.client.basic-auth.username=username",
      "cibseven.bpm.client.basic-auth.password=password",
      "cibseven.bpm.client.base-url=http://base-url/engine-rest"
    })
public class CibSevenOpenApiStarterTest {
  @Autowired CibSevenOpenApiStarter starter;

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
