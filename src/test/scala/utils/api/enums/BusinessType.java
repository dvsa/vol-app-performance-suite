package utils.api.enums;

import activesupport.system.out.Output;
import org.jetbrains.annotations.NotNull;

public enum BusinessType {
    org_t_rc("limited_company"),
    org_t_st("sole_trader"),
    org_t_p("partnership"),
    org_t_llp("limited_partnership"),
    org_t_pa("other");

    private String name;

    BusinessType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BusinessType getEnum(@NotNull String businessType){
        BusinessType businessTypeEnum;

        switch(businessType){
            case "limited_company":
                businessTypeEnum = org_t_rc;
                break;
            case "sole_trader":
                businessTypeEnum = org_t_st;
                break;
            case "partnership":
                businessTypeEnum = org_t_p;
                break;
            case "limited_partnership":
                businessTypeEnum = org_t_llp;
                break;
            case "other":
                businessTypeEnum = org_t_pa;
                break;
            default:
                throw new IllegalArgumentException(Output.printColoredLog("[ERROR] Unable to convert " + businessType + " into a BusinessType enum"));
        }

        return businessTypeEnum;
    }
}