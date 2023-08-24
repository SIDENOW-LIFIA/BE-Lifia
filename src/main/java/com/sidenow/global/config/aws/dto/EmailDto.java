package com.sidenow.global.config.aws.dto;

import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailSenderDto {

    public static final String FROM_EMAIL = "kusitms.hjs@gmail.com"; // 보내는 사람

    private String receiver; // 받는 사람
    private String subject; // 제목
    private String content; // 본문

    public SendEmailRequest toSendRequestDto() {

        Destination destination = new Destination()
                .withToAddresses(this.receiver);
    }
}
