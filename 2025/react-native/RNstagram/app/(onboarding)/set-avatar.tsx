import { useRef } from "react";
import { Image } from "react-native";
import { type BottomSheetModal } from "@gorhom/bottom-sheet";

import {
  ThemedSafeAreaView,
  ThemedText,
  ThemedView,
} from "@/components/common/Themed";
import Button from "@/components/common/Button";
import GorhomBottomSheetModal from "@/components/common/GorhomBottomSheetModal";

export default function SetAvatarScreen() {
  const modalRef = useRef<BottomSheetModal>(null);

  const openModal = () => modalRef.current?.present();
  const closeModal = () => modalRef.current?.dismiss();

  function handleAddAvatar() {
    openModal();
  }

  function handleSkip() {}

  return (
    <ThemedSafeAreaView
      className="flex-1 justify-between px-4 pb-6"
      edges={["bottom"]}
    >
      <ThemedView className="gap-3">
        <ThemedText type="title">프로필 사진 추가</ThemedText>
        <ThemedText>
          친구들이 회원님을 알아볼 수 있도록 프로필 사진을 추가하세요. 프로필
          사진은 모든 사람에게 공개됩니다.
        </ThemedText>
        <ThemedView
          style={{
            shadowColor: "#000",
            shadowOffset: { width: 0, height: 2 },
            shadowOpacity: 0.2,
            shadowRadius: 4,

            elevation: 15,
          }}
          className="items-center justify-center mt-5 mx-auto p-2 rounded-full w-60 h-60"
        >
          <Image
            source={require("@/assets/images/default-avatar.png")}
            className="rounded-full w-56 h-56"
          />
        </ThemedView>
      </ThemedView>
      <ThemedView className="gap-3">
        <Button onPress={handleAddAvatar}>사진 추가</Button>
        <Button variant="outline" onPress={handleSkip}>
          건너뛰기
        </Button>
      </ThemedView>

      <GorhomBottomSheetModal
        modalRef={modalRef}
        className="items-start w-full"
      >
        <ThemedView className="gap-5 w-full">
          <ThemedText className="text-3xl font-semibold">사진 추가</ThemedText>
          <ThemedView className="border p-2 gap-1.5 border-gray-300 rounded-2xl">
            <Button variant="ghost" className="items-start" onPress={() => {}}>
              갤러리에서 선택
            </Button>
            <Button variant="ghost" className="items-start" onPress={() => {}}>
              사진 찍기
            </Button>
          </ThemedView>
        </ThemedView>
      </GorhomBottomSheetModal>
    </ThemedSafeAreaView>
  );
}
