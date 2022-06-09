package io.github.kyago.domain.user.model;

import net.bytebuddy.utility.RandomString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReferralCode {

    @Column(name = "referral_code", length = 50)
    private String value;

    private ReferralCode(String value) {
        this.value = value;
    }

    protected ReferralCode() {
    }

    public static ReferralCode of(final String value) {
        return new ReferralCode(value);
    }

    public static ReferralCode generateCode() {
        return new ReferralCode(RandomString.make(10));
    }

    public String getValue() {
        return value;
    }
}