import { useState } from "react";
import { ActivityIndicator, StyleSheet, TextInput, View } from "react-native";
import { useRouter } from "expo-router";

import { useAuthContext } from "@/components/auth/AuthProvider";
import ThemedSafeAreaView from "@/components/ThemedSafeAreaView";
import Button from "@/components/common/Button";
import { ThemedText } from "@/components/common/Themed";
import Icon from "@/components/ui/Icon";

export default function LoginScreen() {
  const router = useRouter();
  const { signIn } = useAuthContext();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  async function handleSignIn() {
    setIsLoading(true);
    await signIn(email, password);
  }

  function handleForgotPassword() {}

  function handleSignup() {
    router.push("/(sign-up)/send-otp");
  }

  return (
    <ThemedSafeAreaView style={styles.container}>
      <View style={styles.subContainer}>
        <Icon
          color="black"
          library="antDesign"
          name="instagram"
          size={100}
          style={styles.icon}
        />
        <TextInput
          style={styles.input}
          placeholder="이메일 주소"
          onChangeText={setEmail}
        />
        <TextInput
          style={styles.input}
          placeholder="비밀번호"
          onChangeText={setPassword}
          secureTextEntry={true}
          autoCapitalize="none"
        />
        <View style={styles.buttonContainer}>
          <Button onPress={handleSignIn}>
            로그인 {isLoading && <ActivityIndicator />}
          </Button>
          <Button onPress={handleForgotPassword} variant="ghost">
            비밀번호를 잊으셨나요?
          </Button>
        </View>
      </View>
      <View style={styles.subContainer}>
        <Button onPress={handleSignup} variant="outline">
          새 계정 만들기
        </Button>
        <View style={styles.footer}>
          <Icon library="ionicons" name="logo-react" size={20} color="black" />
          <ThemedText>React Native</ThemedText>
        </View>
      </View>
    </ThemedSafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "space-between",
    alignItems: "center",
    paddingTop: 100,
    paddingBottom: 20,
    padding: 20,
    width: "100%",
  },
  subContainer: {
    width: "95%",
    gap: 12,
  },
  icon: {
    margin: "auto",
    marginBottom: 90,
  },
  input: {
    borderWidth: 1,
    borderColor: "gray",
    borderRadius: 10,
    paddingHorizontal: 10,
  },
  buttonContainer: {
    width: "100%",
    gap: 4,
  },
  footer: {
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    gap: 6,
  },
});
