import { Tabs } from "expo-router";
import Feather from "@expo/vector-icons/Feather";

export default function TabLayout() {
  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: "green",
      }}
    >
      <Tabs.Screen
        name="(products)"
        options={{
          title: "상점",
          headerShown: false,
          tabBarIcon: ({ color, size }) => (
            <Feather name="shopping-bag" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="profile"
        options={{
          title: "프로필",
          tabBarIcon: ({ color, size }) => (
            <Feather name="user" size={size} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="checkout"
        options={{
          title: "결제",
          href: null,
        }}
      />
    </Tabs>
  );
}
