
queryOptionList
===
* 查询选项合集

select *
from es_exercise_option
where exercise_id = #qid#


queryRandomOptionList
===
* 乱序查询选项列表-生成考题

select *
from es_exercise_option
where exercise_id = #qid#
order by rand()

