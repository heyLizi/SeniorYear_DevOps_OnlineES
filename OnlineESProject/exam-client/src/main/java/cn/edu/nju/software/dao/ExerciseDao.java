package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.ExamExerciseDto;
import dto.QuestionDto;
import entity.ExamExercise;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.RowSize;
import org.beetl.sql.core.annotatoin.RowStart;

import java.util.List;

public interface ExerciseDao extends $Mapper<ExamExercise> {

    /**
     * 分页查询考试题目
     * @param epid
     * @param pageStart
     * @param pageSize
     * @return
     */
    public List<QuestionDto> queryExamQuestionList(@Param("epid") Integer epid, @RowStart int pageStart, @RowSize int pageSize);
}
