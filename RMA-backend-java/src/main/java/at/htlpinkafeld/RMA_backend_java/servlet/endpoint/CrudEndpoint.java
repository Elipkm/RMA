package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Identifiable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public abstract class CrudEndpoint<T extends Identifiable> {

    public abstract String getPath();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final T entity){
        try {
            this.createService(entity);
        } catch (DaoSysException daoSysException) {
            return responseDueToDaoException(daoSysException);
        } catch (IllegalArgumentException illegalArgumentException){
            return Response.status(409,illegalArgumentException.getMessage()).build();
        } catch (DaoResourceAlreadyExistsException resourceAlreadyExistsException){
            return Response.status(409, resourceAlreadyExistsException.getMessage()).build();
        }
        JsonObject json = new JsonObject();
        json.add("id",new JsonPrimitive(entity.getID()));

        return Response.ok(json.toString())
                .status(Response.Status.CREATED)
                .header("Location", "/RMA_Restful_Service/rma" + this.getPath() + "/" + entity.getID())
                .build();
    }
    public abstract void createService(final T entity) throws DaoSysException, DaoResourceAlreadyExistsException;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") int id){
        try{
            T entity;
            try {
                entity = this.readService(id);
            } catch (DaoSysException daoSysException) {
                return responseDueToDaoException(daoSysException);
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String response = gson.toJson(entity);

            return Response.ok(response).build();

        } catch (DaoNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return Response.status(404).build();
        }
    }
    public abstract T readService(int id) throws DaoSysException, DaoNotFoundException;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(final T entity){
        try {
            try {
                this.updateService(entity);
            } catch (DaoSysException daoSysException) {
                return responseDueToDaoException(daoSysException);
            }
            return Response.ok().build();
        } catch (DaoNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return Response.status(404).build();
        }
    }
    public abstract void updateService(final T entity) throws DaoSysException, DaoNotFoundException;

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id){
        try {
            try {
                this.deleteService(id);
            } catch (DaoSysException daoSysException) {
                return responseDueToDaoException(daoSysException);
            }
            return Response.status(200).build();

        } catch (DaoNotFoundException notFoundException){
            notFoundException.printStackTrace();
            return Response.status(404).build();
        }
    }
    public abstract void deleteService(int id) throws DaoSysException, DaoNotFoundException;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        List<T> entityList;
        try {
            entityList = this.listService();
        } catch (DaoSysException daoSysException) {
            return responseDueToDaoException(daoSysException);
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String response = gson.toJson(entityList);

        return Response.ok(response).build();
    }
    public abstract List<T> listService() throws DaoSysException;

    private Response responseDueToDaoException(DaoSysException daoSysException){
        return Response.serverError().status(500,daoSysException.getMessage()).build();
    }
}
