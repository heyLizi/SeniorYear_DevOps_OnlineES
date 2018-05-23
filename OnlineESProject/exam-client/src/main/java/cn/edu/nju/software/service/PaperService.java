package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.ExamDao;
import cn.edu.nju.software.dao.ExerciseDao;
import cn.edu.nju.software.dao.OptionDao;
import cn.edu.nju.software.dao.PaperDao;
import com.alibaba.fastjson.JSON;
import dto.QuestionDto;
import dto.StudentExamDetailDto;
import dto.StudentExamDto;
import entity.Exam;
import entity.ExerciseOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：试卷逻辑层接口
 * 创建者：zs
 * 创建时间：2017/12/12 下午5:38
 * 包名：cn.edu.nju.software.service
 */
@Service
public class PaperService {

    @Autowired
    private ExamDao examDao;
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private ExerciseDao exerciseDao;
    @Autowired
    private OptionDao optionDao;

    /**
     * 查询学生试卷情况
     * @param eid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<StudentExamDto> queryExamStudentList(Integer eid, Integer pageIndex, Integer pageSize) {
        int pageStart = pageIndex*pageSize+1;
        List<StudentExamDto> examDtos = paperDao.queryExamStudentList(eid,pageStart,pageSize);
        for(StudentExamDto examDto : examDtos){
            examDto.setRank(pageStart);
            pageStart++;
        }
        return examDtos;
    }

    /**
     * 查询试卷学生情况
     * @param epid
     * @return
     */
    public StudentExamDetailDto queryExamDetail(Integer epid) {
        return paperDao.queryExamDetail(epid);
    }

    /**
     * 查询试卷问题详情
     * @param epid
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<QuestionDto> queryExamQuestionList(Integer epid, Integer pageIndex, Integer pageSize) {
        //获得考试相关信息
        Exam examDto = examDao.querySimpleExam(epid);
        int singleScore = examDto.getSingleScore();
        int mutliScore = examDto.getMultiScore();
        //获得问题列表
        int pageStart = pageIndex*pageSize+1;
        List<QuestionDto> questionDtos = exerciseDao.queryExamQuestionList(epid,pageStart,pageSize);
        //单独处理
        for(QuestionDto questionDto : questionDtos){
            //拿到选项
            String[] options = questionDto.getOptionStr().replace("，",",").split(",");
            List<ExerciseOption> optionList = optionDao.queryOptionListByOrder(options);
            questionDto.setOptions(optionList);
            //设置得分
            int score = questionDto.getTypeId() == 1 ? singleScore : mutliScore;
            int correctStatus = questionDto.getCorrectStatus();
            switch (correctStatus){
                case 1:
                    questionDto.setSScore(score);break;
                case 2:
                    questionDto.setSScore((score+1)/2);break;
                default:
                    questionDto.setSScore(0);
            }
            //处理答案显示
            Map<String,String> map = new HashMap<>();
            for(int i=0;i<options.length;i++){
                char tempChar = (char) ('A'+i);
                map.put(options[i],String.valueOf(tempChar));
            }
            String tAnswer = questionDto.getTAnswer();
            questionDto.setTAnswer(createOptionStr(map,tAnswer));
            //正确答案 tAnswer;
            String sAnswer = questionDto.getSAnswer();
            if(sAnswer != null && !sAnswer.equals("")){
                questionDto.setSAnswer(createOptionStr(map,sAnswer));
            }else{
                questionDto.setSAnswer("/");
            }
        }
        return questionDtos;
    }

    private String createOptionStr(Map<String, String> map, String options) {
        String[] strs = options.split(",");
        List<String> builder = new ArrayList<>();
        for(int i=0;i<strs.length;i++){
            if(map.containsKey(strs[i])){
                builder.add(map.get(strs[i]));
            }
        }
        String result = JSON.toJSONString(builder).replace("\"","");
        return result.substring(1,result.length()-1);
    }


    /**
     * 查询考试学生ID
     * @param eid
     * @return
     */
    public List<String> queryExamStudentIDList(Integer eid) {
        return paperDao.queryExamStudentIDList(eid);
    }

    /**
     * 根据考试ID和学生ID查询试卷ID
     * @param eid
     * @param sids
     * @return
     */
    public List<Integer> queryIdList(Integer eid, String[] sids) {
        return paperDao.queryIdList(eid,sids);
    }
}
