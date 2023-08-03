package com.yoi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoi.entity.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin>{
        @Select("select * from t_admin where admin_name like '%${keyword}%'")
        public List<Admin> getAll(String keyword) ;

        @Select("select * from t_admin where admin_id=#{adminId}")
        public Admin getById(int adminId);

        @Select("select * from t_admin where admin_name=#{adminName} and admin_Password=#{adminPassword}")
        public Admin getByLogin(Admin admin);


        @Insert("insert into t_admin(admin_name,admin_password) values(#{adminName},#{adminPassword})")
        public boolean addAdmin(Admin admin) ;


        @Delete("delete from t_admin where admin_id=#{adminId}")
        public boolean deleteAdmin(Integer adminId);


        @Update("update t_admin set admin_name=#{adminName},admin_password=#{adminPassword} where admin_id=#{adminId}")
        public boolean updateAdmin(Admin admin) ;
}
