package com.example.socialnetworkproject.solr;

import com.example.socialnetworkproject.models.entities.document.UserDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SolrUserRepository extends SolrCrudRepository<UserDocument, String> {
}
