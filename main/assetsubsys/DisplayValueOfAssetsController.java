package main.assetsubsys;
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.FontUIResource;
import java.text.DecimalFormat;
import main.userinterface.Form;
import main.repositorysys.Asset;
import main.repositorysys.Repository;

public class DisplayValueOfAssetsController {

    public DisplayValueOfAssetsController(Form form) {

        double assetSum = 0;

        for (Asset a : Repository.getAssets())
            assetSum += a.getValue();

        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        JOptionPane.showMessageDialog(form, "Value of assets: $"+ formatter.format(assetSum));

    } // DisplayValueOfAssetsController()

} // DisplayValueOfAssetsController
