* 登录

loginForTeacher
===
select temp.id,temp.name,temp.number,school.name as school_name,institution.name as institution_name
from(
    select * from es_teacher where 1 = 1
    @if(!isEmpty(name)){
        and number = #name#
    @}
    @if(!isEmpty(password)){
        and password = #password#
    @}
) temp
left join es_school school on school.id = temp.school_id
left join es_institution institution on institution.id = temp.institution_id

