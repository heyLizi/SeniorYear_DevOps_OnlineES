package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.CourseDto;
import dto.StudentBriefDto;
import entity.Course;
import entity.Student;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.RowSize;
import org.beetl.sql.core.annotatoin.RowStart;
import org.beetl.sql.core.annotatoin.SqlStatement;

import java.util.List;

/**
 * 类说明：课程Dao
 * 创建者：zs
 * 包名：cn.edu.nju.software.dao
 */

public interface StudentDao extends $Mapper<Student> {

    /**
     * 查询导入学生
     * @param emails
     * @return
     */
    public List<StudentBriefDto> queryStudentList(@Param("emails")String[] emails);
}
