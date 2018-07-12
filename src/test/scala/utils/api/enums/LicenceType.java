package utils.api.enums;

import activesupport.system.out.Output;
import org.jetbrains.annotations.NotNull;

public enum LicenceType {
    ltyp_r("restricted"),
    ltyp_sn("standard_international"),
    ltyp_si("standard_national"),
    ltyp_sr("standard_restricted");

    private String name;

    LicenceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LicenceType getEnum(@NotNull String licenceType) {
        LicenceType licenceTypeEnum;

        switch (licenceType) {
            case "restricted":
                licenceTypeEnum = ltyp_r;
                break;
            case "standard_international":
                licenceTypeEnum = ltyp_si;
                break;
            case "standard_national":
                licenceTypeEnum = ltyp_sn;
                break;
            case "standard_restricted":
                licenceTypeEnum = ltyp_sr;
                break;
            default:
                throw new IllegalArgumentException(Output.printColoredLog("[ERROR] Unable to convert " + licenceType + " into a LicenceType enum"));
        }
        return licenceTypeEnum;
    }
}