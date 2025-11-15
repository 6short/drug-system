package cn.bugstack.service;

public interface MailService {
    void sendEmailCode(String to,String code);
}
