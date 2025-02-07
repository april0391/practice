const Post = ({ post }) => {
  return (
    <div className="border p-4 mb-4 rounded shadow-sm">
      <h3 className="text-xl font-bold">{post.title}</h3>
      <p>{post.content}</p>
      <p className="text-sm text-gray-500">작성자: {post.author}</p>
    </div>
  );
};

export default Post;
