package com.example.myspring.service.impl;

import com.example.myspring.entity.InternalUser;
import com.example.myspring.entity.OrgManagement;
import com.example.myspring.mapper.InternalUserMapper;
import com.example.myspring.mapper.OrgManagementMapper;
import com.example.myspring.service.OrgManagementService;
import org.springframework.stereotype.Service;

import javax.naming.LimitExceededException;
import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrgManagementServiceImpl implements OrgManagementService {

    private final OrgManagementMapper orgManagementMapper;

    private final InternalUserMapper internalUserMapper;

    public OrgManagementServiceImpl(OrgManagementMapper orgManagementMapper, InternalUserMapper internalUserMapper) {
        this.orgManagementMapper = orgManagementMapper;
        this.internalUserMapper = internalUserMapper;
    }

    @Override
    public List<OrgManagement> getAll() {
        orgManagementMapper.selectList( null);
        List<OrgManagement> orgManagements = orgManagementMapper.selectList( null);
        //        实现一个树形方法 返回
        return buildTree(orgManagements);
    }

    /**
     * 新增
     * @param orgManagement orgManagement
     * @return orgManagement
     */
    @Override
    public OrgManagement add(OrgManagement orgManagement) {
//        从里边取出组织id 从数据库里查询出是否有相同的 orgcode 如果相同就不能添加
        OrgManagement orgManagement1 = orgManagementMapper.selectByOrgcode(orgManagement.getOrgcode());
        System.out.println(orgManagement1);
        if(orgManagement1 != null){
            throw new IllegalArgumentException("组织编码已存在，无法重复添加");
        }
        orgManagementMapper.insert(orgManagement);
        return orgManagement;
    }

    /**
     * 删除
     * @param id id
     */
    @Override
    public void delete(Integer id) {
//        先查询前端传入的id 的菜单
        OrgManagement orgManagement = orgManagementMapper.selectById(id);
        if (orgManagement != null) {
            orgManagementMapper.deleteById(id);
        }
    }

    /**
     * 详情
     * @param id id
     * @return orgManagement
     */
    @Override
    public OrgManagement detail(Integer id) {
        if (id == null) {
            return null;
        }
//      根据id 查询关联的所有用户
        List<InternalUser> internalUsers = internalUserMapper.getUsersByOrganid(id);
        OrgManagement orgManagement = orgManagementMapper.selectById(id);
        orgManagement.setEmployees(internalUsers);
        return orgManagement;
    }

    /**
     * @param orgManagement orgManagement
     * @return orgManagement
     */
    @Override
    public OrgManagement update(OrgManagement orgManagement) {
        Integer organid = orgManagement.getOrganid();
        OrgManagement existing = orgManagementMapper.selectById(organid);
        if (existing == null) {
           throw new IllegalArgumentException("组织不存在");
        }
        orgManagementMapper.updateById(orgManagement);
        return orgManagement;
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
}
