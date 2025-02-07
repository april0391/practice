import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Home = () => {
  const navigate = useNavigate();
  const [token, setToken] = useState();
  useEffect(() => {
    setToken(sessionStorage.getItem("token"));
  }, []);

  const handleLogout = async () => {
    const res = await axios.post(
      `${import.meta.env.VITE_BACKEND_URL}/auth/logout`,
      { token }
    );
    if (res.status === 200) {
      sessionStorage.removeItem("token");
      alert("로그아웃 되었습니다.");
      window.location.reload();
    } else {
      alert("잘못된 요청입니다.");
    }
  };

  return (
    <div className="flex flex-col items-center gap-4 p-4">
      {!token ? (
        <>
          <button
            onClick={() => navigate("/register")}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            회원 가입
          </button>
          <button
            onClick={() => navigate("/login")}
            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
          >
            로그인
          </button>
        </>
      ) : (
        <button
          onClick={handleLogout}
          className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
        >
          로그아웃
        </button>
      )}
      <button
        onClick={() => navigate("/board")}
        className="px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600"
      >
        게시판
      </button>
    </div>
  );
};

export default Home;
