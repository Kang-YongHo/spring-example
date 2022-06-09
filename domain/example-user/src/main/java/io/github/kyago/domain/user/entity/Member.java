package io.github.kyago.domain.user.entity;

import io.github.kyago.domain.user.model.Email;
import io.github.kyago.domain.user.model.Name;
import io.github.kyago.domain.user.model.ReferralCode;

import javax.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true, updatable = false, length = 50))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "referral_code", nullable = false, unique = true, updatable = false, length = 50))
    private ReferralCode referralCode;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "first", column = @Column(name = "first_name", nullable = false)),
            @AttributeOverride(name = "middle", column = @Column(name = "middle_name")),
            @AttributeOverride(name = "last", column = @Column(name = "last_name", nullable = false))
    })
    private Name name;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    protected Member() {
    }

    public Member(Email email, ReferralCode referralCode, Name name) {
        this.email = email;
        this.referralCode = referralCode;
        this.name = name;
    }

    public void updateProfile(final Name name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public ReferralCode getReferralCode() {
        return referralCode;
    }

    public Name getName() {
        return name;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email=" + email +
                ", referralCode=" + referralCode +
                ", name=" + name +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (!id.equals(member.id)) return false;
        if (email != null ? !email.equals(member.email) : member.email != null) return false;
        if (referralCode != null ? !referralCode.equals(member.referralCode) : member.referralCode != null)
            return false;
        if (name != null ? !name.equals(member.name) : member.name != null) return false;
        if (createAt != null ? !createAt.equals(member.createAt) : member.createAt != null) return false;
        return updateAt != null ? updateAt.equals(member.updateAt) : member.updateAt == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (referralCode != null ? referralCode.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        return result;
    }
}