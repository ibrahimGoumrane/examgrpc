package org.example.mediaserver.repository;

import org.example.mediaserver.dao.Creator;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CreatorRepository {
    private final Map<String, Creator> creators = new ConcurrentHashMap<>();

    public Creator save(Creator creator) {
        // Check if creator already exists
        if (creators.containsKey(creator.getId())) {
            return creator;
        }
        creators.put(creator.getId(), creator);
        return creator;
    }

    public Creator findById(String id) {
        // Check if creator exists
        if (!creators.containsKey(id)) {
            return null;
        }
        return creators.get(id);
    }

}