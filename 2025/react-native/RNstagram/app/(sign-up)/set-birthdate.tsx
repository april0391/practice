import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";
import DateTimePicker from "@react-native-community/datetimepicker";
import { useRouter } from "expo-router";
import { useState } from "react";
import {
  Modal,
  Platform,
  Pressable,
  Text,
  TouchableOpacity,
  View,
} from "react-native";

export default function SetBirthdateScreen() {
  const [showPicker, setShowPicker] = useState(false);
  const [date, setDate] = useState(new Date());
  const [displayBirthdate, setDisplayBirthdate] = useState("");

  const router = useRouter();

  function handleChangeBirthDate(_event: any, selectedDate?: Date) {
    if (selectedDate) {
      setDate(selectedDate);

      const year = selectedDate.getFullYear();
      const month = selectedDate.getMonth() + 1;
      const day = selectedDate.getDate();

      const formatted = `${year}년 ${month}월 ${day}일`;

      setDisplayBirthdate(formatted);
    }

    if (Platform.OS === "android") {
      setShowPicker(false);
    }
  }

  function handleNext() {
    // router.push("/set-next-screen"); // 다음 화면으로 이동
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
        <Text>{displayBirthdate ? displayBirthdate : "생년월일 선택"}</Text>
      </TouchableOpacity>

      <Modal
        visible={showPicker}
        transparent
        animationType="fade"
        onRequestClose={() => setShowPicker(false)}
      >
        <Pressable
          style={{
            flex: 1,
            backgroundColor: "rgba(0,0,0,0.3)",
            justifyContent: "center",
          }}
          onPress={() => setShowPicker(false)}
        >
          <View>
            <DateTimePicker
              value={date}
              mode="date"
              display="spinner"
              locale="ko-KR"
              maximumDate={new Date()}
              onChange={handleChangeBirthDate}
            />
          </View>
        </Pressable>
      </Modal>

      <Button onPress={handleNext}>다음</Button>
    </ThemedView>
  );
}
