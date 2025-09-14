import { Tabs } from "expo-router";
import AntDesign from "@expo/vector-icons/AntDesign";
import FontAwesome from "@expo/vector-icons/FontAwesome";

export default function TabsLayout() {
  return (
    <Tabs>
      <Tabs.Screen name="index" options={{ href: null }} />
      <Tabs.Screen
        name="products/index"
        options={{
          title: "상품",
          tabBarIcon: ({ color, size }) => (
            <AntDesign name="shop" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="profile"
        options={{
          title: "프로필",
          tabBarIcon: ({ color, size }) => (
            <FontAwesome name="user-o" size={size} color={color} />
          ),
        }}
      />
    </Tabs>
  );
}
