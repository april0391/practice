import Feed from "@/components/home/Feed";
import Header from "@/components/home/Header";
import StoriesBar from "@/components/home/StoriesBar";
import ThemedSafeAreaView from "@/components/ThemedSafeAreaView";

export default function HomeScreen() {
  return (
    <ThemedSafeAreaView>
      <Header />
      <StoriesBar />
      <Feed />
    </ThemedSafeAreaView>
  );
}
