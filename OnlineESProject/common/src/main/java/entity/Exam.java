package entity;

import common.IDEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.Table;
import param.CreateExamParam;

import java.util.Date;

/**
 * Created by mengf on 2017/11/21 0021.
 */

/**
 * 考试表实体类
 */
@Data
@Table(name = "es_exam")
public class Exam extends IDEntity {
    //考试名称
    private String name;

    //考试科目的ID
    private Integer courseId;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //持续时间-多少分值
    private Integer period;

    //单选题个数
    private Integer singleNum;

    //单选题分值
    private Integer singleScore;

    //多选题个数
    private Integer multiNum;

    //多选题分值
    private Integer multiScore;

    //题库id集合-,号隔开
    private String lids;

    private Integer studentCount;

    public Exam() {
    }

    public Exam(CreateExamParam param) {
        super();
        this.name = param.getName();
        this.courseId = param.getCid();
        this.startTime = param.getStartTime();
        this.endTime = param.getEndTime();
        this.period = param.getPeriod();
        this.singleNum = param.getSingleNum();
        this.singleScore = param.getSingleScore();
        this.multiNum = param.getMultiNum();
        this.multiScore = param.getMultiScore();
        this.lids = param.getLids();
    }
}
