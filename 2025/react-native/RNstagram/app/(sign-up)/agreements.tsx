import { useState } from "react";
import { Link } from "expo-router";

import { useSignUpContext } from "@/components/auth/SignUpProvider";

import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";

export default function AgreementsScreen() {
  const [agreements, setAgreements] = useState({
    agreedToTerms: false,
    agreedToPrivacyPolicy: false,
    agreedToLocationServices: false,
  });

  const { updateSignUpField } = useSignUpContext();

  function handleNext() {}

  return (
    <ThemedView className="flex-1 gap-5 p-7">
      <ThemedText type="title">RNstagram 약관 및 정책에 동의</ThemedText>
      <ThemedText>계정을 만들려면 모든 약관에 동의해주세요</ThemedText>
      <ThemedText type="subtitle">이용 약관</ThemedText>
      <ThemedView className="gap-3 border border-gray-400 rounded-2xl p-3">
        <ThemedView>
          <ThemedText type="defaultSemiBold">이용 약관(필수)</ThemedText>
          <Link href="/agreements-detail">
            <ThemedText type="link">더 알아보기</ThemedText>
          </Link>
        </ThemedView>
        <ThemedView>
          <ThemedText type="defaultSemiBold">개인정보처리방침(필수)</ThemedText>
          <Link href="/agreements-detail">
            <ThemedText type="link">더 알아보기</ThemedText>
          </Link>
        </ThemedView>
        <ThemedView>
          <ThemedText type="defaultSemiBold">위치 기반 기능(필수)</ThemedText>
          <Link href="/agreements-detail">
            <ThemedText type="link">더 알아보기</ThemedText>
          </Link>
        </ThemedView>
      </ThemedView>
      <Button onPress={handleNext}>동의</Button>
    </ThemedView>
  );
}
