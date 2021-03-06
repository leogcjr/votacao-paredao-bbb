package com.globo.votacaoparedao.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "paredao")
public class Paredao {

    @Id
    private ObjectId id;

    private String name;

    public Paredao(String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paredao paredao = (Paredao) o;
        return Objects.equals(id, paredao.id) &&
                Objects.equals(name, paredao.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
