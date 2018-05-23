
queryBankCount
===
* 查询题库数

select count(id)
from es_exercise_bank
where 1=1
@if(!isEmpty(cid)){
    and course_id = #cid#
@}


queryBankList
===
* 分页查询题库

select *
from es_exercise_bank
where 1=1
@if(!isEmpty(cid)){
    and course_id = #cid#
@}


queryBankById
===
* 查询题库信息

select *
from es_exercise_bank
where id = #lid#


querySimpleBankList
===
* 查询简单题库

select *
from es_exercise_bank
where course_id = #cid#


updateBankCount
===
* 更新题目数量

update es_exercise_bank
set count = count + #count# , modify_time = #modifyTime#
where id = #lid#





