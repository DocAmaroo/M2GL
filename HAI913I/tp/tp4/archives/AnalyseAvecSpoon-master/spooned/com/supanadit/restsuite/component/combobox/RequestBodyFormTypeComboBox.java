package com.supanadit.restsuite.component.combobox;
import com.supanadit.restsuite.model.BodyFormTypeModel;
import com.supanadit.restsuite.renderer.RequestBodyFormTypeRenderer;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.*;
public class RequestBodyFormTypeComboBox extends JComboBox<BodyFormTypeModel> {
    ArrayList<BodyFormTypeModel> listBodyFormType = new ArrayList<>();

    public RequestBodyFormTypeComboBox() {
        setRenderer(new RequestBodyFormTypeRenderer());
        listBodyFormType.add(BodyFormTypeModel.FIELD());
        listBodyFormType.add(BodyFormTypeModel.FILE());
        for (BodyFormTypeModel bodyFormTypeModel : listBodyFormType) {
            addItem(bodyFormTypeModel);
        }
    }

    public RequestBodyFormTypeComboBox(String type) {
        this();
        for (BodyFormTypeModel bodyFormTypeModel : getListBodyFormType()) {
            if (bodyFormTypeModel.getName().equals(type)) {
                setSelectedItem(bodyFormTypeModel);
            }
        }
    }

    public ArrayList<BodyFormTypeModel> getListBodyFormType() {
        return listBodyFormType;
    }

    @Override
    public String toString() {
        BodyFormTypeModel model = ((BodyFormTypeModel) (getSelectedItem()));
        assert model != null;
        return model.getName();
    }
}