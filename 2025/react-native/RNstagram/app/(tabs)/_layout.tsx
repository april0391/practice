import { Tabs } from "expo-router";
import React from "react";
import { Platform } from "react-native";

import { HapticTab } from "@/components/HapticTab";
import Icon from "@/components/ui/Icon";
import TabBarBackground from "@/components/ui/TabBarBackground";
import { Colors } from "@/constants/Colors";
import { useColorScheme } from "@/hooks/useColorScheme";
import type { Tab } from "@/types/app-types";

export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: Colors[colorScheme ?? "light"].tint,
        headerShown: false,
        tabBarButton: HapticTab,
        tabBarShowLabel: false,
        tabBarBackground: TabBarBackground,
        tabBarStyle: Platform.select({
          ios: {
            // Use a transparent background on iOS to show the blur effect
            position: "absolute",
          },
          default: {},
        }),
      }}
    >
      {tabs.map((tab) => (
        <Tabs.Screen
          key={tab.name}
          name={tab.name}
          options={{
            title: tab.name,
            tabBarIcon: ({ color }) => (
              <Icon
                library={tab.icon.library}
                name={tab.icon.name}
                size={26}
                color={color}
              />
            ),
          }}
        />
      ))}
    </Tabs>
  );
}

const tabs: Tab[] = [
  {
    name: "index",
    label: "Home",
    icon: { library: "material", name: "home" },
  },
  {
    name: "search",
    label: "Search",
    icon: { library: "ionicons", name: "search" },
  },
  {
    name: "new-post",
    label: "New Post",
    icon: { library: "ionicons", name: "add-circle-outline" },
  },
  {
    name: "reels",
    label: "Reels",
    icon: { library: "material", name: "video-library" },
  },
  {
    name: "profile",
    label: "Profile",
    icon: { library: "fontAwesome", name: "user-circle" },
  },
];
