package com.yoi.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.entity.User;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

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

            /*判断管理员是否进行了查询操作，没查询走else分支，查询操作如果传入的查询名有效会记录在redis中，
            管理员如果想在查询完后查询所有数据，应该在查询输入框内清除输入数据重新查询，可以在点击查询后出现一个查询所有数据的按钮，点击后自动查询*/

        if (searchName.equals("null")) {
            /*管理员未进行查询，或者已经查询过，点击翻页后，进入此操作， 如果管理员查询过数据将把查询的关键字继续使用查询下一页*/
            String keyword = op.get(adminId + "searchUser");
            if (StringUtils.isNotEmpty(keyword)) {
                searchName = keyword;
            } else {
                searchName = "";
            }
        }else {
            op.set(adminId + "searchUser",searchName,3, TimeUnit.MINUTES);
            if (searchName.equals("all")){
                searchName="";
                stringRedisTemplate.delete(adminId + "searchUser");
            }
        }

//          返回分页结果
        Page<User> all = userService.getAll(searchName, pageNo, pageSize);
        if (!all.getRecords().isEmpty()) {
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

}
