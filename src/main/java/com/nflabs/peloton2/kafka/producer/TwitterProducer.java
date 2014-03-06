package com.nflabs.peloton2.kafka.producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

/**
 * Kafka producer which listens for Twitter 1% firehose and send every tweet to Kafka 
 *
 */
public class TwitterProducer {
    private static final Logger logger = LoggerFactory.getLogger(TwitterProducer.class);
    
    /** Information necessary for accessing the Twitter API */
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    
    /** The actual Twitter stream. It's set up to collect raw JSON data */
    private TwitterStream twitterStream;
    
    private void start(final Context context) {

        /** Producer properties **/
        Properties props = new Properties();
        props.put("metadata.broker.list",
                context.getString(TwitterSourceConstant.BROKER_LIST));
        props.put("serializer.class",
                context.getString(TwitterSourceConstant.SERIALIZER));
        props.put("request.required.acks",
                context.getString(TwitterSourceConstant.REQUIRED_ACKS));

        ProducerConfig config = new ProducerConfig(props);
        final Producer<String, String> producer = new Producer<String, String>(config);

        /** Twitter properties **/
        consumerKey = context.getString(TwitterSourceConstant.CONSUMER_KEY_KEY);
        consumerSecret = context.getString(TwitterSourceConstant.CONSUMER_SECRET_KEY);
        accessToken = context.getString(TwitterSourceConstant.ACCESS_TOKEN_KEY);
        accessTokenSecret = context.getString(TwitterSourceConstant.ACCESS_TOKEN_SECRET_KEY);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret)
                .setJSONStoreEnabled(true).setIncludeEntitiesEnabled(true);

        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();


        StatusListener tweetListener = new StatusListener() {

            /**
             * Executed every time a new tweet comes in
             */
            public void onStatus(Status status) {
                //logger.info(status.getUser().getScreenName() + ": " + status.getText());
                String topic = context.getString(TwitterSourceConstant.TOPIC_NAME); 
                KeyedMessage<String, String> data = new KeyedMessage<String, String>(
                        topic , DataObjectFactory.getRawJSON(status));
                producer.send(data);
            }

            public void onDeletionNotice(
                    StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onScrubGeo(long userId, long upToStatusId) {
            }

            public void onException(Exception ex) {
                logger.info("Shutting down Twitter sample stream...");
                twitterStream.shutdown();
            }

            public void onStallWarning(StallWarning warning) {}
        };

        /** Bind the listener **/
        twitterStream.addListener(tweetListener);

        /** GOGOGO **/
        twitterStream.sample();
    }
    
    public static void main(String[] args) {
        try {
            Context context = args.length > 1 ? new Context(args[0]) : new Context("./conf/producer.conf");
            TwitterProducer tp = new TwitterProducer();
            tp.start(context);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
