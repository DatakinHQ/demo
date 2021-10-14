
{{
    config(
        materialized='view'
    )
}}

select
    id,
    owner_user_id,
    accepted_answer_id,
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