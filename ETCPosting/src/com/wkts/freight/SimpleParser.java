package com.wkts.freight;

import org.xml.sax.InputSource;
import org.w3c.dom.*;

import javax.xml.xpath.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimpleParser {

	private static final String XML_FP1 = "src/googleout.xml";
	// private static final String XML_FP1 = "src/gout2.xml";

	// Note: The default XPath expression "/" selects
	// all text within the XML.

	// private static String xPathString =
	// "Root/GeocodeResponse/result[1]/address_component[type[1]/text()='country']/long_name/text()";
	// private static String xPathString2 =
	// "Root/GeocodeResponse/status/text()";
	// private static String xPathString2 =
	// "Root/GeocodeResponse/result[1]/formatted_address/text()";

	// private static String xPathString2 =
	// "Root/GeocodeResponse/result[1]/address_component[type[1]/text()='postal_code']/../formatted_address/text()";

	private static String xPathString2 = "Root/GeocodeResponse/result[1]/formatted_address/text()";
	// private static String xPathString2 =
	// "Root/GeocodeResponse/result[1]/address_component[type[1]/text()='postal_code']//long_name/text()";
	private static String xPathString3 = "Root/GeocodeResponse/result[1]/address_component[type[1]/text()='postal_code']/long_name/text()";

	private static final String LOG_FILEPATH = "src/LOGFILE.log";
	private static final Integer LOG_SIZE = 1000000;
	private static final Integer LOG_ROTATION_COUNT = 2;

	private static final Logger LOGGER = Logger.getLogger(FreightPoster.class
			.getName());

	public static void main(String[] args) throws IOException {
		SimpleParser sp = new SimpleParser();

		// LOGGER Set-up
		FileHandler fh = new FileHandler(LOG_FILEPATH, LOG_SIZE,
				LOG_ROTATION_COUNT);
		fh.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fh);

		String inputXPath = sp.readFile(XML_FP1, Charset.defaultCharset());

		// processResponse(xPathString,
		// sp.readFile(XML_FP2, Charset.defaultCharset()));

		processResponse(xPathString2,
				sp.readFile(XML_FP1, Charset.defaultCharset()));

	}

	private static void processResponse(String xpathString, String xmlString) {

		XPathFactory factory = XPathFactory.newInstance();

		XPath xpath = factory.newXPath();

		try {
			System.out.print("Geocode Parser 1.0\n");
			File xmlFile = new File(XML_FP1);
			InputSource inputXml = new InputSource(new FileInputStream(xmlFile));

			NodeList nodes = (NodeList) xpath.evaluate(xpathString, inputXml,
					XPathConstants.NODESET);

			for (int i = 0, n = nodes.getLength(); i < n; i++) {

				String nodeString = nodes.item(i).getTextContent();

				// System.out.print("|" + i + "|" + nodeString + "|" + "\n");
				// LOGGER.log(Level.INFO, "Children_count: " +
				// nodes.item(i).getParentNode().getParentNode().getParentNode().getChildNodes().getLength());
				// LOGGER.log(Level.INFO, "|" + (i + 1) + "|" + nodeString +
				// "|");
				String tagName = nodes.item(i).getParentNode().getNextSibling()
						.getNextSibling().getNodeName();
				LOGGER.log(Level.INFO, "|" + (i + 1) + "|" + tagName + "|");
				// File xmlFile3= new File(XML_FP1);
				// InputSource inputXml3 = new InputSource(new
				// FileInputStream(xmlFile3));
				// NodeList nodes3 = (NodeList) xpath.evaluate(xPathString3,
				// inputXml3,
				// XPathConstants.NODESET);

				//
				// for (int j = 0, m = nodes.getLength(); j < m; j++){
				// String nodeString3 = nodes3.item(j).getTextContent();
				// LOGGER.log(Level.INFO, "|" + (i+1) + "|" + (j+1)+"|"
				// +nodeString3 + "|");
				// }

			}
		} catch (XPathExpressionException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String readFile(String filePath, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
}