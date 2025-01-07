import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();

  return (
    <>
      <div>home</div>
      <button onClick={() => navigate("/board")}>게시판 이동</button>
      <button onClick={() => navigate("/user/signup")}>회원가입</button>
    </>
  );
};

export default Home;
