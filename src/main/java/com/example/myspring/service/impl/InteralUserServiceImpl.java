package com.example.myspring.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myspring.entity.InternalUser;
import com.example.myspring.entity.Menu;
import com.example.myspring.entity.Role;
import com.example.myspring.fileList.entity.FileListEntity;
import com.example.myspring.fileList.mapper.FileListMapper;
import com.example.myspring.fileList.service.FileListService;
import com.example.myspring.mapper.InternalUserMapper;
import com.example.myspring.mapper.MenuMapper;
import com.example.myspring.mapper.RoleMapper;
import com.example.myspring.service.InternalUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InteralUserServiceImpl implements InternalUserService {

    private final InternalUserMapper internalUserMapper;

    private final RoleMapper roleMapper;
//    注入附件表
    private final FileListMapper fileListMapper;

    private final FileListService fileListService;

    private MenuMapper menuMapper;

    public InteralUserServiceImpl(InternalUserMapper internalUserMapper, RoleMapper roleMapper, FileListMapper fileListMapper, FileListService fileListService) {
        this.internalUserMapper = internalUserMapper;
        this.roleMapper = roleMapper;
        this.fileListMapper = fileListMapper;
        this.fileListService = fileListService;
    }

    @Override
    public List<InternalUser> getAll() {
        return internalUserMapper.selectList(null);
    }

    @Override
    public Map<String, Object> getUserPage(int page, int pageSize) {
        Page<InternalUser> userPage = new Page<>(page, pageSize);
        internalUserMapper.selectPage(userPage, null); // 无条件分页
        Map<String, Object> result = new HashMap<>();
        // 便利 result 根据用户id 查询对应的附件表
        for (InternalUser user : userPage.getRecords()) {
//            List<Role> roleIds = roleMapper.findRolesByUserId(user.getId());
//            List<FileListEntity> fileList = fileListMapper.selectByUserId(user.getId());
//            System.out.println("用户附件：" + fileList);
//            调用附件的服务更具id 查询用户附件
//            Map<String, Object> fileList = (Map<String, Object>) fileListService.queryById(user.getId())
            System.out.println("用户附件：" + user);
//            这里以直接调用 FileListServiceImpl，但 不推荐。
//            使用接口（FileListService）而不是实现类，是为了 解耦、可维护、可扩展、易测试。
            FileListEntity fileList = fileListService.queryById(user.getAvatarId());
            user.setAvatar(fileList);
        }
        result.put("data", userPage.getRecords());
        result.put("total", userPage.getTotal());
        return result;
    }

    /**
     * 根据用户名查询用户信息
     */
    @Override
    public InternalUser getByUsername(String username) {
        System.out.println("查询的用户名：" + username);
        return internalUserMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public InternalUser getById(Integer id) {
//        通过过来的id 查询用户信息
        InternalUser internalUser = internalUserMapper.selectById(id);
        if (internalUser != null) {
//            找下用户头像
            FileListEntity fileList = fileListService.queryById(internalUser.getAvatarId());
            internalUser.setAvatar(fileList);
            List<Role> roleIds = roleMapper.findRolesByUserId(internalUser.getId());
//          便利 roleIds 取出角色id 放入 internalUser.roleIds 中，这里需要和 nestjs 端对应
            List<Integer> Ids = new ArrayList<>();
            for (Role role : roleIds) {
                Ids.add(role.getId());
                internalUser.setRoleIds(Ids);
            }
            return internalUser;
        }
        return null;
    }


    /**
     * 修改用户
     * @param internalUser internalUser
     * @return internalUser
     */
    @Override
    @Transactional
    public InternalUser updateUser(InternalUser internalUser) throws IOException {
        // 先获取是否存在
        InternalUser oldUser = internalUserMapper.selectById(internalUser.getId());
        if (oldUser == null) {
            return null;
        }
        // 再获取之前的用户关联的角色
        List<Role> oldRoleIds = roleMapper.findRolesByUserId(oldUser.getId());
        // 删除掉旧的关联角色
        // 判断如果之前不存在关联就不需要调用删除了
        if (oldRoleIds != null && !oldRoleIds.isEmpty()) {
            internalUserMapper.deleteRolesByUserId(internalUser.getId());
        }
        // 再获取传来的用户关联的角色
        List<Integer> newRoleIds = internalUser.getRoleIds();
        for (Integer roleId : newRoleIds) {
        // 设置新的关联角色
            internalUserMapper.addRole(internalUser.getId(), roleId);
        }
        // 处理头像更新
        Integer newAvatarId = null;

        // 从传入的 avatar 对象中获取新的头像ID
        if (internalUser.getAvatar() != null) {
            if (internalUser.getAvatar() instanceof LinkedHashMap) {
                LinkedHashMap<String, Object> avatarMap = (LinkedHashMap<String, Object>) internalUser.getAvatar();
                newAvatarId = (Integer) avatarMap.get("id");
                internalUser.setAvatarId(newAvatarId);
            } else if (internalUser.getAvatar() instanceof Integer) {
                newAvatarId = (Integer) internalUser.getAvatar();
                internalUser.setAvatarId(newAvatarId);
            }
        }

        // 删除旧的头像物理文件（如果头像被更换了）
        if (oldUser.getAvatarId() != null &&
                !oldUser.getAvatarId().equals(newAvatarId)) {

            FileListEntity oldAvatar = fileListService.queryById(oldUser.getAvatarId());
            if (oldAvatar != null) {
                System.out.println("准备删除旧头像物理文件，ID: " + oldAvatar.getId());

                // 只删除物理文件，保留数据库记录
//                deletePhysicalFile(oldAvatar);
                System.out.println("旧头像物理文件已删除");
            }
        }

        // 设置更新时间
        internalUser.setUpdatedAt(LocalDateTime.now());
        internalUser.setAvatar(null);
        // 最后更新用户自身信息
        internalUser.setUpdatedAt(LocalDateTime.now());
        internalUserMapper.updateById(internalUser);
            return internalUser;
    }

    /**
     * 删除用户
     * @param id id
     */
    @Override
    @Transactional
    public void deleteUser(Integer id) {
     InternalUser internalUser = internalUserMapper.selectById(id);

     System.out.println("删除的用户：" + internalUser);
     if (internalUser != null && internalUser.getId() != 1){
//         先删除用户关联的角色 这里由于数据库是级联删除，所以这里不用写
//         internalUserMapper.deleteRolesByUserId( id);
//         再删除用户自身
         internalUserMapper.deleteById(id);
     }
        //  判断用户是否有头像 有的话删除关联的附件  这里不能先删除附件，因为附件表有外键关联，会报错
        if (internalUser != null && internalUser.getAvatarId() != null) {
            FileListEntity fileList = fileListService.queryById(internalUser.getAvatarId());
            System.out.println("用户附件：" + fileList);
            // 检查删除结果
            int deleteResult = fileListMapper.deleteById(fileList.getId());
            System.out.println("附件删除结果：" + deleteResult); // 1表示删除成功，0表示删除失败
        }
    }


    /**
     * 添加用户
     * @param internalUser internalUser
     * @return internalUser
     */
    @Override
    @Transactional
    public InternalUser add(InternalUser internalUser) {
        System.out.println("添加的用户信息：" + internalUser);
//       先判断 username 是否存在
        InternalUser oldUser = internalUserMapper.selectByUsername(internalUser.getUsername());
        if (oldUser != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        // 先取roleIds数组集合
        List<Integer> roleIds = internalUser.getRoleIds();
//        设置创建时间
        internalUser.setCreatedAt(LocalDateTime.now());
//        暂时先设置一个密码
        internalUser.setPassword("888888");
        //  头像
        System.out.println("用户头像：" + internalUser.getAvatar());
        // 直接从 LinkedHashMap 中获取 id
        LinkedHashMap<String, Object> avatarMap = (LinkedHashMap<String, Object>) internalUser.getAvatar();
        System.out.println("用户头像ID：" + avatarMap);
        internalUser.setAvatarId((Integer) avatarMap.get("id"));
        // 先插入用户，让数据库生成自增ID
        internalUserMapper.insert(internalUser);
        System.out.println("插入后的用户ID：" + internalUser.getId());
        // 然后使用生成的ID添加角色关联
        if (roleIds != null && !roleIds.isEmpty()) {
            for (Integer roleId : roleIds) {
                System.out.println("添加的用户ID：" + internalUser.getId());
                System.out.println("添加的角色ID：" + roleId);
                internalUserMapper.addRole(internalUser.getId(), roleId);
            }
        }
        return internalUser;
    }


    /**
     * 登录
     * @param username username
     * @param password password
     * @return Map 用户的信息、token、按钮权限
     */
    @Override
    public Map<String, Object> login(String username, String password) {
        InternalUser internalUser = getByUsername(username);
        System.out.println("登录的用户：" + internalUser);
//        查出用户头像
        FileListEntity fileListEntity = fileListService.queryById(internalUser.getAvatarId());
        internalUser.setAvatar(fileListEntity);
        Map<String, Object> result = new HashMap<>();
        result.put("informationObject", internalUser);

//        这里先暂时写死后续在搞jtw 双token
        result.put("token", "123455");
//        再获取用户所属的角色
        List<Role> roleList = menuMapper.queryRoleIdsByMenuId(internalUser.getId());
        System.out.println( "查询到的数据：" + roleList );
//        再根据角色list便利查询菜单
        // 收集所有菜单（去重）
        Set<Menu> menuSet = new HashSet<>();
//
        System.out.println( "查询到的数据：" + roleList);

//        判断 如果roleList包含了id是1的数据就证明他是超管 可以查询所有菜单
        boolean isSuperAdmin = roleList.stream().anyMatch(role -> role.getId() != null && role.getId() == 1);

        if(isSuperAdmin ){
//          超管 查所有
            menuSet.addAll(menuMapper.selectList( null)) ;
        }else {
            for ( Role role : roleList ){
                List<Menu> menuList = menuMapper.findMenusByRoleId(role.getId());
                menuSet.addAll(menuList);
            }
        }
        System.out.println( "查询到的菜单数据：" + menuSet);

//        List<Role> roles = roleMapper.findRolesByUserId(internalUser.getId());
//        System.out.println("用户角色ID：" + roles);
//       便利 menuSet菜单集合 判断里边的权限字段 perms 有值的话就 创建一个 perms 集合放到这里
        List<String> perms = new ArrayList<>();
        for (Menu menu : menuSet) {
            if (menu.getPerms() != null && !menu.getPerms().isEmpty()) {
                perms.add(menu.getPerms());
            }
        }
        System.out.println("用户权限：" + perms);
        result.put("perms", perms);

//        List<Menu> menus = RoleMapper.findMenusByRoleId();

        return result;
//        更具用户名查询用户
//        这里需要把 internalUser  对象都包装在一个对象里informationObject里
//        InternalUser internalUser = getByUsername(username);
//        return internalUser;
    }
}
