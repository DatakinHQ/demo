
{{
    config(
        materialized='view'
    )
}}

select
    id,
    owner_user_id,
    accepted_answer_id,
    (
        select owner_user_id
        from {{ ref('answer_stats') }}
        where id = filtered_questions.accepted_answer_id
    ) as accepted_answer_owner_user_id,
    answer_count,
    (
        select count(*)
        from {{ ref('filtered_votes') }}
        where post_id = filtered_questions.id
        and vote_type_id = 2
    ) as upvote_count,
    (
        select count(*)
        from {{ ref('filtered_votes') }}
        where post_id = filtered_questions.id
        and vote_type_id = 3
    ) as downvote_count

from {{ ref('filtered_questions') }}