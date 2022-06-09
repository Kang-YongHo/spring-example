package io.github.kyago.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email {

    @Column(name = "email", length = 50, nullable = false)
    private String value;

    private Email(final String value) {
        this.value = value;
    }

    protected Email() {

    }

    public static Email of(final String value) {
        return new Email(value);
    }

    public String getHost() {
        final int index = value.indexOf("@");
        return index == -1 ? null : value.substring(index + 1);
    }

    public String getId() {
        final int index = value.indexOf("@");
        return index == -1 ? null : value.substring(0, index);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}