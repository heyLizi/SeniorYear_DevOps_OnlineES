package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.TeacherDto;
import entity.Teacher;
import org.beetl.sql.core.annotatoin.SqlStatement;

public interface TeacherDao extends $Mapper<Teacher> {

    /**
     * 老师登录
     * @param name
     * @param password
     * @return
     */
    @SqlStatement(params = "name,password")
    public TeacherDto loginForTeacher(String name, String password);
}
