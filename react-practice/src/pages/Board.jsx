import { useParams, useSearchParams } from "react-router-dom";
import { hostUrl } from "../util/constants";
import { useState, useEffect } from "react";

const Board = () => {
  const [posts, setPosts] = useState();

  useEffect(() => {
    fetch(hostUrl + "/posts")
      .then((res) => res.json())
      .then((data) => setPosts(data.data));
  }, []);

  posts.forEach((post) => console.log(post));

  return <></>;
};

export default Board;
