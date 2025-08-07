package org.cibseven.community.rest.client.springboot;

import org.cibseven.community.rest.client.api.*;
import org.cibseven.community.rest.client.invoker.ApiClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CibSevenApi {

  private final ApiClient apiClient;

  public CibSevenApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  @Bean
  public AuthorizationApi authorizationApi() {
    return new AuthorizationApi(apiClient);
  }

  @Bean
  public BatchApi batchApi() {
    return new BatchApi(apiClient);
  }

  @Bean
  public ConditionApi conditionApi() {
    return new ConditionApi(apiClient);
  }

  @Bean
  public DecisionDefinitionApi decisionDefinitionApi() {
    return new DecisionDefinitionApi(apiClient);
  }

  @Bean
  public DecisionRequirementsDefinitionApi decisionRequirementsDefinitionApi() {
    return new DecisionRequirementsDefinitionApi(apiClient);
  }

  @Bean
  public DeploymentApi deploymentApi() {
    return new DeploymentApi(apiClient);
  }

  @Bean
  public EngineApi engineApi() {
    return new EngineApi(apiClient);
  }

  @Bean
  public EventSubscriptionApi eventSubscriptionApi() {
    return new EventSubscriptionApi(apiClient);
  }

  @Bean
  public ExecutionApi executionApi() {
    return new ExecutionApi(apiClient);
  }

  @Bean
  public ExternalTaskApi externalTaskApi() {
    return new ExternalTaskApi(apiClient);
  }

  @Bean
  public FilterApi filterApi() {
    return new FilterApi(apiClient);
  }

  @Bean
  public GroupApi groupApi() {
    return new GroupApi(apiClient);
  }

  @Bean
  public HistoryCleanupApi historyCleanupApi() {
    return new HistoryCleanupApi(apiClient);
  }

  @Bean
  public IdentityApi identityApi() {
    return new IdentityApi(apiClient);
  }

  @Bean
  public IncidentApi incidentApi() {
    return new IncidentApi(apiClient);
  }

  @Bean
  public JobApi jobApi() {
    return new JobApi(apiClient);
  }

  @Bean
  public JobDefinitionApi jobDefinitionApi() {
    return new JobDefinitionApi(apiClient);
  }

  @Bean
  public MessageApi messageApi() {
    return new MessageApi(apiClient);
  }

  @Bean
  public MetricsApi metricsApi() {
    return new MetricsApi(apiClient);
  }

  @Bean
  public MigrationApi migrationApi() {
    return new MigrationApi(apiClient);
  }

  @Bean
  public ModificationApi modificationApi() {
    return new ModificationApi(apiClient);
  }

  @Bean
  public ProcessDefinitionApi processDefinitionApi() {
    return new ProcessDefinitionApi(apiClient);
  }

  @Bean
  public ProcessInstanceApi processInstanceApi() {
    return new ProcessInstanceApi(apiClient);
  }

  @Bean
  public SchemaLogApi schemaLogApi() {
    return new SchemaLogApi(apiClient);
  }

  @Bean
  public SignalApi signalApi() {
    return new SignalApi(apiClient);
  }

  @Bean
  public TaskApi taskApi() {
    return new TaskApi(apiClient);
  }

  @Bean
  public TaskAttachmentApi taskAttachmentApi() {
    return new TaskAttachmentApi(apiClient);
  }

  @Bean
  public TaskCommentApi taskCommentApi() {
    return new TaskCommentApi(apiClient);
  }

  @Bean
  public TaskIdentityLinkApi taskIdentityLinkApi() {
    return new TaskIdentityLinkApi(apiClient);
  }

  @Bean
  public TaskVariableApi taskVariableApi() {
    return new TaskVariableApi(apiClient);
  }

  @Bean
  public TaskLocalVariableApi taskLocalVariableApi() {
    return new TaskLocalVariableApi(apiClient);
  }

  @Bean
  public TelemetryApi telemetryApi() {
    return new TelemetryApi(apiClient);
  }

  @Bean
  public TenantApi tenantApi() {
    return new TenantApi(apiClient);
  }

  @Bean
  public UserApi userApi() {
    return new UserApi(apiClient);
  }

  @Bean
  public VariableInstanceApi variableInstanceApi() {
    return new VariableInstanceApi(apiClient);
  }

  @Bean
  public VersionApi versionApi() {
    return new VersionApi(apiClient);
  }

  @Bean
  public ProcessInstanceCommentApi processInstanceCommentApi() {
    return new ProcessInstanceCommentApi(apiClient);
  }
}
