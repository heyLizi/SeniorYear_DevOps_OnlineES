queryExamPaper
===
select * from es_exam_paper
where 1=1
@if(!isEmpty(sid)){
 and student_id = #sid#
@}
@if(!isEmpty(eid)){
 and exam_id = #eid#
@}