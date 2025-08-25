import { useState } from "react";
import axios from "axios";

const Register = () => {
  const [formData, setFormData] = useState({
    name: "",
    password: "",
    email: "",
  });

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleRegister = async () => {
    const backendUrl = import.meta.env.VITE_BACKEND_URL;
    try {
      const res = await axios.post(`${backendUrl}/users`, formData);
      alert(JSON.stringify(res.data, null, 2));
    } catch (error) {
      alert(error);
    }
  };
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <h2 className="text-2xl font-semibold text-center text-gray-800 mb-6">
        회원가입
      </h2>
      <div className="flex flex-col space-y-4">
        <input
          onChange={handleOnChange}
          type="text"
          name="name"
          placeholder="이름"
          className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <input
          onChange={handleOnChange}
          type="password"
          name="password"
          placeholder="비밀번호"
          className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <input
          onChange={handleOnChange}
          type="email"
          name="email"
          placeholder="이메일"
          className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <button
          className="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition"
          onClick={handleRegister}
        >
          회원가입
        </button>
      </div>
    </div>
  );
};

export default Register;
