{{
    config(
        materialized='incremental'
    )
}}

select * from {{ source('stackoverflow', 'users') }}
where (
    id in (select owner_user_id from {{ ref('filtered_questions') }} )
    or id in (select owner_user_id from {{ ref('filtered_answers') }} )
)

-- only grab the rows we don't have
{% if is_incremental() %}
  and creation_date > (select max(creation_date) from {{ this }})
{% endif %}
