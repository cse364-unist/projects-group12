package com.group12.rest2night.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    private List<Integer> wl;
    
    public List<Integer> getWl() {
        return this.wl;
    }
}
