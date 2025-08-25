import { useState } from "react";

const lastDrwNo = 1155;
const options = [];
for (let i = lastDrwNo; i >= 1; i--) {
  options.push(<option key={i}>{i}회</option>);
}

const CheckWinNumber = () => {
  const [value, setValue] = useState();

  fetch(
    "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=1155"
  )
    .then((res) => res.text())
    .catch((error) => {
      console.error("Error:", error);
    });

  return (
    <>
      <select>{options}</select>
    </>
  );
};

export default CheckWinNumber;
