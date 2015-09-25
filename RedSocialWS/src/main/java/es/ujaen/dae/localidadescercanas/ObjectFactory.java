
package es.ujaen.dae.localidadescercanas;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.ujaen.dae.localidadescercanas package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CiudadInexistente_QNAME = new QName("http://LocalidadesCercanas.dae.ujaen.es/", "CiudadInexistente");
    private final static QName _CiudadesCercanasResponse_QNAME = new QName("http://LocalidadesCercanas.dae.ujaen.es/", "ciudadesCercanasResponse");
    private final static QName _RadioIncorrecto_QNAME = new QName("http://LocalidadesCercanas.dae.ujaen.es/", "RadioIncorrecto");
    private final static QName _CiudadesPorDistancia_QNAME = new QName("http://LocalidadesCercanas.dae.ujaen.es/", "ciudadesPorDistancia");
    private final static QName _CiudadesPorDistanciaResponse_QNAME = new QName("http://LocalidadesCercanas.dae.ujaen.es/", "ciudadesPorDistanciaResponse");
    private final static QName _CiudadesCercanas_QNAME = new QName("http://LocalidadesCercanas.dae.ujaen.es/", "ciudadesCercanas");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.ujaen.dae.localidadescercanas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RadioIncorrecto }
     * 
     */
    public RadioIncorrecto createRadioIncorrecto() {
        return new RadioIncorrecto();
    }

    /**
     * Create an instance of {@link CiudadesPorDistancia }
     * 
     */
    public CiudadesPorDistancia createCiudadesPorDistancia() {
        return new CiudadesPorDistancia();
    }

    /**
     * Create an instance of {@link CiudadesPorDistanciaResponse }
     * 
     */
    public CiudadesPorDistanciaResponse createCiudadesPorDistanciaResponse() {
        return new CiudadesPorDistanciaResponse();
    }

    /**
     * Create an instance of {@link CiudadesCercanas }
     * 
     */
    public CiudadesCercanas createCiudadesCercanas() {
        return new CiudadesCercanas();
    }

    /**
     * Create an instance of {@link CiudadInexistente }
     * 
     */
    public CiudadInexistente createCiudadInexistente() {
        return new CiudadInexistente();
    }

    /**
     * Create an instance of {@link CiudadesCercanasResponse }
     * 
     */
    public CiudadesCercanasResponse createCiudadesCercanasResponse() {
        return new CiudadesCercanasResponse();
    }

    /**
     * Create an instance of {@link Ciudad }
     * 
     */
    public Ciudad createCiudad() {
        return new Ciudad();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CiudadInexistente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LocalidadesCercanas.dae.ujaen.es/", name = "CiudadInexistente")
    public JAXBElement<CiudadInexistente> createCiudadInexistente(CiudadInexistente value) {
        return new JAXBElement<CiudadInexistente>(_CiudadInexistente_QNAME, CiudadInexistente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CiudadesCercanasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LocalidadesCercanas.dae.ujaen.es/", name = "ciudadesCercanasResponse")
    public JAXBElement<CiudadesCercanasResponse> createCiudadesCercanasResponse(CiudadesCercanasResponse value) {
        return new JAXBElement<CiudadesCercanasResponse>(_CiudadesCercanasResponse_QNAME, CiudadesCercanasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RadioIncorrecto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LocalidadesCercanas.dae.ujaen.es/", name = "RadioIncorrecto")
    public JAXBElement<RadioIncorrecto> createRadioIncorrecto(RadioIncorrecto value) {
        return new JAXBElement<RadioIncorrecto>(_RadioIncorrecto_QNAME, RadioIncorrecto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CiudadesPorDistancia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LocalidadesCercanas.dae.ujaen.es/", name = "ciudadesPorDistancia")
    public JAXBElement<CiudadesPorDistancia> createCiudadesPorDistancia(CiudadesPorDistancia value) {
        return new JAXBElement<CiudadesPorDistancia>(_CiudadesPorDistancia_QNAME, CiudadesPorDistancia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CiudadesPorDistanciaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LocalidadesCercanas.dae.ujaen.es/", name = "ciudadesPorDistanciaResponse")
    public JAXBElement<CiudadesPorDistanciaResponse> createCiudadesPorDistanciaResponse(CiudadesPorDistanciaResponse value) {
        return new JAXBElement<CiudadesPorDistanciaResponse>(_CiudadesPorDistanciaResponse_QNAME, CiudadesPorDistanciaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CiudadesCercanas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://LocalidadesCercanas.dae.ujaen.es/", name = "ciudadesCercanas")
    public JAXBElement<CiudadesCercanas> createCiudadesCercanas(CiudadesCercanas value) {
        return new JAXBElement<CiudadesCercanas>(_CiudadesCercanas_QNAME, CiudadesCercanas.class, null, value);
    }

}
