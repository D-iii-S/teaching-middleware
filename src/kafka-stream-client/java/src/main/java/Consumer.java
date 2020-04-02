import java.time.Duration;
import java.util.Properties;
import java.util.Collections;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class Consumer {
    public static void main (String [] args) {
        try {
            Properties config = new Properties ();
            config.put (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Shared.KAFKA_BOOTSTRAP_ADDRESS);
            config.put (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName ());
            config.put (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName ());
            config.put (ConsumerConfig.GROUP_ID_CONFIG, "Consumer");
            KafkaConsumer <Integer, String> consumer = new KafkaConsumer <> (config);

            consumer.subscribe (Collections.singletonList (Shared.KAFKA_CONSUMER_TOPIC));

            while (true) {
                ConsumerRecords <Integer, String> records = consumer.poll (Duration.ofDays (365));
                for (ConsumerRecord <Integer, String> record : records) {
                    System.out.println (record.value ());
                }
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
