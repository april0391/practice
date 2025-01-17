import { useSearchParams } from "react-router-dom";
import { hostUrl } from "../util/constants";
import { useState, useEffect } from "react";
import Post from "../components/Post";

const Board = () => {
  const [posts, setPosts] = useState([]);
  const [pageInfo, setPageInfo] = useState({});
  const [searchParams, setSearchParams] = useSearchParams();

  // 현재 페이지 번호: Query Parameter로부터 가져오며 기본값은 1 (1 기반 페이지)
  const currentPage = parseInt(searchParams.get("page")) || 1;

  useEffect(() => {
    // API 호출에 사용할 0 기반 페이지 번호로 변환

    fetch(`${hostUrl}/posts?page=${currentPage}`)
      .then((res) => res.json())
      .then((json) => {
        setPosts(json.data.posts);
        setPageInfo(json.data.pageInfo);
        console.log(json);
      })
      .catch((error) => console.error("Error fetching posts:", error));
  }, [currentPage]);

  // 페이지 변경 함수
  const changePage = (newPage) => {
    setSearchParams({ page: newPage }); // 1 기반 페이지로 Query Parameter 설정
  };

  return (
    <div className="max-w-4xl mx-auto py-8">
      {/* 게시글 목록 */}
      <div className="grid grid-cols-1 gap-4">
        {posts.map((post) => (
          <Post key={post.id} post={post} />
        ))}
      </div>

      {/* 페이징 */}
      <div className="mt-6 flex flex-col items-center">
        <p className="text-gray-700 text-sm">
          Page {currentPage} of {pageInfo.totalPages} ({pageInfo.totalElements}{" "}
          posts)
        </p>
        <div className="mt-4 flex gap-2">
          {Array.from({ length: pageInfo.totalPages }, (_, idx) => idx + 1).map(
            (page) => (
              <button
                key={page}
                className={`px-4 py-2 text-sm rounded ${
                  page === currentPage
                    ? "bg-blue-500 text-white"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                }`}
                onClick={() => changePage(page)}
              >
                {page}
              </button>
            )
          )}
        </div>
      </div>
    </div>
  );
};

export default Board;
