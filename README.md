# EDC Data space
The purpose of this project is to make preparations for a connector-to-connector communication.
For that we'll set up two connectors.

One connector will be the "consumer" connector while the other will act as the
"provider" connector. 

This project will go through 3 phases:

Phase 1 
* Building the connector module
* Running the provider connector
* Running the consumer connector
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


### 2. Run the connectors

To run the provider, just run the following command
```bash
java "-Dedc.keystore=resources/certs/cert.pfx" "-Dedc.keystore.password=123456" "-Dedc.vault=configuration/connector/provider-vault.properties" "-Dedc.fs.config=configuration/connector/provider-configuration.properties" -jar connector/build/libs/connector.jar
``` 

To run the consumer, just run the following command (different terminal)
```bash
java "-Dedc.keystore=resources/certs/cert.pfx" "-Dedc.keystore.password=123456" "-Dedc.vault=configuration/connector/consumer-vault.properties" "-Dedc.fs.config=configuration/connector/consumer-configuration.properties" -jar connector/build/libs/connector.jar
``` 
### 3. Register data plane instance for provider
Before a consumer can start talking to a provider, it is necessary to register the data plane
instance of a connector. This is done by sending a POST request to the management API of the
provider connector. The [request body](resources/dataplane/register-data-plane-provider.json) should contain the data plane instance of the consumer
connector.

The registration of the provider data plane instance is done by sending a POST
request to the management API of the connector.

Open a new terminal and execute:

```bash
curl -H "Content-Type: application/json" -d @resources/dataplane/register-data-plane-provider.json -X POST "http://localhost:19193/management/v2/dataplanes" -s | jq
```
The connectors have been configured successfully and are ready to be used.

## Phase 2
