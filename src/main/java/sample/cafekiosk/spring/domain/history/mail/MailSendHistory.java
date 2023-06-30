package sample.cafekiosk.spring.domain.history.mail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailSendHistory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String toEmail;
    private String fromEmail;
    private String subject;
    private String content;

    @Builder
    public MailSendHistory(String toEmail, String fromEmail, String subject, String content) {
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.subject = subject;
        this.content = content;
    }
}
