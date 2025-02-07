function TailwindTest() {
  return (
    <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-blue-500 to-purple-600">
      <div className="max-w-sm bg-white rounded-lg shadow-lg overflow-hidden transform hover:scale-105 transition-transform duration-300">
        <img
          className="w-full h-48 object-cover"
          src="https://via.placeholder.com/400x200"
          alt="Sample"
        />
        <div className="p-6">
          <h2 className="text-xl font-semibold text-gray-800">
            Welcome to Tailwind CSS
          </h2>
          <p className="text-gray-600 mt-2">
            Tailwind CSS는 빠르고 유연한 유틸리티 기반 CSS 프레임워크로,
            직관적인 클래스를 사용해 스타일링을 간편하게 만듭니다.
          </p>
          <div className="mt-4">
            <button className="px-4 py-2 bg-blue-500 text-white font-semibold rounded-lg shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75">
              Learn More
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default TailwindTest;
