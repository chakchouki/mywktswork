package com.wkts.freight;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author sac
 * @version FIRST IN GIT
 * 
 */
public class JTransform {
    public static void main(String[] args) throws IOException, URISyntaxException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("src/XSLTrans.xml"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("src/third.xml"));
        transformer.transform(text, new StreamResult(new File("src/output-third.txt")));
    }
}