/*
 * Copyright (C) 2013 Michał Charmas (http://blog.charmas.pl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.charmas.parcelablegenerator;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.panels.VerticalBox;

import org.jetbrains.annotations.Nullable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GenerateDialog extends DialogWrapper {

    private final CollectionListModel<PsiField> fieldsCollection;
    private final LabeledComponent<JPanel> fieldsComponent;
    private final JBCheckBox includeSubclasses;
    private final boolean showCheckbox;

    protected GenerateDialog(final PsiClass psiClass) {
        super(psiClass.getProject());
        setTitle("Select Fields for Parcelable Generation");

        fieldsCollection = new CollectionListModel<PsiField>();
        final JBList fieldList = new JBList(fieldsCollection);
        fieldList.setCellRenderer(new DefaultPsiElementCellRenderer());
        final ToolbarDecorator decorator = ToolbarDecorator.createDecorator(fieldList).disableAddAction();
        final JPanel panel = decorator.createPanel();

        fieldsComponent = LabeledComponent.create(panel, "Fields to include in Parcelable");

        includeSubclasses = new JBCheckBox("Include fields from base classes");
        setupCheckboxClickAction(psiClass);
        showCheckbox = psiClass.getFields().length != psiClass.getAllFields().length;

        updateFieldsDisplay(psiClass);
        init();
    }

    /**
     * Hookup action listener for {@link #includeSubclasses} checkbox.
     */
    private void setupCheckboxClickAction(final PsiClass psiClass) {
        includeSubclasses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                updateFieldsDisplay(psiClass);
            }
        });
    }

    /**
     * Update {@link #fieldsCollection} with class fields.
     */
    private void updateFieldsDisplay(PsiClass psiClass) {
        final List<PsiField> fields;
        if (includeSubclasses.isSelected()) {
            fields = getClassFields(psiClass.getAllFields());
        } else {
            fields = getClassFields(psiClass.getFields());
        }
        fieldsCollection.removeAll();
        fieldsCollection.add(fields);
    }

    /**
     * Exclude static fields.
     */
    private List<PsiField> getClassFields(PsiField[] allFields) {
        final List<PsiField> fields = new ArrayList<PsiField>();
        for (PsiField field : allFields) {
            if (!field.hasModifierProperty(PsiModifier.STATIC) && !field.hasModifierProperty(PsiModifier.TRANSIENT)) {
                fields.add(field);
            }
        }
        return fields;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return fieldsComponent;
    }

    @Nullable
    @Override
    protected JComponent createSouthPanel() {
        JComponent southPanel = super.createSouthPanel();
        if(showCheckbox && southPanel != null) {
            final VerticalBox combinedView = new VerticalBox();
            combinedView.add(includeSubclasses);
            combinedView.add(southPanel);
            return combinedView;
        } else {
            return southPanel;
        }
    }

    public List<PsiField> getSelectedFields() {
        return fieldsCollection.getItems();
    }
}
