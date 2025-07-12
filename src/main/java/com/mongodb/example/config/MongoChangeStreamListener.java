package com.mongodb.example.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.example.models.User;
import com.mongodb.example.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.messaging.ChangeStreamRequest;
import org.springframework.data.mongodb.core.messaging.DefaultMessageListenerContainer;
import org.springframework.data.mongodb.core.messaging.Message;
import org.springframework.data.mongodb.core.messaging.MessageListener;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class MongoChangeStreamListener {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ObjectId lastSeenId = new ObjectId(0, 0); // lowest possible

//    @Scheduled(fixedDelay = 10000)
//    public void pollForNewDocs() {
//        System.out.println("Polling DB !!");
//        List<User> newUsers = userRepository.findByIdGreaterThan(lastSeenId);
//
//        for (User user : newUsers) {
//            System.out.println("🆕 New User: " + user.toString());
//            lastSeenId = user.getId(); // advance the pointer
//        }
//    }

    @PostConstruct
    public void initiate() {
        executorService.submit(()->{
            System.out.println("Listening to Mongo Changes !! ");
            // Implement concept of ResumeToken in order to establish watermarking
            // to deal with cases where this process stops and consuming changes
            // right from where it left in the time application was shutdown.
            MongoCollection<Document> collection = mongoTemplate.getCollection("TestCollection");
            MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch().iterator();
            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> change = cursor.next();
                System.out.println("Change detected: " + change.getOperationType());
                System.out.println("Full Document: " + change.getFullDocument());
                // Update Resume token after successful processing of data
            }
        });
    }
}
