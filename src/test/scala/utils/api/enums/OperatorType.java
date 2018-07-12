package utils.api.enums;

import activesupport.system.out.Output;
import org.jetbrains.annotations.NotNull;

public enum OperatorType {
    lcat_gv("goods"),
    lcat_psv("public");

    private String name;

    OperatorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OperatorType getEnum(@NotNull String operatorType) {
        OperatorType operatorTypeEnum;

        switch (operatorType) {
            case "goods":
                operatorTypeEnum = lcat_gv;
                break;
            case "public":
                operatorTypeEnum = lcat_psv;
                break;
            default:
                throw new IllegalArgumentException(Output.printColoredLog("[ERROR] Unable to convert " + operatorType + " into an OperatorType enum"));
        }
        return operatorTypeEnum;
    }
}