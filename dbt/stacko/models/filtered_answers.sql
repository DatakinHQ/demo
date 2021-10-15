{{
    config(
        materialized='incremental'
    )
}}

select * from {{ source('stackoverflow', 'posts_answers') }}
where parent_id in (select id from {{ ref('filtered_questions') }} )

-- to keep things from getting out of hand!
and creation_date > '2021-01-01'

-- only grab the rows we don't have
{% if is_incremental() %}
  and creation_date > (select max(creation_date) from {{ this }})
{% endif %}
