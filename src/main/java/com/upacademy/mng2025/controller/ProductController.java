package com.upacademy.mng2025.controller;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.upacademy.mng2025.entity.Product;
import com.upacademy.mng2025.repo.JDBCProductRepository;
import com.upacademy.mng2025.repo.ProductRepository;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    private final ProductRepository repo = new JDBCProductRepository();

    @OPTIONS
    public Response optionsRoot() { return Response.ok().build(); }

    @OPTIONS
    @Path("/{id}")
    public Response optionsWithId() { return Response.ok().build(); }

    @GET
    public List<Product> getAll() throws SQLException {
        return repo.findAll();
    }

    @GET
    @Path("/{id}")
    public Product getById(@PathParam("id") String id) throws SQLException {
        Product p = repo.findById(id);
        if (p == null) throw new NotFoundException("Produto não encontrado com ID: " + id);
        return p;
    }

    @POST
    public Product add(Product p) throws SQLException {
        if (p == null || p.getId() == null || p.getId().trim().isEmpty()) {
            throw new BadRequestException("ID do produto é obrigatório.");
        }
        repo.insert(p);
        return p;
    }

    @PUT
    @Path("/{id}")
    public Product update(@PathParam("id") String id, Product p) throws SQLException {
        Product existing = repo.findById(id);
        if (existing == null) throw new NotFoundException("Produto não encontrado com ID: " + id);
        p.setId(id);
        repo.update(p);
        return p;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) throws SQLException {
        Product existing = repo.findById(id);
        if (existing == null) throw new NotFoundException("Produto não encontrado com ID: " + id);
        repo.delete(id);
        return Response.noContent().build();
    }
}

