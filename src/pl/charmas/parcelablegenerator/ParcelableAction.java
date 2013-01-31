package pl.charmas.parcelablegenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.JavaFactoryProvider;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

public class ParcelableAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        GenerateDialog dlg = new GenerateDialog(psiClass);
        dlg.show();
        if (dlg.isOK()) {
            generateParcelable(psiClass, dlg.getFields());
        }
    }

    private void generateParcelable(final PsiClass psiClass, final List<PsiField> fields) {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                new CodeGenerator(psiClass, fields).generate();
            }
        }.execute();
    }


    @Override
    public void update(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        e.getPresentation().setEnabled(psiClass != null);
    }

    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        return psiClass;
    }
}
