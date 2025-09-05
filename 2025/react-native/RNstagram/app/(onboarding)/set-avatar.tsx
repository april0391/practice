import { useRef, useState } from "react";
import { Image } from "react-native";
import * as ImagePicker from "expo-image-picker";
import { type BottomSheetModal } from "@gorhom/bottom-sheet";

import { SafeAreaView, Text, View } from "@/components/common/Themed";
import Button from "@/components/common/Button";
import GorhomBottomSheetModal from "@/components/common/GorhomBottomSheetModal";

export default function SetAvatarScreen() {
  const modalRef = useRef<BottomSheetModal>(null);
  const [image, setImage] = useState("");

  const openModal = () => modalRef.current?.present();
  const closeModal = () => modalRef.current?.dismiss();

  function handleAddAvatar() {
    openModal();
  }

  const pickImage = async () => {
    // No permissions request is necessary for launching the image library
    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ["images"],
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });

    console.log(result);

    if (!result.canceled) {
      setImage(result.assets[0].uri);
    }
  };

  function handleSkip() {}

  return (
    <SafeAreaView
      className="flex-1 justify-between px-4 pb-6"
      edges={["bottom"]}
    >
      <View className="gap-3">
        <Text type="title">프로필 사진 추가</Text>
        <Text>
          친구들이 회원님을 알아볼 수 있도록 프로필 사진을 추가하세요. 프로필
          사진은 모든 사람에게 공개됩니다.
        </Text>
        <View
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
            source={
              image
                ? { uri: image }
                : require("@/assets/images/default-avatar.png")
            }
            className="rounded-full w-56 h-56"
          />
        </View>
      </View>
      <View className="gap-3 border-t-[1px] border-gray-200 pt-5">
        <Button onPress={handleAddAvatar}>사진 추가</Button>
        <Button variant="outline" onPress={handleSkip}>
          건너뛰기
        </Button>
      </View>

      <GorhomBottomSheetModal
        modalRef={modalRef}
        className="items-start w-full"
      >
        <View className="gap-5 w-full">
          <Text className="text-3xl font-semibold">사진 추가</Text>
          <View className="border p-2 gap-1.5 border-gray-300 rounded-2xl">
            <Button variant="ghost" className="items-start" onPress={pickImage}>
              갤러리에서 선택
            </Button>
            <Button variant="ghost" className="items-start" onPress={() => {}}>
              사진 찍기
            </Button>
          </View>
        </View>
      </GorhomBottomSheetModal>
    </SafeAreaView>
  );
}
