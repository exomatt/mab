package com.exomat.mab.service;

import com.exomat.mab.model.Group;
import com.exomat.mab.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group findGroup(String name) {
        return groupRepository.findByName(name).orElseThrow(() -> new IllegalGroupNameException("Cannot found group with that name"));
    }
}
