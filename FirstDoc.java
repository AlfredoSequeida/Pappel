import java.io.*;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class FirstDoc {

        public static void main(String[] args) 
                throws IOException, DocumentException {
                String inputFile = "./firstdoc.xhtml";
                String url = new File(inputFile).toURI().toURL().toString();
                String outputFile = "./firstdoc.pdf";
                OutputStream os = new FileOutputStream(outputFile);

                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocument(url);
                renderer.layout();
                renderer.createPDF(os);

                os.close();
        }
}
