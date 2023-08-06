package com.yoi.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoi.entity.Admin;
import com.yoi.entity.PagePackage;
import com.yoi.entity.ReturnInfo;
import com.yoi.enumvalue.ReturnEnum;
import com.yoi.service.AdminService;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RefreshScope
@Validated
public class AdminController {

    @Resource
    private AdminService adminService;
    @Resource
    StringRedisTemplate stringRedisTemplate;
//    private final static ObjectMapper object = new ObjectMapper();

    /**
     * @param adminId    管理员ID
     * @param pageNo     页数
     * @param pageSize   页面显示数量
     * @param searchName 查询关键字
     * @param operate    操作名
     * @return 返回包装类
     */
    @CrossOrigin
    @GetMapping("/admin/{adminId}/{pageNo}/{pageSize}/{searchName}/{operate}")
    public ReturnInfo<PagePackage<Admin>> index(@NotNull @PathVariable(value = "adminId") Long adminId,
                                                @NotNull @PathVariable(value = "pageNo") Integer pageNo,
                                                @NotNull @PathVariable(value = "pageSize") Integer pageSize,
                                                @Length(max = 100) @PathVariable(value = "searchName", required = false) String searchName,
                                                @Length(max = 100) @PathVariable(value = "operate", required = false) String operate) {
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();

            /*判断管理员是否进行了查询操作，没查询走else分支，查询操作如果传入的查询名有效会记录在redis中，
            管理员如果想在查询完后查询所有数据，应该在查询输入框内清除输入数据重新查询，可以在点击查询后出现一个查询所有数据的按钮，点击后自动查询*/
        if (StringUtils.isNotEmpty(operate) && operate.equals("getByName")) {
            if (StringUtils.isEmpty(searchName)) {
                searchName = "";
            }
            op.set(adminId + "searchAdmin", searchName);
        } else {
            /*管理员未进行查询，或者已经查询过，点击翻页后，进入此操作， 如果管理员查询过数据将把查询的关键字继续使用查询下一页*/
            String keyword = op.get(adminId + "searchAdmin");
            if (StringUtils.isNotEmpty(keyword)) {
                searchName = keyword;
            } else {
                searchName = "";
            }
        }
//          返回分页结果
        Page<Admin> all = adminService.getAll(searchName, pageNo, pageSize);
        if (!all.getRecords().isEmpty()){
            //          包装分页数据
            PagePackage<Admin> adminPagePackage = new PagePackage<>(all.getRecords(), all.getCurrent(),
                    all.getPages(), all.getSize(), all.getTotal(), all.hasNext(), all.hasPrevious());
//          返回前端统一返回格式
            return new ReturnInfo<>(200, "用户信息获取成功", adminPagePackage);
        }else {
            return new ReturnInfo<>(404, "用户信息为空");
        }
    }

    @PostMapping("/add_admin")
    public ReturnInfo<Admin> addAdmin(@RequestBody Admin admin) {
        if (adminService.addAdmin(admin)) {
            return ReturnInfo.withEnumNoData(ReturnEnum.ADD_SUCCESS);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.NAME_FAILED);
        }
    }


    @DeleteMapping("/admin")
    public ReturnInfo<Admin> deleteAdmin(@RequestBody @Valid Admin admin) {
        if (adminService.deleteAdmin(admin.getId())) {
            return  ReturnInfo.withEnumNoData(ReturnEnum.DELETE_SUCCESS);
        } else {
            return  ReturnInfo.withEnumNoData(ReturnEnum.DELETE_FAILED);
        }
    }


    @GetMapping("/admin/{adminId}")
    public ReturnInfo<Admin> alterAdmin(@NotNull @PathVariable("adminId") Long adminId) {
        Admin byAdminId = adminService.getByAdminId(adminId);
        if ( byAdminId!= null) {
            return  ReturnInfo.withEnumData(ReturnEnum.GET_SUCCESS,byAdminId);
        } else {
            return ReturnInfo.withEnumNoData(ReturnEnum.GET_FAILED);
        }
    }

    @PutMapping("/admin")
    public ReturnInfo<Admin> updateAdmin(@RequestBody @Valid Admin admin) {
        if (adminService.updateAdmin(admin)) {
            return   ReturnInfo.withEnumNoData(ReturnEnum.ALTER_SUCCESS);
        } else {
            return   ReturnInfo.withEnumNoData(ReturnEnum.ALTER_FAILED);
        }
    }

}
