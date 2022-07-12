package org.camunda.community.rest.client.springboot;

import org.apache.commons.io.IOUtils;
import org.camunda.community.rest.client.api.DeploymentApi;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Auto deploys all Camunda resources found on classpath during startup of the application
 */
@Configuration
public class CamundaProcessAutodeployment {

    @Autowired
    private DeploymentApi deploymentApi;

    @Autowired
    private ResourcePatternResolver patternResolver;

    // TODO Possible extension: Provide a @Deployment annotation like Spring Zeebe
    @Value("${camunda.autoDeploy.bpmnResources:}")
    private String bpmnResourcesPattern;

    @Value("${camunda.autoDeploy.dmnResources:}")
    private String dmnResourcesPattern;

    @Value("${camunda.autoDeploy.formResources:}")
    private String formResourcesPattern;

    @Value("${spring.application.name:spring-app}")
    private String applicationName;

    @PostConstruct
    public void deployCamundaResources() throws IOException, ApiException {
        if (bpmnResourcesPattern==null || bpmnResourcesPattern.length()==0) {
            bpmnResourcesPattern = "classpath*:**/*.bpmn"; // Not sure why the default mechanism in @Value makes problems - but this works!
        }
        if (dmnResourcesPattern==null || dmnResourcesPattern.length()==0) {
            dmnResourcesPattern = "classpath*:**/*.dmn";
        }
        if (formResourcesPattern==null || formResourcesPattern.length()==0) {
            formResourcesPattern = "classpath*:**/*.form";
        }

        List<Resource> resourcesToDeploy = new ArrayList<>();
        resourcesToDeploy.addAll(Arrays.asList( patternResolver.getResources(bpmnResourcesPattern) ));
        resourcesToDeploy.addAll(Arrays.asList( patternResolver.getResources(dmnResourcesPattern) ));
        resourcesToDeploy.addAll(Arrays.asList( patternResolver.getResources(formResourcesPattern) ));

        for (Resource camundaResource: resourcesToDeploy) {
            final File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(camundaResource.getInputStream(), out);
            }
            deploymentApi.createDeployment(
                    null,
                    null,
                    true, // changedOnly
                    true, // duplicateFiltering
                    applicationName + "-" + camundaResource.getFilename(), // deploymentName
                    null,
                    tempFile);
            // deploying the files one by one because of limitation of OpenAPI at the moment
            // see https://jira.camunda.com/browse/CAM-13105
        }
    }
}
