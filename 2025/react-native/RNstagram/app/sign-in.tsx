import { useState } from "react";
import { ActivityIndicator, StyleSheet, useColorScheme } from "react-native";
import { useRouter } from "expo-router";

import { useAuthContext } from "@/components/auth/AuthProvider";

import { AntDesign, Ionicons } from "@expo/vector-icons";
import Button from "@/components/common/Button";
import {
  SafeAreaView,
  View,
  Text,
  TextInput,
} from "@/components/common/Themed";

export default function LoginScreen() {
  const router = useRouter();
  const { signIn } = useAuthContext();
  const colorScheme = useColorScheme();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  function handleSignIn() {
    setIsLoading(true);
    signIn(email, password);
  }

  function handleForgotPassword() {}

  function handleSignup() {
    router.push("/(sign-up)/send-otp");
  }

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.subContainer}>
        <AntDesign
          color={colorScheme === "dark" ? "white" : "black"}
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
          <Ionicons
            name="logo-react"
            size={20}
            color={colorScheme === "dark" ? "white" : "black"}
          />
          <Text>React Native</Text>
        </View>
      </View>
    </SafeAreaView>
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
