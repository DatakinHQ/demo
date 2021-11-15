{{
    config(
        materialized='incremental'
    )
}}

select * from {{ source('stackoverflow', 'posts_questions') }}
where tags like '%etl%'

-- only grab the rows we don't have
{% if is_incremental() %}
  and creation_date > (select max(creation_date) from {{ this }})
{% endif %}
