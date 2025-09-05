import { useState } from "react";
import { Link } from "expo-router";
import { Checkbox } from "expo-checkbox";

import { useSignUpContext } from "@/components/auth/SignUpProvider";

import Button from "@/components/common/Button";
import { Text, View } from "@/components/common/Themed";

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
    <View className="flex-1 gap-5 p-7">
      <Text type="title">RNstagram 약관 및 정책에 동의</Text>
      <Text>계정을 만들려면 모든 약관에 동의해주세요</Text>
      <Text type="subtitle">이용 약관</Text>
      <View className="gap-3 border border-gray-400 rounded-2xl p-3">
        <View className="flex-row justify-between items-center">
          <View>
            <Text type="defaultSemiBold">이용 약관(필수)</Text>
            <Link href="/agreements-details" push>
              <Text type="link">더 알아보기</Text>
            </Link>
          </View>
          <Checkbox
            className="mr-3"
            value={agreements.agreedToTerms}
            color="blue"
            onValueChange={(value) => handleValueChange("agreedToTerms", value)}
          />
        </View>
        <View className="flex-row justify-between items-center">
          <View>
            <Text type="defaultSemiBold">개인정보처리방침(필수)</Text>
            <Link href="/agreements-details" push>
              <Text type="link">더 알아보기</Text>
            </Link>
          </View>
          <Checkbox
            className="mr-3"
            value={agreements.agreedToPrivacyPolicy}
            color="blue"
            onValueChange={(value) =>
              handleValueChange("agreedToPrivacyPolicy", value)
            }
          />
        </View>
        <View className="flex-row justify-between items-center">
          <View>
            <Text type="defaultSemiBold">위치 기반 기능(필수)</Text>
            <Link href="/agreements-details" push>
              <Text type="link">더 알아보기</Text>
            </Link>
          </View>
          <Checkbox
            className="mr-3"
            value={agreements.agreedToLocationServices}
            color="blue"
            onValueChange={(value) =>
              handleValueChange("agreedToLocationServices", value)
            }
          />
        </View>
      </View>
      {error && <Text className="text-red-500">{error}</Text>}
      <Button onPress={handleNext}>동의</Button>
    </View>
  );
}
