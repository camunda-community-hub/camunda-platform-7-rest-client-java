package org.camunda.community.rest.client.springboot;

import org.camunda.community.rest.client.api.DeploymentApi;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Auto deploys all Camunda resources found on classpath during startup of the application
 */
@Configuration
public class CamundaProcessAutodeployment {

    @Autowired
    private DeploymentApi deploymentApi;

    // TODO Possible extension: Provide a @Deployment annotation like Spring Zeebe
    @Value("classpath*:**/*.bpmn")
    private Resource[] bpmnResources;

    @Value("classpath*:**/*.dmn")
    private Resource[] dmnResources;

    @Value("classpath*:**/*.form")
    private Resource[] formResources;

    @Value("${spring.application.name:spring-app}")
    private String applicationName;

    @PostConstruct
    public void deployCamundaResources() throws IOException, ApiException {
        List<Resource> resourcesToDeploy = new ArrayList<>();
        resourcesToDeploy.addAll(Arrays.asList( bpmnResources ));
        resourcesToDeploy.addAll(Arrays.asList( dmnResources ));
        resourcesToDeploy.addAll(Arrays.asList( formResources ));

        for (Resource camundaResource: resourcesToDeploy) {
            deploymentApi.createDeployment(
                    null,
                    null,
                    true, // changedOnly
                    true, // duplicateFiltering
                    applicationName + "-" + camundaResource.getFilename(), // deploymentName
                    null,
                    camundaResource.getFile());
            // deploying the files one by one because of limitation of OpenAPI at the moment
            // see https://jira.camunda.com/browse/CAM-13105
        }
    }
}
