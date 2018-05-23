package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.StudentDto;
import entity.Student;
import org.beetl.sql.core.annotatoin.SqlStatement;

/**
 * Created by mengf on 2017/12/11 0011.
 */
public interface StudentDao extends $Mapper<Student> {

    @SqlStatement(params = "mail")
    Integer countByMailBox(String mail);
    @SqlStatement(params = "number")
    Integer countByNumber(String number);
    @SqlStatement(params = "sid")
    StudentDto getUserDto(Long sid);
}
