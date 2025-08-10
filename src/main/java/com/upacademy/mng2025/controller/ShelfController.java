package com.upacademy.mng2025.controller;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.upacademy.mng2025.entity.Shelf;
import com.upacademy.mng2025.repo.JDBCShelfRepository;
import com.upacademy.mng2025.repo.ShelfRepository;

@Path("/shelves")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShelfController {

    private final ShelfRepository repo = new JDBCShelfRepository();

    @OPTIONS
    public Response optionsRoot() { return Response.ok().build(); }

    @OPTIONS
    @Path("/{id}")
    public Response optionsWithId() { return Response.ok().build(); }

    @GET
    public List<Shelf> getAll() throws SQLException { return repo.findAll(); }

    @GET
    @Path("/{id}")
    public Shelf getById(@PathParam("id") String id) throws SQLException {
        Shelf s = repo.findById(id);
        if (s == null) throw new NotFoundException("Prateleira não encontrada com ID: " + id);
        return s;
    }

    @POST
    public Shelf add(Shelf shelf) throws SQLException {
        if (shelf == null || shelf.getId() == null || shelf.getId().trim().isEmpty()) {
            throw new BadRequestException("ID da prateleira é obrigatório.");
        }
        repo.insert(shelf);
        return shelf;
    }

    @PUT
    @Path("/{id}")
    public Shelf update(@PathParam("id") String id, Shelf shelf) throws SQLException {
        Shelf existing = repo.findById(id);
        if (existing == null) throw new NotFoundException("Prateleira não encontrada com ID: " + id);
        Shelf updated = new Shelf(
            id,
            shelf.getCapacity(),
            shelf.getDailyRentPrice(),
            shelf.getProductId()
        );
        repo.update(updated);
        return updated;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) throws SQLException {
        Shelf existing = repo.findById(id);
        if (existing == null) throw new NotFoundException("Prateleira não encontrada com ID: " + id);
        repo.delete(id);
        return Response.noContent().build();
    }
}
