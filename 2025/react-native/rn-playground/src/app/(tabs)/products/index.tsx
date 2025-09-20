import { useState } from "react";
import { ActivityIndicator, FlatList, View } from "react-native";
import { Image as ExpoImage } from "expo-image";
import { cssInterop } from "nativewind";
import { clsx } from "clsx";
import useSWR from "swr";
import useSWRInfinite from "swr/infinite";
import useAuth from "@/stores/auth-store";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Text } from "@/components/ui/text";

const Image = cssInterop(ExpoImage, {
  className: "style",
});

const apiUrl = `${process.env.EXPO_PUBLIC_SUPABASE_URL_AVD}/functions/v1/api`;

const fetcher = async (url: string, token: string) => {
  const res = await fetch(`${apiUrl}${url}`, {
    headers: { Authorization: `Bearer ${token}` },
  });

  if (!res.ok) {
    throw new Error("An error occurred while fetching the data.");
  }

  return res.json();
};

const blurhash =
  "|rF?hV%2WCj[ayj[a|j[az_NaeWBj@ayfRayfQfQM{M|azj[azf6fQfQfQIpWXofj[ayj[j[fQayWCoeoeaya}j[ayfQa{oLj?j[WVj[ayayj[fQoff7azayj[ayj[j[ayofayayayj[fQj[ayayj[ayfjj[j[ayjuayj[";

export default function ProductsScreen() {
  const { session } = useAuth();
  const token = session!.access_token;

  const [currentPage, setCurrentPage] = useState(1);
  const [buttonBlock, setButtonBlock] = useState(0);

  const { data, isLoading, error, isValidating } = useSWR(
    [`/products?page=${currentPage}`, token],
    ([url, token]) => fetcher(url, token)
  );

  if (isLoading) return <Text>Loading...</Text>;
  if (error) return <Text>Error: {error.message}</Text>;
  if (!data) return <Text>No data</Text>;

  const { data: products, total, perPage } = data;

  const totalPage = Math.ceil(total / perPage);
  const maxButtons = 5;

  const start = buttonBlock * maxButtons + 1;
  const end = Math.min(start + maxButtons - 1, totalPage);

  const handlePrevBlock = () => {
    if (buttonBlock > 0) {
      const newBlock = buttonBlock - 1;
      setButtonBlock(newBlock);
      setCurrentPage(newBlock * maxButtons + 1);
    }
  };

  const handleNextBlock = () => {
    if ((buttonBlock + 1) * maxButtons < totalPage) {
      const newBlock = buttonBlock + 1;
      setButtonBlock(newBlock);
      setCurrentPage(newBlock * maxButtons + 1);
    }
  };

  return (
    <View className="flex-1 p-2">
      <FlatList
        data={products}
        keyExtractor={(item) => item.id}
        numColumns={2}
        columnWrapperStyle={{ justifyContent: "space-between" }}
        renderItem={({ item }) => (
          <View className="flex-1 m-2 rounded-sm p-2">
            <Image
              className="w-full h-[150px] flex-1"
              source={item.imageUrl}
              contentFit="cover"
              placeholder={blurhash}
              transition={500}
            />
            <Text className="mt-2 text-base font-semibold text-gray-800">
              {item.name}
            </Text>
            <Avatar alt="Avatar">
              <AvatarImage source={{ uri: item.profiles.avatarUrl }} />
              <AvatarFallback>
                <Text>ZN</Text>
              </AvatarFallback>
            </Avatar>
            <Text className="text-sm text-gray-600">₩ {item.price}</Text>
          </View>
        )}
        ListFooterComponent={
          <View className="flex-row gap-1 justify-between w-[90%] mx-auto">
            <Button onPress={handlePrevBlock} disabled={buttonBlock === 0}>
              <Text>{"<"}</Text>
            </Button>

            {Array.from({ length: end - start + 1 }, (_, idx) => {
              const pageNum = start + idx;
              return (
                <Button
                  key={pageNum}
                  className={clsx(currentPage === pageNum && "bg-green-300")}
                  variant="outline"
                  onPress={() => setCurrentPage(pageNum)}
                >
                  <Text>{pageNum}</Text>
                </Button>
              );
            })}

            <Button onPress={handleNextBlock} disabled={end === totalPage}>
              <Text>{">"}</Text>
            </Button>
          </View>
        }
      />
    </View>
  );
}
