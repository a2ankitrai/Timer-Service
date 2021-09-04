package com.ank.timer.repository;

import com.ank.timer.model.Timer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimerRepository extends MongoRepository<Timer, ObjectId> {

    Optional<Timer> findTimerById(ObjectId id);

    default Optional<Timer> findTimerById(String id) {
        return findTimerById(new ObjectId(id));
    }
}
