package utils;

public class EmailEngineDetails {
    public String getUsername() {
        return ServerContext.getProperties().getValue(ConstantsUtils.PROP_EMAIL_USERNAME);
    }

    public String getPassword() {
        return ServerContext.getProperties().getValue(ConstantsUtils.PROP_EMAIL_PASSWORD);
    }

    public String getSmtpServer() {
        return ServerContext.getProperties().getValue(ConstantsUtils.PROP_EMAIL_SMTP_SERVER);
    }

    public String getEmailResetPasswordHtml() {
        return ServerContext.getProperties().getValue(ConstantsUtils.PROP_EMAIL_RESET_PASSWORD_HTML);
    }
}
