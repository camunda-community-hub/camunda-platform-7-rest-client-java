package org.camunda.community.rest.client.springboot;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.commons.support.ReflectionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class CamundaApiTest {
  @Autowired ApplicationContext applicationContext;

  @TestFactory
  Stream<DynamicTest> shouldHaveAllApisAsBean() {
    List<Class<?>> allApis =
        ReflectionSupport.findAllClassesInPackage(
            "org.camunda.community.rest.client.api", c -> !c.isAnonymousClass(), s -> true);
    return allApis.stream()
        .map(
            c ->
                DynamicTest.dynamicTest(
                    c.getName(),
                    () -> {
                      assertThat(applicationContext.getBean(c)).isNotNull();
                    }));
  }
}
