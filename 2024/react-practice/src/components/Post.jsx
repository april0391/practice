import React from "react";

const Post = ({ post }) => {
  return (
    <div>
      {post.title}
      {post.content}
    </div>
  );
};

export default Post;
