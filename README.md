# Spring Cloud Azure Eventhub Demo 

## Prerequisite
* Java 11
* Azure Event Hub 


## Getting Started

* Build
    ```bash
    ./mvn clean install
    ```
* Run
    ```bash
    export EVENTHUB_CONNECTION_STRING=""
    export EVENTHUB_STORAGE_ACCOUNT_NAME=""
    export EVENTHUB_STORAGE_ACCOUNT_KEY=""
    export EVENTHUB_STORAGE_ACCOUNT_CONTAINER_NAME=""
    export EVENTHUB_NAME=""
    export EVENTHUB_CONSUMER_GROUP_NAME=""
    
    # run producer
    export ACTIVE_PROFILE="producer"
    ./mvn spring-boot:run

    # run consumer
    export ACTIVE_PROFILE="consumer"
    ./mvn spring-boot:run
    ```
  
For more info, https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-cloud-stream-binder-java-app-azure-event-hub 