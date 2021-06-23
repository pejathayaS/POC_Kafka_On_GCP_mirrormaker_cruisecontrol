# POC_Kafka_On_GCP_mirrormaker_cruisecontrol
As part of the POC and learning exercise I have done  this project and hosted in GCP environment. A detailed document explaining the steps and the tradeoff is attached to the repository.

1. Install Kafka Cluster with Zookeeper enabled - Write a Rest API to publish and Consumer the messages.
2. Have a Kafka cluster on both the region / Datacentre and configure Mirror making (Open source)
3. Explore Cruise control – open source plug-in and develop a POC for your Kafka clusters . .Write a API using Kafka API to Delete the messages within the give Kafka and Delete the topic
4. Also I have put across my thoughts on How we can build a abstraction layer on top of Event processing and user will have only 2 API’s - Consumer or Producer API, user should not aware what we were using in the back-ground, today it might be Kafka and in future it might be something else . Abstraction with user having no impact. 
