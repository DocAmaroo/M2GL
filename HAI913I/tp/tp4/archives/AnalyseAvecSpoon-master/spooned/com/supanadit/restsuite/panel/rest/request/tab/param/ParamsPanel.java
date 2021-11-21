package com.supanadit.restsuite.panel.rest.request.tab.param;
import com.supanadit.restsuite.component.input.api.InputTextURL;
import com.supanadit.restsuite.component.table.ParamsTable;
import com.supanadit.restsuite.helper.UrlParser;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import io.reactivex.disposables.Disposable;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
public class ParamsPanel extends JPanel {
    public ParamsPanel(InputTextURL inputTextURL) {
        super(new MigLayout());
        ParamsTable paramsTable = new ParamsTable(false, null);
        Disposable disposable = inputTextURL.getSubject().throttleWithTimeout(300, TimeUnit.MILLISECONDS).subscribe(( s) -> {
            UrlParser urlParser = new UrlParser(s);
            paramsTable.setFromRequestArrayList(urlParser.getQueryParams());
        });
        add(paramsTable, "growx,pushx");
    }
}