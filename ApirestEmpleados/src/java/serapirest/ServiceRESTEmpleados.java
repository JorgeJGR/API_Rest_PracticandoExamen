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
import jpaempleados.Empleado;
import jpaempleados.EmpleadoJpaController;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Jorge J. Guijarro Rabasco
 */
@Path("empleados")
public class ServiceRESTEmpleados {

    private static final String PERSISTENCE_UNIT = "ApirestEmpleadosPU";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        List<Empleado> lista;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            lista = dao.findEmpleadoEntities();
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
        Empleado emp;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            emp = dao.findEmpleado(id);
            if (emp == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe empleado con ID " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(emp)
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
    public Response put(Empleado emp) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            Empleado empFound = dao.findEmpleado(emp.getIdEmpleado());
            if (empFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe empleado con ID " + emp.getIdEmpleado());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                // Actualizar campos del libro encontrado
                empFound.setNombre(emp.getNombre());
                empFound.setApellidos(emp.getApellidos());
                empFound.setEmail(emp.getEmail());
                empFound.setTelefono(emp.getTelefono());
                empFound.setIdDepartamento(emp.getIdDepartamento());
                // Grabar los cambios
                dao.edit(empFound);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Empleado con ID " + emp.getIdEmpleado() + " actualizado");
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
    public Response post(Empleado emp) {
        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;
        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            Empleado empFound = null;
            if ((emp.getIdEmpleado() != 0) && (emp.getIdEmpleado() != null)) {
                empFound = dao.findEmpleado(emp.getIdEmpleado());
            }
            if (empFound != null) {
                statusResul = Response.Status.FOUND;
                mensaje.put("mensaje", "Ya existe Empleado con ID " + emp.getIdEmpleado());
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.create(emp);
                statusResul = Response.Status.CREATED;
                mensaje.put("mensaje", "Empleado " + emp.getNombre() + " grabado");
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
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            Empleado empFound = dao.findEmpleado(id);
            if (empFound == null) {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe empleado con ID " + id);
                response = Response
                        .status(statusResul)
                        .entity(mensaje)
                        .build();
            } else {
                dao.destroy(id);
                statusResul = Response.Status.OK;
                mensaje.put("mensaje", "Empleado con ID " + id + " eliminado");
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
    @Path("/departamento/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleadosDep(@PathParam("id") int id) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        String resultado = "{}";

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            EntityManager em = dao.getEntityManager();

            // JPQL corregido con parámetro
            TypedQuery<Empleado> query = em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.idDepartamento.idDepartamento = :id",
                    Empleado.class
            );
            query.setParameter("id", id);

            List<Empleado> lista = query.getResultList();

            if (lista != null && !lista.isEmpty()) {
                // Construir JSON usando org.json
                JSONArray jsonArray = new JSONArray();
                for (Empleado e : lista) {
                    JSONObject json = new JSONObject();
                    json.put("nombre", e.getNombre());
                    json.put("apellidos", e.getApellidos());
                    json.put("email", e.getEmail());
                    json.put("telefono", e.getTelefono());
                    json.put("departamento", e.getIdDepartamento() != null ? e.getIdDepartamento().getNombre() : JSONObject.NULL);
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
            mensaje.put("mensaje", "Error al procesar la petición");
            response = Response
                    .status(statusResul)
                    .entity(mensaje)
                    .build();
        }

        return response;
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleadoByEmail(@PathParam("email") String email) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            EntityManager em = dao.getEntityManager();

            TypedQuery<Empleado> query
                    = em.createNamedQuery("Empleado.findByEmail", Empleado.class);
            query.setParameter("email", email);

            Empleado emp = query.getSingleResult();

            if (emp != null) {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(emp)
                        .build();

            } else {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe empleado con email " + email);
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
    @Path("/apellidos/{texto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleadoApellido(@PathParam("texto") String texto) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        String resultado = "{}";

        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {
            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            EntityManager em = dao.getEntityManager();

            TypedQuery<Empleado> query = em.createQuery(
                    "SELECT e FROM Empleado e WHERE e.apellidos LIKE :texto",
                    Empleado.class
            );
            query.setParameter("texto", "%" + texto + "%");

            List<Empleado> lista = query.getResultList();

            if (lista != null && !lista.isEmpty()) {
                // Construir JSON usando org.json
                JSONArray jsonArray = new JSONArray();
                for (Empleado e : lista) {
                    JSONObject json = new JSONObject();
                    json.put("nombre", e.getNombre());
                    json.put("apellidos", e.getApellidos());
                    json.put("email", e.getEmail());
                    json.put("telefono", e.getTelefono());
                    json.put("departamento", e.getIdDepartamento() != null ? e.getIdDepartamento().getNombre() : JSONObject.NULL);
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
    @Path("/telefono/{telefono}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmpleadoByTelefono(@PathParam("telefono") String telefono) {

        HashMap<String, String> mensaje = new HashMap<>();
        Response response;
        Response.Status statusResul;

        try (EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT)) {

            EmpleadoJpaController dao = new EmpleadoJpaController(emf);
            EntityManager em = dao.getEntityManager();

            TypedQuery<Empleado> query
                    = em.createNamedQuery("Empleado.findByTelefono", Empleado.class);
            query.setParameter("telefono", telefono);

            Empleado emp = query.getSingleResult();

            if (emp != null) {
                statusResul = Response.Status.OK;
                response = Response
                        .status(statusResul)
                        .entity(emp)
                        .build();

            } else {
                statusResul = Response.Status.NOT_FOUND;
                mensaje.put("mensaje", "No existe empleado con teléfono " + telefono);
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
