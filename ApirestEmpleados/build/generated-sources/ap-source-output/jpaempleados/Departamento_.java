package jpaempleados;

import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;
import jpaempleados.Empleado;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-02-10T11:16:57", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Departamento.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Departamento_ { 

    public static volatile SingularAttribute<Departamento, Integer> idDepartamento;
    public static volatile SingularAttribute<Departamento, String> ubicacion;
    public static volatile CollectionAttribute<Departamento, Empleado> empleadoCollection;
    public static volatile SingularAttribute<Departamento, String> nombre;

}