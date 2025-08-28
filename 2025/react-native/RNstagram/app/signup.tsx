import Button from "@/components/common/Button";
import ThemedSafeAreaView from "@/components/ThemedSafeAreaView";
import { ThemedText } from "@/components/ThemedText";
import { supabase } from "@/utils/supabase";

import { StyleSheet, TextInput } from "react-native";

export default function SignupScreen() {
  async function handleVerifyEmail() {
    const { data, error } = await supabase.auth.signInWithOtp({
      email: "ghwns9393@naver.com",
      options: { shouldCreateUser: false },
    });

    console.log("data", data);
    console.log("error", error);
  }

  async function handleOtp() {
    const { data, error } = await supabase.auth.verifyOtp({
      email: "ghwns9393@naver.com",
      type: "signup",
      token: "",
    });

    console.log("data", data);
    console.log("error", error);
  }

  return (
    <ThemedSafeAreaView style={styles.container}>
      <ThemedText type="title">이메일 주소 입력</ThemedText>
      <ThemedText>
        회원님에게 연락할 수 있는 이메일 주소를 입력하세요. 이 이메일 주소는
        프로필에서 다른 사람에게 공개되지 않습니다.
      </ThemedText>
      <TextInput
        style={styles.input}
        placeholder="이메일 주소"
        autoCapitalize="none"
        autoCorrect={false}
      />
      <ThemedText>
        또한 회원님은 저희가 보내는 이메일을 받게 되며 언제든지 이를 수신 거부할
        수 있습니다.
      </ThemedText>
      <Button onPress={handleVerifyEmail} textColor="white">
        다음
      </Button>
      <Button onPress={handleOtp} textColor="white">
        확인
      </Button>
    </ThemedSafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    gap: 16,
    padding: 15,
  },
  input: {
    borderWidth: 1,
    borderColor: "gray",
    borderRadius: 12,
    padding: 17,
  },
});
