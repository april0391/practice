import { SafeAreaView } from "@/components/common/Themed";
import Header from "@/components/home/Header";
import StoriesBar from "@/components/home/StoriesBar";
import Feed from "@/components/home/Feed";

export default function HomeScreen() {
  return (
    <SafeAreaView>
      <Header />
      <StoriesBar />
      <Feed />
    </SafeAreaView>
  );
}
