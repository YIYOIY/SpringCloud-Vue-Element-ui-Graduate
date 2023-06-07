package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.Admin;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 游弋
 * @create 2023-03-31 10:45
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin>{
        @Select("select * from admin where AdminName like '%${keyword}%'")
        public List<Admin> getAll(String keyword) ;

        @Select("select * from admin where AdminId=#{adminId}")
        public Admin getById(int adminId);

        @Select("select * from admin where adminPetName=#{adminPetName} and adminPassword=#{adminPassword}")
        public Admin getByLogin(Admin admin);


        @Insert("insert into admin(adminName,adminPetName,adminPassword) values(#{adminName},#{adminPetName},#{adminPassword})")
        public boolean addAdmin(Admin admin) ;


        @Delete("delete from admin where adminId=#{adminId}")
        public boolean deleteAdmin(Integer adminId);


        @Update("update admin set adminName=#{adminName},adminPetName=#{adminPetName},adminPassword=#{adminPassword} where adminId=#{adminId}")
        public boolean updateAdmin(Admin admin) ;
}
