package com.exomat.mab.service;

import com.exomat.mab.model.Group;

import java.util.List;

public interface GroupService {
    class IllegalGroupNameException extends RuntimeException {
        public IllegalGroupNameException(String message) {
            super(message);
        }
    }

    Group save(Group group);

    Group findGroup(String name);

    Group findById(Long id);

    List<Group> findAll();

}
