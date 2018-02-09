package com.teamrocket.naasp.service.company.model;

/**
 * Representation of company settings.
 */
public class CompanySettings {
    private boolean useRegistration;
    private boolean useTwoFactor;

    public boolean isUseRegistration() {
        return useRegistration;
    }

    public void setUseRegistration(boolean useRegistration) {
        this.useRegistration = useRegistration;
    }

    public boolean isUseTwoFactor() {
        return useTwoFactor;
    }

    public void setUseTwoFactor(boolean useTwoFactor) {
        this.useTwoFactor = useTwoFactor;
    }
}
