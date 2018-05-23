queryExamQuestionList
===
* 查询考试题目

select ee.id,ee.options as option_str,ee.answers as t_answer,ee.student_answers as s_answer,
    type.id as type_id , ee.correct_status , exercise.question,type.name as type_name
from es_exam_exercise ee
left join es_exercise exercise on exercise.id = ee.exercise_id 
left join es_exercise_type type on type.id = exercise.exercise_type_id
where exam_paper_id = #epid#


queryExamExercise
===
select * from 
es_exam_exercise where 1=1
@if(!isEmpty(paperId)){
 and exam_paper_id = #paperId#
@}
@if(!isEmpty(exerciseId)){
 and exercise_id = #exerciseId#
@}


queryPaperExercises
===
select es_exam_exercise.*,es_exercise.exercise_type_id as exerciseType
from es_exam_exercise inner join es_exercise
on es_exam_exercise.exercise_id=es_exercise.id
where es_exam_exercise.exam_paper_id=#epid#
order by es_exam_exercise.id;

queryPaperExerciseResults
===
select es_exam_exercise.*,es_exercise.exercise_type_id as exerciseType
from es_exam_exercise inner join es_exercise
on es_exam_exercise.exercise_id=es_exercise.id
where es_exam_exercise.exam_paper_id=#epid#
order by es_exam_exercise.id;

queryExamExerciseDto
===
select es_exam_exercise.*,es_exercise.exercise_type_id as exerciseType
from es_exam_exercise inner join es_exercise
on es_exam_exercise.exercise_id=es_exercise.id
where es_exam_exercise.id=#id#


queryById
===
* id是exericseId

select * from es_exercise where id=#id#