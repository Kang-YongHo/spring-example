package io.github.kyago.domain.user.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.StringUtils;

@Embeddable
public class Name {

    @Column(name = "first_name", length = 50, nullable = false)
    private String first;

    @Column(name = "middle_name", length = 50)
    private String middle;

    @Column(name = "last_name", length = 50, nullable = false)
    private String last;

    protected Name() {
    }

    public Name(final String first, final String middle, final String last) {
        this.first = first;
        this.middle = StringUtils.hasText(middle) ? null : middle;
        this.last = last;
    }

    public String getFullName() {
        if (this.middle == null) {
            return String.format("%s %s", this.first, this.last);
        }
        return String.format("%s %s %s", this.first, this.middle, this.last);
    }

    public String getFirst() {
        return first;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLast() {
        return last;
    }

    @Override
    public String toString() {
        return "Name{" +
                "first='" + first + '\'' +
                ", middle='" + middle + '\'' +
                ", last='" + last + '\'' +
                '}';
    }
}