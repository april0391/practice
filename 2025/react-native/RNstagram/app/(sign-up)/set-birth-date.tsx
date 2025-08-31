import { useState } from "react";
import { Text, TouchableOpacity } from "react-native";
import DateTimePicker from "@react-native-community/datetimepicker";

import { useSignUpContext } from "@/components/auth/SignUpProvider";

import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";

export default function SetBirthDateScreen() {
  const [birthDate, setBirthDate] = useState<Date | null>(null);
  const [displayBirthDate, setDisplayBirthDate] = useState("");
  const [error, setError] = useState("");

  const [showPicker, setShowPicker] = useState(false);

  const { updateAndNext } = useSignUpContext();

  function handleChangeBirthDate({ type }: any, selectedDate?: Date) {
    if (type === "dismissed" || !selectedDate) {
      setShowPicker(false);
      return;
    }

    setBirthDate(selectedDate);

    const year = selectedDate.getFullYear();
    const month = selectedDate.getMonth() + 1;
    const day = selectedDate.getDate();

    const formatted = `${year}년 ${month}월 ${day}일`;

    setDisplayBirthDate(formatted);
    setShowPicker(false);
  }

  function handleNext() {
    if (!birthDate) {
      setError("생년월일을 선택해주세요");
      return;
    }

    updateAndNext("birthDate", birthDate.toISOString().slice(0, 10));
  }

  return (
    <ThemedView className="flex-1 gap-5 p-7">
      <ThemedText type="title">생년월일 선택</ThemedText>
      <ThemedText>
        비즈니스, 반려동물 또는 기타 목적으로 이 계정을 만드는 경우에도 회원님의
        실제 생년월일을 사용하세요. 이 생년월일 정보는 회원님이 공유하지 않는 한
        다른 사람에게 공개되지 않습니다.
      </ThemedText>

      <TouchableOpacity
        onPress={() => setShowPicker(true)}
        className="border border-gray-400 rounded-xl p-4 bg-gray-100"
      >
        <Text>{displayBirthDate || "생년월일 선택"}</Text>
      </TouchableOpacity>

      {showPicker && (
        <DateTimePicker
          value={birthDate || new Date()}
          mode="date"
          display="spinner"
          locale="ko-KR"
          maximumDate={new Date()}
          onChange={handleChangeBirthDate}
        />
      )}

      {error && <Text className="text-red-500">{error}</Text>}
      <Button onPress={handleNext}>다음</Button>
    </ThemedView>
  );
}
