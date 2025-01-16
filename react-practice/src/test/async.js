async function fetchPosts() {
  const posts = await fetch("http://localhost:8080/posts").then((res) =>
    res.json()
  );
  console.log(posts);
}
