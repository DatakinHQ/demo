{{
    config(
        materialized='incremental'
    )
}}

select * from {{ source('stackoverflow', 'posts_answers') }}
where parent_id in (select id from {{ ref('filtered_questions') }} )

-- only grab the rows we don't have
{% if is_incremental() %}
  and creation_date > (select max(creation_date) from {{ this }})
{% endif %}
