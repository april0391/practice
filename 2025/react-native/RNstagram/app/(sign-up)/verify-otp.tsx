import { useCallback, useRef, useState } from "react";
import { OtpInput } from "react-native-otp-entry";
import type { BottomSheetModal } from "@gorhom/bottom-sheet";

import { verifyOtp } from "@/utils/auth";
import { useSignUpContext } from "@/components/auth/SignUpProvider";

import Button from "@/components/common/Button";
import { ThemedText, ThemedView } from "@/components/common/Themed";

import OtpBottomSheetModal from "@/components/auth/OtpBottomSheetModal";

export default function VerifyOtpScreen() {
  const [otp, setOtp] = useState("");
  const [error, setError] = useState("");
  const bottomSheetModalRef = useRef<BottomSheetModal>(null);

  const {
    signUpData: { email },
    updateAndNext,
  } = useSignUpContext();

  async function handleVerifyOtp(filledOtp: string | null) {
    if (filledOtp == null && otp.length === 0) {
      setError("코드가 필요합니다. 이메일에서 코드를 확인하세요.");
      return;
    } else if (filledOtp == null && otp.length !== 6) {
      setError(
        "코드가 너무 짧습니다. 정확한 코드를 입력했는지 확인하고 다시 시도하세요."
      );
      return;
    }

    setError("");

    const res = await verifyOtp(email, filledOtp || otp);

    if (!res.success) {
      setError(res.error.message);
      return;
    }

    updateAndNext("email", email);
  }

  const openOtpModal = useCallback(() => {
    bottomSheetModalRef.current?.present();
  }, []);

  return (
    <ThemedView className="flex-1 gap-5 p-7">
      <ThemedText type="title">인증 코드 입력</ThemedText>
      <ThemedText>
        계정을 확인하려면 {email} 주소로 전송된 6자리 코드를 입력하세요.
      </ThemedText>
      <OtpInput
        numberOfDigits={6}
        onTextChange={(text) => setOtp(text)}
        onFilled={(filledOtp) => handleVerifyOtp(filledOtp)}
      />
      {error && <ThemedText className="text-red-600">{error}</ThemedText>}
      <Button onPress={() => handleVerifyOtp(null)} variant="default">
        다음
      </Button>
      <Button onPress={openOtpModal} variant="outline">
        코드를 받지 못했습니다
      </Button>

      <OtpBottomSheetModal
        bottomSheetModalRef={bottomSheetModalRef}
        email={email}
      />
    </ThemedView>
  );
}
