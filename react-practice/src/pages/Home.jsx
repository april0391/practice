import React from "react";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

const Home = () => {
  const nav = useNavigate();
  const handleNavigate = (path) => {
    nav(path);
  };
  const buttons = [
    { text: "게시판 이동", path: "/board" },
    { text: "회원 가입", path: "/signup" },
  ];

  return (
    <>
      <div>Home</div>
      {buttons.map((button, index) => (
        <Button
          key={index}
          text={button.text}
          onClick={() => handleNavigate(button.path)}
        />
      ))}
    </>
  );
};

export default Home;
