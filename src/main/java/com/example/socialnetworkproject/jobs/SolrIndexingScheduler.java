package com.example.socialnetworkproject.jobs;

import com.example.socialnetworkproject.models.entities.Users;
import com.example.socialnetworkproject.repositories.UserRepository;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SolrIndexingScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SolrClient solrClient;

    private final String SOLR_COLLECTION_NAME = "user_collection";

    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void indexUsers() {
        try {
            List<Users> usersList = userRepository.findAll();
            List<SolrInputDocument> documents = new ArrayList<>();

            for (Users user : usersList) {
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", user.getUserId());
                doc.addField("email", user.getEmail());
                doc.addField("userName", user.getUserName());
                doc.addField("firstName", user.getInformation().getFirstName());
                doc.addField("lastName", user.getInformation().getLastName());
                documents.add(doc);
            }

            if (!documents.isEmpty()) {
                solrClient.add(SOLR_COLLECTION_NAME, documents);
                solrClient.commit(SOLR_COLLECTION_NAME);
            }
        }catch (IOException | SolrServerException e) {
            throw new RuntimeException("Failed to index users to Solr: " + e.getMessage());
        }

    }
}
