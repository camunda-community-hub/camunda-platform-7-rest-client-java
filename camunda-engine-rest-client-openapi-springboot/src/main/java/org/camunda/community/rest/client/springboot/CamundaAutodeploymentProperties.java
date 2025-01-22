package org.camunda.community.rest.client.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("camunda.auto-deploy")
public class CamundaAutodeploymentProperties {
  private String bpmnResources = "classpath*:**/*.bpmn";
  private String dmnResources = "classpath*:**/*.dmn";
  private String formResources = "classpath*:**/*.form";
  private boolean enabled = true;
  private boolean failStartupOnError = true;

  public String getBpmnResources() {
    return bpmnResources;
  }

  public void setBpmnResources(String bpmnResources) {
    this.bpmnResources = bpmnResources;
  }

  public String getDmnResources() {
    return dmnResources;
  }

  public void setDmnResources(String dmnResources) {
    this.dmnResources = dmnResources;
  }

  public String getFormResources() {
    return formResources;
  }

  public void setFormResources(String formResources) {
    this.formResources = formResources;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isFailStartupOnError() {
    return failStartupOnError;
  }

  public void setFailStartupOnError(boolean failStartupOnError) {
    this.failStartupOnError = failStartupOnError;
  }
}
