Kafka Twitter Producer
======================

Consume the Twitter Streaming API and send all messages to a Kafka broker. Use Twitter4j.

Configuration
-------------

Set up your Twitter API credentials and your Kafka brokers in ``conf/producer.conf``


Execution
-------------
just run ``gradlew run -Pargs="PATH_TO/producer.conf"``
 

-------

For those who are new to Kafka on OSX following this guide to install
kafka on your OSX machine. https://dtflaneur.wordpress.com/2015/10/05/installing-kafka-on-mac-osx/

Before you run the above test program do the following:

1. Run kafka server on your machine as follows:
   kafka-server-start.sh /usr/local/etc/kafka/server.properties

2. Start simple consumer as follows:
   kafka-console-consumer.sh --zookeeper localhost:2181 --topic twitterlive

Now execute the test program and your consumer window should display all the tweets.
