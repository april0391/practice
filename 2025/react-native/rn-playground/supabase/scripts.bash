supabase functions serve --env-file ./functions/.env.local

# routing
npx uri-scheme open exp://10.238.179.174:8081/--/set-username --android

# sitemap
npx expo-router-sitemap

#
# invoke functions
#
curl -i -L \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZS1kZW1vIiwicm9sZSI6ImFub24iLCJleHAiOjE5ODM4MTI5OTZ9.CRXP1A7WOeoJeXxjNni43kdQwgnWNReilDMblYTn_I0' \
  'http://10.238.179.174:54321/functions/v1/api/products'
