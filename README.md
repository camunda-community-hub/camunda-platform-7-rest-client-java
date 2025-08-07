# CIB seven Engine OpenAPI REST Client Java and Spring Boot

[![](https://img.shields.io/badge/Community%20Extension-An%20open%20source%20community%20maintained%20project-FF4700)](https://github.com/cibseven-community-hub/community) [![](https://img.shields.io/badge/Lifecycle-Incubating-blue)](https://github.com/CIBseven-Community-Hub/community/blob/main/extension-lifecycle.md#incubating-) ![Compatible with: CIB seven Platform 7](https://img.shields.io/badge/Compatible%20with-CIB%20seven%20Platform%207-26d07c)

This community extension is a convenience wrapper around the generated Java client from the CIB seven Platform 7.x OpenAPI spec.

## Example application

An example application using this community extension in a Spring Boot context can be found here: https://github.com/berndruecker/cibseven-platform-remote-spring-boot-example

## Using the client

The CIB seven endpoint in the `ApiClient` is by default `http://localhost:8080/engine-rest`, if you use a different path then set it through *`setBasePath`* method of the `ApiClient`.

### Plain Java

In a plain Java project you can simply add this dependency (**please make sure to use the latest version as there were a couple of important bug fixes lately**):

```
    <dependency>
      <groupId>org.cibseven.community</groupId>
      <artifactId>cibseven-engine-rest-client-openapi-java</artifactId>
      <version>7.23.0</version>
    </dependency>
```

Now you can use the `ApiClient` and various generated artifacts, for example:

```
 public static void main(String[] args) throws ApiException {
    ApiClient client = new ApiClient();
    client.setBasePath("http://localhost:8080/engine-rest");

    new DeploymentApi(client).createDeployment(
            null,
            null,
            true,
            true,
            "benchmark",
            null,
            new File(App.class.getClassLoader().getResource(bpmnXmlPath).getFile())
    );
    System.out.println("DEPLOYED");

    new ProcessDefinitionApi(client).startProcessInstanceByKey(
            processId,
            new StartProcessInstanceDto()
                    .variables(Collections.singletonMap("json", new VariableValueDto().value("test").type("string"))));

    System.out.println("STARTED");
  }
```

### Spring Boot Starter

For convenience, there is also a Spring Boot Starter, that

* Wires the ApiClient and provide all API's
* Autodeploys all BPMN, DMN and form resources it finds on the classpath during startup.

Add this dependency:

```
    <dependency>
      <groupId>org.cibseven.community</groupId>
      <artifactId>cibseven-engine-rest-client-openapi-springboot</artifactId>
      <version>7.20.0</version>
    </dependency>
```

You can configure the CIB seven *endpoint* via your `application.properties` and initialize the client like this:

```
cibseven.bpm.client.base-url: http://localhost:8080/engine-rest

```

And then simply inject the API, for example:

```
@RestController
public class ExampleRestEndpoint {

    @Autowired
    private ProcessDefinitionApi processDefinitionApi;

    @PutMapping("/start")
    public ResponseEntity<String> startProcess(ServerWebExchange exchange) throws ApiException {
        // ...

        // start process instance
        ProcessInstanceWithVariablesDto processInstance = processDefinitionApi.startProcessInstanceByKey(
                ProcessConstants.PROCESS_KEY,
                new StartProcessInstanceDto().variables(variables));
        // ...
```

#### Auto Deployment

You can auto-deploy resources from your project, like BPMN processes. As default, all `.bpmn`, `.dmn`, and `.form` files are picked up and deployed. The pattern for resource files can be configured:

```
##### Auto-deployment configuration
cibseven.autoDeploy.bpmnResources: 'classpath*:**/*.bpmn'
cibseven.autoDeploy.dmnResources: 'classpath*:**/*.dmn'
cibseven.autoDeploy.formResources: 'classpath*:**/*.form'
```

You can disable auto deployment (which is enabled by default):

```
cibseven.autoDeploy.enabled: false
```

If you want to disable service start failure if it fails during deployment of the resource (which is enabled by default):
```
cibseven.autoDeploy.failStartupOnError: false
```

### Spring Boot OpenAPI + External Task Bundle

Most often you might also want to use the OpenAPI, but also leverage the [CIB seven External Task Client as Spring Boot Starter](https://github.com/cibseven/cibseven-bpm-platform/tree/master/spring-boot-starter/starter-client). To do so you can simply add this convenience library which bundles both:

```
    <dependency>
      <groupId>org.cibseven.community</groupId>
      <artifactId>cibseven-engine-rest-client-complete-springboot-starter</artifactId>
      <version>7.20.0</version>
    </dependency>
```

Now you further simply add workers, for example:

```
@Configuration
@ExternalTaskSubscription("check-number")
public class ExampleCheckNumberWorker implements ExternalTaskHandler {

    private final static Logger LOGGER = Logger.getLogger(ExampleCheckNumberWorker.class.getName());

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
      // ...
      externalTaskService.complete(externalTask);
    }
```
