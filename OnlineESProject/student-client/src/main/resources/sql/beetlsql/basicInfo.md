querySchools
===
select * from es_school as s;


queryInstitutions
===
select i.*,s.name as schoolName from es_institution as i
inner join es_school s on i.school_id = s.id
where 1=1
@if(!isEmpty(schoolId)){
 and school_id = #schoolId#
@}

queryGrades
===
select g.*,i.name as institutionName,
s.id as schoolId,s.name as schoolName
from es_grade g
inner join es_institution i on i.id = g.institution_id
inner join es_school s on s.id = i.school_id
where 1=1
@if(!isEmpty(institutionId)){
 and g.institution_id = #institutionId#
@}


queryClasses
===
select c.*,g.name as gradeName,
i.id as institutionId,i.name as institutionName,
s.id as schoolId,s.name as schoolName
from es_class c
inner join es_grade g on c.grade_id = g.id 
inner join es_institution i on i.id = g.institution_id
inner join es_school s on s.id = i.school_id
where  1=1
@if(!isEmpty(gradeId)){
 and c.grade_id = #gradeId#
@}
