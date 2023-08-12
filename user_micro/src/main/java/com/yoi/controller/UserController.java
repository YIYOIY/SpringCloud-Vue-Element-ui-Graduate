package com.yoi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.UserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Validated
@RestController
@RefreshScope
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 为管理员提供的方法，用来查看以及管理用户列表
     *
     * @param adminId    操作的管理员的Id
     * @param pageNo     页面
     * @param pageSize   页面显示数量
     * @param searchName 搜索名称
     * @return 返回用户列表
     */
    @GetMapping("/user/{adminId}/{pageNo}/{pageSize}/{searchName}")
    public ReturnInfo<PagePackage<User>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                               @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                               @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                               @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName) {
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        if (searchName.equals("null")) {
            searchName = "";
        }
//          返回分页结果
        Page<User> all = userService.getAll(searchName, pageNo, pageSize);
        if (!all.getRecords().isEmpty()) {
            op.set(adminId.toString(), searchName + LocalDateTime.now());
//          包装分页数据
            PagePackage<User> userPagePackage = new PagePackage<>(all.getRecords(), all.getCurrent(),
                    all.getPages(), all.getSize(), all.getTotal(), all.hasNext(), all.hasPrevious());
//          返回前端统一返回格式
            return new ReturnInfo<>(200, "用户信息获取成功", userPagePackage);
        } else {
            return new ReturnInfo<>(404, "用户信息为空");
        }
    }

    /**
     * 用户个人信息页面的api，同时是管理员修改用户和用户自我修改信息的api
     *
     * @param userId 用户id
     */
    @GetMapping("/user/{userId}")
    public ReturnInfo<User> userSelf(@NotNull @PathVariable("userId") Long userId) {
        User byId = userService.getById(userId);
        if (byId != null) {
            return ReturnInfo.withEnumData(ReturnEnum.GET_SUCCESS, byId);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.GET_FAILED);
        }
    }

    /**
     * 添加用户
     *
     * @param user 将要增加的用户信息
     * @return 返回增加操作结果
     */
    @PostMapping("/add_user")
    public ReturnInfo<String> addUser(@RequestBody User user) {
        if (userService.addUser(user)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.NAME_FAILED);
        }
    }

    /**
     * 删除用户
     *
     * @param user 获取用户Id
     * @return 返回删除操作结果
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    @DeleteMapping("/user")
    public ReturnInfo<User> deleteUser(@Valid @RequestBody User user) {
        if (userService.deleteUser(user)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.DELETE_FAILED);
        }

    }

    /**
     * 修改用户
     *
     * @param user 用户信息
     * @return 返回更新操作结果
     */
    @PutMapping("/user")
    public ReturnInfo<User> updateUser(@Valid @RequestBody User user) {
        if (userService.updateUser(user)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.ALTER_FAILED);
        }
    }
    @GlobalTransactional(rollbackFor = Exception.class)
    @PutMapping("/user_money")
    public ReturnInfo<User> updateUserMoney(@Valid @RequestBody User user) {
        if (userService.updateUserMoney(user)) {
            return new ReturnInfo<>(200,"充值成功！");
        } else {
            return new ReturnInfo<>(404,"充值失败！");
        }
    }

}
