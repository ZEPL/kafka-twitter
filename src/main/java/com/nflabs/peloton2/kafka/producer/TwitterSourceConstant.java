package com.nflabs.peloton2.kafka.producer;

public class TwitterSourceConstant {
	public static final String CONSUMER_KEY_KEY = "consumerKey";
	public static final String CONSUMER_SECRET_KEY = "consumerSecret";
	public static final String ACCESS_TOKEN_KEY = "accessToken";
	public static final String ACCESS_TOKEN_SECRET_KEY = "accessTokenSecret";

	public static final String BATCH_SIZE_KEY = "batchSize";
	public static final long DEFAULT_BATCH_SIZE = 1000L;
	public static final String KEYWORDS_KEY = "keywords";

	public static final String BROKER_LIST = "broker.list";
	public static final String SERIALIZER = "serializer.class";
	public static final String REQUIRED_ACKS = "request.required.acks";
	public static final String TOPIC_NAME = "topic.name";

}
