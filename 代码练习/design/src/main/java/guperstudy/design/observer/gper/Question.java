package guperstudy.design.observer.gper;

import lombok.Getter;
import lombok.Setter;

/**
 * @author joe
 * @program: design
 * @description: GPer学习提出的问题
 * @date 2020-10-07 11:22:42
 */
@Getter
@Setter
public class Question {
    /**
     * 提问题的用户
     */
    private String userName;
    /**
     * 问题内容
     */
    private String content;

    public Question(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
