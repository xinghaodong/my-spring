package com.example.myspring.service.impl;

import com.example.myspring.entity.Menu;
import com.example.myspring.entity.OrgManagement;
import com.example.myspring.entity.Role;
import com.example.myspring.mapper.OrgManagementMapper;
import com.example.myspring.service.OrgManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrgManagementServiceImpl implements OrgManagementService {

    @Autowired
    private OrgManagementMapper orgManagementMapper;

    @Override
    public List<OrgManagement> getAll() {
        orgManagementMapper.selectList( null);
        List<OrgManagement> orgManagements = orgManagementMapper.selectList( null);
        //        实现一个树形方法 返回
        return buildTree(orgManagements);
    }


    private List<OrgManagement> buildTree(List<OrgManagement> orgManagements) {
        // 1. 创建一个 Map，用于快速通过 id 查找菜单
        Map<Integer, OrgManagement> orgManagementMap = new HashMap<>();
        System.out.println(orgManagements);
        orgManagements.forEach(orgManagement -> orgManagementMap.put(orgManagement.getOrganid(), orgManagement));
        // 2. 构建最终的树形结构
        List<OrgManagement> tree = new ArrayList<>();
        orgManagements.forEach(orgManagement -> {
            Integer parentId = orgManagement.getParentId();
            if (parentId == null || parentId == 0) {
                // 如果 parentId 为空或 0，说明是根节点
                tree.add(orgManagement);
            } else {
                // 否则查找父节点并加入其 children 列表
                OrgManagement parent = orgManagementMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(orgManagement);
                }
            }
        });
        return tree;
    }


//    @Override
//    public List<Role> getAll() {
//        return roleMapper.selectList(null);
//    }
}
