{{
    config(
        materialized='table'
    )
}}

with questions as (
    select
        DATE_TRUNC(DATE(creation_date), DAY) AS `day`,
        count(*) as num_questions
    from {{ ref('filtered_questions') }}
    group by `day`
),
answers as (
    select
        DATE_TRUNC(DATE(creation_date), DAY) AS `day`,
        count(*) as num_answers
    from {{ ref('filtered_answers') }}
    group by `day`
),
askers as (
    select
        DATE_TRUNC(DATE(creation_date), DAY) AS `day`,
        count(distinct(owner_user_id)) as num_askers
    from {{ ref('filtered_questions') }}
    group by `day`
),
answerers as (
    select
        DATE_TRUNC(DATE(creation_date), DAY) AS `day`,
        count(distinct(owner_user_id)) as num_answerers
    from {{ ref('filtered_answers') }}
    group by `day`
)

select
    questions.day as `day`,
    questions.num_questions,
    answers.num_answers,
    askers.num_askers,
    answerers.num_answerers
from questions
left join answers on answers.day = questions.day
left join askers on askers.day = questions.day
left join answerers on answerers.day = questions.day
order by `day` desc
