
package es.ujaen.dae.localidadescercanas;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "CiudadInexistente", targetNamespace = "http://LocalidadesCercanas.dae.ujaen.es/")
public class CiudadInexistente_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CiudadInexistente faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public CiudadInexistente_Exception(String message, CiudadInexistente faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public CiudadInexistente_Exception(String message, CiudadInexistente faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: es.ujaen.dae.localidadescercanas.CiudadInexistente
     */
    public CiudadInexistente getFaultInfo() {
        return faultInfo;
    }

}
