
{{
    config(
        materialized='view'
    )
}}

select
    id,
    display_name,
    creation_date,
    last_access_date,
    profile_image_url,
    (
        select count(*) from {{ ref('question_stats') }}
        where owner_user_id = filtered_users.id
    ) as questions_posted,
    (
        select sum(upvote_count) from {{ ref('question_stats') }}
        where owner_user_id = filtered_users.id
    ) as questions_posted_upvotes,
    (
        select sum(downvote_count) from {{ ref('question_stats') }}
        where owner_user_id = filtered_users.id
    ) as questions_posted_downvotes,
    (
        select sum(answer_count) from {{ ref('question_stats') }}
        where owner_user_id = filtered_users.id
    ) as answers_inspired,
    (
        select count(*) from {{ ref('answer_stats') }}
        where owner_user_id = filtered_users.id
    ) as answers_posted,
    (
        select count(*) from {{ ref('question_stats') }}
        where accepted_answer_owner_user_id = filtered_users.id
    ) as answers_accepted,
    (
        select sum(upvote_count) from {{ ref('answer_stats') }}
        where owner_user_id = filtered_users.id
    ) as answers_posted_upvotes,
    (
        select sum(downvote_count) from {{ ref('answer_stats') }}
        where owner_user_id = filtered_users.id
    ) as answers_posted_downvotes
from {{ ref('filtered_users') }}
