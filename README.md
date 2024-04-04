#to build 
./gradlew connector:build

# start a provider
java "-Dedc.keystore=resources/certs/cert.pfx" "-Dedc.keystore.password=123456" "-Dedc.vault=configuration/connector/provider-vault.properties" "-Dedc.fs.config=configuration/connector/provider-configuration.properties" -jar connector/build/libs/connector.jar
# start a consumer
java "-Dedc.keystore=resources/certs/cert.pfx" "-Dedc.keystore.password=123456" "-Dedc.vault=configuration/connector/consumer-vault.properties" "-Dedc.fs.config=configuration/connector/consumer-configuration.properties" -jar connector/build/libs/connector.jar

curl -H "Content-Type: application/json" -d @resources/dataplane/register-data-plane-provider.json -X POST "http://localhost:19193/management/v2/dataplanes" -s | jq

