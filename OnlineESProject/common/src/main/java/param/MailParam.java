package param;

import lombok.Data;

/**
 * Created by mengf on 2017/12/7 0007.
 */
@Data
public class MailParam {
    private String[] tos;
    private String subject;
    private String content;

    public String[] getTos() {
    	return tos;
    }
    
    public String getSubject() {
    	return subject;
    }
    
    public String getContent() {
    	return content;
    }
    
    
    public MailParam() {
    }

    public MailParam(String[] tos, String subject, String content) {
        this.tos = tos;
        this.subject = subject;
        this.content = content;
    }

    public MailParam(String tos, String subject, String content) {
        this.tos = new String[]{tos};
        this.subject = subject;
        this.content = content;
    }

}
