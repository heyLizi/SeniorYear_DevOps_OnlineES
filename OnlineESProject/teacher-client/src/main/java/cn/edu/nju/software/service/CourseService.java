package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.CourseDao;
import dto.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 类说明：课程Service
 * 创建者：zs
 * 创建时间：2017/12/6 下午11:58
 * 包名：cn.edu.nju.software.service
 */

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    /**
     * 查询课程总数
     * @return
     * @param tid
     */
    public int queryCourseCount(String tid) {
        return courseDao.queryCourseCount(tid);
    }

    /**
     * 分页查询课程
     * @param tid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<CourseDto> queryCourseList(String tid, int pageIndex, int pageSize) {
        int pageStart = pageIndex*pageSize+1;
        return courseDao.queryCourseList(tid,pageStart,pageSize);
    }

    /**
     * 查询课程信息
     * @param tid
     * @param cid
     * @return
     */
    public CourseDto queryCourseDetail(String tid, Integer cid) {
        return courseDao.queryCourseDetail(tid, cid);
    }
}
