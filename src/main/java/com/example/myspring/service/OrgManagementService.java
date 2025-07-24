package com.example.myspring.service;

import com.example.myspring.entity.OrgManagement;

import java.util.List;

public interface OrgManagementService {
    List<OrgManagement> getAll();

    OrgManagement add(OrgManagement orgManagement);

    void delete(Integer id);
}
