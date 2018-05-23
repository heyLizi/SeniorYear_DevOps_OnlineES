package cn.edu.nju.software.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dto.ClassDto;
import dto.GradeDto;
import dto.InstitutionDto;
import dto.SchoolDto;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by mengf on 2017/11/12 0012.
 */
@Service
public class BasicInfoService {

    @Autowired
    private SQLManager sql;

    public List<SchoolDto> getSchools() {
        //获取学校信息
        List<SchoolDto> schools = sql.select("basicInfo.querySchools",SchoolDto.class,Maps.newHashMap());
        return schools;
    }

    public List<InstitutionDto> getInstitutions(Long school_id) {
        Map<String,Long> params = Maps.newHashMap();
        params.put("schoolId",school_id);
        List<InstitutionDto> institutions = sql.select("basicInfo.queryInstitutions",InstitutionDto.class,params);
        return institutions;
    }

    public List<GradeDto> getGrades(Long institution_id){
        Map<String,Long> params = Maps.newHashMap();
        params.put("institutionId",institution_id);
        List<GradeDto> grades = sql.select("basicInfo.queryGrades",GradeDto.class,params);
        return grades;
    }

    public List<ClassDto> getClasses(Long grade_id){
        Map<String,Long> params = Maps.newHashMap();
        params.put("gradeId",grade_id);
        List<ClassDto> classes = sql.select("basicInfo.queryClasses",ClassDto.class,params);
        return classes;
    }
}
