package io.github.nicoprezz.padel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicación.
 *
 * @implNote
 * {@code @SpringBootApplication} activa la configuración automática y el escaneo de
 * componentes: Spring busca {@code @Service}, {@code @RestController}, repositorios, etc.
 * en este paquete y sus subpaquetes. Por eso esta clase está en la raíz.
 */
@SpringBootApplication
public class PadelBookingApiApplication {

	/**
	 * Arranca Spring: crea los beans, conecta con MySQL y levanta el servidor en el 8080.
	 *
	 * @param args argumentos de línea de comandos (p. ej., el perfil activo)
	 */
	public static void main(String[] args) {
		SpringApplication.run(PadelBookingApiApplication.class, args);
	}

}
