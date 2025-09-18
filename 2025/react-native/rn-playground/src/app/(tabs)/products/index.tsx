import { ActivityIndicator, FlatList, Text, View } from "react-native";
import { Image as ExpoImage } from "expo-image";
import { cssInterop } from "nativewind";
import useSWRInfinite from "swr/infinite";

const Image = cssInterop(ExpoImage, {
  className: "style",
});

const apiUrl = `${process.env.EXPO_PUBLIC_SUPABASE_URL_AVD}/functions/v1/api`;

const fetcher = (url: string) =>
  fetch(`${apiUrl}${url}`, {
    headers: {
      Authorization: `Bearer ${process.env.EXPO_PUBLIC_SUPABASE_ANON_KEY}`,
    },
  }).then((res) => res.json());

const getKey = (pageIndex: number, previousPageData: any) => {
  // 이전 페이지가 빈 배열이면 끝
  if (previousPageData && previousPageData.length === 0) return null;

  return `/products?page=${pageIndex + 1}`;
};

export default function ProductsScreen() {
  const { data, isLoading, error, size, setSize, isValidating } =
    useSWRInfinite(
      getKey,
      fetcher
      // {
      //   revalidateOnFocus: false,
      //   revalidateOnMount: true,
      //   revalidateOnReconnect: false,
      // }
    );

  if (isLoading) return <Text>Loading...</Text>;
  if (error) return <Text>Error: {JSON.stringify(error, null, 2)}</Text>;
  if (!data) return <Text>No data</Text>;

  const products = data.flat();

  console.log(data.length);

  // 마지막 페이지 판단: 이전 페이지가 비었거나 현재 마지막 페이지가 없으면 끝
  const isEmpty = data[0]?.length === 0;
  const isReachingEnd = isEmpty || data[data.length - 1].length === 0;
  const isLoadingMore = isValidating && !isReachingEnd;

  console.log("isEmpty", isEmpty);
  console.log("isReachingEnd", isReachingEnd);
  console.log("isLoadingMore", isLoadingMore);

  return (
    <FlatList
      data={products}
      keyExtractor={(item) => item.id}
      numColumns={2}
      columnWrapperStyle={{ justifyContent: "space-between" }}
      renderItem={({ item }) => (
        <View className="flex-1 m-2 rounded-sm p-2">
          <View className="h-[150px]">
            <Image
              className="w-full flex-1"
              source={item.imageUrl}
              contentFit="cover"
              transition={500}
            />
          </View>
          <Text className="mt-2 text-base font-semibold text-gray-800">
            {item.name}
          </Text>
          <Text className="text-sm text-gray-600">₩ {item.price}</Text>
        </View>
      )}
      onEndReached={() => {
        if (!isLoadingMore && !isReachingEnd) {
          setSize(size + 1);
        }
      }}
      onEndReachedThreshold={0.5}
      ListFooterComponent={
        isLoadingMore ? <ActivityIndicator style={{ margin: 16 }} /> : null
      }
    />
  );
}
