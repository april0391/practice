import { useState, useEffect } from "react";
import axios from "axios";

const Posts = () => {
  const [posts, setPosts] = useState([]); // 게시글 리스트 상태
  const [pageInfo, setPageInfo] = useState({
    pageNumber: 0,
    pageSize: 10,
    totalPages: 0,
    totalElements: 0,
  });
  const [loading, setLoading] = useState(false);

  // 데이터 로딩 함수
  const fetchPosts = async (page = 0) => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/posts", {
        params: {
          page,
          size: pageInfo.pageSize,
          sort: "createdDate",
        },
      });
      setPosts(response.data.data.posts); // 게시글 데이터 업데이트
      setPageInfo(response.data.data.pageInfo); // 페이지 정보 업데이트
    } catch (error) {
      console.error("Error fetching posts:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts(pageInfo.pageNumber);
  }, [pageInfo.pageNumber]); // 페이지가 변경될 때마다 데이터 로딩

  // 페이지 변경 함수
  const handlePageChange = (newPage) => {
    setPageInfo((prev) => ({ ...prev, pageNumber: newPage }));
  };

  // 페이지 번호 범위 생성 함수 (최대 10페이지 표시)
  const getPageNumbers = () => {
    const totalPages = pageInfo.totalPages;
    const currentPage = pageInfo.pageNumber;

    const rangeStart = Math.max(0, currentPage - 4); // 현재 페이지를 기준으로 이전 4페이지
    const rangeEnd = Math.min(totalPages, currentPage + 5); // 현재 페이지를 기준으로 이후 5페이지

    const pageNumbers = [];
    for (let i = rangeStart; i < rangeEnd; i++) {
      pageNumbers.push(i);
    }

    return pageNumbers;
  };

  return (
    <div>
      <h1>게시글 목록</h1>

      {loading ? (
        <p>로딩 중...</p>
      ) : (
        <div>
          <ul>
            {posts.map((post) => (
              <li key={post.id}>
                <h3>{post.title}</h3>
                <p>{post.content}</p>
                <small>{new Date(post.createdDate).toLocaleString()}</small>
              </li>
            ))}
          </ul>

          {/* 페이지 네비게이션 */}
          <div>
            <button
              disabled={pageInfo.pageNumber === 0}
              onClick={() => handlePageChange(pageInfo.pageNumber - 1)}
            >
              이전
            </button>

            {/* 페이지 번호 표시 */}
            {getPageNumbers().map((pageNumber) => (
              <button
                key={pageNumber}
                onClick={() => handlePageChange(pageNumber)}
                style={{
                  fontWeight:
                    pageInfo.pageNumber === pageNumber ? "bold" : "normal",
                }}
              >
                {pageNumber + 1}
              </button>
            ))}

            <button
              disabled={pageInfo.pageNumber === pageInfo.totalPages - 1}
              onClick={() => handlePageChange(pageInfo.pageNumber + 1)}
            >
              다음
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Posts;
