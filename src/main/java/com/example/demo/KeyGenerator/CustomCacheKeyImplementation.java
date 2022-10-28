package com.example.demo.KeyGenerator;

import java.io.Serializable;

public class CustomCacheKeyImplementation implements Serializable {
    private final Object id;
    private final String entityOrRoleName;

    public CustomCacheKeyImplementation(Object id, String entityOrRoleName) {
        this.id = id;
        this.entityOrRoleName = entityOrRoleName;
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

            return true;
    }

    public Object getId() {
        return id;
    }

    public String toString() {
        // Used to be required for OSCache
        return entityOrRoleName + '#' + id.toString();
    }

}
