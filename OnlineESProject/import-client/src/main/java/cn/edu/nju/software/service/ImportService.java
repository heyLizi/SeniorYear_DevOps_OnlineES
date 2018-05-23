package cn.edu.nju.software.service;

import dto.QuestionDto;
import dto.StudentUploadDto;
import entity.ExerciseOption;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImportService {


    /**
     * 导入题库
     * @param filepath
     * @return
     * @throws IOException
     */
    public List<QuestionDto> importQuestionExcel(String filepath) throws IOException {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<List<String>> excelList = excelToArray(filepath);
        for(List<String> cellList : excelList){
            int length = cellList.size();
            if( length < 5){
                continue;
            }
            QuestionDto questionDto = new QuestionDto();
            List<ExerciseOption> optionList = new ArrayList<>();
            //题型
            String type = cellList.get(0);
            if(type.equals("单选题")){
                questionDto.setTypeId(1);
            }else if(type.equals("多选题")){
                questionDto.setTypeId(2);
            }else{
                continue;
            }
            //题目
            questionDto.setQuestion(cellList.get(1));
            //答案选项
            String answerStr = cellList.get(2).replace("，",",");
            String[] answers = answerStr.split(",");
            Map<String,String> answerMap = new HashMap<>();
            for(String answer : answers){
                answerMap.put(answer,answer);
            }
            //选项处理
            int optionCount = Integer.parseInt(cellList.get(3));
            if(optionCount != length-4){
                continue;
            }
            questionDto.setOptionCount(optionCount);
            int answerCount = 0;
            for(int i=1;i<optionCount+1;i++){
                int order = 3+i;
                ExerciseOption option = new ExerciseOption();
                option.setContent(cellList.get(order));
                if(answerMap.containsKey(String.valueOf(i))){
                    option.setValidity(1);
                    answerCount++;
                }else{
                    option.setValidity(0);
                }
                optionList.add(option);
            }
            questionDto.setAnswerCount(answerCount);
            questionDto.setOptions(optionList);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }


    /**
     * 导入学生
     * @param filepath
     * @return
     */
    public List<StudentUploadDto> importStudentExcel(String filepath) throws IOException {
        List<StudentUploadDto> studentDtos = new ArrayList<>();
        List<List<String>> excelList = excelToArray(filepath);
        for(List<String> cellList : excelList){
            int length = cellList.size();
            if( length != 6){
                continue;
            }
            StudentUploadDto studentDto = new StudentUploadDto();
            //题型
            studentDto.setInstitutionName(cellList.get(0));
            studentDto.setGradeName(cellList.get(1));
            studentDto.setClassName(cellList.get(2));
            studentDto.setNumber(cellList.get(3));
            studentDto.setName(cellList.get(4));
            studentDto.setEmail(cellList.get(5));
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }

    /**
     * 导出excel文件成list
     * @param filepath
     * @return
     * @throws IOException
     */
    private List<List<String>> excelToArray(String filepath) throws IOException {
        List<List<String>> excelList = new ArrayList<>();
        //获取表格
        Workbook book = getExcelWorkbook(filepath);
        Sheet sheet = getSheetByNum(book,0);
        //按行处理表格
        int lastRowNum = sheet.getLastRowNum();
        for(int i = 1 ; i <= lastRowNum ; i++){
            List<String> cellList = new ArrayList<>();
            Row row = sheet.getRow(i);
            if( row != null ){
                int lastCellNum = row.getLastCellNum();
                Cell cell;
                for( int j = 0 ; j <= lastCellNum ; j++ ){
                    cell = row.getCell(j);
                    if( cell != null ){
                        cell.setCellType(CellType.STRING);
                        String cellValue = cell.getStringCellValue();
                        if(cellValue == null || cellValue.equals("")){
                            break;
                        }
                        cellList.add(cellValue);
                    }
                }
                if(!cellList.isEmpty()){
                    excelList.add(cellList);
                }
            }
        }
        return excelList;
    }



    /**
     * 获取工作簿
     * @param filePath
     * @return
     * @throws IOException
     */
    private Workbook getExcelWorkbook(String filePath) throws IOException {
        Workbook book = null;
        File file  = new File(filePath);
        if(!file.exists()) {
            throw new FileNotFoundException("文件不存在");
        }
        FileInputStream fis = new FileInputStream(file);
        try {
            book = WorkbookFactory.create(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
        file.delete();
        return book;
    }

    /**
     * 获取表栏
     * @param book
     * @param number
     * @return
     */
    private Sheet getSheetByNum(Workbook book,int number){
        Sheet sheet = null;
        try {
            sheet = book.getSheetAt(number);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return sheet;
    }

}
