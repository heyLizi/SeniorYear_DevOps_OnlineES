package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.CourseDto;
import entity.Course;
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

public interface CourseDao extends $Mapper<Course> {

    /**
     * 查询课程总数
     * @param tid
     * @return
     */
    @SqlStatement(params = "tid")
    public int queryCourseCount(String tid);


    /**
     * 分页查询课程
     * @param tid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CourseDto> queryCourseList(@Param("tid") String tid, @RowStart int pageIndex, @RowSize int pageSize);

    /**
     * 查询课程信息
     * @param tid
     * @param cid
     * @return
     */
    public CourseDto queryCourseDetail(@Param("tid") String tid, @Param("cid") Integer cid);
}
