queryExamCount
===
* 查询老师发布考试数量

select count(exam.id)
from es_exam exam
left join es_course course on course.id = exam.course_id
where course.teacher_id = #tid#


queryExamList
===
* 查询老师考试列表

select exam.*, exam.single_num+exam.multi_num as question_count
from es_exam exam
left join es_course course on course.id = exam.course_id
where course.teacher_id = #tid#


queryExamResult
===
* 查询考试结果

select exam.*,exam.single_num+exam.multi_num as question_count,course.name as course_name,student_count as total_num
from es_exam exam
left join es_course course on course.id = exam.course_id
where exam.id = #eid#


querySimpleExam
===
* 根据试卷ID查询考试信息

select exam.*
from es_exam exam
left join es_exam_paper paper on paper.exam_id = exam.id
where paper.id = #epid#

getExamDto
===
select exam.*,(exam.single_num+exam.multi_num) as questionCount,
course.name as courseName from es_exam exam 
inner join es_course as course on exam.course_id=course.id
where 1=1
@if(!isEmpty(eid)){
 and exam.id = #eid#
@}