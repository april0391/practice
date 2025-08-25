import { useNavigate } from "react-router-dom";
import TailwindTest from "../components/TailwindTest";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col gap-2">
      <button onClick={() => navigate("/board")}>게시판 이동</button>
      <button onClick={() => navigate("/signup")}>회원가입</button>
      <TailwindTest />
    </div>
  );
};

export default Home;
