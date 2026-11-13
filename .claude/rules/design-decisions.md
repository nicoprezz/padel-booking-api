# Decisiones de diseño

- Antes de proponer algo, comprobar que no contradice una decisión vigente del registro
  de decisiones (`docs/adr/`; vigente = estado `Aceptada`).
- Si hay varias formas razonables de hacer algo, presentar las opciones con sus pros y
  contras **sin elegir**: decide el autor.
- Preferir la solución más simple que cumpla los requisitos, y avisar si algo se está
  complicando más de lo necesario.
- No añadir dependencias al `pom.xml` sin preguntar antes y explicar para qué sirven.
- Toda decisión técnica tomada se registra como un ADR nuevo en `docs/adr/`, para que el
  porqué no dependa de la memoria de nadie. Las decisiones antiguas no se reescriben: se
  sustituyen o se amplían con otro ADR.
