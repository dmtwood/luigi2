package be.vdab.luigi.restclients;

import be.vdab.luigi.exceptions.KoersClientException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

@Component // creates a bean to inject in DefaultEuroService
//@Qualifier("ECB")
// when +1 bean classes can be implemented in the same (DefaultEuro)Service, add a qualifier to choose which one
@Order(1) // replaces Qualifier
public class ECBKoersClient implements KoersClient {

    private final URL url;

    ECBKoersClient() {
        try {
            this.url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
        } catch (MalformedURLException ex) {
            throw new KoersClientException("ECB URL is verkeerd. ", ex);
        }
    }

    @Override
    public BigDecimal getDollarKoers() {

        // use a design pattern FACTORY to create (via new Instance, no constructor) an object to read the XML stream
        XMLInputFactory factory = XMLInputFactory.newInstance();

        // try to fetch an inputstream from the ECB currency source url
        try (InputStream inputStream = url.openStream()) {

            // and make a XML stream reader using the inputfactory and inputstream
            // sequentially reading XML data (tags, commments,...)
            XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(inputStream);

                try {
                    // as long as there is xml data available
                    while (xmlStreamReader.hasNext()) {

                        //  next() returns an int indicating the sort of XML data it's reading
                        if (xmlStreamReader.next()
                                // if it equals the ct START_ELEMENT the positioning is at the begin tag
                                == XMLStreamConstants.START_ELEMENT
                                // and if it has "USD" as "currency"-attribute (using null when there is no namespace associated to XML source)
                                && "USD".equals(xmlStreamReader.getAttributeValue(null, "currency"))) {
                            // read the current dollarkoers in the "rate"-attribute and return it as method output
                            return new BigDecimal(xmlStreamReader.getAttributeValue(null, "rate"));
                        }
                    }
                    // at this point, al the XML data is read and no dollarkoers was found
                    throw new KoersClientException("XML van ECB bevat geen USD. ");
                //  XMLStreamreader doesn't implement AutoCloseable
                } finally { xmlStreamReader.close(); }


            } catch (IOException | NumberFormatException | XMLStreamException ex) {
                throw new KoersClientException("Kan koers niet lezen via ECB. ", ex);
            }
        }
    }
