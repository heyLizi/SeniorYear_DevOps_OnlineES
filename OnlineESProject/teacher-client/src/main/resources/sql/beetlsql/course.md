
queryCourseCount
===
* 查询课程数

select count(id)
from es_course
where 1=1
@if(!isEmpty(tid)){
    and teacher_id = #tid#
@}


queryCourseList
===
* 分页查询课程

select temp.id,temp.name,temp.period,temp.score,type.name as course_type_name,institution.name as institution_name
from (
    select *
    from es_course
    where 1=1
    @if(!isEmpty(tid)){
        and teacher_id = #tid#
    @}
) temp
left join es_course_type type on type.id = temp.course_type_id
left join es_institution institution on institution.id = temp.institution_id


queryCourseDetail
===
* 查询课程详情

select temp.id,temp.name,temp.period,temp.score,type.name as course_type_name,institution.name as institution_name
from (
    select *
    from es_course
    where teacher_id = #tid# and id = #cid#
) temp
left join es_course_type type on type.id = temp.course_type_id
left join es_institution institution on institution.id = temp.institution_id




