package br.com.chfb.api.dto.mailpit;

import java.util.List;
import java.util.Map;

public record MailpitPayload(
        From From,
        List<To> To,
        String Subject,
        String Text,
        String HTML,
        List<Attachment> Attachments,
        Map<String, String> Headers
) {
    public record From(String Email, String Name) {}
    public record To(String Email, String Name) {}
    public record Attachment(String Content, String ContentID, String ContentType, String Filename) {}
}