import { useEffect, useState } from "react";
import { Alert, Button } from "react-native";
import { useLocalSearchParams } from "expo-router";
import type {
  AgreementWidgetControl,
  PaymentMethodWidgetControl,
} from "@tosspayments/widget-sdk-react-native";
import {
  AgreementWidget,
  PaymentMethodWidget,
  PaymentWidgetProvider,
  usePaymentWidget,
} from "@tosspayments/widget-sdk-react-native";

import { supabase } from "@/lib/supabase";
import { useAuthStore } from "@/store/authStore";

import { ScrollView } from "@/components/common/Themed";

export default function CheckoutScreen() {
  const { session } = useAuthStore();
  const { productId } = useLocalSearchParams<{ productId: string }>();

  const [product, setProduct] = useState();

  useEffect(() => {
    (async () => {
      const {
        data: [product],
        error,
      } = await supabase.functions.invoke(`/api/products/${productId}`, {
        method: "GET",
      });

      console.log("error", error);

      setProduct(product);
    })();
  }, [productId]);

  return (
    <ScrollView className="">
      {product && (
        <PaymentWidgetProvider
          clientKey={`test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm`}
          customerKey={session!.user.id}
        >
          <TossSdk product={product} />
        </PaymentWidgetProvider>
      )}
    </ScrollView>
  );
}

function TossSdk({ product }) {
  const paymentWidgetControl = usePaymentWidget();
  const [paymentMethodWidgetControl, setPaymentMethodWidgetControl] =
    useState<PaymentMethodWidgetControl | null>(null);
  const [agreementWidgetControl, setAgreementWidgetControl] =
    useState<AgreementWidgetControl | null>(null);

  return (
    <>
      <PaymentMethodWidget
        selector="payment-methods"
        onLoadEnd={() => {
          paymentWidgetControl
            .renderPaymentMethods(
              "payment-methods",
              { value: product.price },
              {
                variantKey: "DEFAULT",
              }
            )
            .then((control) => {
              setPaymentMethodWidgetControl(control);
            });
        }}
      />
      <AgreementWidget
        selector="agreement"
        onLoadEnd={() => {
          paymentWidgetControl
            .renderAgreement("agreement", {
              variantKey: "DEFAULT",
            })
            .then((control) => {
              setAgreementWidgetControl(control);
            });
        }}
      />
      <Button
        title="결제요청"
        onPress={async () => {
          if (paymentWidgetControl == null || agreementWidgetControl == null) {
            Alert.alert("주문 정보가 초기화되지 않았습니다.");
            return;
          }

          const agreeement = await agreementWidgetControl.getAgreementStatus();
          if (agreeement.agreedRequiredTerms !== true) {
            Alert.alert("약관에 동의하지 않았습니다.");
            return;
          }

          paymentWidgetControl
            .requestPayment?.({
              orderId: "GkE7i5oFXWuuI0xiM9dzx2",
              orderName: product.name,
            })
            .then((result) => {
              console.log("result", result);

              if (result?.success) {
                Alert.alert(result.success.orderId);
                // 결제 성공 비즈니스 로직을 구현하세요.
                // result.success에 있는 값을 서버로 전달해서 결제 승인을 호출하세요.
                // fetch("http://10.0.2.2:4000/confirm/widget", {
                //   method: "POST",
                //   headers: {
                //     "Content-Type": "application/json",
                //   },
                //   body: JSON.stringify({
                //     paymentKey: result.success.paymentKey,
                //     orderId: result.success.orderId,
                //     amount: result.success.amount,
                //   }),
                // })
                //   .then((res) => res.json())
                //   .then((data) => {
                //     console.log("서버 응답:", data);
                //   })
                //   .catch((err) => {
                //     console.error("서버 에러:", err);
                //   });
              } else if (result?.fail) {
                // 결제 실패 비즈니스 로직을 구현하세요.
                Alert.alert(result.fail.code);
              }
            });
        }}
      />
      <Button
        title="선택된 결제수단"
        onPress={async () => {
          if (paymentMethodWidgetControl == null) {
            Alert.alert("주문 정보가 초기화되지 않았습니다.");
            return;
          }
          Alert.alert(
            `선택된 결제수단: ${JSON.stringify(
              await paymentMethodWidgetControl.getSelectedPaymentMethod()
            )}`
          );
        }}
      />
      <Button
        title="결제 금액 변경"
        onPress={() => {
          if (paymentMethodWidgetControl == null) {
            Alert.alert("주문 정보가 초기화되지 않았습니다.");
            return;
          }
          paymentMethodWidgetControl.updateAmount(100_000).then(() => {
            Alert.alert("결제 금액이 100000원으로 변경되었습니다.");
          });
        }}
      />
    </>
  );
}
