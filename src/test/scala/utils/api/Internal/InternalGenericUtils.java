package utils.api.Internal;

import org.dvsa.testing.lib.pages.BasePage;
import org.dvsa.testing.lib.pages.enums.SelectorType;
import utils.api.External.CreateInterimGoodsLicenceAPI;


public class InternalGenericUtils extends BasePage {

    public static void payFees(GrantApplicationAPI grantApp, CreateInterimGoodsLicenceAPI goodsApp) {
        grantApp.createOverview(goodsApp.getApplicationNumber());
        grantApp.getOutstandingFees(goodsApp.getApplicationNumber());
        grantApp.payOutstandingFees(String.valueOf(goodsApp.getOrganisationId()),goodsApp.getApplicationNumber());
        grantApp.grant(goodsApp.getApplicationNumber());
    }
}