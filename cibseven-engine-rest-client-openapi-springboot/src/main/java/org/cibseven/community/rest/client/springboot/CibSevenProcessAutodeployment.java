package org.cibseven.community.rest.client.springboot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.cibseven.community.rest.client.api.DeploymentApi;
import org.cibseven.community.rest.client.invoker.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.DigestUtils;

/** Auto deploys all CIB seven resources found on classpath during startup of the application */
@AutoConfiguration
@EnableConfigurationProperties(CibSevenAutodeploymentProperties.class)
public class CibSevenProcessAutodeployment {

  private static final Logger logger = LoggerFactory.getLogger(CibSevenProcessAutodeployment.class);
  private final DeploymentApi deploymentApi;
  private final ResourcePatternResolver patternResolver;
  private final CibSevenAutodeploymentProperties properties;
  private final String applicationName;

  public CibSevenProcessAutodeployment(
      DeploymentApi deploymentApi,
      ResourcePatternResolver patternResolver,
      CibSevenAutodeploymentProperties properties,
      @Value("${spring.application.name:spring-app}") String applicationName) {
    this.deploymentApi = deploymentApi;
    this.patternResolver = patternResolver;
    this.properties = properties;
    this.applicationName = applicationName;
  }

  // TODO Possible extension: Provide a @Deployment annotation like Spring Zeebe
  @EventListener(ApplicationStartedEvent.class)
  public void deployCibSevenResources() throws IOException, ApiException {
    if (!properties.isEnabled()) {
      return;
    }
    deployResources(
        Arrays.asList(patternResolver.getResources(properties.getBpmnResources())), "bpmn");
    deployResources(
        Arrays.asList(patternResolver.getResources(properties.getDmnResources())), "dmn");
    deployResources(
        Arrays.asList(patternResolver.getResources(properties.getFormResources())), "form");
  }

  private void deployResources(List<Resource> resourcesToDeploy, String type)
      throws IOException, ApiException {
    logger.info("Found resources for deployment of type " + type + ": " + resourcesToDeploy);
    for (Resource resource : resourcesToDeploy) {
      // We have to create a tmpFile because we need to read the files via InputStream to work also
      // in a jar-packed environment
      // but the OpenAPI will need a File.
      // We still have to set the file ending correct in the temp file
      // (because otherwise the deployer will not pick it up as e.g. BPMN file)
      try {
        String tempDirectoryName = FileUtils.getTempDirectory().getAbsolutePath();
        String filename = getResourceFilename(resource, type);
        final File tempFile = new File(tempDirectoryName + File.separator + filename);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
          IOUtils.copy(resource.getInputStream(), out);
        }
        logger.info("  - Now deploying: " + resource);
        deploymentApi.createDeployment(
            null,
            null,
            true, // changedOnly
            true, // duplicateFiltering
            applicationName + "-" + filename, // deploymentName
            null,
            tempFile);
      } catch (Exception exception) {
        logger.error(
            "Error Deploying resources for deployment of type " + type + ": " + resourcesToDeploy);
        if (properties.isFailStartupOnError()) {
          throw exception;
        }
      }
      // deploying the files one by one because of limitation of OpenAPI at the moment
      // see https://github.com/cibseven/cibseven/issues/CAM-13105
    }
  }

  private String getResourceFilename(Resource resource, String type) throws IOException {
    if (resource.getFilename() != null) {
      return resource.getFilename();
    } else {
      return DigestUtils.md5DigestAsHex(resource.getInputStream()) + '.' + type;
    }
  }
}
