import { useState, useEffect } from "react";
import Post from "../components/Post";

export default function Board() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/posts")
      .then((res) => res.json())
      .then((data) => {
        setPosts(data.data);
      });
  }, []);

  return (
    <div>
      <h1>게시판</h1>
      {posts.map((post) => (
        <Post key={post.id} post={post} />
      ))}
    </div>
  );
}
