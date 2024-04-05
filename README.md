# EDC Data space
The purpose of this project is to make preparations for a connector-to-connector communication.
For that we'll set up two connectors.

One connector will be the "consumer" connector while the other will act as the
"provider" connector. 

This project will go through 3 phases:

Phase 1
* Building the connector
* Building the provider and consumer connector container
* Running the provider and consumer connector container
* Registering data plane instance for the provider connector

Phase 2
* Create an asset on the provider (the asset will be the data to be shared)
* Create an access policy on the provider (the policy will define the access right to the data)
* Create a contract definition on the provider

## Phase 1
### 1. Build the connector
To build the connector, execute the following command in project root. This will compile the connector module and generate the necessary artifacts.

```bash
./gradlew connector:build
```
After the build end you should verify that the connector.jar is created in the directory
[/connector/build/libs/connector.jar](connector/build/libs/connector.jar)

We can use the same .jar file for both connectors. Note that the consumer and provider connectors differ in their configuration.

Inspect the different configuration files below:

* [provider-configuration.properties](configuration/connector/provider-configuration.properties)
* [consumer-configuration.properties](configuration/connector/consumer-configuration.properties)


### 2. Building the provider and consumer connector docker images

To build the provider docker image container, just run the following command
```bash
 docker build -f connector/Dockerfile_Provider --build-arg EDC_PARTICIPANT_ID=my_provider -t provider-connector .
``` 

To build the consumer docker image container, just run the following command
```bash
docker build -f connector/Dockerfile_Consumer --build-arg EDC_PARTICIPANT_ID=my_consumer -t consumer-connector .
``` 
### 3. Running the provider and consumer connector docker images

To run the provider docker container, just run the following command
```bash
 docker run -d -p 19191:19191 -p 19192:19192 -p 19193:19193 -p 19194:19194 provider-connector
``` 

To run the consumer docker container, just run the following command
```bash
docker run -d -p 29191:29191 -p 29192:29192 -p 29193:29193 -p 29194:29194 consumer-connector
``` 

### 4. Register data plane instance for provider
Before a consumer can start talking to a provider, it is necessary to register the data plane
instance of a connector. This is done by sending a POST request to the management API of the
provider connector. The [request body](resources/dataplane/register-data-plane-provider.json) should contain the data plane instance of the consumer
connector.

The registration of the provider data plane instance is done by sending a POST
request to the management API of the connector.

In the terminal, execute the following:

```bash
curl -H "Content-Type: application/json" -d @resources/dataplane/register-data-plane-provider.json -X POST "http://localhost:19193/management/v2/dataplanes" -s | jq
```
The connectors have been configured successfully and are ready to be used.

## Phase 2
