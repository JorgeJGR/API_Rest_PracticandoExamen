package serapirest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import jpaempleados.Departamento;
import jpaempleados.DepartamentoJpaController;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Jorge J. Guijarro Rabasco
 */
@Path("departamentos")
public class ServiceRESTDepartamentos {

    private static final String PERSISTENCE_UNIT = "ApirestEmpleadosPU";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Departamento> lista;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            lista = dao.findDepartamentoEntities();
            if (lista == null) {
                statusResul = Response.Status.NO_CONTENT;
                response = Response
                        .status(statusResul)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(lista)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id") int id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        Departamento dep;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            dep = dao.findDepartamento(id);
            if (dep == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe departamento con ID " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(dep)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(Departamento dep) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            Departamento depFound = dao.findDepartamento(dep.getIdDepartamento());
            if (depFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe departamento con ID " + dep.getIdDepartamento());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del departamento encontrado
                depFound.setNombre(dep.getNombre());
                depFound.setUbicacion(dep.getUbicacion());
                // Grabar los cambios
                dao.edit(depFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Departamento con ID " + dep.getIdDepartamento() + " actualizado");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Departamento dep) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            Departamento depFound = null;
            if ((dep.getIdDepartamento() != 0) && (dep.getIdDepartamento() != null)) {
                depFound = dao.findDepartamento(dep.getIdDepartamento());
            }
            if (depFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe departamento con ID " + dep.getIdDepartamento());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(dep);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Departamento " + dep.getNombre() + " grabado");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            Departamento depFound = dao.findDepartamento(id);
            if (depFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe departamento con ID " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Departamento con ID " + id + " eliminado");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }
        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }
        return response;
    }

    @GET
    @Path("/nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartamentoByNombre(@PathParam("nombre") String nombre) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            EntityManager em = dao.getEntityManager();

            TypedQuery<Departamento> query
                    = em.createNamedQuery("Departamento.findByNombre", Departamento.class);
            query.setParameter("nombre", nombre);

            List<Departamento> lista = query.getResultList();

            if (lista != null && !lista.isEmpty()) {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(lista)
                        .build();

            } else {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe departamento con nombre " + nombre);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }

        } catch (Exception ex) {

            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }

        return response;
    }

    @GET
    @Path("con-empleados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepConEmpleados() {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        String resultado = "{}";

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            EntityManager em = dao.getEntityManager();

            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT d.nombre, COUNT(e) FROM Departamento d LEFT JOIN d.empleadoCollection e GROUP BY d.nombre",
                    Object[].class
            );

            List<Object[]> lista = query.getResultList();

            if (lista != null && !lista.isEmpty()) {
                // Construir JSON usando org.json

                JSONArray jsonArray = new JSONArray();
                for (Object[] fila : lista) {
                    JSONObject json = new JSONObject();
                    json.put("departamento", fila[0]);
                    json.put("total_empleados", fila[1]);
                    jsonArray.put(json);
                }
                resultado = jsonArray.toString();
                statusResul = Response.Status.OK;
                response = Response.status(statusResul).entity(resultado).build();
            } else {
                statusResul = Response.Status.NO_CONTENT;
                response = Response
                        .status(statusResul)
                        .build();
            }

            em.close();

        } catch (Exception ex) {
            statusResul = Response.Status.BAD_REQUEST;
            ex.getStackTrace();
            mensaje.put("mensaje", "Error al procesar la petición" + ex.getLocalizedMessage());
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }

        return response;
    }
    
    @GET
    @Path("sin-empleados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepSinEmpleados() {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            DepartamentoJpaController dao = new DepartamentoJpaController(emf);
            EntityManager em = dao.getEntityManager();

            TypedQuery<Departamento> query
                    = em.createNamedQuery("Departamento.findSinEmpleados", Departamento.class);

            List<Departamento> lista = query.getResultList();

            if (lista != null && !lista.isEmpty()) {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(lista)
                        .build();

            } else {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No hay departamentos vacíos.");
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            }

        } catch (Exception ex) {

            statusResul = Response.Status.BAD_REQUEST;
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }

        return response;
    }

}
