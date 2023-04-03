package ru.tayviscon.tayvisconrenderer.markup;

import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;
import org.springframework.util.StringUtils;

public class PrettifyVerbatimSerializer implements VerbatimSerializer {

    static final PrettifyVerbatimSerializer INSTANCE = new PrettifyVerbatimSerializer();
    @Override
    public void serialize(VerbatimNode node, Printer printer) {
        printer.println().print("<pre><code");
        String className = "prettyprint";
        if(StringUtils.isEmpty(node.getType())) {
            className = className.concat(" " + node.getType());
        }
        printAttribute(printer, "class", className);
        printer.print(">");
        String text = node.getText();
        while(text.charAt(0) == '\n') {
            printer.print("</br>");
            text = text.substring(1);
        }
        printer.printEncoded(text);
        printer.print("</code></pre>");
    }

    private void printAttribute(Printer printer, String name, String value) {
        printer.print(' ').print('=').print('"').print(value).print('"');
    }
}
