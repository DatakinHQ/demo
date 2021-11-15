{{
    config(
        materialized='incremental'
    )
}}

select * from {{ source('stackoverflow', 'votes') }}
where (
    post_id in (select id from {{ ref('filtered_questions') }} )
    or post_id in (select id from {{ ref('filtered_answers') }} )
)

-- only grab the rows we don't have
{% if is_incremental() %}
  and creation_date > (select max(creation_date) from {{ this }})
{% endif %}
