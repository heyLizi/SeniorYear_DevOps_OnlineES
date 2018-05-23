package cn.edu.nju.software.service;

import dto.ExamResultDto;
import dto.QuestionDto;
import dto.StudentExamDetailDto;
import dto.StudentExamDto;
import entity.ExerciseOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.DateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：导出实现
 * 创建者：zs
 * 创建时间：2017/12/14 上午11:13
 * 包名：cn.edu.nju.software.service
 */

@Service
public class ExportService {

    @Autowired
    private WordService wordService;
    @Value("${file.export.path}")
    private String filePath;

    /**
     * 导出试卷
     * @param detailDto
     * @param questionDtos
     * @param type
     * @return
     */
    public String exportExamFile(StudentExamDetailDto detailDto, List<QuestionDto> questionDtos, Integer type) {
        if(type == null || (type!=0 && type !=1)){
            return null;
        }
        //组装数据
        Map<String, Object> dataMap = getExamData(detailDto,type);
        List<Map<String,Object>> list = getExamQustionList(questionDtos,type);
        dataMap.put("list",list);
        //组装路径
        String typeName = type == 0 ? "考卷" : "答卷";
        String fileName = detailDto.getEName()+"_"+detailDto.getNumber()+"_"+DateUtil.parseTodayToString()+typeName+".doc";
        //创建文件
        String template = type == 0 ? "exam.ftl" : "examWithAnswer.ftl";
        wordService.createWord(dataMap,filePath,fileName,template);
        return filePath+ File.separator+fileName;
    }

    /**
     * 导出成绩单
     * @param resultDto
     * @param studentExamDtos
     * @return
     */
    public String exportExamReport(ExamResultDto resultDto, List<StudentExamDto> studentExamDtos) {
        //组装数据
        Map<String, Object> dataMap = getResultData(resultDto);
        List<Map<String,Object>> list = getExamStudentList(studentExamDtos);
        dataMap.put("list",list);
        //组装路径
        String fileName = resultDto.getCourseName()+"_"+resultDto.getName()+"_成绩报告_"+DateUtil.parseTodayToString()+".doc";
        //创建文件
        String template = "report.ftl";
        wordService.createWord(dataMap,filePath,fileName,template);
        return filePath+ File.separator+fileName;
    }

    /**
     * 处理考生信息
     * @param studentExamDtos
     * @return
     */
    private List<Map<String,Object>> getExamStudentList(List<StudentExamDto> studentExamDtos) {
        List<Map<String,Object>> list = new ArrayList<>();
        for(StudentExamDto studentExamDto : studentExamDtos){
            Map<String,Object> map = new HashMap<>();
            map.put("name",studentExamDto.getName());
            map.put("number",studentExamDto.getNumber());
            String status = studentExamDto.getStatus() !=0 ? "是" : "否";
            map.put("status",status);
            map.put("correctCount",studentExamDto.getCorrectCount());
            map.put("score",studentExamDto.getScore());
            map.put("rank",studentExamDto.getRank());
            list.add(map);
        }
        return list;
    }

    /**
     * 处理考试统计信息
     * @param resultDto
     * @return
     */
    private Map<String,Object> getResultData(ExamResultDto resultDto) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name",resultDto.getName());
        dataMap.put("courseName", resultDto.getCourseName());
        String startTime = DateUtil.parseTimeToString(resultDto.getStartTime());
        String endTime = DateUtil.parseTimeToString(resultDto.getEndTime());
        dataMap.put("time", startTime+"~"+endTime);
        dataMap.put("questionCount", resultDto.getQuestionCount());
        dataMap.put("singleNum", resultDto.getSingleNum());
        dataMap.put("multiNum", resultDto.getMultiNum());
        dataMap.put("totalScore", resultDto.getTotalScore());
        dataMap.put("singleScore", resultDto.getSingleScore());
        dataMap.put("multiScore", resultDto.getMultiScore());
        dataMap.put("totalNum", resultDto.getTotalNum());
        dataMap.put("attendNum", resultDto.getAttendNum());
        dataMap.put("passedNum", resultDto.getPassedNum());
        dataMap.put("averageScore", resultDto.getAverageScore());
        dataMap.put("highestScore", resultDto.getHighestScore());
        dataMap.put("lowestScore", resultDto.getLowestScore());
        return dataMap;
    }


    /**
     * 处理考试题目信息
     * @param questionDtos
     * @param type
     * @return
     */
    private List<Map<String,Object>> getExamQustionList(List<QuestionDto> questionDtos, Integer type) {
        List<Map<String,Object>> list = new ArrayList<>();
        for(QuestionDto questionDto : questionDtos){
            Map<String,Object> map = new HashMap<>();
            map.put("question",questionDto.getQuestion());
            StringBuilder builder = new StringBuilder();
            char temp = 'A';
            for(ExerciseOption option : questionDto.getOptions()){
                builder.append(temp+"."+option.getContent()+"<w:p></w:p>");
                temp++;
            }
            map.put("optionStr",builder.toString());
            map.put("tAnswer",questionDto.getTAnswer());
            if(type == 1){
                map.put("sAnswer",questionDto.getSAnswer());
                map.put("score",questionDto.getSScore());
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 获得考试基本信息数据
     * @return
     */
    private Map<String,Object> getExamData(StudentExamDetailDto detailDto,Integer type) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("eName", detailDto.getEName());
        dataMap.put("sName", detailDto.getSName());
        dataMap.put("number", detailDto.getNumber());
        dataMap.put("className", detailDto.getClassName());
        dataMap.put("gradeName", detailDto.getGradeName());
        dataMap.put("courseName", detailDto.getCourseName());
        String startTime = DateUtil.parseTimeToString(detailDto.getStartTime());
        String endTime = DateUtil.parseTimeToString(detailDto.getEndTime());
        dataMap.put("examTime", startTime+"~"+endTime);
        if(type == 1){
            String state = detailDto.getState() != 0 ? "否" : "是";
            dataMap.put("state",state);
            dataMap.put("totalScore",detailDto.getTotalScore());
            dataMap.put("score",detailDto.getScore());
        }
        return dataMap;
    }


}
