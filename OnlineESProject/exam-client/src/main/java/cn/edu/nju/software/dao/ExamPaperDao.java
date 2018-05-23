package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.ExamScoreDto;
import entity.ExamPaper;
import org.beetl.sql.core.annotatoin.SqlStatement;

/**
 * Created by mengf on 2017/12/14 0014.
 */
public interface ExamPaperDao extends $Mapper<ExamPaper> {
    @SqlStatement(params = "sid,eid")
    ExamPaper querySingle(Long sid, Long eid);

    @SqlStatement(params = "sid,eid")
    ExamScoreDto queryExamScoreDto(Long sid, Long eid);
}
