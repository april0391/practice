import { useEffect, useState } from "react";
import { FlatList, Image, Pressable } from "react-native";
import { Link } from "expo-router";
import { supabase } from "@/lib/supabase";

import { Text, View } from "@/components/common/Themed";

export default function ProductsStack() {
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState();

  useEffect(() => {
    if (!loading) return;

    (async () => {
      const { data: products, error } = await supabase.functions.invoke(
        "/api/products",
        { method: "GET" }
      );
      console.log("error", error);
      setProducts(products);
      setLoading(false);
    })();
  }, [loading, products]);

  return (
    <View className="flex-1 gap-7 justify-center items-center p-8">
      {!loading && (
        <FlatList
          className="w-full"
          data={products}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => (
            <Link
              href={{
                pathname: "/(tabs)/(products)/[id]",
                params: { id: item.id },
              }}
              asChild
            >
              <Pressable className="my-4 w-[47%]">
                <Image
                  source={{ uri: item.imageUrl }}
                  className="w-full aspect-square rounded-lg"
                  resizeMode="cover"
                />
                <Text>{item.name}</Text>
                <Text>₩{item.price}</Text>
              </Pressable>
            </Link>
          )}
          numColumns={2}
          columnWrapperStyle={{ justifyContent: "space-between" }}
          contentContainerStyle={{ paddingBottom: 16 }}
        />
      )}
    </View>
  );
}
