package br.com.cep.api.exception;

/**
 * The invalid address exception type
 * @author Diego Costa (diegotonzi@gmail.com)
 */
public class AddressNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 2983344247900561871L;

	public AddressNotFoundException(final String message) {
        super(message);
    }
	
}
