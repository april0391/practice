import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await axios.post(
        `${import.meta.env.VITE_BACKEND_URL}/auth/login`,
        formData,
        {
          withCredentials: true, // 쿠키를 포함시키기 위해 필요
        }
      );
      const tokenValue = res.data;
      sessionStorage.setItem("token", tokenValue);
      alert("로그인 성공");
      navigate("/");
    } catch (error) {
      alert("로그인 실패: " + error.response.data.message);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="flex flex-col space-y-4">
        <input
          type="email"
          name="email"
          placeholder="이메일"
          onChange={handleOnChange}
          className="input-style"
        />
        <input
          type="password"
          name="password"
          placeholder="비밀번호"
          onChange={handleOnChange}
          className="input-style"
        />
        <button onClick={handleLogin}>로그인</button>
      </div>
    </div>
  );
};

export default Login;
