import { useEffect, useState } from "react";
import { Button, Image } from "react-native";
import { Stack, useLocalSearchParams, useRouter } from "expo-router";
import { supabase } from "@/lib/supabase";

import { Text, View } from "@/components/common/Themed";

export default function ProductDetailsScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();
  const router = useRouter();

  const [product, setProduct] = useState();

  useEffect(() => {
    (async () => {
      const {
        data: [product],
        error,
      } = await supabase.functions.invoke(`/api/products/${id}`, {
        method: "GET",
      });

      console.log("error", error);

      setProduct(product);
    })();
  }, [id]);

  if (!product) return null;

  return (
    <>
      <Stack.Screen
        options={{
          title: product.name,
        }}
      />
      <View className="flex-1 justify-center items-center gap-5">
        <Image source={{ uri: product.imageUrl }} className="w-40 h-40" />
        <Text className="text-center">{product.name}</Text>
        <Text className="text-center">{product.description}</Text>
        <Text>₩{product.price}</Text>
        <Button
          title="구매"
          onPress={() =>
            router.navigate({
              pathname: "/checkout",
              params: { productId: product.id },
            })
          }
        />
      </View>
    </>
  );
}
