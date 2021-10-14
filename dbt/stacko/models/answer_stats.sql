
{{
    config(
        materialized='view'
    )
}}

select
    id,
    owner_user_id,

    (
        select count(*)
        from {{ ref('filtered_votes') }}
        where post_id = filtered_answers.id
        and vote_type_id = 2
    ) as upvote_count,

    (
        select count(*)
        from {{ ref('filtered_votes') }}
        where post_id = filtered_answers.id
        and vote_type_id = 3
    ) as downvote_count

from {{ ref('filtered_answers') }}