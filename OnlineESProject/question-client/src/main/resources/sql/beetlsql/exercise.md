
queryQuestionCount
===
* 查询题库题目数

select count(id)
from es_exercise
where exercise_bank_id = #lid#


queryQuestionList
===
* 分页查询题库题目

select exercise.*,type.name as type_name
from es_exercise exercise
left join es_exercise_type type on exercise.exercise_type_id = type.id
where exercise.exercise_bank_id = #lid#


queryQuestionById
===
* 查询题目信息

select exercise.*,type.name as type_name
from es_exercise exercise
left join es_exercise_type type on exercise.exercise_type_id = type.id
where exercise.id = #qid#


queryQuestionCounts
===
* 查询题库题型统计

select exercise_type_id as type_id, count(id) as typeCount
from es_exercise
where exercise_bank_id = #lid#
group by exercise_type_id


queryLibQuestion
===
* 查询题库中的某类型题目ID

select id
from es_exercise
where exercise_type_id = #type# and exercise_bank_id in ( #join(lids)#)








