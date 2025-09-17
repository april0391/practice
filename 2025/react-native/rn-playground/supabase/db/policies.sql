create policy "allows insert to authenticated on avatars"
on storage.objects  
for insert 
to authenticated 
with check (
  bucket_id = 'avatars'
);

create policy "allows select to authenticated on avatars"
on storage.objects  
for select 
to authenticated 
using (
  bucket_id = 'avatars'
);
