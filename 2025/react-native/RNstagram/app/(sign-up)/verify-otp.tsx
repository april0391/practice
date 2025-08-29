import OtpBottomSheetModal from "@/components/auth/OtpBottomSheetModal";
import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";
import { supabase } from "@/utils/supabase";
import { BottomSheetModal } from "@gorhom/bottom-sheet";
import { useLocalSearchParams, useRouter } from "expo-router";
import { useCallback, useRef, useState } from "react";
import { Text } from "react-native";
import { OtpInput } from "react-native-otp-entry";

export default function VerifyOtpScreen() {
  const { email }: { email: string } = useLocalSearchParams();
  const router = useRouter();

  const bottomSheetModalRef = useRef<BottomSheetModal>(null);

  const [otp, setOtp] = useState("");
  const [error, setError] = useState("");

  async function handleVerifyOtp(token: string | null) {
    if (token == null && otp.length === 0) {
      setError("코드가 필요합니다. 이메일에서 코드를 확인하세요.");
      return;
    } else if (token == null && otp.length !== 6) {
      setError(
        "코드가 너무 짧습니다. 정확한 코드를 입력했는지 확인하고 다시 시도하세요."
      );
      return;
    }

    setError("");

    const { data, error } = await supabase.auth.verifyOtp({
      email,
      token: token!,
      type: "email",
    });

    console.log("data", data);
    console.log("error", error);

    if (error) {
      setError("코드가 유효하지 않습니다. 새 코드를 요청하세요.");
      return;
    }

    router.navigate("/set-password");
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
        onFilled={(token) => handleVerifyOtp(token)}
      />
      {error && <Text className="text-red-600">{error}</Text>}
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
