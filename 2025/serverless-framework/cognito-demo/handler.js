exports.preSignUp = async (event) => {
  console.log("PreSignUp event:", JSON.stringify(event, null, 2));

  // 자동으로 이메일 인증을 완료 상태로 설정
  event.response.autoConfirmUser = true;

  return event;
};

exports.postConfirmation = async (event) => {
  console.log("postConfirmation event:", JSON.stringify(event, null, 2));

  if (event.triggerSource === "CustomMessage_SignUp") {
    event.response.smsMessage = `Your verification code is ${event.request.codeParameter}`;
    event.response.emailSubject = "Welcome! Verify your email";
    event.response.emailMessage = `Click the link to verify your account: https://your-app.com/verify?code=${event.request.codeParameter}`;
  }

  return event;
};
