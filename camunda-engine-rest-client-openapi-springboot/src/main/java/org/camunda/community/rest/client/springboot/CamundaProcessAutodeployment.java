package org.camunda.community.rest.client.springboot;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.camunda.community.rest.client.api.DeploymentApi;
import org.camunda.community.rest.client.invoker.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.DigestUtils;

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

    private Logger logger = LoggerFactory.getLogger(CamundaProcessAutodeployment.class);

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

        deployResources(Arrays.asList(patternResolver.getResources(bpmnResourcesPattern)), "bpmn");
        deployResources(Arrays.asList(patternResolver.getResources(dmnResourcesPattern)), "dmn");
        deployResources(Arrays.asList(patternResolver.getResources(formResourcesPattern)), "form");
    }

    private void deployResources(List<Resource> resourcesToDeploy, String type) throws IOException, ApiException {
        logger.info("Found resources for deployment of type "+ type +": " + resourcesToDeploy);
        for (Resource camundaResource: resourcesToDeploy) {
            // We have to create a tmpFile because we need to read the files via InputStream to work also in a jar-packed environment
            // but the OpenAPI will need a File.
            // We still have to set the file ending correct in the temp file
            // (because otherwise the deployer will not pick it up as e.g. BPMN file)
            String tempDirectoryName = FileUtils.getTempDirectory().getAbsolutePath();
            String filename = getResourceFilename(camundaResource, type);
            final File tempFile = new File(tempDirectoryName + File.separator + filename);
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(camundaResource.getInputStream(), out);
            }
            logger.info("  - Now deploying: " + camundaResource);
            deploymentApi.createDeployment(
                    null,
                    null,
                    true, // changedOnly
                    true, // duplicateFiltering
                    applicationName + "-" + filename, // deploymentName
                    null,
                    tempFile);
            // deploying the files one by one because of limitation of OpenAPI at the moment
            // see https://jira.camunda.com/browse/CAM-13105
        }
    }

    private String getResourceFilename(Resource camundaResource, String type) throws IOException {
        if (camundaResource.getFilename() != null) {
            return camundaResource.getFilename();
        } else {
            return DigestUtils.md5DigestAsHex(camundaResource.getInputStream()) + '.' + type;
        }
    }
}
