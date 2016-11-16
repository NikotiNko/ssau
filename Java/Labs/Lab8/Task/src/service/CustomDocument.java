package service;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author 1
 * @since 01.11.2016.
 */
public class CustomDocument extends PlainDocument {

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        StringBuilder stringBuilder = new StringBuilder();
        String content = getContent().getString(0, getContent().length());
        if (offs==0 && !str.isEmpty() && str.charAt(0)=='-') stringBuilder.append('-');
        for (char c:str.toCharArray()){
            if (c>= '0' && c<='9') {
                stringBuilder.append(c);
            }
            if (c== '.' && !content.contains(".") && stringBuilder.indexOf(".")==-1 ||
                    c== 'E' && !content.contains("E") && stringBuilder.indexOf("E")==-1) {
                stringBuilder.append(c);
            }
        }
        super.insertString(offs, stringBuilder.toString(), a);
    }
}
