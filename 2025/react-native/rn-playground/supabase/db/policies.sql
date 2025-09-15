create policy "allows authenticated upload to avatars"
on storage.objects  
for insert 
to authenticated 
with check (
  bucket_id = 'avatars'
);
