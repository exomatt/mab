package com.exomat.mab.service;

import com.exomat.mab.model.Group;

public interface GroupService {
    class IllegalGroupNameException extends RuntimeException {
        public IllegalGroupNameException(String message) {
            super(message);
        }
    }

    Group save(Group group);
    Group findGroup(String name);

}
