package org.camunda.community.rest.client.springboot;

import org.camunda.community.rest.client.api.*;
import org.camunda.community.rest.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaHistoryApi {

    @Autowired
    private ApiClient apiClient;

    @Bean
    public HistoricActivityInstanceApi historicActivityInstanceApi () {
        return new HistoricActivityInstanceApi(apiClient);
    }

    @Bean
    public HistoricBatchApi historicBatchApi() {
        return new HistoricBatchApi(apiClient);
    }

    @Bean
    public HistoricDecisionDefinitionApi historicDecisionDefinitionApi() {
        return new HistoricDecisionDefinitionApi(apiClient);
    }

    @Bean
    public HistoricDecisionInstanceApi historicDecisionInstanceApi() {
        return new HistoricDecisionInstanceApi(apiClient);
    }

    @Bean
    public HistoricDecisionRequirementsDefinitionApi historicDecisionRequirementsDefinitionApi() {
        return new HistoricDecisionRequirementsDefinitionApi(apiClient);
    }

    @Bean
    public HistoricDetailApi historicDetailApi() {
        return new HistoricDetailApi(apiClient);
    }

    @Bean
    public HistoricExternalTaskLogApi historicExternalTaskLogApi() {
        return new HistoricExternalTaskLogApi(apiClient);
    }

    @Bean
    public HistoricIdentityLinkLogApi historicIdentityLinkLogApi() {
        return new HistoricIdentityLinkLogApi(apiClient);
    }

    @Bean
    public HistoricIncidentApi historicIncidentApi() {
        return new HistoricIncidentApi(apiClient);
    }

    @Bean
    public HistoricJobLogApi historicJobLogApi() {
        return new HistoricJobLogApi(apiClient);
    }

    @Bean
    public HistoricProcessDefinitionApi historicProcessDefinitionApi() {
        return new HistoricProcessDefinitionApi(apiClient);
    }

    @Bean
    public HistoricProcessInstanceApi historicProcessInstanceApi() {
        return new HistoricProcessInstanceApi(apiClient);
    }

    @Bean
    public HistoricTaskInstanceApi historicTaskInstanceApi() {
        return new HistoricTaskInstanceApi(apiClient);
    }

    @Bean
    public HistoricUserOperationLogApi historicTUserOperationLogApi() {
        return new HistoricUserOperationLogApi(apiClient);
    }

    @Bean
    public HistoricVariableInstanceApi historicVariableInstanceApi() {
        return new HistoricVariableInstanceApi(apiClient);
    }

}
