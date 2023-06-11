package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entity.User;
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
public interface UserMapper extends BaseMapper<User> {
      public User getById(Integer userId);

//    @Select("select * from user where userPetName=#{userPetName} and userPassword=#{userPassword}")
//    public User getByLogin(User user);

//    @Select("select count(*) from user u,bag b where b.userId=u.userId and u.userId=#{userId}")
//    public Long checkBagData(Integer UserId);

//    @Select("select * from user where userName like '%${keyword}%'")
//    public List<User> getAll(String keyword);

//    @Insert("insert into user(userName,userPetName,userPassword,userTel,userAddress) values(#{userName},#{userPetName},#{userPassword},#{userTel},#{userAddress})")
//    public boolean addUser(User user);

//    @Delete("delete from user where userId=#{userId}")
//    public boolean deleteUser(Integer userId);

//    @Update("update user set userName=#{userName},userPetName=#{userPetName},userPassword=#{userPassword},userTel=#{userTel},userAddress=#{userAddress} where userId=#{userId}")
//    public boolean updateUser(User user);
}
