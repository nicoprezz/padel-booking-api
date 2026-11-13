# Estilo de código

## Idioma
- Código (clases, métodos, variables) en inglés; comentarios y documentación en español.

## Java
- Inyección de dependencias por constructor; nunca `@Autowired` en campos, para que las
  dependencias sean explícitas y finales.
- Los DTOs son `record`.

## Javadoc y comentarios
- Javadoc en clases y métodos públicos: descripción, `@param`, `@return` y `@throws` (con
  su código HTTP). Sin Javadoc en getters y setters triviales.
- Los comentarios dentro de los métodos y los `@implNote` explican solo el porqué, nunca
  lo que hace el código.
- En código nuevo no se citan números de decisiones.
- No se modifican los comentarios del código existente, salvo que un issue lo pida.
