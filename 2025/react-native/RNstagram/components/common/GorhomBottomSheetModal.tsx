import { cn } from "@/utils/cn";
import {
  BottomSheetBackdrop,
  BottomSheetModal,
  BottomSheetView,
} from "@gorhom/bottom-sheet";

type Props = {
  modalRef: React.RefObject<BottomSheetModal | null>;
  className?: string;
  children: React.ReactNode;
};

export default function GorhomBottomSheetModal({
  modalRef,
  className,
  children,
}: Props) {
  return (
    <BottomSheetModal
      ref={modalRef}
      snapPoints={["70%"]}
      backdropComponent={(props) => (
        <BottomSheetBackdrop
          {...props}
          disappearsOnIndex={-1} // 시트 닫히면 사라짐
          appearsOnIndex={0} // 시트 열리면 나타남
          pressBehavior="close" // 백드롭 눌러서 시트 닫기
        />
      )}
    >
      <BottomSheetView
        className={cn("flex-1 h-full items-center p-6", className)}
      >
        {children}
      </BottomSheetView>
    </BottomSheetModal>
  );
}
