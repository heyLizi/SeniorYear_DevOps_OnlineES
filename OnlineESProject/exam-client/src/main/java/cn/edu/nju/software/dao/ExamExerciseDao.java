package cn.edu.nju.software.dao;

import beetlsql.$Mapper;
import dto.ExamExerciseDto;
import dto.ExamExerciseResultDto;
import entity.ExamExercise;
import org.beetl.sql.core.annotatoin.SqlStatement;

import java.util.List;

/**
 * Created by mengf on 2017/12/14 0014.
 */
public interface ExamExerciseDao extends $Mapper<ExamExercise> {
    @SqlStatement(params = "epid")
    List<ExamExerciseDto> queryPaperExercises(Long epid);

    @SqlStatement(params = "epid")
    List<ExamExerciseResultDto> queryPaperExerciseResults(Long epid);

    @SqlStatement(params = "id")
    ExamExerciseDto queryExamExerciseDto(Long id);


}
