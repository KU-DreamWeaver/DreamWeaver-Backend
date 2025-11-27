package konkuk.dreamweaver.global.external.openai.constant;

public class OpenAiPrompt {
    public static final String TEXT_SYSTEM_PROMPT = "너는 사용자가 꿨던 꿈에 대한 해석을 제공해야해. " +
                                               "최대한 부드러운 어투(경어체)로 대답해줘";

    public static final String TEXT_USER_PROMPT = "사용자가 제공한 꿈에 대한 키워드는 %s이고" +
                                                 "사용자가 부가적으로 꿨던 꿈에 대한 설명은 %s야." +
                                                 "그리고 꿈을 꾸고 깼을 때의 감정은 %s야." +
                                                 "이걸 기반으로 사용자가 꿨던 꿈에 대한 해몽 요약을 제공해줘." +
                                                 "300자 내외로 만들어줘."
            ;

    public static final String IMAGE_PROMPT = "너는 해몽 요약 설명을 바탕으로 이미지를 생성해야해." +
                                                   "꿈이니까 몽환적인 느낌의 이미지를 생성해줘."+
                                                    "사용자가 꿨던 꿈에 대한 해몽 요약은 %s야." +
                                                   "이를 바탕으로 해몽 요약에 대한 이미지를 생성해줘";
}
