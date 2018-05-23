
queryStudentList
===
* 查询导入学生

select id,name,mail as email
from es_student
where mail in ( #join(emails)# )




