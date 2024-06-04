package org.camunda.community.rest.client.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("camunda.auto-deploy")
public class CamundaAutodeploymentProperties {
  private String bpmnResources;
  private String dmnResources;
  private String formResources;
  private Boolean enabled;
  private Boolean failStartupOnError;

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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getFailStartupOnError() {
    return failStartupOnError;
  }

  public void setFailStartupOnError(Boolean failStartupOnError) {
    this.failStartupOnError = failStartupOnError;
  }
}
