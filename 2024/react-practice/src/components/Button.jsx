const Button = ({ text, onClick }) => {
  return (
    <button
      className="px-6 py-3 bg-blue-500 text-white font-semibold rounded-lg shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition duration-300"
      onClick={onClick}
    >
      {text}
    </button>
  );
};

export default Button;
