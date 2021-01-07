package com.globo.votacaoparedao.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.globo.votacaoparedao.entity.Paredao;

@Repository
public interface ParedaoRepository extends MongoRepository<Paredao, ObjectId> {
}
