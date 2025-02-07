import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const WritePost = () => {
  const [formData, setFormData] = useState({
    title: "",
    content: "",
  });
  const navigate = useNavigate();

  const handleOnChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white rounded-lg shadow-md">
      <h1 className="text-3xl font-bold mb-6 text-center">게시글 작성</h1>
      <div className="space-y-4">
        <div>
          <input
            type="text"
            name="title"
            placeholder="제목을 입력하세요"
            value={formData.title}
            onChange={handleOnChange}
            className="w-full p-4 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>

        <div>
          <textarea
            name="content"
            placeholder="내용을 입력하세요"
            value={formData.content}
            onChange={handleOnChange}
            className="w-full p-4 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 h-48"
          ></textarea>
        </div>

        {/* 작성 완료 버튼 */}
        <div className="flex justify-center">
          <button
            onClick={async () => {
              await axios.post(
                `${import.meta.env.VITE_BACKEND_URL}/posts`,
                formData,
                {
                  headers: {
                    Authorization: `Bearer ${sessionStorage.getItem("token")}`,
                  },
                }
              );
              alert("작성 완료");
              navigate("/board");
            }}
            className="w-full bg-blue-500 text-white py-2 rounded-md shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            작성 완료
          </button>
        </div>
      </div>
    </div>
  );
};

export default WritePost;
