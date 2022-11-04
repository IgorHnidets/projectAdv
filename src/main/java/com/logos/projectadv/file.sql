use  projectadv;

SELECT p.item_id,p.name,p.description,p.salary,p.image FROM items_bucket bp INNER JOIN item p on bp.item_id = p.item_id WHERE bp.bucket_id=1

