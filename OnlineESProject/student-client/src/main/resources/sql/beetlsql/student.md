countByMailBox
===
select count(1) from es_student where mail = #mail#

countByNumber
===
select count(1) from es_student where number = #number#

getUserDto
===
select stu.id as sid, stu.name,stu.mail as email,
stu.number,cls.name as className,grd.name as gradeName,
ins.name as institutionName,sch.name as schoolName
from es_student stu 
inner join es_class cls on stu.class_id=cls.id 
inner join es_grade grd on cls.grade_id = grd.id
inner join es_institution ins on grd.institution_id = ins.id
inner join es_school sch on sch.id = ins.school_id
where 1=1
@if(!isEmpty(sid)){
 and stu.id = #sid#
@}