queryAttendCount
===
* 参加考试的人数

select count(id)
from es_exam_paper
where exam_id = #eid# and status != 0


queryPassCount
===
* 考试及格的人数

select count(id)
from es_exam_paper
where exam_id = #eid# and status = 2 and score >= 60


queryExamScoreAnalyse
===
* 考试成绩统计

select max(score) as max_score,min(score) as min_score, avg(score) as average_score
from es_exam_paper
where exam_id = #eid# and status = 2


queryExamStudentList
===
* 分页查询学生考试情况

select paper.id,paper.status,student.id as sid,student.name,student.number,paper.correct_count,paper.score
from es_exam_paper paper
left join es_student student on student.id = paper.student_id
where exam_id = #eid#
order by score desc

queryExamStudent
===
select paper.id,paper.status,student.id as sid,student.name,student.number,paper.correct_count,paper.score
from es_exam_paper paper
left join es_student student on student.id = paper.student_id
where exam_id = #eid#
and student.id = #sid#

queryExamDetail
===
* 查询试卷学生考试详情

select paper.id,paper.student_id as sid,paper.exam_id as eid,paper.true_start_time,paper.true_end_time,paper.score,paper.status as state,
    exam.name as eName,exam.start_time,exam.end_time, single_num+multi_num as question_count, single_num*single_score+multi_num*multi_score as totalScore,
    student.name as sName,student.number,grade.name as grade_name,class.name as class_name,course.name as course_name
from es_exam_paper paper
left join es_exam exam on exam.id = paper.exam_id
left join es_student student on student.id = paper.student_id
left join es_course course on course.id = exam.course_id
left join es_grade grade on grade.id = student.grade_id
left join es_class class on class.id = student.class_id
where paper.id = #epid#


queryExamStudentIDList
===
* 查询考试所有学生ID

select student_id
from es_exam_paper 
where exam_id = #eid#


queryIdList
===
* 根据考试ID和学生ID查询试卷ID

select id
from es_exam_paper
where exam_id = #eid# and student_id in ( #join(sids)# )

querySingle
===
select * from es_exam_paper
where 1=1
@if(!isEmpty(sid)){
 and student_id=#sid#
@}
@if(!isEmpty(eid)){
 and exam_id=#eid#
@}


queryExamScoreDto
===
select paper.*,exam.*,
(exam.single_num+exam.multi_num) as questionCount,
course.name as courseName,
paper.student_id as sid,
paper.exam_id as eid
from es_exam_paper paper 
inner join es_exam exam on exam.id=paper.exam_id
inner join es_course as course on exam.course_id=course.id
where 1=1
@if(!isEmpty(eid)){
 and exam.id = #eid#
@}
@if(!isEmpty(sid)){
 and paper.student_id = #sid#
@}