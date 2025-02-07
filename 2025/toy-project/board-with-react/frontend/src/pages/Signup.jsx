import { useState } from "react";
import axios from "axios";

function Signup() {
  const [formData, setFormData] = useState({
    nickname: "",
    email: "",
    password: "",
  });
  const [loading, setLoading] = useState(false); // 로딩 상태 추가
  const [error, setError] = useState(""); // 에러 상태 추가

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(""); // 에러 초기화
    console.log(formData);

    try {
      const response = await axios.post(
        "http://localhost:8080/users",
        formData
      );

      console.log("회원가입 성공:", response.data);
      // 회원가입 성공 시, 필요한 작업 (예: 리디렉션, 메시지 표시 등)
    } catch (err) {
      console.error("회원가입 오류:", err);
      setError(err.response ? err.response.error : "회원가입 실패");
    } finally {
      setLoading(false); // 로딩 상태 종료
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-green-400 to-blue-500">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-bold text-gray-800 mb-6 text-center">
          회원가입
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="nickname"
              className="block text-sm font-medium text-gray-700"
            >
              닉네임
            </label>
            <input
              type="text"
              id="nickname"
              name="nickname"
              value={formData.nickname}
              onChange={handleChange}
              placeholder="홍길동"
              className="mt-1 block w-full px-4 py-2 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
            />
          </div>
          <div>
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-700"
            >
              이메일
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="example@email.com"
              className="mt-1 block w-full px-4 py-2 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
            />
          </div>
          <div>
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700"
            >
              비밀번호
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="********"
              className="mt-1 block w-full px-4 py-2 border rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white py-2 px-4 rounded-md shadow-lg hover:bg-blue-600 transition"
            disabled={loading} // 로딩 중에는 버튼 비활성화
          >
            {loading ? "회원가입 중..." : "회원가입"}
          </button>
        </form>
        {error && <p className="mt-4 text-red-500 text-center">{error}</p>}{" "}
        {/* 에러 메시지 표시 */}
        <p className="mt-4 text-sm text-gray-600 text-center">
          이미 계정이 있으신가요?{" "}
          <a href="/login" className="text-blue-500 hover:underline">
            로그인
          </a>
        </p>
      </div>
    </div>
  );
}

export default Signup;
