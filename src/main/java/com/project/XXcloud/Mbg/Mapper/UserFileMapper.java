package com.project.XXcloud.Mbg.Mapper;

import com.project.XXcloud.Mbg.Model.UserFile;
import com.project.XXcloud.Mbg.Model.UserFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserFileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int countByExample(UserFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int deleteByExample(UserFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int deleteByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int insert(UserFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int insertSelective(UserFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    List<UserFile> selectByExample(UserFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    UserFile selectByPrimaryKey(Integer fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int updateByExampleSelective(@Param("record") UserFile record, @Param("example") UserFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int updateByExample(@Param("record") UserFile record, @Param("example") UserFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int updateByPrimaryKeySelective(UserFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_file
     *
     * @mbggenerated Wed Jan 13 11:01:03 CST 2021
     */
    int updateByPrimaryKey(UserFile record);
}