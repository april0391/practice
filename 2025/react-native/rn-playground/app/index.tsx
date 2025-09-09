import { Button, FlatList, ScrollView } from "react-native";
import { Link } from "expo-router";

import { useAuth } from "@/store/AuthProvider";
import { useAuthStore } from "@/store/authStore";

import { Text, View } from "@/components/common/Themed";
import { useEffect, useState } from "react";
import { supabase } from "@/lib/supabase";

export default function HomeScreen() {
  // const { signOut } = useAuth();
  const { signOut } = useAuthStore();
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState();

  useEffect(() => {
    if (!loading) return;

    (async () => {
      const { data: products, error } = await supabase.functions.invoke(
        "/api/products",
        {
          method: "GET",
        }
      );
      setProducts(products);
      setLoading(false);
    })();
  }, [loading, products]);

  return (
    <View className="flex-1 gap-7 justify-center items-center p-8">
      <Button title="로그아웃" onPress={signOut} />
      {!loading && (
        <FlatList
          className="w-full"
          data={products}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => <Text>{item.name}</Text>}
          numColumns={2}
          columnWrapperStyle={{ justifyContent: "space-between" }}
          contentContainerStyle={{ paddingHorizontal: 8, paddingVertical: 16 }}
        />
      )}
    </View>
  );
}
