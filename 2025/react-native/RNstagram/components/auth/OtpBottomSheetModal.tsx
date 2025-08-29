import { supabase } from "@/utils/supabase";
import {
  BottomSheetBackdrop,
  BottomSheetModal,
  BottomSheetView,
  useBottomSheetModal,
} from "@gorhom/bottom-sheet";
import { useRouter } from "expo-router";
import { View } from "react-native";
import Toast from "react-native-toast-message";
import Button from "../common/Button";

type Props = {
  bottomSheetModalRef: React.RefObject<BottomSheetModal | null>;
  email: string;
};

export default function OtpBottomSheetModal({
  bottomSheetModalRef,
  email,
}: Props) {
  const { dismiss } = useBottomSheetModal();

  const router = useRouter();

  async function handleResendOtp() {
    const { error } = await supabase.auth.signInWithOtp({ email });

    if (error) {
      // 에러 핸들링
      return;
    }

    dismiss();
    Toast.show({
      text1: "인증 코드가 재전송되었습니다.",
      position: "bottom",
    });
  }

  return (
    <BottomSheetModal
      ref={bottomSheetModalRef}
      snapPoints={["70%"]}
      backdropComponent={(props) => (
        <BottomSheetBackdrop
          {...props}
          disappearsOnIndex={-1} // 시트 닫히면 사라짐
          appearsOnIndex={0} // 시트 열리면 나타남
          pressBehavior="close" // 백드롭 눌러서 시트 닫기
        />
      )}
    >
      <BottomSheetView className="flex-1 h-full items-center p-6">
        <View className="w-full border border-gray-300 p-3 rounded-2xl">
          <Button
            className="items-start my-1.5"
            variant="ghost"
            onPress={handleResendOtp}
          >
            인증 코드 재전송
          </Button>
          <Button
            className="items-start my-1.5"
            variant="ghost"
            onPress={() => {
              dismiss();
              router.navigate("/(sign-up)/send-otp");
            }}
          >
            이메일 주소 변경
          </Button>
        </View>
      </BottomSheetView>
    </BottomSheetModal>
  );
}
