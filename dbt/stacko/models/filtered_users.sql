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

-- to keep things from getting out of hand!
and creation_date > '2021-01-01'

-- only grab the rows we don't have
{% if is_incremental() %}
  and creation_date > (select max(creation_date) from {{ this }})
{% endif %}
