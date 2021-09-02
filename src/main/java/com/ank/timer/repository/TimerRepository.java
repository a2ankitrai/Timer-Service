package com.ank.timer.repository;

import com.ank.timer.model.Timer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TimerRepository extends MongoRepository<Timer, ObjectId> {

    Optional<Timer> findTimerById(ObjectId id);
}
