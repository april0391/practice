import { useState } from "react";
import { Link } from "expo-router";
import { Checkbox } from "expo-checkbox";

import { useSignUpContext } from "@/components/auth/SignUpProvider";

import Button from "@/components/common/Button";
import { ThemedText, ThemedView } from "@/components/common/Themed";

export default function AgreementsScreen() {
  const [agreements, setAgreements] = useState({
    agreedToTerms: false,
    agreedToPrivacyPolicy: false,
    agreedToLocationServices: false,
  });
  const [error, setError] = useState("");

  const { updateAndNext } = useSignUpContext();

  function handleValueChange(field: keyof typeof agreements, value: boolean) {
    setAgreements((prev) => ({ ...prev, [field]: value }));

    if (Object.values({ ...agreements, [field]: value }).every(Boolean)) {
      setError("");
    }
  }

  function handleNext() {
    const allAgreed = Object.values(agreements).every(
      (field) => field === true
    );

    if (!allAgreed) {
      setError("모든 항목에 동의가 필요합니다");
      return;
    }

    updateAndNext("agreedToPolicies", true);
  }

  return (
    <ThemedView className="flex-1 gap-5 p-7">
      <ThemedText type="title">RNstagram 약관 및 정책에 동의</ThemedText>
      <ThemedText>계정을 만들려면 모든 약관에 동의해주세요</ThemedText>
      <ThemedText type="subtitle">이용 약관</ThemedText>
      <ThemedView className="gap-3 border border-gray-400 rounded-2xl p-3">
        <ThemedView className="flex-row justify-between items-center">
          <ThemedView>
            <ThemedText type="defaultSemiBold">이용 약관(필수)</ThemedText>
            <Link href="/agreements-details" push>
              <ThemedText type="link">더 알아보기</ThemedText>
            </Link>
          </ThemedView>
          <Checkbox
            className="mr-3"
            value={agreements.agreedToTerms}
            color="blue"
            onValueChange={(value) => handleValueChange("agreedToTerms", value)}
          />
        </ThemedView>
        <ThemedView className="flex-row justify-between items-center">
          <ThemedView>
            <ThemedText type="defaultSemiBold">
              개인정보처리방침(필수)
            </ThemedText>
            <Link href="/agreements-details" push>
              <ThemedText type="link">더 알아보기</ThemedText>
            </Link>
          </ThemedView>
          <Checkbox
            className="mr-3"
            value={agreements.agreedToPrivacyPolicy}
            color="blue"
            onValueChange={(value) =>
              handleValueChange("agreedToPrivacyPolicy", value)
            }
          />
        </ThemedView>
        <ThemedView className="flex-row justify-between items-center">
          <ThemedView>
            <ThemedText type="defaultSemiBold">위치 기반 기능(필수)</ThemedText>
            <Link href="/agreements-details" push>
              <ThemedText type="link">더 알아보기</ThemedText>
            </Link>
          </ThemedView>
          <Checkbox
            className="mr-3"
            value={agreements.agreedToLocationServices}
            color="blue"
            onValueChange={(value) =>
              handleValueChange("agreedToLocationServices", value)
            }
          />
        </ThemedView>
      </ThemedView>
      {error && <ThemedText className="text-red-500">{error}</ThemedText>}
      <Button onPress={handleNext}>동의</Button>
    </ThemedView>
  );
}
